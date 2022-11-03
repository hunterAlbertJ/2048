package com.games.TwentyFourtyEight.framework;

import java.util.HashMap;

class Board {
    //4x4 grid that starts with two "2" tiles and nothing else
    HashMap<Integer, Squares> board = new HashMap<>();
    /*
      * (position on board)     (value of square)
      *  Int                    Square
      *   0                         2
      *   1                         0
      *   2                         0
      *   3                         2
      *   ect.
     */
    /*_________________
     *| 0 | 1 | 2 | 3 |
     *| 4 | 5 | 6 | 7 |
     *| 8 | 9 | 10| 11|
     *| 12| 13| 14| 15|
     * ----------------
     */

    /*random number generator determine starting two positions
     *while loop triggered by two boolean flags
     *when two valid numbers that are not the same between 0 and 16 are found,
     */

}