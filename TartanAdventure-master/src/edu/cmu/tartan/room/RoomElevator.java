package edu.cmu.tartan.room;

import edu.cmu.tartan.action.Action;

import java.util.ArrayList;

/**
 * The class for an elevator
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomElevator extends Room {

    // current floor
    private int currentFloor;

    private ArrayList<String> descriptions;

    // rooms for each floor
    private ArrayList<Room> floors;

    // floors that the user cannot get to
    private ArrayList<Integer> restrictedFloors;

    private Action directionOfFloors;
    // should be a single direction, that points to every floor.

    /**
     *  Create a new elevator
     * @param description
     * @param shortDescription
     */
	public RoomElevator(String description, String shortDescription) {
		super(description, shortDescription);
		this.restrictedFloors = new ArrayList<Integer>();
	}

    /**
     * Set the elevator floors
     * @param descriptions the floor descriptions
     * @param floors the
     * @param directionOfFloors
     * @param initial
     */
	public void setFloors(ArrayList<String> descriptions, ArrayList<Room> floors, Action directionOfFloors, int initial) {
		this.descriptions = descriptions;
		this.floors = floors;
		this.directionOfFloors = directionOfFloors;

		setFloor(initial);
	}
	public void setRestrictedFloors(ArrayList<Integer> restrictedFloors) {
		this.restrictedFloors = restrictedFloors;
	}

    /**
     * call to a specific floor. Will set adjacent room
     * @param index target floor
     */
	public void call(int index) {
		if(this.restrictedFloors.contains(index)) {
			System.out.println("You push the button, but nothing happens. Perhaps this floor is off-limits.");
			return;
		}
		else if(index == currentFloor) {
			System.out.println("The elevator is already on this floor -- the doors are open.");
			return;
		}
		for(int i=0; i < 3; i++) {
			System.out.println("...");
			try {
				Thread.sleep(1000);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("Ding");
		System.out.println("The doors open");
		setFloor(index);
	}

    /**
     * set the current floor and adjacent room after elevaor ride
     * @param index te current floor
     */
	protected void setFloor(int index) {
		this.currentFloor = index;
		Room adjacentFloor = this.floors.get(index);
		setAdjacentRoom(this.directionOfFloors, adjacentFloor);
		this.description = this.descriptions.get(index);
	}

    /**
     * go to a room
     * @param floor
     */
	public void call(Room floor) { // call to the floor the player is on
		int index = this.floors.indexOf(floor);
		call(index);
	}
}
