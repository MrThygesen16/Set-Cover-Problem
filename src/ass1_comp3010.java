import java.io.Console;
import java.util.ArrayList;

public class ass1_comp3010 {
    
    // initialise console object
    // for reading user input
    Console console;

    public ass1_comp3010(){
        console = System.console(); // make a Console object named console when new obj is created
    }

    
    public static void main(String[] args) throws Exception {

        ass1_comp3010 client = new ass1_comp3010(); // instance of current object called client
        client.start(); // puts us into starting point for program

    }

    // allows users to send messages to the console
    // str is instructions for the user (e.g. "Enter the number of groups: [user input]")
    //      note: str can be empty
    // msg is the value that the user will enter
    public String userInput(String str){

        String msg = console.readLine(str);
        return msg;

    }

    // process input for number of groups
    public int strToInt(String str){ 
        
        int num = -1; // if try fails we return -1 to indicate failure
    
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    
        return num;
    }


    // method that prompts user to give input -- converts it to an Int and returns int value
    public int inputNumberOfGroups(){
        String strGroupNum = userInput("Enter the number of groups from which you must find representatives: ");
        int numGroups = strToInt(strGroupNum);

        return numGroups;
    }


    // this is where we will call most of our functions
    public void start(){
        
        int numGroups = inputNumberOfGroups(); // user input for getting num groups
        
        ArrayList<ArrayList<Integer>> groupDataLists = strArrToArrList(numGroups); // takes string input and converts to an arrayList of arrayLists of integers

        ArrayList<Person> pList = personList(groupDataLists, numGroups); // arrayList of people objects that contains what groups they are in etc.


        // debugg stuff
        System.out.println();
        printPersonList(pList); // prints people to console


        // greedy
        pList = greedyAlgo(pList);
        System.out.println();
        result(pList);

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

        console.printf("Enter the list of members of each group (one group per line, each terminated by 0):\n");
    
        for (int i = 0; i < numGroups; i++){
            String tempStr = userInput("");
            returnList.add(groupLineToList(tempStr));
        }

        return returnList;
    }


    // given a string input this splits up the string into an array of strings
    // casts all elements of the string array to integers and adds it to an arrayList of integers
    // return that arrayList of integers
    public ArrayList<Integer> groupLineToList(String lineIn){ 

        String[] splitStr = lineIn.split("\\s+");
        ArrayList<Integer> returnList = new ArrayList<Integer>();

        for (int i = 0; i < splitStr.length-1; i ++){ // we do -1 to ommit the 0 at the end
            returnList.add(i, Integer.parseInt(splitStr[i]));
        }

        return returnList;
    }


    // given an arrayList of arraylists of integers (groups and their people)
    // this returns a list of people and their groups (an arrayList of People objects)
    // TODO: optimise methdd -- three for loops!
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


    // debug tool -- shows all people in a list to string...
    public void printPersonList(ArrayList<Person> pList){
        for (int i = 0; i < pList.size(); i++){
            pList.get(i).personToString();
        }
    }

   
    // used for greedy and brute force methods
    // returns the person with highest # of active groups
    public Person findMax(ArrayList<Person> pList){

        int idx = 0;
        
        for (int i = 0; i < pList.size(); i++){
            if (i == 0){
                idx = 0;
                
            } else {

                if (pList.get(i).getActiveGroups() > pList.get(idx).getActiveGroups()){ // compare current index value to i's value
                    idx = i;
                }

            }

        }

        // return the element of list with the highest number of active groups
        return pList.get(idx);

    }


    // for showing the result...
    // given a combination is found we use this to print what people have been selected
    public void result(ArrayList<Person> pList){

        System.out.println("The number of members selected and their ids are : ");
        System.out.println(pList.size());

        for (int i = 0; i < pList.size(); i++){
            if (i == pList.size()){
                System.out.print(pList.get(i).getPersonID() + "\n");
            } else {
                System.out.print(pList.get(i).getPersonID() + " ");
            }
           
        }

    }



    /* 
    
    
    
    GREEDY HEURISTIC STARTS HERE
    
    
    
    
    */

    // start point of greedy method
    public ArrayList<Person> greedyAlgo(ArrayList<Person> pList){

        ArrayList<Person> retList = new ArrayList<Person>(); // create list for storing selected person(s)
        Person p = findMax(pList); // find person that is active in most groups
        retList.add(0,p); // add to retList
        pList.remove(p); // remove selected person from them from pList (the list of unsorted people)

        // if this person alone is active in all groups -> we are done... (this is an edge case)
        if (p.activeVsTotal()){
            return retList;// if the largest person we happen to find is a member of all groups 
        }

        int maxIdx = 0; // max index
        int maxVal = 0; // max value

        // essentially we add up and compare each persons 'score' to the current max person
        // assuming we don't find a match straight away
        // the person with the best score is used for union in the next iteration
        // repeat until match --> call recursive method of this function
        for (int i = 0; i < pList.size(); i++){
            
            if (i == 0){
                maxVal = retList.get(0).sumUnion(pList.get(i).getGroupList());
            } 
        
        
            int curr = retList.get(0).sumUnion(pList.get(i).getGroupList()); // curr sum
            

            if (curr > maxVal){
                maxVal = curr;
                maxIdx = i;
            }
        }

        Person temp = pList.get(maxIdx);

        retList.add(temp);
        retList.get(0).createUnion(temp.getGroupList());
        pList.remove(temp);


        return chosenP(retList, pList);
    }



    // recursive function for greedy method
    // does the same thing as above method
    // however takes in the previous chosen list (mList) and previous person list to be searched through (pList)
    public ArrayList<Person> chosenP(ArrayList<Person> mList, ArrayList<Person> pList){
        
        // base case 
        // if mList has its num active groups equal to number of groups
        // solution found so we return immmediately
        if (mList.get(0).activeVsTotal()){
            return mList;
        }
        
      
        int maxIdx = 0;
        int maxVal = 0;

        for (int i = 0; i < pList.size(); i++){
            
            if (i == 0){ // first loop the max is initially the first item...
                maxVal = mList.get(0).sumUnion(pList.get(i).getGroupList());
            } 
        
        
            int curr = mList.get(0).sumUnion(pList.get(i).getGroupList()); // curr sum
            
            // if we come across a better score
            if (curr > maxVal){
                maxVal = curr; // new best score is current
                maxIdx = i; // new index is i
            }

        }

        Person temp = pList.get(maxIdx);

        mList.add(temp); // add and union selected p
        mList.get(0).createUnion(temp.getGroupList()); 

        pList.remove(temp); // remove selected p from pList

   
        return chosenP(mList, pList);
    }



    /* 
    
    
        GREEDY ENDS HERE
    
    
    
    */



    // brute force was my first attempt
    // greedy is an alteration
    // keeping both so I may compare...



    /* 
    
    
    
    brute force  STARTS HERE
    
    

    
        methods to use bruteForce: 

        personList = sortPersonList(personList);
        personList = selectedPersons(personList);
        result(personList);

    
    */


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
            Person p = findMax(pList);
            sortedList.add(sortedList.size(), p);

            if (p.getActiveGroups() == p.gettotalGroups()){

                result(sortedList); // if the largest person we happen to find is a member of all groups 
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
    public int[][] groupArrays(int numGroups){
        int[][] groupOfGroups = new int[numGroups][];

        console.printf("Enter the list of members of each group (one group per line, each terminated by 0):\n");

        for (int i = 0; i < numGroups; i++){
            String tempStr = userInput("");
            groupOfGroups[i] = groupToArray(tempStr);
        }

        return groupOfGroups;
    }

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
