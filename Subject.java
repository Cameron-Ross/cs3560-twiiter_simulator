import java.util.*;
public abstract class Subject 
{
	private ArrayList<Observer> followers;
	
	public Subject()
	{
		followers = new ArrayList<Observer>();
	}
	
	public void post(String post)
	{
		for (Observer observer : followers) 
		{
			observer.notifyMe(post);
		}
	}
	
	public void addFollower(String userID)
	{
		Observer observer =  (Observer) DataBase.getInsatnce().getEntry(userID); 
		followers.add(observer);
	}
	
	public ArrayList<Observer> getFollowers()
	{
		return followers;
	}
	
}
