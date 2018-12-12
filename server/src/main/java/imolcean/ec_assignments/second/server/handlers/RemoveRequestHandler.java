package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import imolcean.ec_assignments.second.server.KVStorage;
import imolcean.ec_assignments.second.server.exceptions.InvalidRequestException;

/**
 * Handler for a REMOVE request.
 */
public class RemoveRequestHandler extends AbstractRequestHandler
{
    public RemoveRequestHandler(KVStorage storage)
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

        this.storage.delete(key);

        return new Response("Success", true, req);
    }
}
