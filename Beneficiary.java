public class Beneficiary extends User
{
    private int noPersons = 1;  
    private RequestDonationList receivedList = new RequestDonationList(); //List of all the items that have been received
    private Requests requestsList = new Requests(); //List of all the items that have been requested
    
    public Beneficiary(String name, String phone, int noPersons)
    {
        super(name,phone);
        this.noPersons = noPersons;
    }
    
    public int determineLevel()
    {
        int belongsInLevel = 0;
        
        if (noPersons == 1)
        {
            belongsInLevel = 1;
        }
        else if (noPersons < 5)
        {
            belongsInLevel = 2;
        }
        else belongsInLevel = 3;
        
        return belongsInLevel;
    }
    
    //Methods that handle requestsList
    public void add(RequestDonation requestDonation) throws EntityNotInOrganizationException, InsufficientQuantityException, InvalidRequestException
    {
        if (requestDonation.getQuantity() > 0)
        {
            requestsList.add
            (
                requestDonation,
                determineLevel(),
                requestDonation.getQuantity(), //Requested quantity
                getPreviouslyRequestedQuantity(requestDonation.getEntity()), //Previously requested quantity
                getReceivedQuantity(requestDonation.getEntity()) //Already received quantity
            );
        }
    }
    
    public void remove(RequestDonation requestDonation)
    {
        requestsList.remove(requestDonation);
    }
    
    public void modify(RequestDonation requestDonation, double newQuantity)
    throws EntityNotInOrganizationException, InsufficientQuantityException, InvalidRequestException
    {
        requestsList.modify
        (
            requestDonation,
            determineLevel(),
            newQuantity,
            getReceivedQuantity(requestDonation.getEntity())
        );
    }
    
    public void commit()
    {
        requestsList.commit(this);
    }
    
    public void reset()
    {
        requestsList.reset();
    }
    
    public RequestDonation getFromIndex(int index)
    {
        return requestsList.getFromIndex(index);
    }
    
    //Methods that handle receivedList
    public void addToReceivedList(RequestDonation requestDonation)
    {
        try
        {
            receivedList.add(requestDonation);
        }
        catch (EntityNotInOrganizationException e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void clearReceivedList()
    {
        receivedList.reset();
        
        System.out.println("\nReceived list for \"" + this.getName() + "\" cleared successfully!");
    }
    
    //Methods for printing lists
    public int listRequestedEntities()
    {
        return requestsList.monitor();
    }
    
    public void listReceivedEntities()
    {
        receivedList.monitor();
    }
    
    //Methods for getting quantity
    public double getReceivedQuantity(Entity entity)
    {   
        return receivedList.findItemQuantity(entity);
    }
    
    public double getPreviouslyRequestedQuantity(Entity entity)
    {        
        return requestsList.findItemQuantity(entity);
    }
}
