package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for a ladder, which can be held.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemLadder extends Item implements Holdable {

    /**
     * Constructor for a ladder
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemLadder(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(15);
    }
}
