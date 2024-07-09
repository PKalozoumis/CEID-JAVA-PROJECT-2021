public class Offers extends RequestDonationList
{
    public void commit()
    {
        for (RequestDonation rd : rdEntities)
        {
            try
            {
                Main.org.addDonation(rd);
            }
            catch (EntityNotInOrganizationException e)
            {
                System.out.println(e.toString());
            }
        }
        
        reset();
    }
    
    @Override
    public int monitor()
    {
        String str = "";
        int count = 0;
        
        for(RequestDonation rd : rdEntities)
        {
            str += "\n" + "  " + (count + 1) + ".OFFER: " + rd.getEntity().getName() + " (" + rd.getQuantity() + ")";
            count++;
        }
        
        if (count > 0)
        {
            System.out.println("\nOffers:" + str);
        }
        else System.out.println("\nNo offers");
        
        return count;
    }
}
