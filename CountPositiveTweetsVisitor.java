import java.text.DecimalFormat;

public class CountPositiveTweetsVisitor implements EntryVisitor
{

	@Override
	public int visit(Entry e) 
	{
		int total = 0;
		if(e.getClass().getName().equals("User"))
		{
			for(String s : ((User)e).getTweets())
			{
				String msg = s.toLowerCase();
				if(msg.contains("good") || msg.contains("great") || msg.contains("excellent"))
				{
					total++;
				}
			}
		}
		return total;
	}

}
