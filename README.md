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

Person 2 groups = `{1, 2, 4}`, and person 9 groups = `{1, 3, 5}`.

If we combine these together we get `{1,1,2,3,4,5}`, all groups are represented.

However this is a relatively small example that we can figure out just by looking at it. In this we are to assume we can have any number of groups, and any number of persons in a group. Once `g` and `n` start increasing the ability to do this by hand quickly becomes to slow. 

### Key Consideration

Rather than looking at the persons in a group, it would be useful to look at the groups a person is in. 

My solution uses an array to represent group membership for each person. Using the example above, if we were to map the groups to each person we would get something like this:

```                 
Person: 1 Groups: 0 0 1 0 1
Person: 2 Groups: 0 1 0 1 1
Person: 3 Groups: 0 1 1 0 1
Person: 4 Groups: 1 0 0 1 1
Person: 5 Groups: 0 1 1 0 1     
Person: 6 Groups: 1 0 0 1 1
Person: 7 Groups: 0 1 1 0 1
Person: 8 Groups: 1 0 0 1 1
Person: 9 Groups: 1 0 1 0 1
```
We are using a binary array to represent group membership. 

Another way to view this is:

```
Person: n Groups: [group 5] ][group 4] [group 3] [group 2] [group 1] 
        9             1          0         1         0         1         
```

Using person `9` as an example we can see that a `1` represents group memebership, and a `0` represents not being part of that group. 
