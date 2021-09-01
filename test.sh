#!/bin/bash

# for storing what test file
var=$1

# case match any input
case $var in

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