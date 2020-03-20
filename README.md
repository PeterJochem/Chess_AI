# Description
This is a chess game, with a full interactive GUI. You can play chess against an AI, or watch two AIs play each other in simulation.

# Implementation
This program plays chess by modeling chess as a Markov decesion process. Each piece has a very specefic set of moves it can make given the state of the board. I expand this tree of all possible moves and then used a heurestic scoring function to approximate how likely/desirable each state is. This allows me to compute the state with the highest expected value. Once I have the most desirable state at a leaf in the tree, I simply need to backrack to the immediate move that leads to it. 

# Results
![Chess AI Interface](images/chess.gif)


# How to Run My Code

