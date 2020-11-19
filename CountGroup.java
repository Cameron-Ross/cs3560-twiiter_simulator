public class CountGroup implements EntryVisitor 
{

	@Override
	public int visit(Entry e) 
	{
		if(e.getClass().getName().equals("User"))
		{
			return 0;
		}
		return 1;
	}

}
