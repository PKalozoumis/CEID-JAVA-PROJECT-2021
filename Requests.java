public class Requests extends RequestDonationList
{
    public void add(RequestDonation requestDonation, int belongsInLevel, double requestedQuantity, double previouslyRequestedQuantity, double receivedQuantity)
    throws EntityNotInOrganizationException, InsufficientQuantityException, InvalidRequestException
    {
        //Check if the requested quantity has been donated to the organization
        double donatedQuantity = Main.org.getDonatedQuantity(requestDonation.getEntity());
        
        if (requestedQuantity + previouslyRequestedQuantity > donatedQuantity)
        {
            throw new InsufficientQuantityException();
        }
        else //If the requested quantity exists, check if the request is valid
        {  
            if (validRequestDonation(requestDonation, belongsInLevel, requestedQuantity + previouslyRequestedQuantity + receivedQuantity))
            {
                super.add(requestDonation);
            }
            else throw new InvalidRequestException();
        }
    }
    
    public void modify(RequestDonation requestDonation, int belongsInLevel, double newQuantity, double receivedQuantity)
    throws EntityNotInOrganizationException, InsufficientQuantityException, InvalidRequestException
    {   
        double donatedQuantity = Main.org.getDonatedQuantity(requestDonation.getEntity());
        
        if (newQuantity > donatedQuantity)
        {
            throw new InsufficientQuantityException();
        }
        else //If the requested quantity exists, check if the request is valid
        {  
            if (validRequestDonation(requestDonation, belongsInLevel, newQuantity + receivedQuantity))
            {
                super.modify(requestDonation, newQuantity);
            }
            else throw new InvalidRequestException();
        }
    }
    
    public boolean validRequestDonation(RequestDonation requestDonation, int belongsInLevel, double existingQuantity)
    {
        if (requestDonation.getEntity() instanceof Material)
        {
            double[] level = ((Material)requestDonation.getEntity()).getLevels();
            boolean valid = false;
            
            if (existingQuantity <= level[belongsInLevel - 1])
            {
                valid = true;
            }
            
            return valid;
        }
        else return true; //Entity is a service and does not require a check
    }
    
    public int monitor()
    {
        String str = "";
        int count = 0;
        
        for(RequestDonation rd : rdEntities)
        {
            str += "\n" + "  " + (count + 1) + ".REQUEST: " + rd.getEntity().getName() + " (" + rd.getQuantity() + ")";
            count++;
        }
        
        if (count > 0)
        {
            System.out.println("\nRequests:" + str);
        }
        else System.out.println("\nNo requests");
        
        return count;
    }
    
    public void commit(Beneficiary beneficiary)
    {
        for (RequestDonation rd: rdEntities)
        {
            double donatedQuantity = Main.org.getDonatedQuantity(rd.getEntity());
            
            try
            {
                if (!Main.org.isInEntityList(rd.getEntity()))
                {
                    throw new EntityNotInOrganizationException(rd.getEntity());
                }
                else if (rd.getQuantity() > donatedQuantity)
                {
                    throw new InsufficientQuantityException();
                }
                else if (!validRequestDonation(rd, beneficiary.determineLevel(), rd.getQuantity()))
                {
                    throw new InvalidRequestException();
                }
                else
                {
                    Main.org.subtractDonatedQuantity(rd.getEntity(), rd.getQuantity());
                    beneficiary.addToReceivedList(rd);
                }
            }
            
            catch(EntityNotInOrganizationException e)
            {
                System.out.println(e.toString());
            }
            catch(InsufficientQuantityException e)
            {
                System.out.println("For entity " + rd.getEntity().getName() + ": " + e.toString());
            }
            catch(InvalidRequestException e)
            {
                System.out.println("For entity " + rd.getEntity().getName() + ": " + e.toString());
            }
        }
        
        reset();
        System.out.println("\nCommit successful!");
    }
}
