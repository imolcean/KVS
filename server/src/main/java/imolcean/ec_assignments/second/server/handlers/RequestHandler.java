package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.IRequestHandler;

/**
 * Requires a response per default.
 */
public interface RequestHandler extends IRequestHandler
{
    @Override
    default boolean requiresResponse()
    {
        return true;
    }
}
