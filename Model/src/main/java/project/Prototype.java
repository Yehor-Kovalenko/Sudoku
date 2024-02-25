package project;

import exceptions.CloningException;

public interface Prototype extends Cloneable {
    Prototype clone() throws CloningException;
}
