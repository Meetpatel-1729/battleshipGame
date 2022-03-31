/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import battleship.BattleShip;
import battleship.CellState;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Meet Patel
 */
public class OptimizedBot {

    private final int gameSize; // Stores the gamesize

    private final BattleShip battleShip; // Battleship object

    private int numberofShipsShunk; // Stores the number of ship shunk

    int x = 1; // Starting poing x-axis (It gives better result than 00 as it is not too far from the boarder and in close to center point)

    int y = 6; // Starting point y-axis

    int i = 0; // Counter to keep count of the shot position in horizontal direction

    boolean parity = true; // parity to skip one space

    // CellState (enum whihc has following predefined constants
    // Hit : Hit the ship
    // Miss : Miss the Shot
    // Empty : Not visited
    // Arraylist which stores the cellstate of all 10 x 10 grid and updated when hit or miss
    ArrayList<ArrayList<CellState>> gridState = new ArrayList<>();

    /**
     * Creates a object of optimized bot and set all the properties based on
     * battleship object. It sets 10 x 10 grid to empty state which is the
     * default state when game starts.
     *
     * @param b battleship object which is used to set properties of optimized
     * bot.
     */
    public OptimizedBot(BattleShip b) {
        battleShip = b; // Assign the battleship object

        gameSize = b.BOARDSIZE; // Assign the board size

        numberofShipsShunk = b.numberOfShipsSunk(); // Assign the number of ship shunk

        // Loop through 10 arraylist and create empty arraylist of size 10 and set all the values to empty
        // This way it keeps track of all 100 locations
        for (int i = 0; i < gameSize; i++) {
            gridState.add(new ArrayList<>()); // Creates a new arraylist
            for (int j = 0; j < gameSize; j++) {
                gridState.get(i).add(CellState.Empty); // Set all the cells to empty
            }
        }
    }

    /**
     * This method set the CellState to miss or hit and if ship is Sunk then it
     * increment the number of ship sunk count.
     *
     * @param x x-axis point.
     * @param y y-axis point.
     * @return true if hit the ship else false
     */
    public boolean setCellState(int x, int y) {
        boolean hit; // true if it hits the target otherwise false

        // If the cell is already visited then it returns false
        if (!gridState.get(x).get(y).equals(CellState.Empty)) {
            hit = false;
        } else {
            hit = battleShip.shoot(new Point(x, y)); // Hit the (x,y) point and set the hit to true if hits the ship otherwise false

            if (!hit) {
                // If it not hit then it set the cell to Miss state
                gridState.get(x).set(y, CellState.Miss);
            } else {
                // If it hits the target then it set the cell to hit
                gridState.get(x).set(y, CellState.Hit);

                if (numberofShipsShunk != battleShip.numberOfShipsSunk()) {
                    // If the ship is shunk then it increment the count of total number of ship shunk
                    numberofShipsShunk++;
                }
            }
        }
        return hit; //Return true if hit else false
    }

    /**
     * Set all the cells to empty state when the game is over.
     */
    public void setCellToEmpty() {

        // Loop thorugh all the arraylist and set all the cells to empty state.
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                gridState.get(i).set(j, CellState.Empty); // Set cell to empty
            }
        }
    }

    /**
     * Fire at specified location
     */
    public void fireShot() {
        i++; // Increment the count

        // If the cell is hit then it shot in all 4 possible directions
        if (gridState.get(x).get(y).equals(CellState.Hit)) {
            attackShot(x, y); // Call the attack shot method
        } // If the cell is not visited earlier then first it hit the cell and if it founds the hit then it shot in all 4 possible directions
        else if (gridState.get(x).get(y).equals(CellState.Empty)) {
            boolean hit = setCellState(x, y); // set the hit to true if it hit the ship else false
            if (hit) {
                if (numberofShipsShunk == battleShip.numberOfShipsSunk()) {
                    // If the ship is not shunk then it hits in all 4 possible direction
                    attackShot(x, y);
                }
            }
        }

        if (i != 5) {
            y += 2; // Skip one cell state at a time

            // if y equals 10 then it set y to 0
            if (y == 10) {
                y = 0;
            }
        } else {
            i = 0;
            x++; // Incremnt the x - axis point

            // if x equals 10 then it set x to 0
            if (x == 10) {
                x = 0;
            }

            parity = !parity; // Set the parity to true if its false or if its false then it set to true

            // If parity is true then it starts the y from 0
            if (parity) {
                y = 0;
            } // If parity is false then it starts the y from 1
            else {
                y = 1;
            }
        }
    }

    /**
     * This method shots in 4 possible directions.
     *
     * @param x x-axis point.
     * @param y y-axis point.
     */
    public void attackShot(int x, int y) {
        int i; // Stores the x - axis

        int j; // Stores the y - axis

        boolean hit; // True if it hits the ship otherwise false

        int shipShunkNumber = numberofShipsShunk; // Keep track of number of ship sunk

        // Shot in the Right direction. If hit hit the target then it will continue hit the target until it does not hit 
        // Until it reaches the right-end
        if (y != 9) {
            i = x; // x - axis

            j = y + 1; // Increment the y - axis

            hit = setCellState(i, j); // Set the flag based on it hit the ship

            // Loop in the Right direction until it misses the target or sunk the ship
            while (hit && j != 9 && shipShunkNumber == numberofShipsShunk) {
                j = j + 1; // Increment the y - axis

                hit = setCellState(i, j); // Set the flag based on it hit the ship
            }
        }

        // Shot in the Left direction. If hit hit the target then it will continue hit the target until it does not hit 
        // Until it reaches the left-end
        if (y != 0) {
            i = x; //x - axis

            j = y - 1; //Decrement the y - axis

            hit = setCellState(i, j); // Set the flag based on it hit the ship

            // Loop in the Left direction until it misses the target or sunk the ship
            while (hit && j != 0 && shipShunkNumber == numberofShipsShunk) {
                j = j - 1; //Decrement the y - axis

                hit = setCellState(i, j); // Set the flag based on it hit the ship
            }
        }

        // Shot in the Downward direction. If hit hit the target then it will continue hit the target until it does not hit 
        // Until it reaches the bottom-end
        if (x != 9) {
            i = x + 1; //Increment the x - axis

            j = y; //y - axis

            hit = setCellState(i, j); // Set the flag based on it hit the ship

            // Loop in the Downward direction until it misses the target or sunk the ship
            while (hit && i != 9 && shipShunkNumber == numberofShipsShunk) {
                i = i + 1; //Increment the x - axis

                hit = setCellState(i, j); // Set the flag based on it hit the ship
            }
        }
        // Shot in the Upward direction. If hit hit the target then it will continue hit the target until it does not hit
        // Until it reaches the top-end
        if (x != 0) {
            i = x - 1; //Decrement the x - axis

            j = y; //y - axis

            hit = setCellState(i, j);  // Set the flag based on it hit the ship

            // Loop in the Upward irection until it misses the target or sunk the ship
            if (hit && i != 0 && shipShunkNumber == numberofShipsShunk) {
                i = i - 1; //Decrement the x - axis

                hit = setCellState(i, j); // Set the flag based on it hit the ship
            }
        }
    }
}
