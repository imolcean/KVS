package imolcean.ec_assignments.second.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// TODO Throw exceptions in case of a failure
// TODO Rewrite with NIO.2

public class FileSystemKVStorage implements KVStorage
{
    // Default storage location in the file system, relative to the  runtime directory
    public static final String DEFAULT_LOCATION = "." + File.separator + "store";

    // Location of the storage in the file system
    private String storageDir;

    /**
     * Creates a store with the default storage location.
     */
    public FileSystemKVStorage()
    {
        this(DEFAULT_LOCATION);
    }

    /**
     * Creates a store with the given storage location.
     *
     * @param storageDir Location of the storage in the file system
     */
    public FileSystemKVStorage(String storageDir)
    {
        this.storageDir = storageDir;
    }

    @Override
    public Serializable getValue(String key)
    {
        File f = new File(storageDir + File.separator + key);

        Object value = null;
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(f)))
        {
            value = oi.readObject();
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.err.println("Retrieving value for key " + key + " failed.");
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }

        if(!(value instanceof Serializable))
        {
            return null;
        }

        return (Serializable) value;
    }

    @Override
    public List<String> getKeys()
    {
        // TODO Rewrite with StreamAPI

        List<String> result = new ArrayList<>();

        for(File f : new File(storageDir).listFiles())
        {
            result.add(f.getName());
        }
        return result;
    }

    @Override
    public void store(String key, Serializable value)
    {
        File f = new File(storageDir + File.separator + key);

        if(!f.isDirectory() && !f.isFile())
        {
            try
            {
                File parent = f.getParentFile();
                parent.mkdirs(); // create parent directories
                f.createNewFile();
            }
            catch(IOException e)
            {
                System.err.println("File " + f.getAbsolutePath() + " could not be created.");
                e.printStackTrace();
            }

            // update file content
            try(ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(f)))
            {
                oo.writeObject(value);
            }
            catch(IOException e)
            {
                System.err.println("Writing value to file failed for key " + key + ".");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String key)
    {
        File f = new File(storageDir + File.separator + key);

        if(!f.isDirectory() && f.isFile())
        {
            f.delete();
        }
    }
}
