package edu.cmu.tartan.properties;

/**
 * Enables an item to be shaken.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Shakeable {

    /**
     * Shake the item
     */
    void shake();

    /**
     * Shaking items can cause an accident!
     * @return true if the item is shaken too much; false otherwise
     */
	boolean accident();
}
