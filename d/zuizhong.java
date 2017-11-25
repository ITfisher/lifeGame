package d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class zuizhong extends JFrame{// implements MouseMotionListener
	private final World world;
	JSlider changeSpeed;
	public zuizhong(){
		world = new World(40,40);
		world.setLayout(null);
        world.setBounds(7, 10, 40*15, 40*15);
      //  world.setBackground(Color.black);
        world.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(world.diy){
				int x=e.getX();
				int y=e.getY();
				//button.setText("x:"+x+"y:"+y);
				World.pauseshape[(y-30)/15+2][(x-16)/15+1]=1;
				world.setDiy();
				
				}else if(world.clean&&e.getX()<40*15&&e.getY()<40*15){
					int x=e.getX();
					int y=e.getY();
					//button.setText("x:"+x+"y:"+y);
					World.pauseshape[(y-30)/15+2 ][(x-16)/15+1]=0;
					world.setDiy();
					}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        JPanel setting= new JPanel();
		setting.setLayout(null);
		setting.setBackground(Color.CYAN);
        setting.setBounds(0, 620, 620, 90);
        setting=control(setting);   
		add(world);	
		add(setting);
		new Thread(world).start();
	}
	public JPanel control(JPanel c){
        JPanel control=c;
        
        int but_x=50;
		JButton random=new JButton("Random");
		random.setFont(new Font("宋体", Font.BOLD, 10));
		random.setMargin(new Insets(0,0,0,0));
		random.addActionListener(this.new RandomActionListener());
		random.setBounds(20, 10, but_x, 30);
		
		JButton choose=new JButton("Add");
		choose.setFont(new Font("宋体", Font.BOLD, 10));
		choose.setMargin(new Insets(0,0,0,0));
		choose.addActionListener(this.new DIYActionListener());
		choose.setBounds(90, 10,but_x, 30);
		
		JButton clean=new JButton("Kill");
		clean.setMargin(new Insets(0,0,0,0));
		clean.setFont(new Font("宋体", Font.BOLD, 10));
		clean.addActionListener(this.new CleanActionListener());
		clean.setBounds(160, 10, but_x, 30);
		
		JButton start=new JButton("Start");
		start.setFont(new Font("宋体", Font.BOLD, 10));
		start.setMargin(new Insets(0,0,0,0));
		start.addActionListener(this.new StartActionListener());
		start.setBounds(230, 10, but_x, 30);
		
		JButton pause=new JButton("Pause");
		pause.setFont(new Font("宋体", Font.BOLD, 10));
		pause.setMargin(new Insets(0,0,0,0));
		pause.addActionListener(this.new PauseActionListener());
		pause.setBounds(300, 10, but_x, 30);
		
		JButton stop=new JButton("Stop");
		stop.setFont(new Font("宋体", Font.BOLD, 10));
		stop.setMargin(new Insets(0,0,0,0));
		stop.addActionListener(this.new StopActionListener());
		stop.setBounds(370, 10, but_x, 30);
		
		  changeSpeed=new JSlider();
		//设置滑块必须停在刻度处  
        changeSpeed.setSnapToTicks(true);  
        //设置绘制刻度  
        changeSpeed.setPaintTicks(true);  
        //设置主、次刻度的间距 
        changeSpeed.setMaximum(8);
        changeSpeed.setMinimum(1);
        changeSpeed.setValue(1);
        changeSpeed.addChangeListener(new SpeedListener());
        changeSpeed.setMinorTickSpacing(1);  
        changeSpeed.setPreferredSize(new Dimension(400, 40));
        changeSpeed.setBounds(440,10,150,30);
	   		
		control.add(changeSpeed);
		control.add(stop);
		control.add(pause);
		control.add(start);
		control.add(random);
		control.add(clean);
		control.add(choose);
		control.setBorder(BorderFactory.createRaisedBevelBorder());//上凸边框
		return control;
	}
	
	public static void main(String[] args) {
		zuizhong a=new zuizhong();
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setSize(620,700);
		a.setLayout(null);
		//a.getContentPane().setBackground(Color.green);
		
		a.setVisible(true);
		a.setResizable(false);
		a.setTitle("Game of Life");
		a.setLocationRelativeTo(null);
	}
	class RandomActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.diy=false;
			world.clean=false;
			world.setBackground(Color.LIGHT_GRAY);
			//world.setStop();
			world.setRandom();
		}
	}
	class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//world.begintime=System.currentTimeMillis();
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
			world.setShape();
		}
	}
	class StopActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//world.time=0;
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
			world.setStop();
		}
	}
	class PauseActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
		}
	}
	
	class SpeedListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			world.getSpeed(9-changeSpeed.getValue());
			
			
		}
	}
	class CleanActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			world.clean=true;
			world.diy=false;
			world.setBackground(Color.orange);
		}
	}
	class DIYActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			world.diy=true;
			world.clean=false;
			world.setBackground(Color.cyan);
		}
	}
	
}


	

