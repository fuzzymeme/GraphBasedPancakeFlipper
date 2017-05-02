# GraphBasedPancakeFlipper
Experiments to solve the Pancake Flipping problem to find optimal solutions - even though memory cost scales factorially.

So I'm returning to the Pancake flipping problem, partly because is gave me an excuse to write some Graph code and partly because the output of this code will help me improve the Sequence based Pancake flipping code. With this code ideally producing the optimal flips to produce a solution it should be easier to find the sub-optimal flips in my other solution. You may ask, if this produces optimal solutions then why bother with the other code? Well this code has to enumerate all solutions to find the best solution, and that requires the memory to scale factorially. With stacks of length 10 the program requires nearly 4Gb of RAM. Length 11 would require 44Gb length 12 -> 523Gb, 13->6.8Tb etc. As you can see this not scalable. However finding the optimal solutions at lower lengths will guide the tweaks (or major re-writes) of the Sequence based Solution. 

As this is experimental code it's pretty gnarly, all I need are the results and I can see if it's working or not so no tests are required. 

I got a overly focused on getting it to run all stacks of length 10. Length 9 ran fine, and quickly, but length 10 just wasn't happening. It would start off OK, but get slower and slower. Switching one the agenda list to a LinkedList helped, but then it was hitting a wall when it got to around 61% complete. Upping the heap size to 4Gb broke that barrier and it ran pretty quickly. My current laptop doesn't have 44Gb of memory, so I'm going to leave it there maxing out a stack length 10.

Running it on various lengths produced some pretty interesting results. Below is a comparison of the two outputs

```
Length   Permutations      Total flips (Sequence Based)   Av.       Total flips (Graph Based)     Av.
2            2                      1                    0.50               1                     0.5
3            6                      6                    1.50               9                     1.50
4            24                     61                   2.52               60                    2.50
5            120                    466                  3.80               425                   3.54
6            720                    3,629                5.04               3,295                 4.56
7            5040                   31,678               6.28               28,280                5.61
8            40320                  304,169              7.54               267,711               6.64
9            362,880                3,194,911            8.80               2,780,947             7.66                                                             
```
As you can see the number of flips in total is less for the Graph based system, and that's reflected in the averages. Taking a look at the differences between the two I can see which permutations are sub-optimal and the flips in the optimal solutions. If I print out the distribution of flips required across all permutations it shows that there is a number that sub-optimal. 

Below is the distribution for stack length 5

```
Num flips         Optimal         Sequence based
0                   1                 1
1                   4                 4
2                   12                12
3                   35                31
4                   48                42
5                   20                20
6                                     6
7                                     2
8                                     1
9                                     1
```

To explain... The optimal solution across all permutations of length 5 how 35 solutions that had 3 flips, whereas the Sequence based code had 31 solutions with 3 flips. 

It would seem that the optimal solution always has less than N flips. I'd need to check this out at other lengths. 

Taking a look at the different strategies of the two algorithms for a randomly chosen permutation it looks like the optimal flips actually breaks a sequence in preference for enlarging another sequence but ultimately wins by having the resulting sequence in position for easy flips to the final configuration - whereas the sequence based code has sequences that are in the wrong configuration for each other, requiring multiple flips to rearrange and then flip to the correct orientation once the single sequence was formed. 

Looking at other differences makes me think that it not just a case of extra flip rules but of choosing the most appropriate flip. In some case the sequence based code could merge the top sequence with two others rather than just one. It currently chooses the first sequence mergeable sequence it finds. It would be better if it found all mergeable sequences (and hopefully) used a rule to chose the better merge. 

Anyway I have to wrap this up just now (2nd May 2017). There's still scope for improving the rules, use a tree to explore both options (should be OK as max branching of 2 usually 1 and max depth of N), and having code find optimal rules and rule ordering. 
My main was to be able to find flip solutions that have less than 2N steps. I've done that. Even with lengths of 4000 it still finds solutions quickly and less than 8000 flips. Also the solutions should be found in less than O(n!) time and memory and that is also true for the Sequence Based Flipper. I know there are some improvements that can be made but I really to move onto other code. 














