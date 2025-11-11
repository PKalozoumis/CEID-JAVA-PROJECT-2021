import java.util.*;

public class Organization
{
    private String name = "";
    private Admin admin;
    
    private ArrayList<Entity> entityList = new ArrayList<Entity>(); //List of all the items that can be donated/offered
    private ArrayList<Donator> donatorList = new ArrayList<Donator>();
    private ArrayList<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();
    
    //currentDonation is used to access the items that have been donated to the organization
    private RequestDonationList currentDonations = new RequestDonationList();
    
    public Organization(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Admin getAdmin()
    {
        return admin;
    }
    
    public void setAdmin(Admin admin)
    {
        this.admin = admin;
    }
    
    public void addDonation(RequestDonation requestDonation) throws EntityNotInOrganizationException
    {
        currentDonations.add(requestDonation);
    }
    
    public void addEntity(Entity entity) throws ItemAlreadyInListException
    {   
        if (isInEntityList(entity))
        {
            throw new ItemAlreadyInListException(entity);
        }
        
        entityList.add(entity);
    }
    
    public void removeEntity(Entity entity)
    {   
        Iterator<Entity> iter = entityList.iterator();
        
        while(iter.hasNext())
        {            
            if (iter.next().getId() == entity.getId())
            {
                iter.remove();
            }
        }
    }
    
    public void insertDonator(Donator donator) throws ItemAlreadyInListException
    {   
        for (Donator d : donatorList)
        {
            if (d.getPhone().equals(donator.getPhone()))
            {
                throw new ItemAlreadyInListException(d);
            }
        }
        
        donatorList.add(donator);
    }
    
    public void removeDonator(Donator donator)
    {
        Iterator<Donator> iter = donatorList.iterator();
        
        while(iter.hasNext())
        {            
            if (iter.next().getPhone().equals(donator.getPhone()))
            {
                iter.remove();
            }
            
        }
        
        System.out.println("\nDonator \"" + donator.getName() + "\" removed successfully!");
    }
    
    public void insertBeneficiary(Beneficiary beneficiary) throws ItemAlreadyInListException
    {   
        for (Beneficiary b : beneficiaryList)
        {
            if (b.getPhone().equals(beneficiary.getPhone()))
            {
                throw new ItemAlreadyInListException(b);
            }
        }
        
        beneficiaryList.add(beneficiary);
    }
    
    public void removeBeneficiary(Beneficiary beneficiary)
    {
        Iterator<Beneficiary> iter = beneficiaryList.iterator();
        
        while(iter.hasNext())
        {   
            if (iter.next().getPhone().equals(beneficiary.getPhone()))
            {
                iter.remove();
            }            
        }
        
        System.out.println("\nBeneficiary \"" + beneficiary.getName() + "\" removed successfully!");
    }
    
    public boolean isInEntityList(Entity entity)
    {
        boolean found = false;
        
        for (Entity e : entityList)
        {
            if (e.getId() == entity.getId())
            {
                found = true;
                break;
            }
        }
        
        return found;
    }
    
    public double getDonatedQuantity(Entity entity)
    {
        return currentDonations.findItemQuantity(entity);
    }
    
    public void subtractDonatedQuantity(Entity entity, double quantity)
    {
        RequestDonation donation = currentDonations.get(entity.getId());
        
        if(donation.getQuantity() - quantity != 0)
        {
            currentDonations.modify(donation,donation.getQuantity() - quantity);
        }
        else currentDonations.remove(donation);
    }
    
    public Material getMaterial(int n)
    {
        int i = 0;
        Material ret = null;
        
        for (Entity e : entityList)
        {
            if (e instanceof Material)
            {
                i++;
                if (i == n)
                {
                   ret = (Material)e;
                }
            }    
        }

        return ret;
    }
    
    public Service getService(int n)
    {
        int i = 0;
        Service ret = null;
        
        for (Entity e : entityList)
        {
            if (e instanceof Service)
            {
                i++;
                if (i == n)
                {
                   ret = (Service)e;
                }
            }    
        }

        return ret;
    }
    
    public Material getDonatedMaterial(int n)
    {
        return currentDonations.getMaterial(n);
    }
    
    public Service getDonatedService(int n)
    {
        return currentDonations.getService(n);
    }
    
    public Donator getDonator(int n)
    {
        return donatorList.get(n-1);
    }
    
    public Beneficiary getBeneficiary(int n)
    {
        return beneficiaryList.get(n-1);
    }
    
    public void listEntities()
    {
        String str_materials = "\n";
        String str_services = "\n";
        
        for (Entity e : entityList)
        {
            String str = e.getName() + "\n";
            
            if (e instanceof Material)
            {
                str_materials += str;
            }
            else if (e instanceof Service)
            {
                str_services += str;
            }
        }
        
        System.out.println("A list of all entities in the organization by category:");
        System.out.print("Materials:" + str_materials);
        System.out.println("Services:" + str_services);
    }
    
    public int listMaterials()
    {
        String str = "";
        int i = 1;
        
        for (Entity e : entityList)
        {
            if (e instanceof Material)
            {
                str += "\n" + "  " + i + "." + e.getName() + " (" + getDonatedQuantity(e) + ")";
                i++;
            }
        }
        
        System.out.println("\nMaterials:" + str);
        
        return i-1;
    }
    
    public int listServices()
    {
        String str = "";
        int i = 1;
        
        for (Entity e : entityList)
        {
            if (e instanceof Service)
            {
                str += "\n" + "  " + i + "." + e.getName() + " (" + getDonatedQuantity(e) + ")";
                i++;
            }
        }
        
        System.out.println("\nServices:" + str);
        
        return i - 1;
    }
    
    public int listDonatedMaterials()
    {
        return currentDonations.listMaterials();
    }
    
    public int listDonatedServices()
    {
        return currentDonations.listServices();
    }
    
    public int listDonators()
    {
        int count = 0;
        String str = "";
        
        for (Donator d: donatorList)
        {
            str += "\n" + "  " + (count + 1) + "." + d.getName();
            count++;
        }
        
        if (count>0)
        {
            System.out.println("\nDonators:" + str);
        }
        else System.out.println("\nNo donators:" + str);
        
        return count;
    }
    
    public int listBeneficiaries()
    {
        int count = 0;
        String str = "";
        
        for (Beneficiary b: beneficiaryList)
        {
            str += "\n" + "  " + (count + 1) + "." + b.getName();  
            count++;
        }
        
        if (count>0)
        {
            System.out.println("\nBeneficiaries:" + str);
        }
        else System.out.println("\nNo beneficiaries:" + str);
        
        return count;
    }
    
    public void resetBeneficiariesLists()
    {
        for(Beneficiary b : beneficiaryList)
        {
            b.clearReceivedList();
        }
    }
    
    public User findRegisteredUser(String phone)
    {
        boolean found = false;
        User user = null;
        
        //Check if the admin is the user with this phone number
        if (phone.equals(admin.getPhone()))
        {
            user = admin;
            found = true;
        }
        else //If the user is not the admin, check in the beneficiary list
        {
            for (Beneficiary b : beneficiaryList)
            {
                if (phone.equals(b.getPhone()))
                {
                    found = true;
                    user = b;
                    break;
                }
            }
            
            //If the user was not found, look in the donator list.
            if (!found)
            {
                for (Donator d : donatorList)
                {
                    if (phone.equals(d.getPhone()))
                    {
                        found = true;
                        user = d;
                        break;
                    }
                }
            }
        }
        
        //If the user has still not been found, it means it's a new user and must be registered.
        if (!found) user = registerNewUser(phone);
        
        return user;
    }
    
    public User registerNewUser(String phone)
    {
        User newUser = null;
        System.out.print("\nThere is no user with the phone number "+ phone +".\nWould you like to register? ");
        
        if(myMethods.YNchoose().equals("n")) //Do not register
        {
            System.out.println("\nYou were not registered.");
        }
        else
        {
            System.out.print("\nUsername: ");
            String name = myMethods.readString();
            
            System.out.println("\nDo you wish to sign up as a donator or as a beneficiary?\n1: Donator\n2: Beneficiary");
            
            try
            {
                if (myMethods.choose(1,2) == 1)
                {
                    newUser = new Donator(name,phone);
                    insertDonator((Donator)newUser);
                    
                    System.out.println("\nYou have successfully signed up as a donator with the following information:\nUsername: "
                    + name + "\nPhone number: " + phone);
                }
                else
                {
                    System.out.print("Number of people in the family: ");
                    int noPersons = myMethods.readInt();
                    
                    newUser = new Beneficiary(name,phone,noPersons);
                    insertBeneficiary((Beneficiary)newUser);
                
                    System.out.println("\nYou have successfully signed up as a beneficiary with the following information:\nUsername: "
                    + name + "\nPhone number: " + phone + "\nPeople: " + noPersons);
                }
            }
            catch(ItemAlreadyInListException e)
            {
                e.toString();
            }
        }
        
        return newUser;
    }
}
