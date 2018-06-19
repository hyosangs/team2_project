package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Inspectable;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.properties.Visible;
import edu.cmu.tartan.room.Room;

import java.util.LinkedList;

/**
 * This is the main class for game items. Items are things that can be used in the game
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Item implements Comparable, Inspectable, Visible, Valuable {

    // every item is visible by default
    private boolean visible = true;
    private Integer value = null;

    /**
     * Items are referenced by descriptions
     */
    private String description=null;
    String detailDescription= null;

    /**
     * Items can have a list of unique aliases
     */
    private String[] aliases;
    private static LinkedList<Item> sharedInstances;

    Room relatedRoom; // items can open rooms, call elevators, etc (e.g., an ItemButton instance)
    Item relatedItem; // items can also affect other items, like setting other items breakable (like a junction box);
    private String inspectMessage;

    /**
     * Create a new item
     * @param description short description
     * @param detailDescription long description
     * @param a alias list
     */
    public Item(String description, String detailDescription, String[] a) {
        this.description = description;
        this.detailDescription = detailDescription;
        this.aliases = a;
        this.relatedRoom = null;
        this.relatedItem = null;
        this.inspectMessage = null;
        this.value = null;
    }

    /**
     * Initialize default items. These are the items initially available
     */
    private static void initSharedInstances() {

        sharedInstances = new LinkedList<Item>();
        sharedInstances.add(new ItemShovel("shovel", "metal shovel", new String[]{"shovel"}));
        sharedInstances.add(new ItemBrick("brick", "clay brick", new String[]{"brick"}));
        sharedInstances.add(new ItemFood("food", "food", new String[]{"food"}));
        sharedInstances.add(new ItemLadder("ladder", "wooden ladder", new String[]{"ladder"}));
        sharedInstances.add(new ItemKey("key", "gold key", new String[]{"key"}));
        sharedInstances.add(new ItemLock("lock", "gold lock", new String[]{"lock"}));
        sharedInstances.add(new ItemKeycard("keycard", "plastic keycard", new String[]{"keycard", "card"}));
        sharedInstances.add(new ItemKeycardReader("keycard reader", "metal keycard reader", new String[]{"reader", "slot"}));
        sharedInstances.add(new ItemClayPot("pot", "clay pot", new String[]{"pot", "pottery"}));
        sharedInstances.add(new ItemDiamond("diamond", "white diamond", new String[]{"diamond", "jewel"}));
        sharedInstances.add(new ItemGold("gold", "shiny gold bar", new String[]{"gold", "bar"}));
        sharedInstances.add(new ItemMicrowave("microwave", "microwave that stinks of month old popcorn", new String[]{"microwave", "appliance"}));
        sharedInstances.add(new ItemFridge("fridge", "white refrigerator", new String[]{"fridge", "refrigerator"}));
        sharedInstances.add(new ItemFlashlight("flashlight", "battery operated flashlight", new String[]{"flashlight"}));
        sharedInstances.add(new ItemTorch("torch", "metal torch", new String[]{"torch", "candle"}));
//        sharedInstances.add(new ItemWatch("watch", "smart watch", new String[]{"watch"}));
        sharedInstances.add(new ItemMagicBox("pit", "bottomless pit", new String[]{"pit", "hole"}));
        sharedInstances.add(new ItemVendingMachine("machine", "vending machine with assorted candies and treats", new String[]{"machine", "vendor"}));
        sharedInstances.add(new ItemSafe("safe", "bullet-proof safe", new String[]{"safe"}));
        sharedInstances.add(new ItemFolder("folder", "manilla folder", new String[]{"folder"}));
        sharedInstances.add(new ItemDocument("document", "Secret document", new String[]{"document"}));
        sharedInstances.add(new ItemLock("fan", "ventilation fan", new String[]{"fan"}));
        sharedInstances.add(new ItemComputer("computer", "Apple computer", new String[]{"apple", "computer", "keyboard", "imac"}));
        sharedInstances.add(new ItemCoffee("coffee", "steaming cup of coffee", new String[]{"coffee", "beverage", "mug"}));
        sharedInstances.add(new ItemDeskLight("light", "desk light", new String[]{"light"}));
        sharedInstances.add(new ItemDynamite("dynamite", "bundle of dynamite", new String[]{"dynamite", "explosive", "explosives"}));
        sharedInstances.add(new ItemButton("Button", "Elevator Button", new String[]{"button"}));
        sharedInstances.add(new ItemButton("Floor 1 Button", "Elevator Floor 1 Button", new String[]{"1"}));
        sharedInstances.add(new ItemButton("Floor 2 Button", "Elevator Floor 2 Button", new String[]{"2"}));
        sharedInstances.add(new ItemButton("Floor 3 Button", "Elevator Floor 3 Button", new String[]{"3"}));
        sharedInstances.add(new ItemButton("Floor 4 Button", "Elevator Floor 4 Button", new String[]{"4"}));
        sharedInstances.add(new ItemUnknown("unknown", "unknown", new String[]{"unknown"}));

        // there can be no overlap in aliases
        checkUniqueAliases();
    }

    /**
     * Factory to create a designed item. All items must be instantiated using this method. Items are created by name
     * @param s the name of the item (or perhaps it's alias)
     * @return the newly instantiated item
     */
    public static Item getInstance(String s) {
        if (sharedInstances == null) {
            initSharedInstances();
        }
        for (Item i : sharedInstances) {
            for (String a : i.getAliases()) {
                if (s.equals(a)) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Ensure that aliases are unique
     */
    private static void checkUniqueAliases() {
        for (Item item : sharedInstances) {
            for (Item i : sharedInstances) {
                if (item == i) {
                    continue;
                }
                for (String string : item.getAliases()) {
                    for (String s : i.getAliases()) {
                        if (string == s) {
                            System.err.println("Warning: alias conflict between " + item + " and " + i);
                        }
                    }
                }
            }
        }
    }

    // Getter & setters
    public Item relatedItem() {
        return this.relatedItem;
    }
    public void setRelatedItem(Item i) {
        this.relatedItem = i;
    }

    public Room relatedRoom() {
        return this.relatedRoom;
    }
    public void setRelatedRoom(Room r) {
        this.relatedRoom = r;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String toString() {
        return this.description;
    }

    public String detailDescription() {
        return this.detailDescription;
    }
    public String description() {
        return this.description;
    }

    public void setDescription(String s) {
        this.description = s;
    }
    public void setDetailDescription(String s) {
        this.detailDescription = s;
    }

    /**
     * The comparison is based on description
     * @param i
     * @return
     */
    public int compareTo(Object i) {
        if (((Item) i).detailDescription.equals(this.detailDescription())) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Control visibility
     */
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    // Inspectable
    public Boolean inspect() {
        if (this.inspectMessage != null) {
            System.out.println(this.inspectMessage);
        } else {
            System.out.println("It appears to be a " + this + ".");
        }
        return true;
    }

    public void setInspectMessage(String message) {
        this.inspectMessage = message;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}



