package imolcean.ec_assignments.second.server.exceptions;

/**
 * Is thrown, if the request parameters are invalid.
 */
public class InvalidRequestException extends Exception
{
    public InvalidRequestException(String msg)
    {
        super(msg);
    }
}
