#!/bin/bash

# for storing what test file
var=$1

# case match any input
case $var in

    *) 
        # tell user what test file they entered
        echo "Testing for: Test File ($var)" $'\n'
        # change to src folder
        cd src/
        # first compile java file
        javac ass1_comp3010.java
        # run java class with input test...
        java ass1_comp3010 < tests/$var
        ;;
    
esac