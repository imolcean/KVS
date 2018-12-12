package imolcean.ec_assignments.second.server.handlers;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.Instant;
import java.util.StringJoiner;

/**
 * Allows to log time when the underlying request handler is done with its job.
 *
 * Writes a log in the .csv format with two columns: id and termination, where
 * id is the second parameter of the original request and
 * termination is a unix timestamp of the moment when the response is created.
 */
public class TimeLoggingDecorator extends AbstractRequestDecorator
{
    // Writes to the CSV file
    private PrintWriter csv;

    /**
     * Wraps a request handler.
     *
     * @param wrappee Handles the request, if the originator is in the white list
     * @param logName Name of the log file
     */
    public TimeLoggingDecorator(RequestHandler wrappee, String logName)
    {
        super(wrappee);

        try
        {
            csv = new PrintWriter(new FileWriter(logName), true);
            csv.println("id, termination");
        }
        catch(IOException e)
        {
            System.err.println("Log could not be created");
        }
    }

    @Override
    public Response handleRequest(Request req)
    {
        Response resp = this.wrappee.handleRequest(req);


        // Log the timestamp of the operation's termination

        Serializable value = req.getItems().get(1);
        long tTermination = Instant.now().toEpochMilli();

        csv.println(
                new StringJoiner(",")
                        .add(value.toString())
                        .add(Long.toString(tTermination)));


        return resp;
    }
}
