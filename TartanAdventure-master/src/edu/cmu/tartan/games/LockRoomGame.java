package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemLock;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Demonstrate a locked room game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class LockRoomGame extends GameConfiguration {

    public LockRoomGame() {
        super.name = "Lock Demo";
    }

    /**
     * Configure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException {

        Room mid1 = new Room("There is a fork", "Fork");
        Room mid2 = new Room("Ferocious bear", "bear");
        Item key = Item.getInstance("key");
        Room end = new RoomLockable("You are inside of a building", "interior",
                true, key);

        end.setAdjacentRoom(Action.ActionGoNortheast, mid1);

        LinkedList<Item> startItems = new LinkedList<Item>();
        Item lock = Item.getInstance("lock");

        // Install the lock and key to unlock the locked room. You must 'open' or 'unlock' the lock
        // to go through the door into the locked room
        ((ItemLock) lock).install(key);
        lock.setRelatedRoom(end);
        mid2.putItem(lock);

        startItems.add(key);
        startItems.add(lock);

        Room start = new Room("There is a tree, with a building to the West. There is a lock on the door.", "Tree" );
        start.setAdjacentRoom(Action.ActionGoNorth, mid1);
        start.setAdjacentRoom(Action.ActionGoEast, mid2);
        start.setAdjacentRoom(Action.ActionGoWest, end);
        start.putItems(startItems);

        Vector<String> goals = new Vector<>();
        goals.add("Fork");
        goals.add("bear");
        goals.add("interior");

        Player player = new Player(start);

        game.setPlayer(player);
        game.addGoal(new GameExploreGoal(goals, game.getPlayer()));

        game.setDescription("The objective of this game is to unlock a room.");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;
    }
}
