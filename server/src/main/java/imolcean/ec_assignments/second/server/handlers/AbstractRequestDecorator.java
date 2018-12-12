package imolcean.ec_assignments.second.server.handlers;

/**
 * Provides additional functionality to a request handler.
 */
public abstract class AbstractRequestDecorator implements RequestHandler
{
    protected RequestHandler wrappee;

    public AbstractRequestDecorator(RequestHandler wrappee)
    {
        this.wrappee = wrappee;
    }
}
