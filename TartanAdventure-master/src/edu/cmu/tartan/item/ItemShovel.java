package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for a shovel, which can be held .
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemShovel extends Item implements Holdable {
    public ItemShovel(String d, String sd, String[] a) {

        super(d, sd, a);
        setValue(5);
    }
}