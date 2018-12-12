package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.*;

/**
 * Allows to replicate the key-value storage by sending the request copies to the replicas.
 */
public class ReplicationRequestDecorator extends AbstractRequestDecorator
{
    // Connection to the replica
    private Sender replica;

    // ID of the current server
    private String serverId;

    // Request timeout in milliseconds
    private int timeout;

    /**
     * Wraps a request handler.
     *
     * @param wrappee Handles the request
     * @param replica Replica of the current server
     * @param serverId ID of the current server
     * @param timeout Request timeout in milliseconds
     */
    public ReplicationRequestDecorator(RequestHandler wrappee, Sender replica, String serverId, int timeout)
    {
        super(wrappee);
        this.replica = replica;
        this.serverId = serverId;
        this.timeout = timeout;
    }

    @Override
    public Response handleRequest(Request req)
    {
        Response response = this.wrappee.handleRequest(req);


        // If request executed successfully, set actual server as originator and resend to the replica

        if(response.responseCode())
        {
            req.setOriginator(this.serverId);
            this.replica.sendMessage(req, this.timeout);
        }

        return response;
    }
}
