package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Shakeable;

/**
 * This class for a keycard, which can be shaken.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemVendingMachine extends Item implements Shakeable {

    /**
     * Constructor
     * @param d description
     * @param sd long description
     * @param a aliases
     */
    public ItemVendingMachine(String d, String sd, String[] a) {
        super(d, sd, a);
        this.count = 0;
        setValue(15);
    }

    /**
     * Shaking this machine too much can cause an accident
     * @return true if an accident can occur; false otherwise
     */
    public boolean accident() {
        return this.count > 2;
    }

    public void shake() {
        switch (this.count) {
            case 0:
                System.out.println("You shake the vending machine, and your favorite treat inches its way off the tray.");
                break;
            case 1:
                System.out.println("The treat begins to bend toward the will of gravity.");
                break;
            case 2:
                System.out.println("Just as the candy falls, the machine also falls over and crushes you.");
                break;
            default:
                break;
        }
        this.count++;
    }

    protected int count;
}
