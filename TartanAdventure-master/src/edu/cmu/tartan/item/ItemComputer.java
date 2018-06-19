package edu.cmu.tartan.item;

/**
 * This class for a computer
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemComputer extends Item {

    /**
     * Constructor for a computer
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemComputer(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(50);
    }
}
