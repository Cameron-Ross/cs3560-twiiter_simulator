public class Driver 
{
	public static void main(String[] args) 
	{
		createDefaultUsers();
		Admin.getInsatnce().Launch();
	}
	
	private static void createDefaultUsers()
	{
		User user1 = new User("John", null);
		User user2 = new User("Amy", null);
		UserGroup group1 = new UserGroup("Group 1", null);
		User user3 = new User("Susan", group1);
		User user4 = new User("Adam", group1);
	}
	
	
}
