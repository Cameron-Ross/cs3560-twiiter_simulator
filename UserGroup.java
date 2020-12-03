import java.util.*;

public class UserGroup implements Entry
{
	private final String id;
	private String displayName;
	private UserGroup parent;
	private ArrayList<Entry> entries;
	private long creationTime;
	
	public UserGroup(String displayName, UserGroup parent) 
	{
		String unique = UUID.randomUUID().toString();
		id = unique.substring(0,unique.length() / 2);
		this.displayName = displayName;
		
		entries = new ArrayList<Entry>();
		
		if(parent == null)
		{
			this.parent = (UserGroup) DataBase.getInsatnce().getRoot();
		}
		else 
		{
			this.parent = parent;
		}
		this.parent.addEntry(this);
		DataBase.getInsatnce().addEntry(this);
		creationTime = System.currentTimeMillis();
	}
	
	public UserGroup(String displayName) 
	{
		String unique = UUID.randomUUID().toString();
		id = unique.substring(0,unique.length() / 2);
		this.displayName = displayName;
		entries = new ArrayList<Entry>();
		parent = null;
	}

	@Override
	public String getID() 
	{
		return id;
	}

	@Override
	public String getDisplayName() 
	{
		return displayName;
	}
	
	public void addEntry(Entry other)
	{
		entries.add(other);
	}
	
	public ArrayList<Entry> getEntries()
	{
		return entries;
	}
	
	@Override
	public UserGroup getParent() 
	{
		return parent;
	}
	
	@Override
	public int accept(EntryVisitor vistor) 
	{
		return vistor.visit(this);
	}
	
	public String toString()
	{
		return displayName;
	}

	@Override
	public long getCreationTime() 
	{
		return creationTime;
	}
}
