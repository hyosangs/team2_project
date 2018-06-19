package edu.cmu.tartan.action;

import edu.cmu.tartan.item.Item;

/**
 * This enumeration of actions available to a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public enum Action {

    // Actions that do not rely on an object
    ActionLook(new String[]{"lookAround", "l"}, Type.TYPE_HASNOOBJECT),

    ActionJump(new String[]{"jump"}, Type.TYPE_HASNOOBJECT),
    ActionClimb(new String[]{"climb"}, Type.TYPE_HASNOOBJECT),
    ActionViewItems(new String[]{"inventory", "items", "i"}, Type.TYPE_HASNOOBJECT),
    ActionDie(new String[]{"terminate"}, Type.TYPE_HASNOOBJECT),
    ActionHelp(new String[]{"help", "h"}, Type.TYPE_HASNOOBJECT),

    // Directional actions; for movement
    ActionGoEast(new String[]{"east", "e"}, Type.TYPE_DIRECTIONAL),
    ActionGoWest(new String[]{"west", "w"}, Type.TYPE_DIRECTIONAL),
    ActionGoSouth(new String[]{"south", "s"}, Type.TYPE_DIRECTIONAL),
    ActionGoNorth(new String[]{"north", "n"}, Type.TYPE_DIRECTIONAL),
    ActionGoNortheast(new String[]{"northeast", "ne"}, Type.TYPE_DIRECTIONAL),
    ActionGoNorthwest(new String[]{"northwest", "nw"}, Type.TYPE_DIRECTIONAL),
    ActionGoSoutheast(new String[]{"southeast", "se"}, Type.TYPE_DIRECTIONAL),
    ActionGoSouthwest(new String[]{"southwest", "sw"}, Type.TYPE_DIRECTIONAL),
    ActionGoDown(new String[]{"down", "d"}, Type.TYPE_DIRECTIONAL),
    ActionGoUp(new String[]{"up", "u"}, Type.TYPE_DIRECTIONAL),

    // Direct Object. Has one direct object e.g. Break shovel, throw lamp
    ActionAcquire(new String[]{"acquire"}, Type.TYPE_HASNOOBJECT),
    ActionBurn(new String[]{"burn"}, Type.TYPE_HASNOOBJECT),
    ActionDig(new String[]{"dig"}, Type.TYPE_HASDIRECTOBJECT),
    ActionPickUp(new String[]{"pickup", "get", "take", "acquire", "grab"}, Type.TYPE_HASDIRECTOBJECT),
    ActionDestroy(new String[]{"break", "smash", "destroy", "obliterate"}, Type.TYPE_HASDIRECTOBJECT),
    ActionInspect(new String[]{"inspect", "examine", "read", "view"}, Type.TYPE_HASDIRECTOBJECT),
    ActionDrop(new String[]{"drop"}, Type.TYPE_HASDIRECTOBJECT),
    ActionThrow(new String[]{"throw", "chuck"}, Type.TYPE_HASDIRECTOBJECT),
    ActionShake(new String[]{"shake", "chickendance"}, Type.TYPE_HASDIRECTOBJECT),
    ActionEnable(new String[]{"enable", "hit", "start", "use", "deploy"}, Type.TYPE_HASDIRECTOBJECT),
    ActionPush(new String[]{"push", "call"}, Type.TYPE_HASDIRECTOBJECT), // used with elevator
    ActionEat(new String[]{"eat", "chew", "consume", "bite", "swallow", "drink"}, Type.TYPE_HASDIRECTOBJECT),
    ActionOpen(new String[]{"open", "unlock"}, Type.TYPE_HASDIRECTOBJECT),
    ActionExplode(new String[]{"detonate", "explode"}, Type.TYPE_HASDIRECTOBJECT),

    // Indirect Object. Has one direct object and one indirect object, e.g. Put cpu in computer
    ActionPut(new String[]{"put", "install"}, Type.TYPE_HASINDIRECTOBJECT),
    ActionTake(new String[]{"remove"}, Type.TYPE_HASINDIRECTOBJECT),

    // Misc
    ActionUnknown(new String[]{}, Type.TYPE_UNKNOWN),
    ActionError(new String[]{}, Type.TYPE_UNKNOWN),
    ActionPass(new String[]{"pass", "\n"}, Type.TYPE_UNKNOWN);

    /**
     * Create an new action
     * @param aliases THe set of alias fo rthe action
     * @param type the type of action
     */
    Action(String[] aliases, Type type) {
        this.aliases = aliases;
        this.type = type;
    }

    // Shortcuts to reverse movement
    static {
        ActionGoEast.opposite = ActionGoWest;
        ActionGoWest.opposite = ActionGoEast;
        ActionGoNorth.opposite = ActionGoSouth;
        ActionGoSouth.opposite = ActionGoNorth;
        ActionGoNortheast.opposite = ActionGoSouthwest;
        ActionGoSoutheast.opposite = ActionGoNorthwest;
        ActionGoNorthwest.opposite = ActionGoSoutheast;
        ActionGoSouthwest.opposite = ActionGoNortheast;
        ActionGoUp.opposite = ActionGoDown;
        ActionGoDown.opposite = ActionGoUp;
    }

    // Getters and Setters
    public String[] getAliases() {
        return this.aliases;
    }

    public Type type() {
        return this.type;
    }

    public void setDirectObject(Item directObject) {
        this.directObject = directObject;
    }

    public Item directObject() {
        return this.directObject;
    }

    public void setIndirectObject(Item indirectObject) {
        this.indirectObject = indirectObject;
    }

    public Item indirectObject() {
        return this.indirectObject;
    }

    // opposite directions are used for the directional enumeration constants.
    public Action getOppositeDirection() {

        if (this.type() == Type.TYPE_DIRECTIONAL) {
            return this.opposite;
        } else {
            return null;
        }
    }

    /**
     * Fields to describe actions
     */
    private Action opposite;
    private String[] aliases;
    private Type type;
    private Item directObject;
    private Item indirectObject;
}

