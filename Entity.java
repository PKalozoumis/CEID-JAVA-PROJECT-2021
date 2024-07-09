public abstract class Entity
{
    private String name = "";
    private String description = "";
    private int id;
    
    public Entity(String name, String description, int id)
    {
        this.name = name;
        this.description = description;
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getId()
    {
        return id;
    }
    
    abstract String getDetails();
    
    public String getEntityInfo()
    {
        return "Name: " + name + "\nDescription: " + description + "\nID: " + String.valueOf(id) + "\n";
    }
    
    @Override
    public String toString()
    {
        return getEntityInfo() + getDetails();
    }
}
