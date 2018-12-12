package imolcean.ec_assignments.second.client.exceptions;

/**
 * Is thrown, if a client tries to perform an operation it should not.
 */
public class PrivilegedOperationException extends Exception
{
    public PrivilegedOperationException(String msg)
    {
        super(msg);
    }
}
