public class Main
{
    //Create the organization
    public static Organization org = new Organization("Name");
    
    public static void main(String[] args)
    { 
        //Create entities and add them to the organization's entity list
        try
        {
            //Milk
            org.addEntity(new Material("Milk", "A carton of milk. 2% fat.", 0, 2, 4, 6));
            
            //Sugar
            org.addEntity(new Material("Sugar", "A small bag of sugar. Very sweet.", 1, 1, 2, 3));
            
            //Rice
            org.addEntity(new Material("Rice", "A small bag of rice.", 2, 2, 5, 8));
            
            //Medical Support
            org.addEntity(new Service("Medical Support", "Medical support for those in need.", 3));
            
            //Nursery Support
            org.addEntity(new Service("Nursery Support", "Nursery support for those with children.", 4));
            
            //Babysitting
            org.addEntity(new Service("Babysitting", "Babysitting services for those with children.", 5));
        }
        catch(ItemAlreadyInListException e)
        {
            System.out.println(e.toString());
        }
        
        //Create and set the admin
        org.setAdmin(new Admin("Bob", "1"));
    
        try
        {
            //Create the beneficiaries and add them to the organization's beneficiary list
            org.insertBeneficiary(new Beneficiary("Mike", "2", 1));
            org.insertBeneficiary(new Beneficiary("Kyle", "3", 4));
            
            //Create the donator and add them to the organization's donator list
            org.insertDonator(new Donator("Karen", "4"));
        }
        catch(ItemAlreadyInListException e)
        {
            System.out.println(e.toString());
        }
        
        //The donator donates 4 milk and 10 hours of medical service
        try
        {
            org.getDonator(1).add(new RequestDonation(org.getMaterial(1), 4));
            org.getDonator(1).add(new RequestDonation(org.getService(1), 10));
            org.getDonator(1).commit();
        
        }
        catch (EntityNotInOrganizationException e)
        {
            System.out.println(e.toString());
        }
        
        //The first beneficiary requests 2 milk and 10 hours medical service
        try
        {
            org.getBeneficiary(1).add(new RequestDonation(org.getMaterial(1), 2));
            org.getBeneficiary(1).add(new RequestDonation(org.getService(1), 10));
        }
        catch (EntityNotInOrganizationException e)
        {
            System.out.println(e.toString());
        }
        catch (InsufficientQuantityException e)
        {
            System.out.println(e.toString());
        }
        catch (InvalidRequestException e)
        {
            System.out.println(e.toString());
        }
    
        
        //Run the main menu method
        Menu.mainMenu();
    }
}
