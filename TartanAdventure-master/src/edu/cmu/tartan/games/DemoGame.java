package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.DemoGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemCoffee;
import edu.cmu.tartan.item.ItemLock;
import edu.cmu.tartan.room.*;

import java.util.Vector;

/**
 * Example game to explore a series of rooms. This is the configuraion discussed in the project description.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class DemoGame extends GameConfiguration {

    public DemoGame() {
        super.name = "Demo";
    }

    /**
     * Configure the game
     * @param game the Game object that will manage exectuion
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException {

        Room room1 = new Room("You are in the first room. There seems to be a dark room to the East.",
                "Room1");

        // put the item in room1 for use in room 2
        room1.putItem(Item.getInstance("torch"));
        RoomDark room2 = new RoomDark("You are in a dark room. You can go South to West to the beginning and you can go South",
                "room2", "You cannot see", "blind!");


        Room room3 = new Room("You are in room3. There is a locked room to the West and a room to the East.",
                "room3");

        Item key = Item.getInstance("key");
        // Install the lock and key to open room 4
        Room room4 = new RoomLockable("You are in the locked room. There is a fridge here", "locked",
                true, key);
        // Create the new lock item (note that key was created above)
        Item lock = Item.getInstance("lock");
        ((ItemLock) lock).install(key);
        // This lock "locks" room4
        lock.setRelatedRoom(room4);
        // Install the items
        room3.putItem(lock);
        room3.putItem(key);

        Item food = Item.getInstance("food");
        RoomRequiredItem room6 = new RoomRequiredItem("You are in the room that required food", "Required",
                "food", "Warning you need food", food);

        room3.putItem(food);
        Item fridge = Item.getInstance("fridge");
        RoomObscured room5 = new RoomObscured("Obscured Room 5" ,"Room5", fridge);
        room5.setObscured(true);
        room5.setUnobscureMessage("You've revelealed a hidden room to the West!");
        room5.setObscureMessage("This room is hidden");
        fridge.setRelatedRoom(room5);

        room4.putItem(fridge);

        // Connect the rooms.
        // From room 1 a player can go east to
        // room2, which is a dark room (i.e. they need a Luminous object).
        room1.setAdjacentRoom(Action.ActionGoEast, room2);
        // From room2 a player can go south to room 3
        room2.setAdjacentRoom(Action.ActionGoSouth, room3);
        // from room3 a player can go east to room6 if they have the required item
        room3.setAdjacentRoom(Action.ActionGoEast, room6);
        // from room3 a player can go west to room4 if they can unlock the door
        room3.setAdjacentRoom(Action.ActionGoWest, room4);
        // from room4 a player can go west to room5 if they move the obscuring item
        room4.setAdjacentRoom(Action.ActionGoWest, room5);

        // Set the initial room
        Player player = new Player(room1);
        game.setPlayer(player);
        game.addGoal(new DemoGoal());

        game.setDescription("Demo game rooms.");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;
    }
}

