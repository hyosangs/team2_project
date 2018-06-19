package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomObscured;

import java.util.Vector;

/**
 * A game demonstrating a hidden (obscured) room.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ObscuredRoomGame extends GameConfiguration {

    public ObscuredRoomGame() {
        super.name = "Obscured";
    }

    /**
     * Configure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException{

        String passageDescription = "You are in a dark corridor dimly lit by torches.";
		String passageShortDescription = "Dark Corridor.";

        Room room1 = new Room("You are in the first room. There is a fridge in here.",
                "Room1");

        // You must push the fridge to expose the hidden passage
        Item fridge = Item.getInstance("fridge");
		RoomObscured passage = new RoomObscured(passageDescription ,passageShortDescription, fridge);
		passage.setObscured(true);
        passage.setUnobscureMessage("You've revelealed a hidden passage to the east!");
        passage.setObscureMessage("This room is hidden");
        passage.putItem(Item.getInstance("torch"));

        room1.setAdjacentRoom(Action.ActionGoEast,passage);
		room1.putItem(fridge);
		fridge.setRelatedRoom(passage);

        Player player = new Player(room1);
        Vector<String> goalItems = new Vector<>();
        goalItems.add("passage");
        goalItems.add("room1");

        game.setPlayer(player);
        game.addGoal(new GameExploreGoal(goalItems,player));

        game.setDescription("The objective of this game is to earn explore an obscured room");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

    }
}
