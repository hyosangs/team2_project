package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;

import java.util.LinkedList;
import java.util.Vector;

/**
 * A dark room game shows how to traverse a dark room
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class DarkRoomGame extends GameConfiguration {

    /**
     * Create a new dark room game.
     */
    public DarkRoomGame() {
        super.name = "Darkess";
    }

    /**
     * Confugure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException{

        Room room1 = new Room("You are in the first room. There seems to be a room to the North.", "Room1");
        // player would type 'go north'

        LinkedList<Item> items = new LinkedList<>();
        String classroomDescription = "You are in a classroom.";
        String classroomShortDescription = "Classroom";
        String classroomDarkDescription = "It is dark. Perhaps you can find a way to see...";
        String classroomDarkShortDescription = "Darkness";

        // A flashlight (or any luminous object) is needed or the player will lose!
        items.add(Item.getInstance("flashlight"));

        RoomDark classroom =
                new RoomDark(classroomDescription,
                        classroomShortDescription,
                        classroomDarkDescription,
                        classroomDarkShortDescription, true);
        room1.putItems(items);
        classroom.setDeathMessage("As walk in the dark room, you trip on a mysterious object. You fall toward the floor, and hit your head against a large rock.");

        room1.setAdjacentRoom(Action.ActionGoNorth, classroom);

        Player player = new Player(room1);
        Vector<String> goalItems = new Vector<>();
        goalItems.add("room1");
        goalItems.add("Classroom");

        game.setPlayer(player);
        game.addGoal(new GameExploreGoal(goalItems,player));

        game.setDescription("Explore a dark room");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;

    }
}
