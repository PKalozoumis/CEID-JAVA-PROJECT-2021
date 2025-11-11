public class Admin extends User
{
    private final Boolean isAdmin = true;
    
    public Admin(String name, String phone)
    {
        super(name,phone);
    }
}
