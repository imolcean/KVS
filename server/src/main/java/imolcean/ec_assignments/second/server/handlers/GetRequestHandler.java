package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import imolcean.ec_assignments.second.server.KVStorage;
import imolcean.ec_assignments.second.server.exceptions.InvalidRequestException;

import java.io.Serializable;

/**
 * Handler for a GET request.
 */
public class GetRequestHandler extends AbstractRequestHandler
{
    /**
     * Is used to set the reference to the server's key-value storage.
     *
     * @param storage Server's key-value storage
     */
    public GetRequestHandler(KVStorage storage)
    {
        super(storage);
    }

    @Override
    public Response handleRequest(Request req)
    {
        String key;


        // Extract the key

        try
        {
            key = this.extractKey(req);
        }
        catch(InvalidRequestException e)
        {
            return new Response(e.getMessage(), false, req);
        }


        // Retrieve the corresponding value and return it with success

        Serializable value = this.storage.getValue((String) key);

        if(value == null)
        {
            return new Response("Value not found", false, req);
        }

        return new Response("Success", true, req, value);
    }
}
