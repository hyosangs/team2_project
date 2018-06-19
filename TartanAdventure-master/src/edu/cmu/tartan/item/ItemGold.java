package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for a gold bar, which can be held and installed.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemGold extends Item implements Installable, Holdable {

    /**
     * Constructor for a piece of gold
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemGold(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(500);
    }
}
