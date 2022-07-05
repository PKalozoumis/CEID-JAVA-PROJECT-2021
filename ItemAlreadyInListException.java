public class ItemAlreadyInListException extends Exception
{
    Object obj;
    
    public ItemAlreadyInListException(Object obj)
    {
        this.obj =  obj;
    }
    
    @Override
    public String toString()
    {
        if (obj instanceof Entity)
        {
            return "Entity \"" + ((Entity)obj).getName() + "\" was already in the list and did not get added!";
        }
        else return "User \"" + ((User)obj).getName() + "\" was already in the list and did not get added!";
    }
}
