package edu.cmu.tartan.action;

/**
 * Enumeration of action types.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public enum Type {

     // Directional actions move the player through the game.
	TYPE_DIRECTIONAL,

    // Some actions require direct objects to perform
    TYPE_HASDIRECTOBJECT,

    // Indirect objects are things that other objects need/use
    TYPE_HASINDIRECTOBJECT,

     // No object and unknown are self-explanatory
    TYPE_HASNOOBJECT,

    // Catch all
	TYPE_UNKNOWN
}
