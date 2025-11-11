public class EntityNotInOrganizationException extends Exception
{
    private Entity entity;
    
    public EntityNotInOrganizationException(Entity entity)
    {
        this.entity = entity;
    }
    
    @Override
    public String toString()
    {
        return "Entity \"" + entity.getName() + "\" was not in the organization's entity list!";
    }
}
