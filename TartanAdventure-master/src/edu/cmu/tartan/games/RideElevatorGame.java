package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;

import java.util.ArrayList;

/**
 * Demonstrate how to use an elevator.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RideElevatorGame extends GameConfiguration {

    public RideElevatorGame() {
        super.name = "Elevator";
    }

    /**
     * Configure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException{

        String elevatorDescription = "Elevator";

        RoomElevator elevator = new RoomElevator(elevatorDescription, elevatorDescription);
        elevator.putItem(Item.getInstance("1"));
        elevator.putItem(Item.getInstance("2"));
        elevator.putItem(Item.getInstance("3"));
        elevator.putItem(Item.getInstance("4"));

        // configure the floors and buttons needed to reach them

        Room floor1 = new Room("floor1", "floor1");
        Item b1 = Item.getInstance("button");
        b1.setRelatedRoom(elevator);
        floor1.putItem(b1);

        Room floor2 = new Room("floor2", "floor2");
        Item b2 = Item.getInstance("button");
        b2.setRelatedRoom(elevator);
        floor2.putItem(b2);

        Room floor3 = new Room("floor3", "floor3");
        Item b3 = Item.getInstance("button");
        b3.setRelatedRoom(elevator);
        floor3.putItem(b3);

        // restricted floors cannot be reached
        Room floor4 = new Room("floor4", "floor4");
        Item b4 = Item.getInstance("button");
        b4.setRelatedRoom(elevator);
        floor4.putItem(b4);

        ArrayList<Room> list = new ArrayList<Room>();
        list.add(floor1);
        list.add(floor2);
        list.add(floor3);
        list.add(floor4);

        ArrayList<String> descriptions = new ArrayList<String>();
        descriptions.add("Elevator -- floor 1.");
        descriptions.add("Elevator -- floor 2");
        descriptions.add("Elevator -- floor 3");
        descriptions.add("Elevator -- floor 4");


        elevator.setFloors(descriptions, list, Action.ActionGoEast, 1);
        ArrayList<Integer> restrictedFloors = new ArrayList<Integer>();
        restrictedFloors.add(2);
        elevator.setRestrictedFloors(restrictedFloors);

        Player player = new Player(elevator);
        game.setPlayer(player);
        game.addGoal(new GamePointsGoal(10, player));

        game.setDescription("The objective of this game is to demo an elevator");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");
        return;
    }
}