import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;


public class DataBase 
{
	private static final DataBase instance = new DataBase();
	private ArrayList<Entry> entries;
	private UserGroup root;
	
	private DataBase() 
	{
		entries = new ArrayList<Entry>();
		root = new UserGroup("Root");
		entries.add(root);
	}
	
	public static DataBase getInsatnce()
	{
		return instance;
	}
	
	public void addEntry(Entry e)
	{
		entries.add(e);
	}
	
	public Entry getEntry(String id)
	{
		for (Entry e : entries) 
		{
			if(e.getID().equals(id))
			{
				return e;
			}
		}
		return null;
	}
	
	public User getUser(String displayName)
	{
		for (Entry e : entries) 
		{
			boolean isUser = e.getClass().getName().equals("User");
			if(isUser && displayName.equals(e.getDisplayName()))
			{
				return (User)e;
			}
		}
		return null;
	}
	
	public Entry getRoot()
	{
		return root;
	}

	public int getUserCount()
	{
		CountUser vistor = new CountUser();
		int total = 0;
		for(Vistee visitee : entries)
		{
			total += visitee.accept(vistor);
		}
		return total;
	}
	
	public int getGroupCount()
	{
		CountGroup vistor = new CountGroup();
		int total = 0;
		for(Vistee visitee : entries)
		{
			total += visitee.accept(vistor);
		}
		return total;
	}
	
	public int getTweetCount()
	{
		CountTweetsVisitor vistor = new CountTweetsVisitor();
		int total = 0;
		for(Vistee visitee : entries)
		{
			total += visitee.accept(vistor);
		}
		return total;
	}
	
	public String getPositivePercentage()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		CountPositiveTweetsVisitor vistor = new CountPositiveTweetsVisitor();
		double total = 0;
		for(Vistee visitee : entries)
		{
			total += visitee.accept(vistor);
		}
		
		total = (total/getTweetCount()) * 100;
		if(getTweetCount() == 0) total = 0.00;
		String output = df.format(total) + "%";
		return output;
	}

}
