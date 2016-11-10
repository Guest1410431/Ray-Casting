package Player;

import java.awt.Color;
import java.awt.Graphics;

public class HUD
{
	int screenWidth;
	int screenHeight;
	
	public HUD(int sw, int sh)
	{
		screenWidth = sw;
		screenHeight = sh;
	}
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(screenWidth-8, screenHeight-1, 16,2);
		g.fillRect(screenWidth-1, screenHeight-8, 2, 16);
	}
}
