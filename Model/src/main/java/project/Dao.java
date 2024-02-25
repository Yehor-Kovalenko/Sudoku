package project;

/**
 * Dao interface for data access operations
 * @param <T> - object to be put into IO stream
 */
public interface Dao<T> extends AutoCloseable {
    /**
     * Represents access to the input stream
     * @return T object that had been deserialized and created from the input stream
     * @throws Exception if there has been an exception while deserializing and constructing the T object
     */
    T read() throws Exception;

    /**
     * Represents the access to the output stream
     * @param obj - object of the type T to be serialized
     * @throws Exception - if there has been an exception while serializing the object
     */
    void write(T obj) throws Exception;
}
