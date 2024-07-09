public class InvalidRequestException extends Exception
{
    @Override
    public String toString()
    {
        return "\nThis beneficiary is not eligible to receive the requested amount!";
    }
}
