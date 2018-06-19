package edu.cmu.tartan.properties;

import edu.cmu.tartan.item.Item;

/**
 * Enables an item to host another item.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Hostable {

	/**
	 * Install a item into this hostable item
	 * @param item the item to install
	 */
	void install(Item item);

	/**
	 * Remove an installed item
	 * @param item the installed item
	 * @return
	 */
	boolean uninstall(Item item);

	/**
	 * Fetch the item currently installed
	 * @return the installed item
	 */
	Item installedItem();
}
