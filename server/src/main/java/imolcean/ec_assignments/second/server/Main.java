package imolcean.ec_assignments.second.server;

public class Main
{
    private static void printUsage()
    {
        System.out.println("Usage: port [hostS portS [sync]]");
        System.out.println();
        System.out.println("port     - Port to listen to");
        System.out.println("hostS    - Hostname or IP of the slave server");
        System.out.println("portS    - Port of the slave server");
        System.out.println("sync     - Synchronous replication mode");
        System.out.println();
    }

    public static void main(String[] argv)
    {
        AbstractServer server;


        // If no port provided, terminate

        if(argv.length < 1)
        {
            printUsage();
            return;
        }


        // If an endpoint for the slave provided, create a master
        // Otherwise, create a slave

        if(argv.length > 2)
        {
            // If sync parameter provided, set the synchronous replication mode

            boolean sync = argv.length > 3;

            server = new ServerMaster("master", argv[1], Integer.parseInt(argv[2]), sync);
        }
        else
        {
            server = new ServerSlave("slave");
        }


        // Start the server's receiver

        server.start(Integer.parseInt(argv[0]));
    }
}
