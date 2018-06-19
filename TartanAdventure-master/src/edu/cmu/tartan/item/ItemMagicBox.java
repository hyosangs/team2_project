package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;

/**
 * This class for a magic box, which can hold something (make it disappear).
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemMagicBox extends Item implements Hostable {

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemMagicBox(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(7);
    }

    /**
     * Install an item
     * @param i the item to install
     */
    @Override
    public void install(Item i) {
        // items fall into black hole
    }

    /**
     * Uninstall an item
     * @param i the item to uninstall
     */
    @Override
    public boolean uninstall(Item i) {
        return false;
    }

    /**
     * Fetch the installed item
     */
    @Override
    public Item installedItem() {
        return null;
    }
}
