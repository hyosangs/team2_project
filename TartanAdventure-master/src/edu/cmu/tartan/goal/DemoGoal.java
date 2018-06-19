package edu.cmu.tartan.goal;

public class DemoGoal implements GameGoal {
    // the list of places required to visit

    @Override
    public String describe() {
        return "Demo goal";
    }

    /**
     * Fetch status of this goal
     * @return a displayable string for progress towards this goal
     */
    public String getStatus() {
        return "Demo status";
    }

    /**
     * Evaluate whether this goal is achieved.
     *
     * @return true if the goal is achieved; false otherwise
     */
    @Override
    public Boolean isAchieved() {
        return false;
    }
}