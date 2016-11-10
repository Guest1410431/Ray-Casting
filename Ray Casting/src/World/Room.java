package World;

public class Room
{
	public int xPos;
	public int yPos;
	public int width;
	public int height;
	
	private final int MAX = 6;
	private final int MIN = 3;
	
	public Room(int x, int y, int w, int h)
	{
		xPos = x;
		yPos = y;
		width = w;
		height = h;
	}
	public Room(int x, int y)
	{
		xPos = x;
		yPos = y;
		width = (int)(Math.random()*(MAX-MIN)+MIN);
		height = (int)(Math.random()*(MAX-MIN)+MIN);
	}
	public void thin()
	{
		width--;
	}
	public void shorten()
	{
		height--;
	}
	public boolean contains(int x, int y)
	{
		return x >= xPos && xPos + width >= x && y >= yPos && yPos+height >= y;
	}
}
