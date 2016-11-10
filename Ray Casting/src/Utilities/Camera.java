package Utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.Game;

public class Camera implements KeyListener
{
	public static double xPos;
	public static double yPos;
	public double xDir;
	public double yDir;
	public double xPlane;
	public double yPlane;
	public boolean left;
	public boolean right;
	public boolean forward;
	public boolean back;
	public final double MOVE_SPEED = .03;
	public final double ROTATION_SPEED = 0.045;

	public Camera(double x, double y, double xd, double yd, double xp, double yp)
	{
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}

	public void keyPressed(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A)
		{
			left = true;
		}
		if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D)
		{
			right = true;
		}
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W)
		{
			forward = true;
		}
		if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S)
		{
			back = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_M)
		{
			Game.mapToggle = !Game.mapToggle;
		}
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A)
		{
			left = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D)
		{
			right = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W)
		{
			forward = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S)
		{
			back = false;
		}
	}

	public void update(int[][] map)
	{
		if (forward)
		{
			if (map[(int) (xPos + xDir * MOVE_SPEED)][(int) yPos] == 0)
			{
				xPos += xDir * MOVE_SPEED;
			}
			if (map[(int) xPos][(int) (yPos + yDir * MOVE_SPEED)] == 0)
			{
				yPos += yDir * MOVE_SPEED;
			}
		}
		if (back)
		{
			if (map[(int) (xPos - xDir * MOVE_SPEED)][(int) yPos] == 0)
			{
				xPos -= xDir * MOVE_SPEED;
			}
			if (map[(int) xPos][(int) (yPos - yDir * MOVE_SPEED)] == 0)
			{
				yPos -= yDir * MOVE_SPEED;
			}
		}
		if (right)
		{
			double oldxDir = xDir;
			xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
			yDir = oldxDir * Math.sin(-ROTATION_SPEED) + yDir * Math.cos(-ROTATION_SPEED);

			double oldxPlane = xPlane;
			xPlane = xPlane * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
			yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
		}
		if (left)
		{
			double oldxDir = xDir;
			xDir = xDir * Math.cos(ROTATION_SPEED) - yDir * Math.sin(ROTATION_SPEED);
			yDir = oldxDir * Math.sin(ROTATION_SPEED) + yDir * Math.cos(ROTATION_SPEED);

			double oldxPlane = xPlane;
			xPlane = xPlane * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
			yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);
		}
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
}
