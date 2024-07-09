public class Material extends Entity
{
    private final double[] level = new double[3];
    /*
     * Level 1: 1 person
     * Level 2: 2-4 people
     * Level 3: 5+ people
     */
    
    public Material(String name, String description, int id, double level1, double level2, double level3)
    {
        super(name,description,id);
        level[0] = level1;
        level[1] = level2;
        level[2] = level3;
    }
    
    public double[] getLevels()
    {
        return level;
    }
    
    public String getDetails()
    {
        return getEntityInfo() + "Type: Material.\nLevel 1: " + level[0] + "\nLevel 2: " + level[1] + "\nLevel 3: " + level[2] + "\n";
    }
}
