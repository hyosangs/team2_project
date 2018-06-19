package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;

/**
 * A game goal based on scoring points.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class GamePointsGoal implements GameGoal {
    // The score needed to reach this goal
    private Integer winningScore = 0;

    // The player working to achieve this goal
    private Player player;

    public GamePointsGoal(Integer g, Player p) {
        this.winningScore = g;
        this.player = p;
    }

    /**
     * Describe this goal
     * @return
     */
    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Points Goal: You must score " + winningScore + " points" );
        return sb.toString();
    }

    /**
     * Fetch the current status of this goal.
     * @return the current status for display.
     */
    public String getStatus() {
        return "You scored " + player.getScore() + " out of " + winningScore + " points.";
    }

    /**
     * Check if the goal is achieved
     * @return true if the goal is achieved; false otherwise.
     */
    @Override
    public Boolean isAchieved() {
        if (player.getScore() >= winningScore) {
            return true;
        }
        return false;
    }
}
