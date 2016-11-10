package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

import Player.HUD;
import Utilities.Camera;
import Utilities.Screen;
import Utilities.Texture;
import World.Maze;

public class Game extends JFrame implements Runnable
{
	public static int mapWidth = 5;
	public static int mapHeight = 5;
	public int screenWidth = 640;//Toolkit.getDefaultToolkit().getScreenSize().width;//640:480
	public int screenHeight = 480;//(Toolkit.getDefaultToolkit().getScreenSize().width/4)*3;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	public int[] pixels;
	
	public ArrayList<Texture> textures;
	
	public static boolean mapToggle = true;
	
	public Camera camera;
	public Screen screen;
	public HUD hud;
	public static Maze maze = new Maze(mapWidth, mapHeight);
	//0-Floor, 1-Wood, 2-Red Brick, 3-Blue Stone, 4-Gray Stone
	public static int[][] map = maze.genMap();
	/*public static int[][]map = new int[][]{
		{1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1}
	};
	*/
	
	public Game()
	{
		thread = new Thread(this);
		image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		
		camera = new Camera(1.5, 1.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, screenWidth, screenHeight);
		hud = new HUD(screenWidth, screenHeight);
		
		addKeyListener(camera);
		setSize(screenWidth*2, screenHeight*2);
		setResizable(false);
		setTitle("Ray Casting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		start();
	}

	private synchronized void start()
	{
		running = true;
		thread.start();
	}

	public synchronized void stop()
	{
		running = false;
		
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth()*2, image.getHeight()*2, null);
		
		if(mapToggle)
		{
			drawMap(g);
		}
		//maze.render(g);
		hud.render(g);
		bs.show();
	}

	private void drawMap(Graphics g)
	{
		int ratio = 13;
		
		g.setColor(Color.WHITE);
		g.fillRect((int)(screen.width*.8)*2, 50, (int)(screen.width*.35), (int)(screen.width*.35));
		
		for(int i=0; i<map.length; i++)
		{
			for(int h=0; h<map[0].length; h++)
			{
				if(map[i][h] == 0)
				{
					g.setColor(Color.WHITE);
				}
				if(map[i][h] == 1)
				{
					g.setColor(Color.ORANGE);
				}
				if(map[i][h] == 2)
				{
					g.setColor(Color.RED);
				}
				if(map[i][h] == 3)
				{
					g.setColor(Color.BLUE);
				}
				if(map[i][h] == 4)
				{
					g.setColor(Color.GRAY);
				}
				g.fillRect((int)(screen.width*.8)*2 + 15 + (i*ratio), 65 + (h*ratio), ratio, ratio);
			}
		}
		g.setColor(Color.BLACK);
		//g.drawString(Camera.xPos + " | " + Camera.yPos, 100, 100);
		g.fillOval((int)(Camera.xPos*ratio) + (int)(screen.width*.8)*2 + 13, (int)(Camera.yPos*ratio) + 63, 7, 7);
	}

	public void run()
	{
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;// 60 times per second
		
		double delta = 0;
		requestFocus();
		
		while (running)
		{
			long now = System.nanoTime();
			delta = delta + ((now - lastTime) / ns);
			lastTime = now;
			
			while (delta >= 1)// Make sure update is only happening 60 times a second
			{
				// handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();// displays to the screen unrestricted time
		}
	}
}
