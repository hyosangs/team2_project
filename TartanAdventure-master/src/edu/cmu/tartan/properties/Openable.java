package edu.cmu.tartan.properties;

/**
 * Enables an item to be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Openable  {

    /**
     * Open the item
     * @return true on success, false otherwise
     */
	Boolean open();

}
