import java.io.*;

public class myMethods
{
    public static String readString()
    {
        String str = "";
        char b;
        
        try
        {
            for (int i = 0; (b = (char)System.in.read())!='\n'; i++)
            {
                str += b;
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        return str;
    }
    
    public static int readInt()
    {
        int num = 0;
        
        try
        {
            num = Integer.parseInt(readString());
        }
        catch(NumberFormatException e)
        {
            e.toString();
        }
        
        return num;
    }
    
    public static double readDouble()
    {
        double num = 0;
        
        try
        {
            do
            {
                if (num < 0)
                {
                    System.out.println("This number cannot be negative!");
                }
                
                num = Double.parseDouble(readString());
            }
            while(num < 0);
        }
        catch(NumberFormatException e)
        {
            e.toString();
        }
        
        return num;
    }
    
    public static int choose(int lower, int upper)
    {
        int option;
        
        do
        {   
            option = readInt();

            if ((option < lower)||(option > upper))
            {
                System.out.print("Invalid option. Try again.\n");
            }
        }
        while((option < lower)||(option > upper));
        
        return option;
    }
    
    public static String YNchoose()
    {
        System.out.print("y/n: ");
        String option;
        
        do
        {   
            option = readString();

            if (!(option.equals("y"))&&!(option.equals("n")))
            {
                System.out.print("\nInvalid option. Try again.\ny/n: ");
            }
        }
        while(!(option.equals("y"))&&!(option.equals("n")));
        
        return option;
    }
}
