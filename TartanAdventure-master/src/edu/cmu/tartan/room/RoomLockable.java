package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

/**
 * The class for a locked room. A key is required to open this type of room
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomLockable extends Room {
    private boolean locked;
    private Item key;
    private boolean causesDeath;
    private String deathMessage;
    private String unlockMessage;

    /**
     * Create a new locked room
     * @param description description
     * @param shortDescription short description
     * @param locked indicates whether the room is locked
     * @param key the specific key needed to unlock this toon
     */
	public RoomLockable(String description, String shortDescription, boolean locked, Item key) {
		super(description, shortDescription);

		this.locked = locked;
		this.key = key;
		this.causesDeath = false;
		this.deathMessage = "";
		this.unlockMessage = "Room unlocked.";
	}

    /**
     * Create a locked room (unlocked by default)
     * @param description description
     * @param shortDescription short description
     */
	public RoomLockable(String description, String shortDescription) {
		// unlocked by default
		this(description, shortDescription, false, Item.getInstance("unknown"));
	}

    /**
     * Indicate whether room is locked
     * @return
     */
	public boolean isLocked() {
		return this.locked;
	}

	// Handle player death

	public void setCausesDeath(boolean causesDeath, String message) {
		this.causesDeath = causesDeath;
		this.deathMessage = message;
	}

	public boolean causesDeath() {
		return this.causesDeath;
	}

	public String deathMessage() {
		return this.deathMessage;
	}

    /**
     * Set unlock message
     * @param s the unlock message
     */
	public void setUnlockMessage(String s){
		this.unlockMessage = s;
	}

    /**
     * Unlock the room
     * @param key the key to use to unlock
     * @return true if it is the right key; false otherwise
     */
	public boolean unlock(Item key) {
		if(this.key.compareTo(key) == 0) {
			this.locked = false;
			System.out.println(this.unlockMessage);
			return true;
		}
		else { 
			if(!causesDeath()) {
				System.out.println("This key doesn't seem to fit");
			}
			return false;
		}
	}
}
