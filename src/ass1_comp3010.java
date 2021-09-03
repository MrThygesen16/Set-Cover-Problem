import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedHashMap;
// essential imports

public class ass1_comp3010 {
    
    // init scanner object
    // for reading user input
    Scanner in;

    // constructor definition for class
    public ass1_comp3010(){
        in  = new Scanner(System.in); // make a Console object named console when new obj is created
    }

    // MAIN
    public static void main(String[] args) throws Exception {

        ass1_comp3010 client = new ass1_comp3010(); // instance of current object called client
        client.start(); // puts us into starting point for program

    }


    // process input for number of groups
    public int strToInt(String str){ 
        
        // if try fails we return -1 to indicate failure
        int num = -1; 
    
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    
        return num;
    }


    // method that prompts user to give input -- converts it to an Int and returns int value
    public int inputNumberOfGroups(){
        System.out.print("Enter the number of groups from which you must find representatives: ");
        String strGroupNum = in.nextLine();
        //System.out.print(strGroupNum);
        int numGroups = strToInt(strGroupNum);

        return numGroups;
    }


    // this is where we will call most of our functions
    // essentially like main
    public void start(){
        
        // user input for getting num groups
        int numGroups = inputNumberOfGroups(); 
        
        // takes string input and converts to an arrayList of arrayLists of integers
        ArrayList<ArrayList<Integer>> groupDataLists = strArrToArrList(numGroups); 

        // arrayList of people objects that contains what groups they are in etc.
        ArrayList<Person> pList = personListHash(groupDataLists, numGroups);


        // resultList is the list we return at the end
        ArrayList<Person> resultList = new ArrayList<Person>();

        // person from pList who is in most groups
        Person maxP = findMaxActiveGroups(pList);

        // remove person in most groups from pList
        pList.remove(maxP);

        // add person in most groups to resultList
        resultList.add(maxP);

        // greedy algo, input result list and pList
        resultList = solver(resultList, pList);
        System.out.println();

        // method shows the selected groups from resultList
        // program ends once this is called.
        displayResult(resultList);

    }

     


    // divide up functions by standard arrays operations and arraylist operations for ease of reading...
    // quite confusing otherwise 
    // these methods do the same exact same things but return different data-structures etc.
    // hence are grouped seperately 
    // done this way as I was unsure if ArrayLists were acceptable or not to use...


    /* 


     ----------------------BEGIN ARRAYLIST METHODS----------------------

        
    
    */


    // given the number of groups by the user
    // we return an ArrayList of ArrayLists that contain Integers
    // we populate this list of lists by calling groupLineToList <-- returns an arrayList of integers
    //
    //                 function might not be used, but here is how to call it: ArrayList<ArrayList<Integer>> groupDataLists = strArrToArrList(numGroups);

    ArrayList<ArrayList<Integer>> strArrToArrList(int numGroups){

        ArrayList<ArrayList<Integer>> returnList = new ArrayList<ArrayList<Integer>>();

        System.out.println("\nEnter the list of members of each group (one group per line, each terminated by 0): ");
    
        for (int i = 0; i < numGroups; i++){
            String tempStr = in.nextLine();
            //System.out.println(tempStr);
            returnList.add(groupLineToList(tempStr));
        }

        return returnList;
    }


    // given a string input this splits up the string into an array of strings
    // casts all elements of the string array to integers and adds it to an arrayList of integers
    // return that arrayList of integers
    public ArrayList<Integer> groupLineToList(String lineIn){ 

        // create an array of strings by splitting by whitespace 
        String[] splitStr = lineIn.split("\\s+");
        ArrayList<Integer> returnList = new ArrayList<Integer>();

        // length-1 to ommit the end of line character 0 
        for (int i = 0; i < splitStr.length-1; i ++){ 
            returnList.add(i, Integer.parseInt(splitStr[i]));
        }

        return returnList;
    }


 
    // given an arrayList of arraylists of integers (groups and their people)
    // this returns a list of people and their groups (an arrayList of People objects)
    public ArrayList<Person> personListHash(ArrayList<ArrayList<Integer>> arrArrList, int numGrps){

        // we use a LinkedHashMap since it preserves ordering... (hashmpa does not preserve ordering) 
        // this hash-map stores the persons ID as a key, and the person object as a value... 
        LinkedHashMap<Integer, Person> contList = new LinkedHashMap<Integer, Person>();

        int group = 1; // we parse this in when we create a new person object

        for (ArrayList<Integer> list : arrArrList){ // iterating through each group
           
            for (Integer num : list){ // iterating through each person in a group
                
                // checking if person ID exists in contList
                // since we are using a hashmap -- contains takes O(1)!
                if (!contList.containsKey(num)){ 

                    // if they don't exist we add them to the contList...
                    // by creating a person object using num as their ID, numGrps for number of groups
                    // p.add(group) adds to the persons array of the active groups they are in...
                    Person p = new Person(num, numGrps);
                    p.addToList(group);
                    contList.put(num,p);

                } else { 
                    
                    // if we come across a person that already exists we log which group we saw them in
                    // use the num (personID) to retrieve person and add the group where we saw them to their active group list
                    // get(id) in a hashmap only takes O(1)
                    contList.get(num).addToList(group);

                }
            }

            // each time iterate group # since we are moving on to the next group when we reach this point
            group = group + 1; 
        }

        // the list we will return at the end of this method
        ArrayList<Person> pList = new ArrayList<Person>(contList.values()); 

        // end of method; return pList
        return pList; 
    }


    // debug tool -- shows all people in a list to string...
    public void printPersonList(ArrayList<Person> pList){
        for (int i = 0; i < pList.size(); i++){
            pList.get(i).personToString();
        }
    }

   
    // used for greedy and brute force methods
    // returns the person with highest # of active groups
    public Person findMaxActiveGroups(ArrayList<Person> pList){

        int idx = 0;
        
        for (int i = 1; i < pList.size(); i++){
      
            // if the current i active groups is larger than the idx that's currently the largest
            // then the new largest index becomes i
            if (pList.get(i).getActiveGroups() > pList.get(idx).getActiveGroups()){
                idx = i;
            }
        }

        // return the element of list with the highest number of active groups
        return pList.get(idx);
    }


    
    // for showing the result...
    // given a combination is found we use this to print what people have been selected
    public void displayResult(ArrayList<Person> pList){

        System.out.println("The number of members selected and their ids are: ");
        System.out.println(pList.size());

        for (int i = 0; i < pList.size(); i++){
            
            System.out.print(pList.get(i).getPersonID() + " ");
           
        }

    }



    /* 
    
    
    
   SOLVER/GREEDY HEURISTIC STARTS HERE
    
    
    
    
    */

  
    // mList is the list of chosen people
    // pList is the list of all people
    public ArrayList<Person> solver(ArrayList<Person> mList, ArrayList<Person> pList){
        
        while (!pList.isEmpty()){
            int maxIdx = 0;
            int maxVal = 0;

            // base case 
            // if mList has its num active groups equal to number of groups
            // solution found so we return immmediately
            if (mList.get(0).activeVsTotal()){
                return mList;
            }

            for (int i = 0; i < pList.size(); i++){
            
                // first loop the max is initially the first item...
                if (i == 0){ 
                    maxVal = mList.get(0).sumUnion(pList.get(i).getGroupList());
                } 
                
                // cunion between the first item in mList and current person (i) in pList
                // sumUnion uses a for loop to find the binary OR of the two people
                int curr = mList.get(0).sumUnion(pList.get(i).getGroupList()); 
                
                // if we come across a better score
                if (curr > maxVal){
                    maxVal = curr; // new best score is current
                    maxIdx = i; // new index is i
                }
    
            }
    
            // get person at the max index
            Person temp = pList.get(maxIdx);
    
            // add and union selected person (temp)
            mList.add(temp); 
            mList.get(0).createUnion(temp.getGroupList()); 
    
            // remove selected p from pList
            pList.remove(temp); 
    
            // if made it this far; recursive call
        
        }

        return mList;

    }


    /* 
    
    
        GREEDY ENDS HERE
    
    
    
    */



    // brute force was my first attempt
    // greedy is an alteration
    // keeping both so I may compare...


    /* 
    
    
        ACTUAL PROGRAM ENDS HERE; REST IS UNUSED/SAVED CODE...
    
    
    */


    /* 
    
    
    
        UN-USED CODE GOES HEre

        this is brute-force and other attempts at solving the problem
        left here to preserve the code
        but also so I may revisit this and see how my solution progressed...
    

    
        methods to use bruteForce: 

        personList = sortPersonList(personList);
        personList = selectedPersons(personList);
        result(personList);

    
    */

 
    // recursive function for greedy method
    // mList is the list of chosen people
    // pList is the list of all people
    public ArrayList<Person> greedyAlgo(ArrayList<Person> mList, ArrayList<Person> pList){
        
        // base case 
        // if mList has its num active groups equal to number of groups
        // solution found so we return immmediately
        if (mList.get(0).activeVsTotal()){
            return mList;
        }
        
      
        int maxIdx = 0;
        int maxVal = 0;

        for (int i = 0; i < pList.size(); i++){
            
            // first loop the max is initially the first item...
            if (i == 0){ 
                maxVal = mList.get(0).sumUnion(pList.get(i).getGroupList());
            } 
            
            // cunion between the first item in mList and current person (i) in pList
            // sumUnion uses a for loop to find the binary OR of the two people
            int curr = mList.get(0).sumUnion(pList.get(i).getGroupList()); 
            
            // if we come across a better score
            if (curr > maxVal){
                maxVal = curr; // new best score is current
                maxIdx = i; // new index is i
            }

        }

        // get person at the max index
        Person temp = pList.get(maxIdx);

        // add and union selected person (temp)
        mList.add(temp); 
        mList.get(0).createUnion(temp.getGroupList()); 

        // remove selected p from pList
        pList.remove(temp); 

        // if made it this far; recursive call
        return greedyAlgo(mList, pList);
    }


   // given an arrayList of arraylists of integers (groups and their people)
    // this returns a list of people and their groups (an arrayList of People objects)
    public ArrayList<Person> personList(ArrayList<ArrayList<Integer>> arrArrList, int numGrps){

        ArrayList<Integer> containList = new ArrayList<Integer>(); // list for keeping track of what people have been seen
        ArrayList<Person> pList = new ArrayList<Person>(); // the list we will return at the end of this method

        int group = 1; // we parse this in when we create a new person object

        for (ArrayList<Integer> list : arrArrList){ // iterating through each group
           
            for (Integer num : list){ // iterating through each person in a group
                
                if (!containList.contains(num)){ // check if person's ID exists in containList (integer arrayList)
                    containList.add(num); // if they don't exist we add them to this arrayList and create a new person object and add it to the pList

                    Person p = new Person(num, numGrps);
                    p.addToList(group);
                    pList.add(p);

                } else { // if we come across a person that already exists we log which group we saw them in
                    for (int i = 0; i < pList.size(); i++){
                        if (pList.get(i).getPersonID() == num){ 
                            pList.get(i).addToList(group);
                        }
                    }
                }
            }

            group = group + 1; // each time iterate group # since we are moving on to the next group when we reach this point
        }

        return pList; 
    }



    // method for sorting the arrayList of people...
    // place holder to get things moving along
    public ArrayList<Person> sortPersonList(ArrayList<Person> pList){
        
        int size = pList.size(); // extract size of list and use this...
        ArrayList<Person> sortedList = new ArrayList<Person>();

        for (int i = 0; i < size; i++){
            
            // for each loop we find the lowest in the set
            // and remove from the input array
            // and add to the sorted list
            // do this for each item in list...
            // not an efficient way...
            Person p = findMaxActiveGroups(pList);
            sortedList.add(sortedList.size(), p);

            if (p.getActiveGroups() == p.gettotalGroups()){

                displayResult(sortedList); // if the largest person we happen to find is a member of all groups 
                System.exit(1); // -- return them and exit the program
               
            }

            pList.remove(p);

        }

        // return back the sorted list
        return sortedList;
    }



    public ArrayList<Person> selectedPersons(ArrayList<Person> pList){

        // what we return
        ArrayList<Person> selectedPeople = new ArrayList<Person>();

        // get first person from list and add it to this...

        selectedPeople.add(pList.get(0));

        pList.remove(0); // top most person is the person in most groups
                        // they are added to the selected person list
                        // so we don't compare against them

        for (int i = 0; i < pList.size(); i++){
            
            boolean isMatch = selectedPeople.get(0).checkUnion(pList.get(i).getGroupList());

            if (isMatch){
                selectedPeople.add(pList.get(i));
                return selectedPeople;
            }

        }

        selectedPeople.add(pList.get(0));
        selectedPeople.get(0).createUnion(pList.get(0).getGroupList());
        pList.remove(0);

        return recursPeople(selectedPeople, pList); // if this method doesn't return anything we call a recursive function...

    }

    
    // recursive method for finding the right combination of people
    // sList is selected person list
    // pList is the list of people to search through
    public ArrayList<Person> recursPeople(ArrayList<Person> sList, ArrayList<Person> pList){

        for (int i = 0; i < pList.size(); i++){
            
            boolean isMatch = sList.get(0).checkUnion(pList.get(i).getGroupList());

            if (isMatch){
                sList.add(pList.get(i));
                return sList;
            }

        }

        sList.add(pList.get(0));
        sList.get(0).createUnion(pList.get(0).getGroupList());
        pList.remove(0);

        return recursPeople(sList, pList);

    }

    /* 
    
    
    
    brute force  ENDS HERE
    
    
    
    
    */




    /* 
    
        ----------------------END ARRAYLIST METHODS----------------------
    
       
    */



    /* 
    
     =======================BEGIN STANDARD ARRAY METHODS=======================
    
    */

    // input to a standard array instead...
    public int[] groupToArray(String group){

        String[] splitStr = group.split("\\s+");
        int groupSize = splitStr.length-1;

        int[] groupArr = new int[groupSize];

        for (int i = 0; i < groupSize; i ++){ // we do -1 to ommit the 0 at the end
            groupArr[i] = Integer.parseInt(splitStr[i]);
        }

        return groupArr;
    }

    // construct an array of arrays        
    // if not used: here is how to call this method...  int[][] groupList = groupArrays(numGroups);
    // public int[][] groupArrays(int numGroups){
    //     int[][] groupOfGroups = new int[numGroups][];

    //     System.out.println("Enter the list of members of each group (one group per line, each terminated by 0):\n");

    //     for (int i = 0; i < numGroups; i++){
    //         String tempStr = userInput("");
    //         groupOfGroups[i] = groupToArray(tempStr);
    //     }

    //     return groupOfGroups;
    // }

    /* 
    
        =======================END STANDARD ARRAY METHODS=======================


        Some code here with how to use the standard array methods...
        
        int[][] groupList = groupArrays(numGroups);

        System.out.println();


        for (int i =0; i < groupList.length; i++){

            int group = i + 1;

            System.out.print("Group " + group + ": ");
            for (int j = 0; j < groupList[i].length; j++){
                System.out.print(groupList[i][j] + " ");
            }
            System.out.println();
        }
    
    */
    


}
