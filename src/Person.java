
public class Person {
    
    int personID; // persons ID
    
    int totalGroups; // number of groups in total for problem
    
    int[] groups; // array of ints to hold group membership

    int activeGroups; // how many groups a particular person is in

    // constructor with person ID and number og groups they are in
    // creates array of ints to hold group
    public Person(int persID, int numGps){
        this.personID = persID;
        this.totalGroups = numGps;
        this.groups = new int[this.totalGroups];
    }

    // when we create a new person object or come across the same person in a different group
    // we also want to keep track of the groups a particular person is in
    // we do totalGroups - groupID as it gives us exactly the form we want for the binary representation
    // e.g. group ID = 5, and totalGroups = 6 --> person would need to go to index of 1... which is 6 - 5.
    public void addToList(int groupID){
       groups[this.totalGroups-groupID] = groupID/groupID; 
       this.activeGroups = this.activeGroups + 1;
    }

    // Below are getters and setters for various attributes for a person object
    public int getPersonID(){
        return this.personID;
    }

    public int[] getGroupList(){
        return this.groups;
    }

    public int gettotalGroups(){
        return this.totalGroups;
    }

    public int getGroupAtIndex(int index){
        return this.groups[index];
    }

    public int getActiveGroups(){
       return this.activeGroups;
    }

    // debug method for verifying correct mapping of groups to a person
    public void personToString(){

        System.out.print("Person: " + this.personID + " Groups: ");
        for (int i = 0; i < this.totalGroups; i++){
            System.out.print(this.groups[i] + " ");
        }
        System.out.println();
    }

}
