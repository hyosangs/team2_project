package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.*;

import java.util.HashMap;
import java.util.Vector;

/**
 * The player for a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Player {

    /**
     * The player's score.
     */
    private int score=0;

    /**
     * The list of rooms that this player has visited.
     */
    private Vector<Room> roomsVisited = new Vector<>();

    /**
     * The points that this player can possibly score.
     */
    private int possiblePoints=0;

    /**
     * The inventory of items this player has.
     */
    private Vector<Item> items = new Vector<>();

    /**
     * This player's goals
     */
    private Vector<GameGoal> goals = new Vector<>();

    /**
     * The current room this player is in.
     */
    private Room currentRoom = null;

    /**
     * Player constructor
     *
     * @param currentRoom the current room
     */
    public Player(Room currentRoom) {
        this(currentRoom, new Vector<Item>());
    }

    /**
     * Player constructor for player with items
     * @param currentRoom the current room
     * @param items the player's items
     */
    public Player(Room currentRoom, Vector<Item> items) {
        this.items = items;
        this.score = 0;
        this.currentRoom = currentRoom;
        this.currentRoom.player = this;
    }

    /**
     * Drop an item
     * @param item the item to drop.
     * @return The dropped item or null if the item cannot be found.
     */
    public Item drop(Item item) {
        if(this.items.remove(item)) {
            this.score -= item.value();
            return item;
        }
        else {
            return null;
        }
    }

    /**
     * Get the player's score.
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Drop an item in the player's possession.
     *
     * @param item the item to drop. The dropped item remains in the room.
     * @return true if the item is dropped, false otherwise.
     */
    public boolean dropItem(Item item) {

        Item dropped = drop(item);
        if (dropped == null) {
            System.out.println("You don't have this item to drop");
            return false;
        }
        this.currentRoom.putItem(dropped);
        return true;
    }

    /**
     * Pickup an item.
     * @param item the item to pickup.
     * @return true
     */
    public boolean pickup(Item item){

        this.grabItem(item);
        return true;
    }

    /**
     * Actually add the item to the player's inventory.
     * @param item
     */
    public void grabItem(Item item) {
        this.items.add(item);
    }

    public boolean hasItem(Item item) {
        if(item == null) return false;
        return this.items.contains(item);
    }

    /**
     * Return true if the player has a luminous object
     * @return true if they have a luminous object, false otherwise.
     */
    public boolean hasLuminousItem() {
        for (Item item : this.items) {
            if (item instanceof Luminous) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current set of items.
     * @return the items.
     */
    public Vector<Item> getCollectedItems() {
        return this.items;
    }

    public void putItemInItem(Item direct, Item indirect) {
        ((Hostable)indirect).install(direct);
        if(indirect instanceof ItemMagicBox && direct instanceof Valuable) {
            score((Valuable)direct);
        }
    }

    /**
     * Move the player to a new room.
     * @param nextRoom the new room.
     */
    public void move(Room nextRoom) {

        nextRoom.setPlayer(this);
        if(this.currentRoom != null && nextRoom.compareTo(this.currentRoom) != 0) {
            Action directionOfTravel = this.currentRoom.getDirectionForRoom(nextRoom);
            HashMap<Action, String> messages = this.currentRoom.transitionMessages();
            String message = messages.get(directionOfTravel);
            int delay = this.currentRoom.transitionDelay();
            if(message != null) {
                if(delay != 0) {
                    for(int i=0; i < 3; i++) {
                        System.out.println("...");
                        try{
                            Thread.sleep(delay);
                        }
                        catch(Exception e1) {
                            // pass
                        }
                    }
                }
                System.out.println(message);
            }
        }
        if(nextRoom instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)nextRoom;
            if(r.diesOnEntry()) {
                System.out.println(r.loseMessage());
                this.terminate();
            }
        }

        this.currentRoom = nextRoom;
        saveRoom(currentRoom);
        System.out.println(this.currentRoom.description());
    }

    /**
     * Save the newly visited room to the list of rooms visited.
     * @param room The room to save.
     */
    private void saveRoom(Room room) {

        roomsVisited.add(room);
    }

    /**
     * Get the list of rooms visited.
     * @return The list of visited rooms.
     */
    public Vector<Room> getRoomsVisited() {
        return roomsVisited;
    }

    /**
     * Move version two based on an action
     * @param action the action associated with the move.
     */
    public void move(Action action) {

        if(this.currentRoom instanceof RoomRequiredItem) {
            RoomRequiredItem room = (RoomRequiredItem)this.currentRoom;

            if(room.shouldLoseForAction(action)) {
                System.out.println(room.loseMessage());
                this.terminate();
            }
        }
        else if(this.currentRoom instanceof RoomDark) {
            RoomDark room = (RoomDark)this.currentRoom;
            if(room.isDark() && !this.hasLuminousItem()) {
                System.out.println(room.deathMessage());
                this.terminate();
            }
        }

        if(this.currentRoom.canMoveToRoomInDirection(action)) {
            Room nextRoom = this.currentRoom.getRoomForDirection(action);
            // test if requires key
            if(nextRoom instanceof RoomLockable) {
                RoomLockable lockedRoom = (RoomLockable)nextRoom;
                if(lockedRoom.isLocked()) {
                    if(lockedRoom.causesDeath()) {
                        System.out.println(lockedRoom.deathMessage());
                        this.terminate();
                    }
                    System.out.println("This door is locked.");
                    return;
                }
            }
            else if(nextRoom instanceof RoomObscured) {
                RoomObscured obscuredRoom = (RoomObscured)nextRoom;
                if(obscuredRoom.isObscured()) {
                    System.out.println("You can't move that way.");
                    return;
                }
            }

            move(nextRoom);
        }
        else {
            System.out.println("You can't move that way.");
        }
    }

    /**
     * Get the current room.
     * @return the current room.
     */
    public Room currentRoom() {
        return this.currentRoom;
    }

    /**
     * Add a goal for this player
     * @param g the new goal.
     */
    public void addGoal(GameGoal g) {
        goals.add(g);
    }

    /**
     * Print information about the room
     */
    public void lookAround() {
        System.out.println(this.currentRoom.toString());
    }

    /**
     * Score by doing something with a Valuable valuableObject.
     * @param valuableObject the valuable valuableObject.
     * @see Valuable
     */
    public void score(Valuable valuableObject) {
        int score = valuableObject.value();
        score(score);
    }

    /**
     * Add to the Player's score
     * @param s the newly scored points.
     */
    public void score(int s) {
        System.out.println("You scored " + s + " points.");
        score += s;
    }

    /**
     * Terminate this player.
     */
    public void terminate() {
        System.out.println("You have scored " + this.score + " out of  " + possiblePoints + " possible points.");
        System.exit(0);
    }

    /**
     * Add points available to this player.
     * @param p The new points available.
     */
    public void addPossiblePoints(int p) {
        possiblePoints += p;
    }

    /**
     * Get the points available to this player.
     * @return the available points.
     */
    public int getPossiblePoints() {
        return possiblePoints;
    }

    /**
     * Fetch the goals for this Player.
     * @return the list of this Player's goals.
     */
    public Vector<GameGoal> getGoals() {
        return goals;
    }
}
