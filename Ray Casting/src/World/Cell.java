package World;

public class Cell
{
	public boolean north;
	public boolean east;
	public boolean south;
	public boolean west;
	public boolean visited;
	
	public boolean end = false;
	
	public Cell()
	{
		north = true;
		east = true;
		south = true;
		west = true;
		visited = false;
	}
	public Cell(boolean n, boolean e, boolean s, boolean w)
	{
		north = n;
		east = e;
		south = s;
		west = w;
		visited = false;
	}
}
