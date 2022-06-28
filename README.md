# Minesweeper Alpha

## Project Description

This is a non-recursive, non-GUI version of the game called **Minesweeper**. The organization of the 
classes in this project demonstrate elementary aspects of game programming.

If you are not familiar with Minesweeper as a game, then please consult the
[Wikipedia entry](https://en.wikipedia.org/wiki/Minesweeper_(video_game))
for the game.

### Minesweeper Overview

In this implementation of Minesweeper, the player is initially presented with a grid of
undifferentiated squares. Either some randomly-selected squares or seed-selected
squares are designated to contain mines. The size of the grid and the number of mines are 
set in advance by a seed file that the user specifies as a command-line argument to the
program. 

The game is played in rounds. During each round, the player is presented with
the grid, the number of rounds completed so far, as well as a prompt. The player
has the option to do 5 different things, each of which is briefly listed below:

 1. Reveal a square on the grid.
 2. Mark a square as potentially containing a mine.
 3. Mark a square as definitely containing a mine.
 4. Lift the fog of war (cheat code).
 5. Display help information.
 6. Quit the game.

When the player reveals a square of the grid, different things can happen:

* If the revealed square contains a mine, then the player loses the game.

* If the revealed square does not contain a mine, then a digit is instead displayed
  in the square, indicating how many adjacent squares contain mines. Typically,
  there are 8 squares adjacent to any given square, unless the square lies on an
  edge or corner of the grid. The player uses this information to deduce the contents
  of other squares, and may perform any of the first three options in the list presented above.

* When the player marks a square as potentially containing a mine, a `?` is displayed
  in the square. This provides the user with a way to note those places that they
  believe may contain a mine but are not sure enough to mark as definitely containing
  a mine.

* When the player marks a square as definitely containing a mine, a flag, denoted
  by the character `F`, is displayed in the square.

To simplify the game mechanics, **the player may mark, guess, or reveal any square in the grid,
even squares that have already been marked or revealed.** 

The game is won when **all** of the following conditions are met:

* All squares containing a mine are marked as _definitely_ containing a mine; and

* All squares not containing a mine are revealed.

At the end of the game, the player is presented with a score. 


#### Revealing a Square

In order to reveal a square, the `reveal` or `r` command
is used. The syntax format for this command is as follows: `-["reveal"/"r"]-[(int)]-[(int)]-`.
The second and third tokens indicate the row and column indices, respectively,
of the square to be revealed.

Suppose that we secretly know that there is a mine in squares (1,1) and (1,3). 
Now suppose that the player wants to reveal square (1, 2). Here is an example of what that might look like.

```

 Rounds Completed: 0

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha: r 1 2

 Rounds Completed: 1

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   | 2 |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha:
```

After the player correctly entered the command `r 1 2`, the state of
the game updates (e.g., number of rounds completed, the grid, etc.), and the
next round happens. Since there was no mine in square (1,2), the player does not
lose the game. Also, since the total number of mines in the 8 cells directly 
adjacent to square (1,2) is 2, the number 2 is now placed in that cell.


#### Mark Command

In order to mark a square as definitely containing a mine, the
`mark` or `m` command is used. The syntax format for this
command is as follows: `-["mark"/"m"]-[(int)]-[(int)]-`.
The second and third tokens indicate the row and column indices, respectively,
of the square to be revealed.

Suppose that the player wants to mark square (1, 2). Here is an example of what that might look like.

```

 Rounds Completed: 0

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha: m 1 2

 Rounds Completed: 1

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   | F |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha:
```

After the player correctly entered the command `m 1 2`, the state of
the game updates (e.g., number of rounds completed, the grid, etc.), and the
next round happens.


#### Guess Command

In order to mark a square as potentially containing a mine, the
`guess` or `g` command is used. The syntax format for this
command is as follows: `-["guess"/"g"]-[(int)]-[(int)]-`.
The second and third tokens indicate the row and column indices, respectively,
of the square to be revealed.

Suppose that the player wants to guess square (1, 2). Here is an example of what that might look like.

```

 Rounds Completed: 0

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha: g 1 2

 Rounds Completed: 1

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   | ? |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha:
```

After the player correctly entered the command `g 1 2`, the state of
the game updates (e.g., number of rounds completed, the grid, etc.), and the
next round happens.


#### Help Command

In order to show the help menu, the `help` or `h` command
is used. The syntax format for this command is as follows: `-["help"/"h"]-`.

Suppose that the player wants to display the help menu. Here is an example of what that might look like.

```

 Rounds Completed: 0

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha: h

Commands Available...
 - Reveal: r/reveal row col
 -   Mark: m/mark   row col
 -  Guess: g/guess  row col
 -   Help: h/help
 -   Quit: q/quit

 Rounds Completed: 1

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha:
```

After the player correctly entered the command `h`, the state of
the game updates (e.g., number of rounds completed, the grid, etc.), the
help menu is displayed, and the next round happens.


#### Quit Command

In order to quit the game, the `quit` or `q` command
is used. The syntax format for this command is as follows: `-["quit"/"q"]-`.

Suppose that the player wants to quit the game. Here is an example of what that might look like.

```

 Rounds Completed: 0

 0 |   |   |   |   |   |   |   |   |   |   |
 1 |   |   |   |   |   |   |   |   |   |   |
 2 |   |   |   |   |   |   |   |   |   |   |
 3 |   |   |   |   |   |   |   |   |   |   |
 4 |   |   |   |   |   |   |   |   |   |   |
 5 |   |   |   |   |   |   |   |   |   |   |
 6 |   |   |   |   |   |   |   |   |   |   |
 7 |   |   |   |   |   |   |   |   |   |   |
 8 |   |   |   |   |   |   |   |   |   |   |
 9 |   |   |   |   |   |   |   |   |   |   |
     0   1   2   3   4   5   6   7   8   9

minesweeper-alpha: q

Quitting the game...
Bye!

```

After the player correctly entered the command `q`, the game
displayed the goodbye message and the program exited gracefully.


### Seed Files

This game can also be setup using seed files. Seed files have the following
format:

 * The first two tokens are two integers (separated by white-space) indicating the
   number of `rows` and `cols`, respectively, for the size
   of the mine board.

 * The third token is an integer indicating the number of mines to be
   placed on the mine board.

 * Subsequent pairs of tokens are integers (separated by white space)
   indicating the location of each mine.

