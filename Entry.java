public interface Entry extends Vistee
{
	public String getID();
	
	public String getDisplayName();
	
	public UserGroup getParent();
	
	public long getCreationTime();
}
