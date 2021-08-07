import java.io.Console;

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

    // return an array of Strings that contain the members of a particular group
    public String[] inputGroupData(int groupSize){

        String[] groupData = new String[groupSize];

        console.printf("Enter the list of members of each group (one group per line, each terminated by 0):\n");

        for (int i = 0; i < groupSize; i++){
            groupData[i] = userInput("");
        }

        return groupData;
    }

    // method that prompts user to give input -- converts it to an Int and returns int value
    public int inputNumberOfGroups(){
        String tempNum = userInput("Enter the number of groups from which you must find representatives: ");
        int numGroups = strToInt(tempNum);

        return numGroups;
    }

    public void start(){
        
        int numGroups = inputNumberOfGroups();
        String[] groupData = inputGroupData(numGroups);

        for (int i = 0; i < numGroups; i++){
            console.printf(groupData[i] + " ");
        }

    }


}
