package lifeGame;
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Map extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private final int MAP_HEIGHT;
	private final int MAP_WIDTH;
	boolean is_add=false;
	boolean is_kill=false;
	private int speed=8;
	private static int current_map[][]=new int [40][40];
	private static int empty_map[][]=new int [40][40];
	static  int pause_map[][]=new int [40][40];
	private final CellStatus[][] generation1;
	private final CellStatus[][] generation2;
	private CellStatus[][] currentGeneration;
	private CellStatus[][] nextGeneration;
	private volatile boolean is_repaint = false;
	
	public Map(int MAP_HEIGHT, int MAP_WIDTH)
	{
		setPreferredSize(new Dimension(MAP_WIDTH*15,MAP_HEIGHT*15));
		this.MAP_HEIGHT=MAP_HEIGHT;
		this.MAP_WIDTH=MAP_WIDTH;
		this.setBackground(Color.LIGHT_GRAY);
		generation1=new CellStatus[MAP_HEIGHT][MAP_WIDTH];
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				generation1[i][j]=CellStatus.Dead;
			}
		}
		generation2=new CellStatus[MAP_HEIGHT][MAP_WIDTH];
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				generation2[i][j]=CellStatus.Dead;
			}
		}
		currentGeneration=generation1;
		nextGeneration=generation2;
	}
	/*
	 * transfrom方法实现细胞状态与二进制数组转化
	 */
	public void transfrom(CellStatus[][] generation, int pauseshape[][])
	{
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				if(generation[i][j]==CellStatus.Active)
				{
					pauseshape[i][j]=1;
				}
				else if(generation[i][j]==CellStatus.Dead)
				{
					pauseshape[i][j]=0;
				}
			}
		}
	}
	/*
	 * run()方法实现Runnable接口
	 */
	public void run()
	{
		while(true)
		{
			synchronized(this)
			{
				while(is_repaint)
				{	
					try
					{
						this.wait();
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				sleep(speed);
				for(int i=0;i<MAP_HEIGHT;i++)
				{
					for(int j=0;j<MAP_WIDTH;j++)
					{
						calculateNextMap(i,j);
					}
				}
				CellStatus[][]temp=null;//临时存储当前状态
				temp=currentGeneration;
				currentGeneration=nextGeneration;
				nextGeneration=temp;
				for(int i=0;i<MAP_HEIGHT;i++)
				{
					for(int j=0;j<MAP_WIDTH;j++)
					{
						nextGeneration[i][j]=CellStatus.Dead;	
					}
				}
				transfrom(currentGeneration,pause_map);
				repaint();
			}
		}
	}
/*
 * paintComponent实现绘图功能
 */
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				if(currentGeneration[i][j]==CellStatus.Active)
				{
					g.setColor(Color.green);
					g.fillOval(j*15, i*15, 15, 15);
				}
				else
				{
					g.setColor(Color.green);
					g.drawOval(j*15, i*15, 15, 15);
				}
			}
		}
	}

	public void setMap()
	{
		setMap(current_map);
	}
	public void setRandom()
	{
		Random a=new Random();
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				int aa=Math.abs(a.nextInt(7));
				if(aa==0){
					current_map[i][j]=1; 	
				}else{
					current_map[i][j]=0;
				}
				pause_map[i][j]=current_map[i][j];
			}
		}
		setTempMap(current_map);
	}
	public void setEmpty()
	{
		for(int i=0;i<MAP_HEIGHT;i++)
		{
			for(int j=0;j<MAP_WIDTH;j++)
			{
				empty_map[i][j]=0;
			}
		}
	}
	public void setClear()
	{
		setEmpty();		
		current_map=empty_map;
		setMap(current_map);
		pause_map=current_map;
	}
	
	public void setPause()
	{
		current_map=pause_map;
		setTempMap(pause_map);
	}
	
	public void setAdd()
	{
		current_map=pause_map;
		setTempMap(current_map);
	}
	private void setTempMap(int [][]shape)
	{
		is_repaint=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<MAP_HEIGHT)?arrowsRows:MAP_HEIGHT;
		int minimunColumns=(arrowsColumns<MAP_WIDTH)?arrowsColumns:MAP_WIDTH;
		synchronized(this)
		{
			for(int i=0;i<MAP_HEIGHT;i++)
			{
				for(int j=0;j<MAP_WIDTH;j++)
				{
					currentGeneration[i][j]=CellStatus.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=CellStatus.Active;
					}
				}
			}
			repaint();
		}
	}
	private void setMap(int [][]shape)
	{
		is_repaint=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<MAP_HEIGHT)?arrowsRows:MAP_HEIGHT;
		int minimunColumns=(arrowsColumns<MAP_WIDTH)?arrowsColumns:MAP_WIDTH;
		synchronized(this)
		{
			for(int i=0;i<MAP_HEIGHT;i++)
			{
				for(int j=0;j<MAP_WIDTH;j++)
				{
					currentGeneration[i][j]=CellStatus.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=CellStatus.Active;
					}
				}
			}
			is_repaint=false;
			this.notifyAll();
		}		
	}
	
	/*
	 * calculateNextMap实现计算下一个矩阵状态
	 */
	public void calculateNextMap(int x,int y)
	{
		int activeSurroundingCell=0;
		if(isVaildCell(x-1,y-1)&&(currentGeneration[x-1][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y-1)&&(currentGeneration[x][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y-1)&&(currentGeneration[x+1][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y)&&(currentGeneration[x+1][y]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y+1)&&(currentGeneration[x+1][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y+1)&&(currentGeneration[x][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y+1)&&(currentGeneration[x-1][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y)&&(currentGeneration[x-1][y]==CellStatus.Active))
			activeSurroundingCell++;
		
		if(activeSurroundingCell==3)
		{
			nextGeneration[x][y]=CellStatus.Active;
		}
		else if(activeSurroundingCell==2)
		{
			nextGeneration[x][y]=currentGeneration[x][y];
		}
		else
		{
			nextGeneration[x][y]=CellStatus.Dead;
		}
	}
	/*
	 * isVaildCel判断是否越界
	 */
	private boolean isVaildCell(int x,int y)
	{
		if((x>=0)&&(x<MAP_HEIGHT)&&(y>=0)&&(y<MAP_WIDTH))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/*
	 * 刷新时间控制
	 */
	private void sleep(int x)
	{
		try {
			Thread.sleep(80*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
	
	static enum CellStatus
	{
		Active,
		Dead;
	}
	public void getSpeed(int i) {
		speed=i;
	}

}



