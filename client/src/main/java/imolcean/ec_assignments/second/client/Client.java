package imolcean.ec_assignments.second.client;

import de.tub.ise.hermes.*;
import imolcean.ec_assignments.second.client.exceptions.PrivilegedOperationException;
import imolcean.ec_assignments.second.client.exceptions.ValueRetrievalException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Client for the key-value storage.
 *
 * Can connect to a server and perform CRUD operations.
 */
public class Client
{
    // Request timeout in milliseconds.
    private static final int TIMEOUT = 5000;

    // ID of the client.
    // This does not necessarily has to be unique among other clients
    // but must not be equal to any ID of the servers.
    private String id;

    // Connection to the server.
    private Sender sender;

    /**
     * Creates a client to communicate to a key-value storage.
     *
     * @param id ID of the client, must not be unique but has to be unequal to any ID of the servers
     * @param serverHost Hostname or IP of the server
     * @param serverPort Port of the server
     */
    public Client(String id, String serverHost, int serverPort)
    {
        this.id = id;
        sender = new Sender(serverHost, serverPort);
    }

    /**
     * Retrieves a value for the specified key.
     *
     * @param key Key of the value that needs to be found in the storage
     * @return Value for the corresponding key if exists in the storage
     * @throws ValueRetrievalException If no value for the given key was found
     */
    public Serializable get(String key) throws ValueRetrievalException
    {
        Request req = new Request(key, "get", id);

        Response resp = sender.sendMessage(req, TIMEOUT);

        if(!resp.responseCode() || resp.getItems() == null)
        {
            throw new ValueRetrievalException(resp.getResponseMessage());
        }

        return resp.getItems().get(0);
    }

    /**
     * Writes a key-value pair.
     *
     * @param key Key for the corresponding value
     * @param value Value the key maps to
     * @throws PrivilegedOperationException If the server is read-only
     */
    public void put(String key, Serializable value) throws PrivilegedOperationException
    {
        List<Serializable> payload = Stream.of(key, value).collect(Collectors.toList());

        Request req = new Request(payload, "put", id);
        Response resp = sender.sendMessage(req, TIMEOUT);

        if(!resp.responseCode())
        {
            throw new PrivilegedOperationException(resp.getResponseMessage());
        }
    }

    /**
     * Removes the key-value from the storage if it exists.
     *
     * @param key Key for the corresponding value
     * @throws PrivilegedOperationException If the server is read-only
     */
    public void remove(String key) throws PrivilegedOperationException
    {
        Request req = new Request(key, "remove", id);
        Response resp = sender.sendMessage(req, TIMEOUT);

        if(!resp.responseCode())
        {
            throw new PrivilegedOperationException(resp.getResponseMessage());
        }
    }
}
