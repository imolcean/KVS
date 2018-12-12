package imolcean.ec_assignments.second.server;

import de.tub.ise.hermes.*;
import imolcean.ec_assignments.second.server.handlers.*;

/**
 * Master server.
 *
 * Allows reads and writes from clients. Replicates to the slave server.
 */
public class ServerMaster extends AbstractServer
{
    // Timeout for any requests to the slave
    public static final int TIMEOUT = 5000;

    // Sender to communicate with the slave
    private Sender slave;

    /**
     * Constructs a master server.
     *
     * @param slaveHost Hostname or IP of the slave server
     * @param slavePort Port of the slave server
     * @param sync True if the server has to perform replication synchronously, false otherwise
     */
    public ServerMaster(String id, String slaveHost, int slavePort, boolean sync)
    {
        super(id);

        if(slaveHost == null)
        {
            throw new RuntimeException("Cannot create a master server without a slave");
        }

        this.slave = new Sender(slaveHost, slavePort);


        // Create handlers for write operations depending on the replication mode

        RequestHandler putHandler;
        RequestHandler removeHandler;

        String logName = "writes_master.log";

        if(sync)
        {
            putHandler =
                    new ReplicationRequestDecorator(
                            new TimeLoggingDecorator(
                                    new PutRequestHandler(this.storage),
                                    logName),
                            slave,
                            this.id,
                            TIMEOUT);

            removeHandler =
                    new ReplicationRequestDecorator(
                            new RemoveRequestHandler(this.storage),
                            slave,
                            this.id,
                            TIMEOUT);
        }
        else
        {
            putHandler =
                    new AsyncReplicationRequestDecorator(
                            new TimeLoggingDecorator(
                                    new PutRequestHandler(this.storage),
                                    logName),
                            slave,
                            this.id,
                            (Response resp) ->
                                    System.out.println(
                                            resp.responseCode() ? "Replication done" : "Replication failed"));

            removeHandler =
                    new AsyncReplicationRequestDecorator(
                            new RemoveRequestHandler(this.storage),
                            slave,
                            this.id,
                            (Response resp) ->
                                    System.out.println(
                                            resp.responseCode() ? "Replication done" : "Replication failed"));
        }


        this.setHandlers(
                new GetRequestHandler(this.storage),
                putHandler,
                removeHandler);
    }
}
