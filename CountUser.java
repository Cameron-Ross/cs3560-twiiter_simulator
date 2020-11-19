
public class CountUser implements EntryVisitor {

	@Override
	public int visit(Entry e) 
	{
		if(e.getClass().getName().equals("User"))
		{
			return 1;
		}
		return 0;
	}

}
