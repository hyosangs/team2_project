package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemCoffee;
import edu.cmu.tartan.room.Room;

import java.util.Vector;

/**
 * Example game to explore a series of rooms.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ExploreGame extends GameConfiguration {

    public ExploreGame() {
        super.name = "Explorer";
    }

    /**
     * Configure the game
     * @param game the Game object that will manage exectuion
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException {

        Room room1 = new Room("You are in the first room. There seems to be a room to the North.", "Room1");
        Room room2 = new Room("You are in the second room. You can go South to return to the beginning and you can go East to get to Room 3.", "Room2");
        Room room3 = new Room("You are in the third room. You can go West to return to the Room 2.", "Room3");

        // player would type 'go north'
        room1.setAdjacentRoom(Action.ActionGoNorth, room2);
        room2.setAdjacentRoom(Action.ActionGoEast, room3);

        // player would type 'drink coffee'
        ItemCoffee coffee = (ItemCoffee) Item.getInstance("coffee");
        room2.putItem(coffee);

        // These are the rooms the must be explored
        Vector<String> goalItems = new Vector<>();
        goalItems.add("room1");
        goalItems.add("room2");
        goalItems.add("room3");

        Player player = new Player(room1);
        game.setPlayer(player);
        game.addGoal(new GameExploreGoal(goalItems,  game.getPlayer()));

        game.setDescription("Explore different connected rooms.");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;
    }
}

