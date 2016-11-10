package World;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Maze
{
	public int width;
	public int height;

	public Cell[][] map;
	private int[][] test;

	ArrayList<Room> rooms = new ArrayList<Room>();

	public Maze(int w, int h)
	{
		width = w;
		height = h;

		map = new Cell[width + 2][height + 2];

		initMaze();
		genMaze();
		test = genMap();
	}

	private void initMaze()
	{
		for (int i = 0; i < width + 2; i++)
		{
			for (int h = 0; h < height + 2; h++)
			{
				map[i][h] = new Cell();
			}
		}
		for (int i = 0; i < width + 2; i++)
		{
			map[i][0].visited = true;
			map[i][height + 1].visited = true;
		}
		for (int h = 0; h < height + 2; h++)
		{
			map[0][h].visited = true;
			map[width + 1][h].visited = true;
		}
	}

	private void genMaze()
	{
		genMaze(1, 1);
		System.out.println("Maze Generation Done");
	}

	private void genMaze(int x, int y)
	{
		map[x][y].visited = true;

		while (!map[x][y + 1].visited || !map[x + 1][y].visited || !map[x][y - 1].visited || !map[x - 1][y].visited)
		{
			while (true)
			{
				int rand = (int) (Math.random() * 4);
				// Down
				if (rand == 0 && !map[x][y + 1].visited && y != height)
				{
					map[x][y].south = false;
					map[x][y + 1].north = false;
					genMaze(x, y + 1);
					break;
				}
				// Right
				else if (rand == 1 && !map[x + 1][y].visited && x != width)
				{
					map[x][y].east = false;
					map[x + 1][y].west = false;
					genMaze(x + 1, y);
					break;
				}
				// Up
				else if (rand == 2 && !map[x][y - 1].visited && y != 1)
				{
					map[x][y].north = false;
					map[x][y - 1].south = false;
					genMaze(x, y - 1);
					break;
				}
				// Left
				else if (rand == 3 && !map[x - 1][y].visited && x != 1)
				{
					map[x][y].west = false;
					map[x - 1][y].east = false;
					genMaze(x - 1, y);
					break;
				}
			}
		}
	}

	public void render(Graphics g)
	{
		int ratio = 64;
		int mapRat = 64 / 3;

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 10000, 10000);

		for (int i = 0; i < width + 2; i++)
		{
			for (int h = 0; h < height + 2; h++)
			{
				if (map[i][h].north)
				{
					g.setColor(Color.RED);
					g.fillRect(i * ratio + 50, h * ratio + 50, ratio, 2);
				}
				if (map[i][h].east)
				{
					g.setColor(Color.ORANGE);
					g.fillRect(i * ratio + ratio + 50, h * ratio + 50, 2, ratio);
				}
				if (map[i][h].south)
				{
					g.setColor(Color.GREEN);
					g.fillRect(i * ratio + 50, h * ratio + ratio + 50, ratio, 2);
				}
				if (map[i][h].west)
				{
					g.setColor(Color.BLUE);
					g.fillRect(i * ratio + 50, h * ratio + 50, 2, ratio);
				}
				g.drawString(i + ":" + h, i * ratio + 50, h * ratio + 50);
			}
		}
		for (int i = 0; i < test.length; i++)
		{
			for (int h = 0; h < test[0].length; h++)
			{
				if (test[i][h] == 1)
				{
					g.setColor(Color.YELLOW);
					g.drawRect((i * mapRat) + 850, (h * mapRat) + 50, mapRat, mapRat);
				}
				if (test[i][h] == 2)
				{
					g.setColor(Color.BLACK);
					g.fillRect((i * mapRat) + 850, (h * mapRat) + 50, mapRat, mapRat);
				}
				if (test[i][h] == 3)
				{
					g.setColor(Color.RED);
					g.drawRect((i * mapRat) + 850, (h * mapRat) + 50, mapRat, mapRat);
				}
			}
		}
	}

	public int[][] genMap()
	{
		int[][] world = new int[(width + 2) * 3][(height + 2) * 3];
		int[][] end = new int[width * 3][height * 3];

		for (int i = 0; i < world.length; i++)
		{
			for (int h = 0; h < world[0].length; h++)
			{
				world[i][h] = 0;
			}
		}

		for (int i = 0; i < width + 2; i++)
		{
			for (int h = 0; h < height + 2; h++)
			{
				if (map[i][h].north)
				{
					world[(i * 3) + 0][h * 3] = 1;
					world[(i * 3) + 1][h * 3] = 1;
					world[(i * 3) + 2][h * 3] = 1;
				}
				if (map[i][h].east)
				{
					world[(i * 3) + 2][(h * 3) + 0] = 1;
					world[(i * 3) + 2][(h * 3) + 1] = 1;
					world[(i * 3) + 2][(h * 3) + 2] = 1;
				}
				if (map[i][h].south)
				{
					world[(i * 3) + 0][(h * 3) + 2] = 1;
					world[(i * 3) + 1][(h * 3) + 2] = 1;
					world[(i * 3) + 2][(h * 3) + 2] = 1;
				}
				if (map[i][h].west)
				{
					world[i * 3][(h * 3) + 0] = 1;
					world[i * 3][(h * 3) + 1] = 1;
					world[i * 3][(h * 3) + 2] = 1;
				}
				if ((!map[i][h].north && map[i][h].east && map[i][h].south && map[i][h].west) || (map[i][h].north && !map[i][h].east && map[i][h].south && map[i][h].west) || (map[i][h].north
						&& map[i][h].east && !map[i][h].south && map[i][h].west) || (map[i][h].north && map[i][h].east && map[i][h].south && !map[i][h].west))
				{
					map[i][h].end = true;
					world[i * 3 + 1][h * 3 + 1] = 2;
				}
			}
		}
		for (int i = 0; i < end.length; i++)
		{
			for (int h = 0; h < end[0].length; h++)
			{
				end[i][h] = world[i + 3][h + 3];
			}
		}
		createRooms(end);

		return end;
	}

	private void createRooms(int[][] end)
	{
		for (int i = 0; i < end.length; i++)
		{
			for (int h = 0; h < end[0].length; h++)
			{
				if (end[i][h] == 2)
				{
					end[i][h] = 0;
					
					Room room = new Room(i, h);

					while ((room.xPos + room.width) >= end.length - 1 && (room.yPos + room.height) >= end[0].length - 1)
						;
					{
						if (room.xPos + room.width >= end.length)
						{
							room.thin();
							continue;
						}
						if (room.yPos + room.height >= end[0].length)
						{
							room.shorten();
							continue;
						}
					}
					rooms.add(room);
				}
			}
		}
		System.out.println("Room Placement Done");
		
		for (Room r : rooms)
		{
			for (int i = 0; i < r.width - 1; i++)
			{
				for (int h = 0; h < r.height - 1; h++)
				{
					end[r.xPos + i][r.yPos + h] = 0;
				}
			}
		}
		System.out.println("Generation Done");
	}
}
