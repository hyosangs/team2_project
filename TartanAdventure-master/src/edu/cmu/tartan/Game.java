package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;


import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The main class for game logic. Many if not all decisions about game play are made
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Game {

    /**
     * Reads input from the command line.
     */
    private Scanner scanner;

    /**
     * Attempt to interpret input more flexibly.
     */
    private PlayerInterpreter interpreter;
    /**
     * The player for the game
     */
    private Player player;

    /**
     * The name and description of the active game
     */
    private String gameName = "";
    private String gameDescription = "";
    /**
     * The set of goals for a game
     */
    private Vector<GameGoal> goals = new Vector<>();
    private Logger logger = Logger.getLogger(Game.class.getName());


    /**
     * Create and configure a new game.
     */
    public Game() {

        // Parse room from file
        this.scanner = new Scanner(System.in);

        // Configure the game, add the goals and exe
        configureGame();

        this.interpreter = new PlayerInterpreter();

        for (GameGoal g : goals) {
            this.player.addGoal(g);
        }
    }

    /**
     *  Display the game menu
     * @param menu The game menu
     */
    private void printMenu(Vector<GameConfiguration> menu) {

        StringBuilder sb = new StringBuilder("Choose a game from the options to below or type 'help' for help. \n");
        for (int i = 0; i < menu.size(); i++) {
            sb.append( (i+1) + ":  " + menu.elementAt(i).name + "\n");
        }
        logger.log(Level.INFO,sb.toString());

    }

    /**
     * Configure the game.
     */
    private void configureGame() {

        Vector<GameConfiguration> menu = new Vector<GameConfiguration>();

        // These are the currently supported games.
        menu.add(new CollectGame());
        menu.add(new PointsGame());
        menu.add(new ExploreGame());
        menu.add(new DarkRoomGame());
        menu.add(new LockRoomGame());
        menu.add(new RideElevatorGame());
        menu.add(new ObscuredRoomGame());
        menu.add(new DemoGame());

        int choice = 0;
        while(true) {
            printMenu(menu);
            System.out.print("> ");
            String input = this.scanner.nextLine();
            try {
                if (input.equalsIgnoreCase("help")) {
                    help();
                    continue;
                }
                choice = Integer.parseInt(input) - 1;
            }
            catch(Exception e) {
                logger.log("Invalid selection.");
                continue;
            }
            try {
                GameConfiguration gameConfig = menu.elementAt(choice);
                gameName = gameConfig.name;
                gameConfig.configure(this);
                break;
            }
            catch (InvalidGameException ige) {
                logger.log("Game improperly configured, please try again.");
            }
        }
        // Once the game has been configured, it is time to play!
        this.showIntro();
    }

    /**
     * Execute an action in the game. This method is where gameplay really occurs.
     * @param a The action to execute
     */
    private void executeAction(Action a) {

        switch(a.type()) {

            // Handle navigation
            case TYPE_DIRECTIONAL:
                player.move(a);
                break;

            // A direct item is an item that is required for an action. These
            // items can be picked up, eaten, pushed
            // destroyed, etc.

            case TYPE_HASDIRECTOBJECT:
                switch(a) {

                    case ActionPickUp: {
                        Item o = a.directObject();
                        Item container = null;
                        if(this.player.currentRoom().hasItem(o)) {
                            if(o instanceof Holdable) {
                                logger.log("Taken.");

                                this.player.currentRoom().remove(o);
                                this.player.pickup(o);
                                this.player.score(o.value());
                            }
                            else {
                                logger.log("You cannot pick up this item.");
                            }
                        }
                        else if((container = containerForItem(o)) != null) {

                            logger.log("Taken.");
                            ((Hostable)container).uninstall(o);
                            this.player.pickup(o);
                            Holdable h = (Holdable) o;
                            this.player.score( o.value());
                        }
                        else if(this.player.hasItem(o)) {
                            logger.log("You already have that item in your inventory.");
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionDestroy: {
                        Item item = a.directObject();
                        if (this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if (item instanceof Destroyable) {
                                logger.log("Smashed.");
                                ((Destroyable)item).destroy();
                                item.setDescription("broken " + item.toString());
                                item.setDetailDescription("broken " + item.detailDescription());
                                if (((Destroyable)item).disappears()) {
                                    this.player.drop(item);
                                    this.player.currentRoom().remove(item);
                                    // Get points!
                                    this.player.score(item.value());
                                }

                                if(item instanceof Hostable) {
                                    this.player.currentRoom().putItem(((Hostable)item).installedItem());
                                    ((Hostable)item).uninstall(((Hostable)item).installedItem());
                                }
                            }
                            else {
                                logger.log("You cannot break this item.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionInspect: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Inspectable) {
                                ((Inspectable)item).inspect();
                            }
                            else {
                                logger.log("You cannot inspect this item.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionDrop: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item)) {
                            if(item instanceof Holdable) {
                                logger.log("Dropped.");
                                this.player.drop(item);
                                logger.log("You Dropped '" +item.description() + "' costing you "
                                        + item.value() + " points.");
                                this.player.currentRoom().putItem(item);
                            }
                            else {
                                logger.log("You cannot drop this item.");
                            }
                        }
                        else {
                            logger.log("You don't have that item to drop.");
                        }
                        if(this.player.currentRoom() instanceof RoomRequiredItem) {
                            RoomRequiredItem r = (RoomRequiredItem)this.player.currentRoom();
                            r.playerDidDropRequiredItem();
                        }
                        break;
                    }
                    case ActionThrow: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item)) {
                            if(item instanceof Chuckable) {
                                logger.log("Thrown.");
                                ((Chuckable)item).chuck();
                                this.player.drop(item);
                                this.player.currentRoom().putItem(item);
                            }
                            else {
                                logger.log("You cannot throw this item.");
                            }
                        }
                        else {
                            logger.log("You don't have that item to throw.");
                        }
                        break;
                    }
                    case ActionShake: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Shakeable) {
                                ((Shakeable)item).shake();
                                if(((Shakeable)item).accident()) {
                                    this.player.terminate();
                                }
                            }
                            else {
                                logger.log("I don't know how to do that.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionEnable: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Startable) {
                                logger.log("Done.");
                                ((Startable)item).start();
                            }
                            else {
                                logger.log("I don't know how to do that.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;

                    }
                    case ActionPush: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Pushable) {

                                // Pushing the button is worth points
                                Pushable p = (Pushable) item;
                                p.push();
                                this.player.score(item.value());

                                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                                    ((RoomElevator)item.relatedRoom()).call(this.player.currentRoom());
                                }
                                else if(this.player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                                    ((RoomElevator)this.player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                                }
                            }
                            else {
                                logger.log("Nothing happens.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionDig: {
                        Item item = a.directObject();
                        if (this.player.currentRoom() instanceof RoomExcavatable && item.description().equals("Shovel")) {
                            RoomExcavatable curr = (RoomExcavatable) this.player.currentRoom();
                            curr.dig();
                        } else {
                            logger.log("You are not allowed to dig here");
                        }
                        break;
                    }
                    case ActionEat: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Edible) {
                                // eating something gives scores
                                Edible e = (Edible)item;
                                e.eat();
                                player.score(item.value());
                                // Once we eat it, then it's gone
                                this.player.currentRoom().remove(item);
                            }
                            else {
                                if(item instanceof Holdable) {
                                    logger.log("As you  shove the " + a.directObject() + " down your throat, you begin to choke.");
                                    this.player.terminate();
                                }
                                else {
                                    logger.log("That cannot be consumed.");
                                }
                            }
                        }
                        break;
                    }
                    case ActionOpen: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item) || this.player.currentRoom().hasItem(item)) {
                            if(item instanceof Openable) {
                                Openable o = ((Openable)item);
                                // if you can open the item , you score!
                                if (o.open()) {
                                    player.score(item.value());
                                    this.player.currentRoom().remove(item);
                                }
                            }
                            else {
                                logger.log("You cannot open this.");
                            }
                        }
                        else {
                            logger.log("I don't see that here.");
                        }
                        break;
                    }
                    case ActionExplode: {
                        Item dynamite = a.directObject();
                        if(this.player.currentRoom().hasItem(dynamite)) {
                            if(dynamite instanceof Explodable) {
                                if(this.player.currentRoom().isAdjacentToRoom(dynamite.relatedRoom())) {
                                    Explodable explode = (Explodable)dynamite;
                                    explode.explode();
                                    this.player.score(explode.value());
                                }
                                else {
                                    logger.log("There isn't anything to blow up here.");
                                }
                            }
                            else {
                                logger.log("That item is not an explosive.");
                            }
                        }
                        else {
                            logger.log("You do not have that item in your inventory.");
                        }
                        break;
                    }

                }
                break;
            // Indirect objects are secondary objects that may be used by direct objects, such as a key for a lock
            case TYPE_HASINDIRECTOBJECT:
                switch(a) {
                    case ActionPut: {
                        Item itemToPut = a.directObject();
                        Item itemToBePutInto = a.indirectObject();
                        if(!this.player.hasItem(itemToPut)) {
                            logger.log("You don't have that object in your inventory.");
                            break;
                        }
                        else if(itemToBePutInto == null) {
                            logger.log("You must supply an indirect object.");
                            break;
                        }
                        else if(!this.player.currentRoom().hasItem(itemToBePutInto)) {
                            logger.log("That object doesn't exist in this room.");
                            break;
                        }
                        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
                            logger.log("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
                        }
                        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
                            logger.log("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
                        }
                        else {
                            logger.log("Done.");
                            this.player.drop(itemToPut);
                            this.player.putItemInItem(itemToPut, itemToBePutInto);
                        }
                        break;
                    }
                    case ActionTake: {
                        Item contents = a.directObject();
                        Item container = a.indirectObject();
                        if(!this.player.currentRoom().hasItem(container)) {
                            logger.log("I don't see that here.");
                        }
                        else if(!(container instanceof Hostable)) {
                            logger.log("You can't have an item inside that.");
                        }
                        else {
                            if(((Hostable)container).installedItem() == contents) {
                                ((Hostable)container).uninstall(contents);
                                this.player.pickup(contents);
                                logger.log("Taken.");
                            }
                            else {
                                logger.log("That item is not inside this " + container);
                            }
                        }
                        break;
                    }
                }
                break;
            // Some actions do not require an object
            case TYPE_HASNOOBJECT: {
                switch(a) {
                    case ActionLook:
                        this.player.lookAround();
                        break;
                    case ActionClimb:
                        player.move(Action.ActionGoUp);
                        break;
                    case ActionJump:
                        player.move(Action.ActionGoDown);
                        break;
                    case ActionViewItems:
                        Vector<Item> items = this.player.getCollectedItems();
                        if (items.size() == 0) {
                            logger.log("You don't have any items.");
                        }
                        else {
                            for(Item item : this.player.getCollectedItems()) {
                                logger.log("You have a " + item.description() + ".");
                            }
                        }
                        break;
                    case ActionDie:
                        this.player.terminate();
                        break;
                    case ActionHelp:
                        help();
                        break;
                }
                break;
            }
            case TYPE_UNKNOWN: {
                switch(a) {
                    case ActionPass: {
                        // intentionally blank
                        break;
                    }
                    case ActionError: {
                        logger.log("I don't understand that.");
                        break;
                    }
                    case ActionUnknown: {
                        logger.log("I don't understand that.");
                        break;
                    }
                }
                break;
            }
            default:
                logger.log("I don't understand that");
                break;
        }
    }

    /**
     * Start the Game.
     * @throws NullPointerException
     */
    public void start() throws NullPointerException {

        // Orient the player
        this.player.lookAround();

        try {
            String input = null;
            while(true) {
                System.out.print("> ");

                input = this.scanner.nextLine();

                if (input.compareTo("quit") == 0) {
                    for (GameGoal g: goals) {
                        logger.log(g.getStatus());
                    }
                    break;
                }
                else if (input.compareTo("look") == 0) {
                    this.player.lookAround();
                }
                else if (input.compareTo("help") == 0) {
                    help();
                }
                else if (input.compareTo("status") == 0) {
                    status();
                }
                else {
                    executeAction(this.interpreter.interpretString(input));
                    // every time an action is executed the game state must be evaluated
                    if (evaluateGame()) {
                        winGame();
                        break;
                    }
                }
            }
        } catch(Exception e) {
            logger.log("I don't understand that \n\nException: \n" + e);
            e.printStackTrace();
            start();
        }

        logger.log("Game Over");
    }

    /**
     * Display the win game message
     */
    private void winGame() {

        logger.log("Congrats!");

        logger.log("You've won the '" + gameName + "' game!\n" );
        logger.log("- Final score: " + player.getScore());
        logger.log("- Final inventory: ");
        if (player.getCollectedItems().size() == 0) {
            logger.log("You don't have any items.");
        }
        else {
            for (Item i : player.getCollectedItems()) {
                logger.log(i.toString() + " ");
            }
        }
        logger.log("\n");
    }

    /**
     * Determine if all the game goals have been completed
     * @return
     */
    private Boolean evaluateGame() {
        Vector<GameGoal> goals = player.getGoals();

        for (Iterator<GameGoal> iterator = goals.iterator(); iterator.hasNext(); ) {
            GameGoal g = iterator.next();
            if (g.isAchieved()) {
                iterator.remove();
            }
        }
        return goals.isEmpty();
    }

    private void status() {
        logger.log("The current game is '" + gameName + "': " + gameDescription + "\n");
        logger.log("- There are " + goals.size() + " goals to achieve:");

        for (int i=0; i < goals.size(); i++) {
            logger.log("  * " + (i+1)+ ": "+ goals.elementAt(i).describe() + ", status: " + goals.elementAt(i).getStatus());
        }
        logger.log("\n");
        logger.log("- Current room:  " + player.currentRoom() + "\n");
        logger.log("- Items in current room: ");
        for (Item i : player.currentRoom().items) {
            logger.log("   * " + i.toString() + " ");
        }
        logger.log("\n");

        logger.log("- Current score: " + player.getScore());

        logger.log("- Current inventory: ");
        if (player.getCollectedItems().size() == 0) {
            logger.log("   You don't have any items.");
        } else {
            for (Item i : player.getCollectedItems()) {
                logger.log("   * " + i.toString() + " ");
            }
        }
        logger.log("\n");

        logger.log("- Rooms visited: ");
        Vector<Room> rooms = player.getRoomsVisited();
        if (rooms.size() == 0) {
            logger.log("You have not been to any rooms.");
        } else {
            for (Room r : rooms) {
                logger.log("  * " +r.description() + " ");
            }
        }
    }

    /**
     *  Getter for a player.
     *
     * @return the current player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Determine if item in room
     * @param item the item to check
     * @return not null if the time is hosted in the room
     */
    private Item containerForItem(Item item) {
        for(Item i : this.player.currentRoom().items) {
            if (i instanceof Hostable) {
                if(item == ((Hostable)i).installedItem() && item.isVisible()) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Display help menu
     */
    private void help() {

        // Credit to emacs Dunnet by Ron Schnell
        logger.log("Welcome to TartanAdventure RPG Help." +
                "Here is some useful information (read carefully because there are one\n" +
                "or more clues in here):\n");

        logger.log("- To view your current items: type \"inventory\"\n");
        logger.log("- You have a number of actions available:\n");

        StringBuilder directions = new StringBuilder("Direction: go [");
        StringBuilder dirobj = new StringBuilder("Manipulate object directly: [");
        StringBuilder indirobj = new StringBuilder("Manipulate objects indirectly, e.g. Put cpu in computer: [");
        StringBuilder misc = new StringBuilder("Misc. actions [");

        for( Action a : Action.values()) {
            if (a.type() == Type.TYPE_DIRECTIONAL) {
                for (String s : a.getAliases()) directions.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_HASDIRECTOBJECT) {
                for (String s : a.getAliases()) dirobj.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_HASINDIRECTOBJECT) {
                for (String s : a.getAliases()) indirobj.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_UNKNOWN) {
                for (String s : a.getAliases()) misc.append("'" + s + "' ");
            }
        }
        directions.append("]");
        dirobj.append("]");
        indirobj.append("]");
        misc.append("]");

        logger.log("- "+ directions.toString() + "\n");
        logger.log("- " + dirobj.toString() + "\n");
        logger.log("- " + indirobj.toString() + "\n");
        logger.log("- " +misc.toString() + "\n");
        logger.log("- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
        logger.log("- You can quit by typing \"quit\"\n");
        logger.log("- Good luck!\n");

    }

    /**
     * Add a goal to the game.
     * @param g the goal to add.
     */
    public void addGoal(GameGoal g) {
        goals.add(g);
    }

    /**
     * Set the player for the game.
     * @param player the player to add to the game.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Show the game introduction
     */
    public void showIntro() {

        logger.log("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
        logger.log("Game: " + gameDescription);
        logger.log("To get help type 'help' ... let's begin\n");
    }

    /**
     * Setter for game description
     * @param description the description
     */
    public void setDescription(String description) {
        this.gameDescription = description;
    }

    /**
     * Ensure that the game parameters are all set
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // TODO: This method is way too simple. A more thorough validation must be done!
        return (gameName!= null && gameDescription != null);
    }
}
