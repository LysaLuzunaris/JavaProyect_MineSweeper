
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game
{
	private static int width = 40;
	private static int height = 40;
	
	private final int AMOUNT_OF_BOMBS = 40;
	
	private boolean finish;
	private boolean dead;
	
	private Random random;
	
	
	private board[] []boards;
	
	private BufferedImage bomb = ImageLoad.scale(ImageLoad.loadImage("pic/bomb.png"), board.getWidth(), board.getHeight());
	private BufferedImage flag = ImageLoad.scale(ImageLoad.loadImage("pic/flag.png"), board.getWidth(), board.getHeight());
	private BufferedImage pressed = ImageLoad.scale(ImageLoad.loadImage("pic/pressed.png"), board.getWidth(), board.getHeight());
	private BufferedImage normal = ImageLoad.scale(ImageLoad.loadImage("pic/normal.png"), board.getWidth(), board.getHeight());
	
	public Game()
	{
		random = new Random();
		
		boards = new board[width] [height];
		
		for(int x = 0;x < width;x++)
		{
			for(int y = 0;y < height;y++)
			{
				boards[x] [y] = new board(x, y, normal, bomb, pressed, flag);
			}
		}
		
		reset();
	}
	
	private void placeBombs()
	{
		for(int i = 0;i < AMOUNT_OF_BOMBS;i++)
		{
			placeBomb();
		}
	}
	
	private void placeBomb()
	{
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		
		if(!boards[x] [y].isBomb()) boards[x] [y].setBomb(true);
		else placeBomb();
	}
	
	private void setNumbers()
	{
		for(int x = 0;x < width;x++)
		{
			for(int y = 0;y < height;y++)
			{
				int mx = x - 1;
				int gx = x + 1;
				int my = y - 1;
				int gy = y + 1;
				
				int amountOfBombs = 0;
				if(mx >= 0&&my >= 0&&boards[mx] [my].isBomb()) amountOfBombs++;
				if(mx >= 0&&boards[mx] [y].isBomb()) amountOfBombs++;
				if(mx >= 0&&gy < height&&boards[mx] [gy].isBomb()) amountOfBombs++;
				
				if(my >= 0&&boards[x] [my].isBomb()) amountOfBombs++;
				if(gy < height&&boards[x] [gy].isBomb()) amountOfBombs++;
				
				if(gx < width&&my >= 0&&boards[gx] [my].isBomb()) amountOfBombs++;
				if(gx < width&&boards[gx] [y].isBomb()) amountOfBombs++;
				if(gx < width&&gy < height&&boards[gx] [gy].isBomb()) amountOfBombs++;
				
				boards[x] [y].setAmountOfNearBombs(amountOfBombs);
			}
		}
	}
	
	public void clickedLeft(int x, int y)
	{
		if(!dead&&!finish)
		{
			int boardX = x/board.getWidth();
			int boardY = y/board.getHeight();
			
			if(!boards[boardX] [boardY].isFlag())
			{
				boards[boardX] [boardY].setOpened(true);
				
				if(boards[boardX] [boardY].isBomb()) dead = true;
				else
				{
					if(boards[boardX] [boardY].getAmountOfNearBombs() == 0) 
					{
						open(boardX, boardY);
					}
				}
				
				checkFinish();
			}
		}
	}
	
	public void clickedRight(int x, int y)
	{
		if(!dead&&!finish)
		{
			int boardX = x/board.getWidth();
			int boardY = y/board.getHeight();
			boards[boardX] [boardY].placeFlag();
			
			checkFinish();
		}
	}
	
	private void open(int x, int y)
	{
		boards[x] [y].setOpened(true);
		if(boards[x] [y].getAmountOfNearBombs() == 0)
		{
			int mx = x - 1;
			int gx = x + 1;
			int my = y - 1;
			int gy = y + 1;
			

			if(mx >= 0&&my >= 0&&boards[mx] [my].canOpen()) open(mx, my);
			if(mx >= 0&&boards[mx] [y].canOpen()) open(mx, y);
			if(mx >= 0&&gy < height&&boards[mx] [gy].canOpen()) open(mx, gy);
			
			if(my >= 0&&boards[x] [my].canOpen()) open(x, my);
			if(gy < height&&boards[x] [gy].canOpen()) open(x, gy);
			
			if(gx < width&&my >= 0&&boards[gx] [my].canOpen()) open(gx, my);
			if(gx < width&&boards[gx] [y].canOpen()) open(gx, y);
			if(gx < width&&gy < height&&boards[gx] [gy].canOpen()) open(gx, gy);
			
//			if(mx >= 0&&tiles[mx] [y].canOpen()) open(mx, y);
//			if(gx < width&&tiles[gx] [y].canOpen()) open(gx, y);
//			if(my >= 0&&tiles[x] [my].canOpen()) open(x, my);
//			if(gy < height&&tiles[x] [gy].canOpen()) open(x, gy);
		}
	}
	
	private void checkFinish()
	{
		finish = true;
		outer : for(int x = 0;x < width;x++)
		{
			for(int y = 0;y < height;y++)
			{
				if(!(boards[x] [y].isOpened()||(boards[x] [y].isBomb()&&boards[x] [y].isFlag())))
				{
					finish = false;
					break outer;
				}
			}
		}
	}
	
	public void reset()
	{
		for(int x = 0;x < width;x++)
		{
			for(int y = 0;y < height;y++)
			{
				boards[x] [y].reset();
			}
		}
		
		dead = false;
		finish = false;
		
		placeBombs();
		setNumbers();
	}
	
	public void draw(Graphics g)
	{
		for(int x = 0;x < width;x++)
		{
			for(int y = 0;y < height;y++)
			{
				boards[x] [y].draw(g);
			}
		}
		
		if(dead)
		{
			g.setColor(Color.RED);
			g.drawString("You're dead!", 10, 30);
		}
		else if(finish)
		{
			g.setColor(Color.RED);
			g.drawString("You won!", 10, 30);
		}
	}
	
	public static int getWidth()
	{
		return width;
	}
	
	public static int getHeight()
	{
		return height;
	}
}
