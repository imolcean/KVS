package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.*;

import imolcean.ec_assignments.second.server.KVStorage;
import imolcean.ec_assignments.second.server.exceptions.InvalidRequestException;

import java.io.Serializable;

/**
 * Specific type of request handlers for a key-value storage server.
 *
 * This type also provides several methods for easy extraction of request parameters.
 */
public abstract class AbstractRequestHandler implements RequestHandler
{
    /**
     * Reference to the key-value storage of the server.
     */
    protected KVStorage storage;

    /**
     * Is used to set the reference to the server's key-value storage.
     *
     * @param storage Server's key-value storage
     */
    public AbstractRequestHandler(KVStorage storage)
    {
        this.storage = storage;
    }

    /**
     * Extracts and validates a key from a given request.
     *
     * @param req Request object
     * @return Validated key parameter
     * @throws InvalidRequestException If the key is invalid or not provided
     */
    protected String extractKey(Request req) throws InvalidRequestException
    {
        Serializable key;


        // Check that key exists

        try
        {
            key = req.getItems().get(0);
        }
        catch(IndexOutOfBoundsException e)
        {
            throw new InvalidRequestException("Key is not provided");
        }


        // Check that key is a valid String

        if(!(key instanceof String))
        {
            throw new InvalidRequestException("Key is not a String");
        }

        return (String) key;
    }

    /**
     * Extracts and validates a value from a given request.
     *
     * @param req Request object
     * @return Validated value parameter
     * @throws InvalidRequestException If the value is not provided
     */
    protected Serializable extractValue(Request req) throws InvalidRequestException
    {
        Serializable value;


        // Check that value exists

        try
        {
            value = req.getItems().get(1);
        }
        catch(IndexOutOfBoundsException e)
        {
            throw new InvalidRequestException("Value is not provided");
        }

        return value;
    }
}
