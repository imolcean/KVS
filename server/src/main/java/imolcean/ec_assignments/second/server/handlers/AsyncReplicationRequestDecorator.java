package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.AsyncCallbackRecipient;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import de.tub.ise.hermes.Sender;

/**
 * Allows to replicate the key-value storage by sending the request copies to the replicas asynchronously.
 */
public class AsyncReplicationRequestDecorator extends AbstractRequestDecorator
{
    // Connection to the replica
    private Sender replica;

    // ID of the current server
    private String serverId;

    // Will be called when the replica works off the request
    private AsyncCallbackRecipient callback;

    /**
     * Wraps a request handler.
     *
     * @param wrappee Handles the request
     * @param replica Replica of the current server
     * @param serverId ID of the current server
     * @param callback Function to be called when the replica returns a response
     */
    public AsyncReplicationRequestDecorator(
            RequestHandler wrappee,
            Sender replica,
            String serverId,
            AsyncCallbackRecipient callback)
    {
        super(wrappee);
        this.replica = replica;
        this.serverId = serverId;
        this.callback = callback;
    }

    public AsyncReplicationRequestDecorator(RequestHandler wrappee, Sender replica, String serverId)
    {
        this(wrappee, replica, serverId, null);
    }

    @Override
    public Response handleRequest(Request req)
    {
        Response response = this.wrappee.handleRequest(req);


        // If request executed successfully, set actual server as originator and resend to the replica

        if(response.responseCode())
        {
            req.setOriginator(this.serverId);
            this.replica.sendMessageAsync(req, this.callback);
        }

        return response;
    }
}
