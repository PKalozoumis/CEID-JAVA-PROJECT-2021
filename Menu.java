public class Menu
{   
    private static boolean loggedInStatus = false;
    private static boolean exit = false;
    
    public static void mainMenu()
    {
        int currentMenu = 0;
        
        do //For staying in the application (this runs again after logging out)
        {   
            String phone;
            System.out.print("Give a phone number: ");
            phone = myMethods.readString();
            
            User loggedIn = Main.org.findRegisteredUser(phone);
            loggedInStatus = true;
            
            if (loggedIn != null)
            {
                System.out.println
                (
                    "\nWelcome!\n~~~~~~~~~~~~~~~~~~~~~\n" + 
                    "Organization name: " + Main.org.getName() +
                    "\nName: " + loggedIn.getName() + ((loggedIn instanceof Admin)?(" (ADMIN)"):(""))
                );
            }
            else
            {
                exit = true;
                loggedInStatus = false;
                break;
            }
        
            do //For staying logged in (loggedInStatus = true)
            {
                //~~~~~~~~~~~~~~~~~~~~~~~~~~ DONATOR ~~~~~~~~~~~~~~~~~~~~~~~~~~
                if (loggedIn instanceof Donator)
                {
                    do //Main menu loop (currentMenu = 0)
                    {
                        System.out.print
                        (
                            "\nWhat would you like to do?\n" +
                            "  1. Add Offer\n" +
                            "  2. Show Offers\n" +
                            "  3. Commit\n" +
                            "  4. Logout\n" +
                            "  5. Exit\n"
                        );
                        
                        switch(myMethods.choose(1,5))
                        {
                            case 1: //Add Offer
                            {
                                do //Category menu loop  (currentMenu = 1)
                                {
                                    currentMenu = 1;
                                    
                                    System.out.print
                                    (
                                        "\nChoose a category:\n" +
                                        "  1. Materials\n" +
                                        "  2. Services\n" +
                                        "  3. Back\n"
                                    );
                                    
                                    int categoryOption = myMethods.choose(1,3);
                                    
                                    do  //Materials/services loop
                                    {
                                        currentMenu = 2;
                                        
                                        switch(categoryOption)
                                        {
                                            case 1: //CATEGORY MENU: Materials
                                            {
                                                int materialCount = Main.org.listMaterials();
                                                System.out.print("  " + (materialCount + 1) + ".Back\n");
                                                int materialOption = myMethods.choose(1,materialCount + 1);
                                                
                                                if (materialOption != materialCount + 1) //If the user didn't choose to go back
                                                {
                                                    Material chosenMaterial = Main.org.getMaterial(materialOption);
                                                    
                                                    System.out.print("\n" + chosenMaterial.getDetails() +
                                                    "\nWill you donate this material? ");
                                                    
                                                    if (myMethods.YNchoose().equals("y")) //If the user wants to donate this entity
                                                    {
                                                        System.out.print("\nGive the quantity you want to donate: ");
                                                        try
                                                        {
                                                            ((Donator)loggedIn).add(new RequestDonation //New donation
                                                            (
                                                            chosenMaterial, //Donated material 
                                                            myMethods.readDouble() //Donated quantity
                                                            ));
                                                        }
                                                        catch (EntityNotInOrganizationException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            e.printStackTrace();
                                                        }
                                                        finally {currentMenu = 0;}
                                                    }
                                                    else currentMenu = 1;  //If the user does not want to donate this entity
                                                }
                                                else currentMenu = 1; //Go back
                                            }
                                            break;
                                            
                                            case 2: //CATEGORY MENU: Services
                                            {
                                                int serviceCount = Main.org.listServices();
                                                System.out.print("  " + (serviceCount + 1) + ".Back\n");
                                                int serviceOption = myMethods.choose(1,serviceCount + 1);
                                                
                                                if (serviceOption != serviceCount + 1) //If the user didn't choose to go back
                                                {
                                                    Service chosenService = Main.org.getService(serviceOption);
                                                    
                                                    System.out.print("\n" + chosenService.getDetails() +
                                                    "\nWill you provide this service? ");
                                                    
                                                    if (myMethods.YNchoose().equals("y"))  //If the user wants to donate this entity
                                                    {
                                                        System.out.print("\nGive the hours you want to provide: ");
                                                        try
                                                        {
                                                            ((Donator)loggedIn).add(new RequestDonation //New donation
                                                            (
                                                            chosenService, //Donated service
                                                            myMethods.readDouble() //Donated quantity
                                                            ));
                                                        }
                                                        catch (EntityNotInOrganizationException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            e.printStackTrace();
                                                        }
                                                        finally {currentMenu = 0;}
                                                    }
                                                    else currentMenu = 1;  //If the user does not want to donate this entity
                                                }
                                                else currentMenu = 1; //Go back
                                            }
                                            break;
                                            
                                            case 3: //Back to main menu
                                            {
                                                currentMenu = 0;
                                            }
                                            break;
                                        }
                                    }
                                    while(currentMenu == 2); //Materials/services menu loop
                                }
                                while(currentMenu == 1); //Category menu loop
                            }
                            break;
                            
                            case 2: //Show offers
                            {
                                do //Offers menu loop(currentMenu = 1)
                                {
                                    currentMenu = 1;
                                    
                                    int offerCount = ((Donator)loggedIn).listOfferedEntities();
                                    System.out.print
                                    (
                                        "  " + (offerCount + 1) + ".Clear all offers" + "\n" +
                                        "  " + (offerCount + 2) + ".Commit" + "\n" +
                                        "  " + (offerCount + 3) + ".Back\n"
                                    );
                                    
                                    int offerOption = myMethods.choose(1, offerCount + 3);
                                    
                                    if (offerOption == offerCount + 1) //Clear offers
                                    {
                                        ((Donator)loggedIn).reset();
                                        System.out.println("\nCleared all existing offers successfully!");
                                        currentMenu = 0;
                                    }
                                    else if (offerOption == offerCount + 2) //Commit
                                    {
                                        ((Donator)loggedIn).commit();
                                        System.out.println("\nCommit successful!");
                                        currentMenu = 0;
                                    }
                                    else if (offerOption == offerCount + 3) //Back
                                    {
                                        currentMenu = 0;
                                    }
                                    else //Choose an offer
                                    {
                                        currentMenu = 2;
                                        RequestDonation chosenOffer= ((Donator)loggedIn).getFromIndex(offerOption - 1);
                                        
                                        do //For modifying the offer (currentMenu = 2)
                                        {
                                            System.out.print
                                            (
                                            "\nYou have chosen the offer: " + chosenOffer.getEntity().getName() + " (" + chosenOffer.getQuantity() + ")" +
                                            "\nWhat do you want to do?\n" +
                                            "  1.Delete this offer\n" +
                                            "  2.Modify this offer\n" +
                                            "  3.Back\n"
                                            );
                                            
                                            int modifyOption = myMethods.choose(1,3);
                                            
                                            switch(modifyOption)
                                            {
                                                case 1: //Delete offer
                                                {
                                                    ((Donator)loggedIn).remove(chosenOffer);
                                                    System.out.println("\nOffer deleted successfully!");
                                                    currentMenu = 1;
                                                }
                                                break;
                                                
                                                case 2: //Modify offer
                                                {
                                                    System.out.print("\nGive the new quantity of the offer: ");
                                                    ((Donator)loggedIn).modify(chosenOffer, myMethods.readDouble());
                                                    System.out.println("\nOffer modified successfully!");
                                                    currentMenu = 1;
                                                }
                                                break;
                                                case 3: /*Back*/ currentMenu = 1; break;
                                            }
                                        }
                                        while(currentMenu == 2); //For modifying the offer (currentMenu = 2)
                                    }
                                }
                                while(currentMenu == 1); //Offers menu loop(currentMenu = 1)
                            }
                            break;
                            
                            case 3: //Commit 
                            {
                                ((Donator)loggedIn).commit();
                                System.out.println("\nCommit successful!");
                            }
                            break;
                            
                            case 4: //Logout
                            {
                                logout(loggedIn);
                            }
                            break;
                            
                            case 5: //Exit
                            {
                                System.out.println("\nAre you sure you want to exit?");
                                if(myMethods.YNchoose().equals("y"))
                                {
                                    exit = true;
                                    loggedInStatus = false;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    while((currentMenu == 0)&&(exit == false)&&(loggedInStatus == true)); //Main menu loop
                }
                //~~~~~~~~~~~~~~~~~~~~~~~~~~ BENEFICIARY ~~~~~~~~~~~~~~~~~~~~~~~~~~
                else if (loggedIn instanceof Beneficiary)
                {
                    do //Main menu loop (currentMenu = 0)
                    {
                        System.out.println
                        (
                            "\nWhat would you like to do?\n" +
                            "  1. Add Request\n" +
                            "  2. Show Requests\n" +
                            "  3. Commit\n" +
                            "  4. Logout\n" +
                            "  5. Exit"
                        );
                        
                        switch(myMethods.choose(1,5))
                        {
                            case 1: //Add request
                            {
                                do //Category menu loop  (currentMenu = 1)
                                {
                                    currentMenu = 1;
                                    
                                    System.out.print
                                    (
                                        "\nChoose a category:\n" +
                                        "  1. Materials\n" +
                                        "  2. Services\n" +
                                        "  3. Back\n"
                                    );
                                    
                                    do  //Materials/services loop
                                    {
                                        currentMenu = 2;
                                        
                                        switch(myMethods.choose(1,3))
                                        {
                                            case 1: //Materials
                                            {
                                                int materialCount = Main.org.listDonatedMaterials();
                                                System.out.print("  " + (materialCount + 1) + ".Back\n");
                                                int materialOption = myMethods.choose(1,materialCount + 1);
                                                
                                                if (materialOption != materialCount + 1) //If the user didn't choose to go back
                                                {
                                                    Material chosenMaterial = Main.org.getDonatedMaterial(materialOption);
                                                    
                                                    System.out.print("\n" + chosenMaterial.getDetails() +
                                                    "\nYou belong in level " + ((Beneficiary)loggedIn).determineLevel() +
                                                    "\nYou have already received " + ((Beneficiary)loggedIn).getReceivedQuantity(chosenMaterial) + " x " + chosenMaterial.getName() +
                                                    "\nYou have already requested " + ((Beneficiary)loggedIn).getPreviouslyRequestedQuantity(chosenMaterial) + " x " + chosenMaterial.getName() +
                                                    "\n\nDo you want to receive this material? ");
                                                    
                                                    if (myMethods.YNchoose().equals("y")) //If the user wants to receive this entity
                                                    {
                                                        System.out.print("\nGive the quantity you want to receive: ");
                                                        try
                                                        {
                                                            ((Beneficiary)loggedIn).add(new RequestDonation //New request
                                                            (
                                                            chosenMaterial, //Requested material 
                                                            myMethods.readDouble() //Requested quantity
                                                            ));
                                                        }
                                                        catch (EntityNotInOrganizationException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        catch (InsufficientQuantityException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        catch (InvalidRequestException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        finally{currentMenu = 0;} 
                                                    }
                                                    else currentMenu = 1;  //If the user does not want to receive this entity
                                                }
                                                else currentMenu = 1; //Go back
                                            }
                                            break;
                                            
                                            case 2: //Services
                                            {
                                                int serviceCount = Main.org.listDonatedServices();
                                                System.out.print("  " + (serviceCount + 1) + ".Back\n");
                                                int serviceOption = myMethods.choose(1,serviceCount + 1);
                                                
                                                if (serviceOption != serviceCount + 1) //If the user didn't choose to go back
                                                {
                                                    Service chosenService = Main.org.getDonatedService(serviceOption);
                                                    
                                                    System.out.print("\n" + chosenService.getDetails() +
                                                    "\nYou have already received " + ((Beneficiary)loggedIn).getReceivedQuantity(chosenService) + " x " + chosenService.getName() +
                                                    "\nYou have already requested " + ((Beneficiary)loggedIn).getPreviouslyRequestedQuantity(chosenService) + " x " + chosenService.getName() +
                                                    "\n\nDo you want to receive this service? ");
                                                    
                                                    if (myMethods.YNchoose().equals("y"))  //If the user wants to receive this entity
                                                    {
                                                        System.out.print("\nGive the hours you want to receive: ");
                                                        try
                                                        {
                                                            ((Beneficiary)loggedIn).add(new RequestDonation //New request
                                                            (
                                                            chosenService, //Requested service
                                                            myMethods.readDouble() //Donated quantity
                                                            ));
                                                        }
                                                        catch (EntityNotInOrganizationException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        catch (InsufficientQuantityException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        catch (InvalidRequestException e)
                                                        {
                                                            System.out.println(e.toString());
                                                            //e.printStackTrace();
                                                        }
                                                        finally{currentMenu = 0;} 
                                                    }
                                                    else currentMenu = 1;  //If the user does not want to receive this entity
                                                }
                                                else currentMenu = 1; //Go back
                                            }
                                            break;
                                            
                                            case 3: //Back to main menu
                                            {
                                                currentMenu = 0;
                                            }
                                            break;
                                        }
                                    }
                                    while(currentMenu == 2); //Materials/services menu loop
                                }
                                while(currentMenu == 1); //Category menu loop
                            }
                            break;
                            
                            case 2: //Show requests
                            {
                                do //Requests menu loop(currentMenu = 1)
                                {
                                    currentMenu = 1;
                                    
                                    int requestCount = ((Beneficiary)loggedIn).listRequestedEntities();
                                    System.out.print
                                    (
                                        "  " + (requestCount + 1) + ".Clear all requests" + "\n" +
                                        "  " + (requestCount + 2) + ".Commit" + "\n" +
                                        "  " + (requestCount + 3) + ".Back\n"
                                    );
                                    
                                    int requestOption = myMethods.choose(1, requestCount + 3);
                                    
                                    if (requestOption == requestCount + 1) //Clear requests
                                    {
                                        ((Beneficiary)loggedIn).reset();
                                        System.out.println("\nCleared all existing requests successfully!");
                                        currentMenu = 0;
                                    }
                                    else if (requestOption == requestCount + 2) //Commit
                                    {
                                        ((Beneficiary)loggedIn).commit();
                                        currentMenu = 0;
                                    }
                                    else if (requestOption == requestCount + 3) //Back
                                    {
                                        currentMenu = 0;
                                    }
                                    else //Choose a request
                                    {
                                        currentMenu = 2;
                                        RequestDonation chosenRequest= ((Beneficiary)loggedIn).getFromIndex(requestOption - 1);
                                        
                                        do //For modifying the request (currentMenu = 2)
                                        {
                                            System.out.print
                                            (
                                            "\nYou have chosen the request: " + chosenRequest.getEntity().getName() + " (" + chosenRequest.getQuantity() + ")" +
                                            "\nWhat do you want to do?\n" +
                                            "  1.Delete this request\n" +
                                            "  2.Modify this request\n" +
                                            "  3.Back\n"
                                            );
                                            
                                            switch(myMethods.choose(1,3))
                                            {
                                                case 1: //Delete request
                                                {
                                                    ((Beneficiary)loggedIn).remove(chosenRequest);
                                                    System.out.println("\nRequest deleted successfully!");
                                                    currentMenu = 1;
                                                }
                                                break;
                                                
                                                case 2: //Modify request
                                                {
                                                    System.out.print("\nGive the new quantity of the request: ");
                                                    try
                                                    {
                                                        ((Beneficiary)loggedIn).modify(chosenRequest, myMethods.readDouble());
                                                        System.out.println("\nRequest modified successfully!");
                                                        currentMenu = 1;
                                                    }
                                                    catch (EntityNotInOrganizationException e)
                                                    {
                                                        System.out.println(e.toString());
                                                        currentMenu = 0;
                                                    }
                                                    catch (InsufficientQuantityException e)
                                                    {
                                                        System.out.println(e.toString());
                                                        //e.printStackTrace();
                                                    }
                                                    catch (InvalidRequestException e)
                                                    {
                                                        System.out.println(e.toString());
                                                        //e.printStackTrace();
                                                    }
                                                }
                                                break;
                                                case 3: /*Back*/ currentMenu = 1; break;
                                            }
                                        }
                                        while(currentMenu == 2); //For modifying the request (currentMenu = 2)
                                    }
                                }
                                while(currentMenu == 1); //Offers menu loop(currentMenu = 1)
                            }
                            break;
                            
                            case 3: //Commit
                            {
                                ((Beneficiary)loggedIn).commit();
                            }
                            break;
                            
                            case 4: logout(loggedIn); break;                     
                            case 5: //Exit 
                            {
                                System.out.println("\nAre you sure you want to exit?");
                                if(myMethods.YNchoose().equals("y"))
                                {
                                    exit = true;
                                    loggedInStatus = false;
                                    break;
                                }
                            }
                        }
                    }
                    while((currentMenu == 0)&&(exit == false)&&(loggedInStatus == true)); //Main menu loop (currentMenu = 0)
                }
                //~~~~~~~~~~~~~~~~~~~~~~~~~~ ADMINISTRATOR ~~~~~~~~~~~~~~~~~~~~~~~~~~
                else if (loggedIn instanceof Admin)
                {
                    do //Main menu loop (currentMenu = 0)
                    {
                        System.out.println
                        (
                            "\nWhat would you like to do?\n" +
                            "  1. View\n" +
                            "  2. Monitor Organization\n" +
                            "  3. Logout\n" +
                            "  4. Exit"
                        );
                        
                        switch(myMethods.choose(1,4))
                        {
                            case 1: //View
                            {
                                currentMenu = 1;
                                do //For the category menu (currentMenu = 1)
                                {
                                    System.out.print
                                    (
                                        "\nChoose a category:\n" + 
                                        "  1.Materials\n" + 
                                        "  2.Services\n" +
                                        "  3.Back\n"
                                    );
                                    
                                    switch(myMethods.choose(1,3))
                                    {
                                        case 1: //Materials
                                        {
                                            currentMenu = 2;
                                            
                                            do
                                            {
                                                int materialCount = Main.org.listDonatedMaterials();
                                                System.out.print("  " + (materialCount + 1) + ".Back\n");
                                                int materialOption = myMethods.choose(1,materialCount + 1);
                                                
                                                if (materialOption == materialCount + 1)
                                                {
                                                    currentMenu = 1;
                                                }
                                                else
                                                {
                                                    Material chosenMaterial = Main.org.getDonatedMaterial(materialOption);
                                                    System.out.print("\n" + chosenMaterial.getDetails());
                                                }
                                            }
                                            while(currentMenu == 2);
                                            
                                        }
                                        break;
                                        
                                        case 2: //Services
                                        {
                                            currentMenu = 2;
                                            
                                            do
                                            {
                                                int serviceCount = Main.org.listDonatedServices();
                                                System.out.print("  " + (serviceCount + 1) + ".Back\n");
                                                int serviceOption = myMethods.choose(1,serviceCount + 1);
                                                
                                                if (serviceOption == serviceCount + 1)
                                                {
                                                    currentMenu = 1;
                                                }
                                                else
                                                {
                                                    Service chosenService = Main.org.getDonatedService(serviceOption);
                                                    System.out.print("\n" + chosenService.getDetails());
                                                }
                                            }
                                            while(currentMenu == 2);
                                            
                                        }
                                        break;
                                        
                                        case 3: currentMenu = 0; break;
                                    }
                                }
                                while (currentMenu == 1);  //For the category menu (currentMenu = 1)
                            }
                            break;
                            
                            case 2: //Monitor organization
                            {
                                currentMenu = 1;
                                do //Monitor menu (currentMenu = 1)
                                {
                                    System.out.print
                                    (
                                        "\nMonitoring organization:\n" +
                                        "  1. List Beneficiaries\n" +
                                        "  2. List Donators\n" +
                                        "  3. Reset Beneficiaries Lists\n" +
                                        "  4. Back\n"
                                    );

                                    switch(myMethods.choose(1,4))
                                    {
                                        case 1: //List beneficiaries
                                        {
                                            currentMenu = 2;
                                            
                                            do //Beneficiary list menu (currentMenu = 2)
                                            {
                                                int beneficiaryCount = Main.org.listBeneficiaries();
                                                System.out.println("  " + (beneficiaryCount + 1) + ".Back");
                                                
                                                int beneficiaryOption = myMethods.choose(1,beneficiaryCount + 1);
                                                
                                                if (beneficiaryOption == beneficiaryCount + 1) //Go back
                                                {
                                                    currentMenu = 1;
                                                }
                                                else
                                                {
                                                    Beneficiary chosenBeneficiary = Main.org.getBeneficiary(beneficiaryOption);
                                                    currentMenu = 3;
                                                    
                                                    do  //Chosen beneficiary menu (currentMenu = 3)
                                                    {
                                                        System.out.print
                                                        (
                                                            "\nFor beneficiary \"" + chosenBeneficiary.getName() + "\":\n" +
                                                            "  1.Show received entities\n" +
                                                            "  2.Clear received list\n" +
                                                            "  3.Delete beneficiary\n" +
                                                            "  4.Back\n"
                                                        );
                                                        
                                                        switch(myMethods.choose(1,4))
                                                        {
                                                            case 1: chosenBeneficiary.listReceivedEntities(); currentMenu = 3; break;
                                                            case 2: chosenBeneficiary.clearReceivedList(); currentMenu = 3; break;
                                                            case 3: Main.org.removeBeneficiary(chosenBeneficiary); currentMenu = 2; break;
                                                            case 4: currentMenu = 2; break;
                                                        }
                                                    }
                                                    while(currentMenu == 3); //Chosen beneficiary menu (currentMenu = 3)
                                                }
                                            }
                                            while(currentMenu == 2); //Beneficiary list menu (currentMenu = 2)
                                        }
                                        break;
                                        
                                        case 2: //List donators
                                        {
                                            currentMenu = 2;
                                            
                                            do //Donator list menu (currentMenu = 2)
                                            {
                                                int donatorCount = Main.org.listDonators();
                                                System.out.println("  " + (donatorCount + 1) + ".Back");
                                                
                                                int donatorOption = myMethods.choose(1,donatorCount + 1);
                                                
                                                if (donatorOption == donatorCount + 1) //Go back
                                                {
                                                    currentMenu = 1;
                                                }
                                                else
                                                {
                                                    Donator chosenDonator = Main.org.getDonator(donatorOption);
                                                    currentMenu = 3;
                                                    
                                                    do  //Chosen donator menu (currentMenu = 3)
                                                    {
                                                        System.out.print
                                                        (
                                                            "\nFor donator \"" + chosenDonator.getName() + "\":\n" +
                                                            "  1.Show offered entities\n" +
                                                            "  2.Delete donator\n" +
                                                            "  4.Back\n"
                                                        );
                                                        
                                                        switch(myMethods.choose(1,4))
                                                        {
                                                            case 1: chosenDonator.listOfferedEntities(); currentMenu = 3; break;
                                                            case 2: Main.org.removeDonator(chosenDonator); currentMenu = 2; break;
                                                            case 4: currentMenu = 2; break;
                                                        }
                                                    }
                                                    while(currentMenu == 3); //Chosen donator menu (currentMenu = 3)
                                                }
                                            }
                                            while(currentMenu == 2); //Donator list menu (currentMenu = 2)
                                        }
                                        break;
                                        
                                        case 3: //Reset beneficiaries lists
                                        {
                                            Main.org.resetBeneficiariesLists();
                                        }
                                        break;
                                        
                                        case 4: //Back
                                        {
                                            currentMenu = 0;
                                        }
                                        break;
                                    }
                                }
                                while (currentMenu == 1);  //Monitor menu (currentMenu = 1)
                            }
                            break;
                            
                            case 3: /*Logout*/ logout(loggedIn); break;
                            case 4: //Exit 
                            {
                                System.out.println("\nAre you sure you want to exit?");
                                if(myMethods.YNchoose().equals("y"))
                                {
                                    exit = true;
                                    loggedInStatus = false;
                                    break;
                                }
                            }
                        }
                    }
                    while((currentMenu == 0)&&(exit == false)&&(loggedInStatus == true)); //Main menu loop (currentMenu = 0)
                }
            }
            while(loggedInStatus); //For staying logged in (loggedInStatus = true)
        }
        while (!exit); //For staying in the application (this runs again after logging out)
        
        System.exit(0);
    }
    
    private static void logout(User loggedIn)
    {
        System.out.println("\nUser \"" + loggedIn.getName() + "\" has been logged out.\n" + 
        "Do you want to login as another user?");
        
        if((myMethods.YNchoose()).equals("y"))
        {
            loggedInStatus = false;
        }
        else
        {
            exit = true;
            loggedInStatus = false;
        }
    }
}
