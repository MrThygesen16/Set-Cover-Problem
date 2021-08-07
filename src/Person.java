import java.util.ArrayList;

public class Person {
    
    int personID; // persons ID
    
    int countGroups; // num groups they are in
    
    ArrayList<Integer> groups = new ArrayList<Integer>(); // binary representation of groups they are in...

    
    int totalGroups;

    // default constructor
    public Person(int pid, int tg){

        this.personID = pid;
        this.totalGroups = tg;

        for (int i = 0; i < tg; i ++){
            groups.add(0);
        }

    }

    public void addItem(int gid){

        int groupAlloc = totalGroups - gid;

        groups.set(groupAlloc, 1);

    }

    public int getPersonID(){

        return this.personID;

    }

}
