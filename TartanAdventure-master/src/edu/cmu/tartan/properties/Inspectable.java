package edu.cmu.tartan.properties;

/**
 * Enables an item to be inspected.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Inspectable {

    /**
     * Inspect the item
     * @return true on success
     */
	Boolean inspect();

    /**
     * Set the inspection message
     * @param s the message
     */
	void setInspectMessage(String s);
}
