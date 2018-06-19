package edu.cmu.tartan.properties;

/**
 * Enables an item to be destroyed.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Destroyable {

    /**
     * Destroy the item
     */
    public void destroy();

    /**
     * Set message to display on destruction.
     * @param s the message
     */
	public void setDestroyMessage(String s);

    /**
     * Make the destroyed item disappear
     * @param b set to true if the item should disappear
     */
	public void setDisappears(boolean b);

    /**
     * Getter for disappear property
     * @return true if the item disappears when destroyed; false otherwise
     */
	public boolean disappears();
}
