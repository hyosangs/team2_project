package edu.cmu.tartan.room;

import edu.cmu.tartan.action.Action;

import java.util.LinkedList;

/**
 * The class for a dark room. Players must have a Luminous item to enter this room safely
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomDark extends Room {

	// Descriptions
	private String darkDescription;
	private String darkShortDescription;

	// indicates whether the room is dark
	private boolean isDark;

	// Message to indicate accident in room
	private String deathMessage;

    /**
     *
     * Create a new dark room
     * @param description the description
     * @param shortDescription the short description
     * @param darkDescription the dark description
     * @param darkShortDescription the long description
     */
	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription) {

		this(description, shortDescription, darkDescription, darkShortDescription, true);
	}

    /**
     * Create a new dark room
     * @param description the description
     * @param shortDescription the short description
     * @param darkDescription the dark description
     * @param darkShortDescription the long description
     * @param isDark determines if the room is dark to start
     */
	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription, boolean isDark){
		super(description, shortDescription);

		this.isDark = isDark;
		this.darkDescription = darkDescription;
		this.darkShortDescription = darkShortDescription;
		this.deathMessage = null;
	}
	// getters & setters
	public boolean isDark() {
		return this.isDark;
	}
	public void setDark(boolean isDark) {
		this.isDark = isDark;
	}
	public String deathMessage() {
		return this.deathMessage;
	}	
	public void setDeathMessage(String s) {
		this.deathMessage = s;
	}

	public String toString() {

		if(this.isDark) {
			if(this.player.hasLuminousItem()) {
				return super.toString();
			}
			else {
				return this.darkDescription;
			}
		}
		else {
			return super.toString();
		}
	}
	public String description() {
		if (this.isDark) {
			if (this.player.hasLuminousItem()) {
				String s = this.roomWasVisited ? this.shortDescription : this.description + "\n" + visibleItems();
				this.roomWasVisited = true;
				return s;
			} else {
				String s = this.roomWasVisited ? this.darkShortDescription : this.darkDescription;
				this.roomWasVisited = true;
				return s;
			}
		} else {
			String s = this.roomWasVisited ? this.shortDescription : this.description + "\n" + visibleItems();
			this.roomWasVisited = true;
			return s;
		}
	}
}
