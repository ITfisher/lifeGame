
import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class World extends JPanel implements Runnable{
	private final int rows;
	private final int columns;
	JLabel  record;
	boolean diy=false;
	boolean clean=false;
	private int speed=8;
	private int lnum;
	private static int shape[][]=new int [40][50];
	private static int zero[][]=new int [40][50];
	static  int pauseshape[][]=new int [40][50];
	private final CellState[][] cellInit;
	private final CellState[][] generationTwo;
	private CellState[][] currentGene;
	private CellState[][] nextGene;
	private volatile boolean isChanging = false;
	public World(int rows, int columns)
	{
		setSize(rows*5,columns*5);
		this.rows=rows;
		this.columns=columns;
		//record = new JLabel();
		//add(record);
		cellInit=new CellState[rows][columns];
	
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				cellInit[i][j]=CellState.Dead;
			}
		}
		generationTwo=new CellState[rows][columns];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				generationTwo[i][j]=CellState.Dead;
			}
		}
		currentGene=cellInit;
		nextGene=generationTwo;
	}
	public void transfrom(CellState[][] generation, int pauseshape[][])
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(generation[i][j]==CellState.Active)
				{
					pauseshape[i][j]=1;
				}
				else if(generation[i][j]==CellState.Dead)
				{
					pauseshape[i][j]=0;
				}
			}
		}
	}
	public void run()
	{

		while(true)
		{
			synchronized(this)
			{
				while(isChanging)
				{
					
					try
					{
						this.wait();
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
//				repaint();
				sleep(speed);
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						evolve(i,j);
					}
				}
				CellState[][]temp=null;
				temp=currentGene;
				currentGene=nextGene;
				nextGene=temp;
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						nextGene[i][j]=CellState.Dead;	
					}
				}
				transfrom(currentGene,pauseshape);
				repaint();
				//updateNumber();
			}
		}
	}
//	public void updateNumber()
//	{
//		String s = " ´æ»îÊýÁ¿£º " + lnum ;
//		record.setText(s);
//	}
	public void changeSpeedSlow()
	{
		speed=8;
	}
	public void changeSpeedFast()
	{
		speed=3;
	}
	public void changeSpeedHyper()
	{
		speed=1;
	}
	public void paintComponent(Graphics g)
	{
		lnum=0;
		super.paintComponent(g);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(currentGene[i][j]==CellState.Active)
				{
					g.fillRect(j*10, i*10, 10, 10);
					lnum++;
				}
				else
				{
					g.drawRect(j*10, i*10, 10, 10);
				}
			}
		}
	}

	public void setShape()
	{
		setShape(shape);
	}
	public void setRandom()
	{
		Random a=new Random();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				shape[i][j]=Math.abs(a.nextInt(2));
				pauseshape[i][j]=shape[i][j];
			}
		}
		setShapetemp(shape);
	}
	public void setZero()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				zero[i][j]=0;
			}
		}
	}
	public void setStop()
	{
		setZero();		
		shape=zero;
		setShape(shape);
		pauseshape=shape;
	}
	
	public void setPause()
	{
		shape=pauseshape;
		setShapetemp(pauseshape);
	}
	
	public void setDiy()
	{
		shape=pauseshape;
		setShapetemp(shape);
	}
	private void setShapetemp(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGene[i][j]=CellState.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGene[i][j]=CellState.Active;
					}
				}
			}
		
			repaint();
			///updateNumber();

		}
	}
	private void setShape(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGene[i][j]=CellState.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGene[i][j]=CellState.Active;
					}
				}
			}
		
			isChanging=false;
			this.notifyAll();
		}
		
	}
	
	public void evolve(int x,int y)
	{
		int activeSurroundingCell=0;
		if(isVaildCell(x-1,y-1)&&(currentGene[x-1][y-1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y-1)&&(currentGene[x][y-1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y-1)&&(currentGene[x+1][y-1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y)&&(currentGene[x+1][y]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y+1)&&(currentGene[x+1][y+1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y+1)&&(currentGene[x][y+1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y+1)&&(currentGene[x-1][y+1]==CellState.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y)&&(currentGene[x-1][y]==CellState.Active))
			activeSurroundingCell++;
		//
		if(activeSurroundingCell==3)
		{
			nextGene[x][y]=CellState.Active;
		}
		else if(activeSurroundingCell==2)
		{
			nextGene[x][y]=currentGene[x][y];
		}
		else
		{
			nextGene[x][y]=CellState.Dead;
		}
	}
	private boolean isVaildCell(int x,int y)
	{
		if((x>=0)&&(x<rows)&&(y>=0)&&(y<columns))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private void sleep(int x)
	{
		try {
			Thread.sleep(80*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
	static enum CellState
	{
		Active,
		Dead;
	}


	
}

