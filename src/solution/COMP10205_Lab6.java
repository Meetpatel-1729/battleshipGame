package solution;

import battleship.BattleShip;

/**
 * Starting code for Comp10205 - Lab#6
 *
 * @author mark.yendt
 * 
 * @modified by Meet Patel
 */
public class COMP10205_Lab6 {

    static final int NUMBEROFGAMES = 10000; // Number of Game

    public static void startingSolution() {
        int totalShots = 0; // Total shots shot
        
        System.out.println(BattleShip.version()); // Print the battleship version
        
        for (int game = 0; game < NUMBEROFGAMES; game++) {

            BattleShip battleShip = new BattleShip(); // Object of battleship
            
            OptimizedBot optimizedBot = new OptimizedBot(battleShip); // Object of optimized bot

            while (!battleShip.allSunk()) {
                optimizedBot.fireShot(); // Fire the shot until it shunk all the ships/boats
            }
            
            int gameShots = battleShip.totalShotsTaken(); // total shots shot per game
            
            totalShots += gameShots; // Total shots of all games played
        }
        System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double) totalShots / NUMBEROFGAMES);
    }

    public static void main(String[] args) {
        startingSolution(); // Call the starting solution method
    }
}
