public class Donator extends User
{
    private Offers offersList = new Offers();
    
    public Donator(String name, String phone)
    {
        super(name,phone);
    }
    
    public void add(RequestDonation requestDonation) throws EntityNotInOrganizationException
    {
        if (requestDonation.getQuantity() > 0)
        {
            offersList.add(requestDonation);
        }
    }
    
    public void remove(RequestDonation requestDonation)
    {
        offersList.remove(requestDonation);
    }
    
    public void modify(RequestDonation requestDonation, double quantity)
    {
        offersList.modify(requestDonation, quantity);
    }
    
    public void commit()
    {
        offersList.commit();
    }
    
    public void reset()
    {
        offersList.reset();
    }
    
    public RequestDonation getFromIndex(int index)
    {
        return offersList.getFromIndex(index);
    }
    
    public int listOfferedEntities()
    {
        return offersList.monitor();
    }
}
