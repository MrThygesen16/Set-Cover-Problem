import java.io.Console;
import java.util.ArrayList;

public class Client {
    
    // initialise console object
    // for reading user input
    Console console;

    public Client(){
        console = System.console(); // make a Console object named console when class Client is created
    }

    
    // allows users to send messages to the console
    // str is instructions for the user (e.g. "Enter the number of groups: [user input]")
    //      note: str can be empty
    // msg is the value that the user will enter
    public String userInput(String str){

        String msg = console.readLine(str);
        return msg;

    }
 
    public static void main(String[] args) throws Exception {

        Client client = new Client(); // create new client object
        client.start(); // puts us into starting point for program

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


    
    // given the number of groups by the user
    // we return an ArrayList of ArrayLists that contain Integers
    // we populate this list of lists by calling groupLineToList <-- returns an arrayList of integers
    /* 
        ArrayList<ArrayList<Integer>> groupDataLists = strArrToArrList(numGroups);
        
        function might not be used, but here is how to call it.
    
    */

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

        ArrayList<Person> personList = personList(groupDataLists, numGroups); // arrayList of people objects that contains what groups they are in etc.

        System.out.println();

        for (int i = 0; i < personList.size(); i++){
            personList.get(i).personToString();
        } 

        
    }


}
