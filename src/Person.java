
public class Person {
    
    int personID; // persons ID
    
    int totalGroups; // num groups they are in
    
    int[] groups;

    int activeGroups;

    public Person(int persID, int numGps){
        this.personID = persID;
        this.totalGroups = numGps;
        this.groups = new int[this.totalGroups];
    }

    public void addToList(int groupID){
       groups[this.totalGroups-groupID] = groupID/groupID;
       this.activeGroups = this.activeGroups + 1;
    }

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

    public void personToString(){

        System.out.print("Person: " + this.personID + " Groups: ");
        for (int i = 0; i < this.totalGroups; i++){
            System.out.print(this.groups[i] + " ");
        }
        System.out.println();
    }

}
