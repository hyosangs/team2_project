package edu.cmu.tartan.goal;

/**
 * Interface that all goals must implement
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface GameGoal {
    /**
     * Evaluate goal progress
     * @return true if achieved; false otherwise.
     */
    public Boolean isAchieved();

    /**
     * Describe the goal
     * @return goal description
     */
    public String describe();

    /**
     * Fetch the current status
     * @return the status
     */
    public String getStatus();

}
