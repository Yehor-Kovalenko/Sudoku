package project;

import exceptions.CloningException;

/**
 * Abstract Prototype interface for cloning the objects
 */
public interface Prototype extends Cloneable {
    Prototype clone() throws CloningException;
}
