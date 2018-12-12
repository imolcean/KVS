package imolcean.ec_assignments.second.server;

import java.io.Serializable;
import java.util.List;

/**
 * Interface of a key value storage.
 *
 * Keys are always Strings, whereas values are of type Serializable, i.e. any Java Object that can be serialized.
 */
public interface KVStorage
{
    /**
     * Returns a value for a given key.
     *
     * @param key Key of the object to retrieve
     * @return Value for the given key if exists and serializable, null otherwise
     */
    Serializable getValue(String key);

    /**
     * Returns a list of all keys.
     */
    List<String> getKeys();

    /**
     * Stores a key value pair.
     *
     * @param key   Key of the object
     * @param value Value for the given key
     */
    void store(String key, Serializable value);

    /**
     * Deletes the key value pair.
     * If the key doesn't exist, nothing will happen.
     *
     * @param key Key for the object to delete
     */
    void delete(String key);
}
