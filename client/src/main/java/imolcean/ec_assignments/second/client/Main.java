package imolcean.ec_assignments.second.client;

import imolcean.ec_assignments.second.client.exceptions.PrivilegedOperationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main
{
    // Stores the value that will be written to the server
    private static int val;

    // Returns the next value
    private static int nextVal()
    {
        return val++;
    }

    // Prints usage instructions to the console.
    private static void printUsage()
    {
        System.out.println("Usage: id hostM portM iter");
        System.out.println();
        System.out.println("id       - Name of the client");
        System.out.println("hostM    - Hostname or IP of the master server");
        System.out.println("portM    - Port of the master server");
        System.out.println("iter     - Number of write iterations");
        System.out.println();
    }

    public static void main(String[] argv)
    {
        // Initialize the client

        if(argv.length < 4)
        {
            printUsage();
            return;
        }

        int iterations = Integer.parseInt(argv[3]);

        Client masterClient = new Client(argv[0], argv[1], Integer.parseInt(argv[2]));


        // Create a log file

        PrintWriter csv;

        try
        {
            csv = new PrintWriter(new FileWriter("latency.log"), true);
            csv.println("id, start, commit, latency");
        }
        catch(IOException e)
        {
            System.err.println("Log could not be created");
            return;
        }


        // Write every second

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                () ->
                {
                    try
                    {
                        // If all values written, stop

                        int val = nextVal();
                        if(val >= iterations)
                        {
                            System.exit(0);
                        }


                        // Write to the server and mention the time

                        long tStart = Instant.now().toEpochMilli();

                        masterClient.put("zero", val);

                        long tCommit = Instant.now().toEpochMilli();


                        // Write to the log: [val, tStart, tCommit, latency]

                        csv.println(
                                new StringJoiner(",")
                                        .add(Integer.toString(val))
                                        .add(Long.toString(tStart))
                                        .add(Long.toString(tCommit))
                                        .add(Long.toString(tCommit - tStart)));

                        System.out.println("PUT");
                    }
                    catch(PrivilegedOperationException e)
                    {
                        e.printStackTrace();
                    }
                },
                0,
                1,
                TimeUnit.SECONDS);
    }
}
