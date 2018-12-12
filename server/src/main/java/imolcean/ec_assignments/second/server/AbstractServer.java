package imolcean.ec_assignments.second.server;

import de.tub.ise.hermes.*;

import java.io.IOException;

/**
 * Provides basic structure of a key-value storage server.
 */
public abstract class AbstractServer
{
    /**
     * ID of the server.
     */
    protected String id;

    /**
     * Key value storage on the server.
     */
    protected KVStorage storage;

    /**
     * Creates a server.
     *
     * @param id ID of the server
     */
    public AbstractServer(String id)
    {
        this.id = id;
        this.storage = new FileSystemKVStorage(FileSystemKVStorage.DEFAULT_LOCATION + "_" + id);
    }

    /**
     * Registers handlers for the CRUD operations.
     *
     * @param get Handler for the GET operation
     * @param put Handler for the PUT operation
     * @param remove Handler for the REMOVE operation
     */
    protected void setHandlers(
            IRequestHandler get,
            IRequestHandler put,
            IRequestHandler remove)
    {
        RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();

        reg.registerHandler("get", get);
        reg.registerHandler("put", put);
        reg.registerHandler("remove", remove);
    }

    /**
     * Starts the server.
     *
     * @param port Port the server listens to
     */
    public void start(int port)
    {
        try
        {
            new Receiver(port).start();
        }
        catch(IOException e)
        {
            System.err.println("Connection error");
            e.printStackTrace();
        }
    }
}
