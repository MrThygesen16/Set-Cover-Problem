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


    
    public ArrayList<Person> personList(ArrayList<ArrayList<Integer>> arrArrList, int numGrps){

        ArrayList<Integer> retList = new ArrayList<Integer>();
        ArrayList<Person> pList = new ArrayList<Person>();

        int group = 1;

        for (ArrayList<Integer> list : arrArrList){
           
            for (Integer num : list){
                
                if (!retList.contains(num)){
                    retList.add(num);

                    Person p = new Person(num, numGrps);
                    p.addToList(group);
                    pList.add(p);

                } else {
                    for (int i = 0; i < pList.size(); i++){
                        if (pList.get(i).getPersonID() == num){
                            pList.get(i).addToList(group);
                        }
                    }
                }
            }

            group = group + 1;
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
        
        int numGroups = inputNumberOfGroups();

        ArrayList<ArrayList<Integer>> groupDataLists = strArrToArrList(numGroups);

        ArrayList<Person> personList = personList(groupDataLists, numGroups);

        System.out.println();

        for (int i = 0; i < personList.size(); i++){
            personList.get(i).personToString();
        } 
        


     
        

    }


}
