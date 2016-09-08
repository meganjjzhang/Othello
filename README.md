# Othello
The course project for iOS development
##Design:
###Basic function:

There are two pages in the game. The first one is the launching page, the second one is the page for playing.

The first page includes these function:
1.	Start a new game
2.	Information about candidate’s name and university number

The second page includes these functions:
1.	Initialize the game board
2.	Show the current turn
3.	Show the current number of white pieces and black pieces
4.	Add a new valid pieces
5.	Flip the pieces according to the rules
6.	Determine which color wins the game
7.	Start a new game by click a button

###Additional function:
1.	Set the components’ width and height according to the screen size
2.	A button in the second page to switch whether shows the hint for player
3.	Play the background music
4.	A button in the second page to switch whether playing the background music
5.	Play the sound effect
6.	A button in the second page to switch whether playing the sound effect
7.	A button in the second page for player to retract the previous move(s)
8.	A button in the second page to go back to the first page
9.	A third page which introduce the game rules

##Implementation:
When the user pressed the “Start Game” in the first page the second page, which is the game main frame, will be loaded.
The second page will be loaded as following steps:
1.	Change the size of components automatically according to the size of screen.
2.	Initial chess board with 4 initial pieces, 2 black and 2 white pieces without push into stack
3.	Initial the amount of chess
4.	Set the first player as black
5.	Give every Imageview a click listener
6.	In the click listener will processing the new chess and the following color flips
7.	Give click listener for the “Home” button, “New Game” button, “Retract” button, “Hint” button, “Background Music” button and “Sound Effect” button

Whenever add a new chess, the program will judge whether it is a valid move. If it is a valid move, the current chess board’s status will be push into a stack. And add a new chess on board, and change all the following chesses according the rules. And determine which pieces of board is a valid move in the next move immediately. After that, the program will update the number of black chess and white chess. Then, it will judge whether the game come to an end and which color wins.
