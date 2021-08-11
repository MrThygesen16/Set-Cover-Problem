# Set Cover Problem

For `g` groups of `n` people, we want to find the least number of people in the shortest time possible so that all groups are represented.

For example given the following 5 groups of people:
```
G1: 1 2 3 4 5 6 7 8 9 0
G2: 2 4 6 8 0
G3: 1 3 5 7 9 0
G4: 2 3 5 7 0
G5: 4 6 8 9 0
```
G# represents the group number, and each person is represented by an ID (integer >= 1), e.g. `9` etc.

From this example we wish to find a combination of people so that all 5 groups are represented/accounted for.

One possible solution to the above example are persons `2` and `9`.

Person 2 groups = {1, 2, 4}, and person 9 groups = {1, 3, 5}.

If we combine these together we get {1,1,2,3,4,5}, all groups are represented...
