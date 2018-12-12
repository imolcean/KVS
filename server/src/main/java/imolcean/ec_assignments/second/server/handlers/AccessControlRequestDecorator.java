package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

import java.util.List;

/**
 * Allows to restrict access to any operation by introducing a white list of request originators.
 *
 * Requests from originators that are in the white list will be handled by the specified request handler.
 * Requests from any other originator will be declined.
 */
public class AccessControlRequestDecorator extends AbstractRequestDecorator
{
    // Originators with the write privilege. If null, everyone can write.
    private List<String> privilegedOriginators;

    /**
     * Wraps a request handler.
     *
     * @param wrappee Handles the request, if the originator is in the white list
     * @param privilegedOriginators White list with IDs of privileged request originators
     */
    public AccessControlRequestDecorator(RequestHandler wrappee, List<String> privilegedOriginators)
    {
        super(wrappee);
        this.privilegedOriginators = privilegedOriginators;
    }

    @Override
    public Response handleRequest(Request req)
    {
        // Check whether the request comes from a privileged originator

        if(!this.privilegedOriginators.contains(req.getOriginator()))
        {
            return new Response("Access forbidden", false, req);
        }

        return this.wrappee.handleRequest(req);
    }
}
