public class InsufficientQuantityException extends Exception
{
    @Override
    public String toString()
    {
        return "\nThe requested quantity is not available in the organization!";
    }
}
