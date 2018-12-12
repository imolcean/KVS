package imolcean.ec_assignments.second.server;

import imolcean.ec_assignments.second.server.handlers.*;

import java.util.Arrays;
import java.util.List;

/**
 * Slave server.
 *
 * Allows reads from clients and writes from the master server.
 */
public class ServerSlave extends AbstractServer
{
    // Originators with the write privilege
    private final List<String> WRITERS = Arrays.asList("master");

    /**
     * Constructs a slave server.
     */
    public ServerSlave(String id)
    {
        super(id);

        this.setHandlers(
                new GetRequestHandler(this.storage),
                new AccessControlRequestDecorator(
                        new TimeLoggingDecorator(
                                new PutRequestHandler(this.storage),
                                "writes_slave.log"),
                        this.WRITERS),
                new AccessControlRequestDecorator(
                        new RemoveRequestHandler(this.storage),
                        this.WRITERS));
    }
}
