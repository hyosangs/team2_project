package edu.cmu.tartan.properties;

import edu.cmu.tartan.item.Item;

/**
 * Enables an item to be melted.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public interface Meltable  {

	/**
	 * Set the item revealed when melted.
	 * @param item the item to melt
	 */
	void setMeltItem(Item item);

	/**
	 * Melt the item
	 * @return the melted item
	 */
	Item meltItem();
}
