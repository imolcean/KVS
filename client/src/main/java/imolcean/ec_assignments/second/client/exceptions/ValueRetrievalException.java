package imolcean.ec_assignments.second.client.exceptions;

/**
 * Is thrown, if retrieval of a value from the storage failed
 * (e.g. because no value was found for the given key).
 */
public class ValueRetrievalException extends Exception
{
    public ValueRetrievalException(String msg)
    {
        super(msg);
    }
}
