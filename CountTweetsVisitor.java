public class CountTweetsVisitor implements EntryVisitor 
{

	@Override
	public int visit(Entry e) 
	{
		int total = 0;
		if(e.getClass().getName().equals("User"))
		{
			total = ((User)e).getTweets().size();
		}
		return total;
	}

}
