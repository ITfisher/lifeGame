package d;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class LifeGame extends JFrame implements MouseMotionListener,MouseListener{
	private final World world;
	//static JButton location=new JButton();
	JSlider changeSpeed;
	public LifeGame()
	{
		world=new World(37,37);
		world.setBackground(Color.LIGHT_GRAY);
		new Thread(world).start();
		//add(world);
	}
	public void lunchFrame() {
		addMouseMotionListener(this);
		JPanel control=new JPanel();
		
		
		JPanel modeP=new JPanel();
		JLabel mode=new JLabel("Mode:");	
		JButton random=new JButton("Random");
		random.addActionListener(this.new RandomActionListener());
		JButton choose=new JButton("Add");
		choose.addActionListener(this.new DIYActionListener());
		JButton clean=new JButton("Kill");
		clean.addActionListener(this.new CleanActionListener());
		//clean.addMouseListener(this.new CleanActionListener());
		modeP.add(mode);modeP.add(random);modeP.add(choose);modeP.add(clean);
		
		
		JPanel playP=new JPanel();
		JLabel play=new JLabel("Play:");	
		JButton start=new JButton("Start");
		start.addActionListener(this.new StartActionListener());
		JButton pause=new JButton("Pause");
		pause.addActionListener(this.new PauseActionListener());
		JButton stop=new JButton("Stop");
		stop.addActionListener(this.new StopActionListener());
		playP.add(play);playP.add(start);playP.add(pause);playP.add(stop);
		
		
		JPanel speedP=new JPanel();
		JLabel speed=new JLabel("Speed:");	
		//JButton slow=new JButton("Slow");
		//slow.addActionListener(this.new SlowActionListener());
		//JButton fast=new JButton("Fast");
		//fast.addActionListener(this.new FastActionListener());
		//JButton hyper=new JButton("Hyper");
		//hyper.addActionListener(this.new HyperActionListener());
		
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
       // changeSpeed.setMajorTickSpacing(8);  
        changeSpeed.setMinorTickSpacing(1);  
		speedP.add(speed);
	    speedP.add(changeSpeed);
	    
		JPanel otherP=new JPanel();
		JLabel other=new JLabel("Other:");	
		JButton help=new JButton("Help");
		help.addActionListener(this.new HelpActionListener());	
		JButton about=new JButton("About");
		about.addActionListener(this.new AboutActionListener());
		//otherP.setLayout(new FlowLayout());
		other.setBounds(0, 0, 20,30);
		otherP.add(other);otherP.add(help);otherP.add(about);
		
		
		control.add(modeP);control.add(playP);control.add(speedP);control.add(otherP);
		control.setSize(200, 400);
		control.setPreferredSize(new Dimension(300,420));
		control.setLayout(new GridLayout(4,1));
		control.setBorder(BorderFactory.createRaisedBevelBorder());//上凸边框
		//control.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//world.setSize(700, 470);
		
		add(world);
		add(control);
		
		setLayout(new FlowLayout());
		//setLayout(new GridLayout(1,2));
		//about.addActionListener(frame.new AboutActionListener());
		//setLocationRelativeTo();//这样就会默认显示在屏幕中央
		setLocation(150,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(900,470);
		pack();
		setTitle("Game of Life");
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[]args)
	{
		LifeGame frame=new LifeGame();
		frame.lunchFrame();
		
		
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
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
			world.setPause();
		}
	}
	
	class SpeedListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			world.getSpeed(9-changeSpeed.getValue());
			
			
		}
	}
	
//	class SlowActionListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent e) 
//		{
//			world.changeSpeedSlow();
//		}
//	}
//	class FastActionListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent e) 
//		{
//			world.changeSpeedFast();
//		}
//	}
//	class HyperActionListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent e) 
//		{
//			world.changeSpeedHyper();
//		}
//	}
	class HelpActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JOptionPane.showMessageDialog(null, "这是生命游戏!!!\n生命游戏是英国数学家约翰·何顿·康威在1970年发明的细胞自动机\n "+"1．如果一个细胞周围有3个细胞为生，则该细胞为生;\n"
												+"2． 如果一个细胞周围有2个细胞为生，则该细胞的生死状态保持不变;\n"
												+"3． 在其它情况下，该细胞为死。");
		}
	}
	class AboutActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JOptionPane.showMessageDialog(null, "游戏作者：李钊，林俊雄，孟猛，尉盛龙");
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
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(world.diy){
		int x=e.getX();
		int y=e.getY();
		//button.setText("x:"+x+"y:"+y);
		World.pauseshape[(y-30)/15][(x-16)/15]=1;
		world.setDiy();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(world.clean&&e.getX()<37*15&&e.getY()<37*15){
		int x=e.getX();
		int y=e.getY();
		//System.out.println(x);
		//System.out.println(y);
		//button.setText("x:"+x+"y:"+y);
		World.pauseshape[(y-30)/15][(x-16)/15]=0;
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
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

