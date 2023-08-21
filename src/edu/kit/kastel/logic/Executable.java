package edu.kit.kastel.logic;

/**
 * Interface, which declares a class as an executable class.
 *
 * @author uhquw
 * @version 1.0.0
 */
public interface Executable {
    /**
     * Checks if the object is active or not.
     *
     * @return A boolean, if the object is active or not.
     */
    boolean isActive();

    /**
     * Makes the object inactive.
     */
    void quit();
}
