import java.util.*;

public class User extends Subject implements Entry, Observer
{
	private final String id;
	private String displayName;
	
	private UserGroup parent;
	
	private ArrayList<String> following;
	private ArrayList<String> feed;
	private ArrayList<String> tweets;
	// follower requirement in parent class
	
	private long creationTime;
	private long lastUpdateTime;
	
	public User(String displayName, UserGroup parent) 
	{
		super();
		String unique = UUID.randomUUID().toString();
		id = unique.substring(0,unique.length() / 2);
		this.displayName = displayName;
		
		following = new ArrayList<String>();
		feed = new ArrayList<String>();
		tweets = new ArrayList<String>();
		
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
		creationTime = lastUpdateTime = System.currentTimeMillis();
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

	public ArrayList<String> getFollowing()
	{
		return following;
	}
	
	public ArrayList<String> getFeed()
	{
		return feed;
	}
	
	public ArrayList<String> getTweets()
	{
		return tweets;
	}
	
	@Override
	public void notifyMe(String post) 
	{
		feed.add(post);
		lastUpdateTime = System.currentTimeMillis();
	}

	public void followUser(String userID)
	{
		following.add(userID);
	}
	
	@Override
	public void post(String post)
	{
		feed.add(post);
		tweets.add(post);
		super.post(post);
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public String toString()
	{
		return displayName;
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

	@Override
	public long getCreationTime() 
	{
		return creationTime;
	}
	
	public long getLastUpdateTime() 
	{
		return lastUpdateTime;
	}

}
