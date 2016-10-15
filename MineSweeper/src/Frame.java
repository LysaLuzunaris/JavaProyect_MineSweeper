import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame implements MouseListener, KeyListener
{
	private static int width = 400;
	private static int height = 400;
	
	private Screen screen;
	private Game Game;
	private Font font;
	
	private int insetLeft;
	private int insetTop;
	
	public Frame()
	{
		super("MineSweeper");
		
		Game = new Game();
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
		addKeyListener(this);
		
		screen = new Screen();
		add(screen);
		
		pack();
		insetLeft = getInsets().left;
		insetTop = getInsets().top;
		setSize(width + insetLeft + getInsets().right, height + getInsets().bottom + insetTop);
		setLocationRelativeTo(null);
		setVisible(true);
		
		font = new Font("SansSerif", 0, 12);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == 1) Game.clickedLeft(e.getX() - insetLeft, e.getY() - insetTop);
		if(e.getButton() == 3) Game.clickedRight(e.getX() - insetLeft, e.getY() - insetTop);
		screen.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			Game.reset();
			screen.repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public class Screen extends JPanel
	{
		@Override
		public void paintComponent(Graphics g)
		{
			g.setFont(font);
			Game.draw(g);
		}
	}
	
	public static int getScreenWidth()
	{
		return width;
	}
	
	public static int getScreenHeight()
	{
		return width;
	}
}

