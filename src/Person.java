public class Person {
    
    // private so we don't accidently change the values...
    private int personID; // persons ID
    
    private int totalGroups; // number of groups in total for problem
    
    private int[] groups; // array of ints to hold group membership

    private int activeGroups; // how many groups a particular person is in

    private boolean feasible; // to mark non-feasible people

    // constructor with person ID and number og groups they are in
    // creates array of ints to hold group
    public Person(int persID, int numGps){
        this.personID = persID;
        this.totalGroups = numGps;
        this.groups = new int[this.totalGroups];
        this.feasible = true;
    }

    // when we create a new person object or come across the same person in a different group
    // we also want to keep track of the groups a particular person is in
    // we do groupID-1 as it gives us exactly the form we want for the binary representation
    public void addToList(int groupID){

       // int idx = this.totalGroups-groupID; // index for given group ID
        int idx = groupID-1; // index for given group ID
        int binRep = groupID/groupID; // to get current 1, 0 etc.

        groups[idx] = binRep; 
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

    public boolean getFeasible(){
        return this.feasible;
    }


    // person is not feasible for solution
    // so they are ignored...
    public void notFeasible(){
        this.feasible = false;
    }

    // debug method for verifying correct mapping of groups to a person
    public void personToString(){

        System.out.print("Person: " + this.personID + " | # Active: " + this.getActiveGroups() + " | Groups: " );
        for (int i = 0; i < this.totalGroups; i++){
            System.out.print(this.groups[i] + " ");
            
        }

        System.out.println();
    }


    // if we can't find match
    // union two top people sets together then keep iterating through list...
    public void createUnion(int[] otherList){ // we go through both at same time, since we know they are both same size

        for (int i = 0; i < this.totalGroups; i++){

            // if other person has 1 and current has 0, union and make total active groups + 1
            if (otherList[i] == 1 && this.groups[i] == 0){
                this.groups[i] = 1;
                this.activeGroups += 1; 
            } 
        }

    }


    // calcualates the union between current person and another
    // where score is how ideal they would be to union
    public int sumUnion(int[] otherList){
        int score = 0;

        for (int i = 0; i < this.totalGroups; i++){
            // gList is other person        this.groups is current persons list (assuming the max person)
            if (otherList[i] == 1 && this.groups[i] == 0){ // if the other person has a 1 current has a 0 this is ideal + 1 to score
                score = score + 1;
            } 
        }
        
        // higher the score the better...
        return score;
    }


    // if the current max has group numbers == to the total then solution found
    public boolean activeVsTotal(){
        if (this.activeGroups == this.totalGroups){
            return true;
        } 

        return false;
    }



    // if we come across a point where they are both 0, then we know it's not a solution
    // so we return false, and check next item...
    // not used anymore but left in because it's useful...
    public boolean checkUnion(int[] otherList){ // we go through both at same time, since we know they are both same size
        
        for (int i = 0; i < this.totalGroups; i++){

            if (otherList[i] == 0 && this.groups[i] == 0){
                return false;
            } 
        }


        return true;
    }


    // check if all 1s
    // if we come across a 0 then we know it's not complete
    public boolean checkAllT(){ 
        
        for (int i = 0; i < this.totalGroups; i++){

            if (this.groups[i] == 0){
                return false;
            } 
        }
        return true;
    }



}
