package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import imolcean.ec_assignments.second.server.KVStorage;
import imolcean.ec_assignments.second.server.exceptions.InvalidRequestException;

import java.io.*;

/**
 * Handler for a PUT request.
 */
public class PutRequestHandler extends AbstractRequestHandler
{
    public PutRequestHandler(KVStorage storage)
    {
        super(storage);
    }

    @Override
    public Response handleRequest(Request req)
    {
        String key;
        Serializable value;


        // Extract the parameters

        try
        {
            key = this.extractKey(req);
            value = this.extractValue(req);
        }
        catch(InvalidRequestException e)
        {
            return new Response(e.getMessage(), false, req);
        }


        // Store the key-value pair

        this.storage.store((String) key, value);

        return new Response("Success", true, req);
    }
}
