#!/bin/bash

# for storing what test file
var=$1

# case match any input
case $var in


    "-all")
        # change to src folder
        cd src/

        # compile java file before running
        javac ass1_comp3010.java

        # for loop; f is file, tests/* is all the files in the folder 'src/tests/'
        for f in tests/*; 
            do echo $'\n=============================================';
            echo $'\t' "Testing file: $f"; 
            echo $'=============================================';
            cat $f;
            echo $'\n';
            java ass1_comp3010 < $f;     
            echo $'\n\n \t [---END OF TEST---] \n';
        done
        # the above is some formatting to make the output look nice

        # finish loop and exit the script
        ;;

    *) 
        # change to src folder
        cd src/

        # first compile java file
        javac ass1_comp3010.java

        # tell user what test file they entered
        echo $'\n============================================='
        echo $'\t' "Testing file: $var" 
        echo $'============================================='
        

        # show file contents...        
        cat tests/$var

        echo $'\n=============================================\n'

  
        # run java class with input test...
        java ass1_comp3010 < tests/$var
        ;;
    
esac