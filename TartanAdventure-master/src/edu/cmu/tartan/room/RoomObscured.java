package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

/**
 * The class for an obscured room. An item is hiding this room
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomObscured extends Room {
    // The item hiding this room
	private Item obscuringItem;
    private boolean isObscured;
    private String obscureMessage;
    private String unobscureMessage;

    /**
     * Create a new obscured room
     * @param description the description
     * @param shortDescription the short description
     * @param obscuringItem The item obscuring this room
     */
	public RoomObscured(String description, String shortDescription, Item obscuringItem) {
		super(description, shortDescription);
		this.obscuringItem = obscuringItem;
		this.isObscured = true;
		this.obscureMessage = null;
		this.unobscureMessage = null;
	}

    // Getters & setters

	public boolean isObscured() {
		return this.isObscured;
	}
	public void setObscured(boolean obscured) {
		this.isObscured = obscured;
	}
	public void setUnobscureMessage(String s) {
		this.unobscureMessage = s;
	}
	public String unobscureMessage() {
		return this.unobscureMessage;
	}
	public void setObscureMessage(String s) {
		this.obscureMessage = s;
	}
	public String obscureMessage() {
		return this.obscureMessage;
	}

}
