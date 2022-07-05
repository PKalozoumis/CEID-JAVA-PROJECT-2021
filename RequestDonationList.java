import java.util.*;

public class RequestDonationList
{
    protected ArrayList<RequestDonation> rdEntities = new ArrayList<RequestDonation>();
    
    public void add(RequestDonation requestDonation) throws EntityNotInOrganizationException
    {
        if (!Main.org.isInEntityList(requestDonation.getEntity())) //If the entity associated with the donation is not in the organization's entity list...
        {
            throw new EntityNotInOrganizationException(requestDonation.getEntity());
        }
        
        boolean exists = false;
        
        for (RequestDonation rd : rdEntities)
        {
            if (rd.getEntity().getId() == requestDonation.getEntity().getId())
            {
                rd.addQuantity(requestDonation.getQuantity());
                exists = true;
                break;
            }
        }
        
        if (!exists)
        {
            rdEntities.add(requestDonation);
        }
    }
    
    public void remove(RequestDonation requestDonation)
    {   
        Iterator<RequestDonation> rdIter = rdEntities.iterator();
        
        while(rdIter.hasNext())
        {
            RequestDonation rd = rdIter.next();
            
            if (rd.getEntity().getId() == requestDonation.getEntity().getId())
            {
                rdIter.remove();
                break;
            }
        }
    }
    
    public void modify(RequestDonation requestDonation, double quantity)
    {
        if (quantity == 0)
        {
            remove(requestDonation);
        }
        else
        {
            for (RequestDonation rd : rdEntities)
            {
                if (rd.getEntity().getId() == requestDonation.getEntity().getId())
                {
                    rd.setQuantity(quantity);
                }
            }
        }
    }
    
    public void reset()
    {
        rdEntities.clear();
    }
    
    public RequestDonation get(int id)
    {
        boolean found = false;
        RequestDonation ret = null;
        
        for (RequestDonation rd : rdEntities)
        {
            int entityID = rd.getEntity().getId();
            
            if (entityID == id)
            {
                found = true;
                ret = rd;
                break;
            }
        }
        
        if (found)
        {
            return ret;
        }
        else
        {
            System.out.println("Entity with an ID of " + id + " could not be found!");
            return null;
        }
    }
    
    public RequestDonation getFromIndex(int index)
    {
        return rdEntities.get(index);
    }
    
    public Material getMaterial(int n)
    {
        int j = 0;
        Material ret = null;
        
        for (RequestDonation rd : rdEntities)
        {
            if (rd.getEntity() instanceof Material)
            {
                j++;
                if (j == n)
                {
                   ret = (Material)(rd.getEntity());
                }
            }    
        }

        return ret;
    }
    
    public Service getService(int n)
    {
        int j = 0;
        Service ret = null;
        
        for (RequestDonation rd : rdEntities)
        {
            if (rd.getEntity() instanceof Service)
            {
                j++;
                if (j == n)
                {
                   ret = (Service)(rd.getEntity());
                }
            }    
        }

        return ret;
    }
    
    public double findItemQuantity(Entity entity)
    {
        double quantity = 0;
        
        for (RequestDonation rd : rdEntities)
        {
            int entityID = rd.getEntity().getId();
            
            if (entityID == entity.getId())
            {
                quantity = rd.getQuantity();
                break;
            }
        }
        
        return quantity;
    }
    
    public int listMaterials()
    {
        String str = "";
        int count = 0;
        
        for (RequestDonation rd : rdEntities)
        {
            if (rd.getEntity() instanceof Material)
            {
                str += "\n" + "  " + (count + 1) + "." + rd.getEntity().getName() + " (" + rd.getQuantity() + ")";
                count++;
            }
        }
        
        if (count>0)
        {
            System.out.println("\nMaterials:" + str);
        }
        else System.out.println("\nNo materials:" + str);
        
        return count;
    }
    
    public int listServices()
    {
        String str = "";
        int count = 0;
        
        for (RequestDonation rd : rdEntities)
        {
            if (rd.getEntity() instanceof Service)
            {
                str += "\n" + "  " + (count + 1) + "." + rd.getEntity().getName() + " (" + rd.getQuantity() + ")";
                count++;
            }
        }
        
        if (count>0)
        {
            System.out.println("\nServices:" + str);
        }
        else System.out.println("\nNo services:" + str);
        
        return count;
    }
    
    public int monitor()
    {
        String str = "";
        int count = 0;
        
        for (RequestDonation rd : rdEntities)
        {
            str += "\n" + "  " + (count + 1) + "." + rd.getEntity().getName() + " (" + rd.getQuantity() + ")";
            count++;
        }
        
        if (count>0)
        {
            System.out.println("\nEntities:" + str);
        }
        else System.out.println("\nNo entities:" + str);
        
        return count;
    }
}
