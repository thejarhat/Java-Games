package KindaSpaceInvaders;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;


public class InvadersApplication extends JFrame implements Runnable, KeyListener {
	
	//member data
	private boolean isGameInProgress = true;
	private static final Dimension WindowSize = new Dimension(800,600);
	private static final int NUMALIENS = 30;
	private Alien[] AliensArray = new Alien[NUMALIENS];
	private Spaceship PlayerShip;
	private int PlayerShipSpeed = 20;
	private BufferStrategy strategy;
	private ArrayList<PlayerBullet> bulletList = new ArrayList<PlayerBullet>();
	private ImageIcon AlienShipIcon1;
	private ImageIcon AlienShipIcon2;
	private ImageIcon BulletIcon;
	String workingDirectory = System.getProperty("user.dir");
	private  ArrayList<Integer> scoreList = new ArrayList<Integer>();
	private  int currentScore = 0;
	private int highScore = 0;
	private int waveNo = 1; 
	public InvadersApplication(){
 	
		
		
		//Create and set up the window.
		this.setTitle("Space Invaders Kinda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//	Display the window, centered on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x, y, 800,600);
		setVisible(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		
		AlienShipIcon1= new ImageIcon(workingDirectory+ "\\alien_ship_1.png");
		AlienShipIcon2= new ImageIcon(workingDirectory+ "\\alien_ship_2.png");
		BulletIcon = new ImageIcon(workingDirectory+ "\\bullet.png");
		for(int i = 0; i <NUMALIENS; i++){
			Alien alienShip = new Alien(AlienShipIcon1.getImage(),AlienShipIcon2.getImage(), WindowSize.width);
					AliensArray[i] = alienShip;
					double xx = 50 + Math.random() * 600;
					double yy = 90 + Math.random() * 300;
					AliensArray[i].setPosition(xx,yy);
					
					AliensArray[i].setXSpeed( (Math.random()*10 + 4)* ((Math.random() <0.5)? -1:1	) );
				}
		ImageIcon PlayerShipIcon = new ImageIcon(workingDirectory+ "\\player_ship.png");
		
		PlayerShip = new Spaceship(PlayerShipIcon.getImage(),WindowSize.width);
		PlayerShip.setPosition(WindowSize.width/2, 550);
		PlayerShip.setXSpeed(PlayerShipSpeed);
		
		Thread t = new Thread(this);
		t.start();
		
		addKeyListener(this);
		


	}
	public void restartGame(){
		waveNo = 1;
		scoreList.add(currentScore);
		currentScore = 0;
		for(int i = 0; i < scoreList.size(); i++){
			if(scoreList.get(i) > highScore)
				highScore = scoreList.get(i); 
		}
		AlienShipIcon1= new ImageIcon(workingDirectory+ "\\alien_ship_1.png");
		AlienShipIcon2= new ImageIcon(workingDirectory+ "\\alien_ship_2.png");
		BulletIcon = new ImageIcon(workingDirectory+ "\\bullet.png");
		for(int i = 0; i <NUMALIENS; i++){
			Alien alienShip = new Alien(AlienShipIcon1.getImage(),AlienShipIcon2.getImage(), WindowSize.width);
					AliensArray[i] = alienShip;
					double xx = Math.random() * 600;
					double yy = Math.random() * 300;
					AliensArray[i].setPosition(xx,yy);
					
					AliensArray[i].setXSpeed((Math.random()*10 + 4) *waveNo*(	(Math.random() <0.5)? -1:1	));
				}
		ImageIcon PlayerShipIcon = new ImageIcon(workingDirectory+ "\\player_ship.png");
		
		PlayerShip = new Spaceship(PlayerShipIcon.getImage(),WindowSize.width);
		PlayerShip.setPosition(WindowSize.width/2, 550);
		PlayerShip.setXSpeed(PlayerShipSpeed);
		
	}
	public void startNewWave(){
		waveNo++;
		AlienShipIcon1= new ImageIcon(workingDirectory+ "\\alien_ship_1.png");
		AlienShipIcon2= new ImageIcon(workingDirectory+ "\\alien_ship_2.png");
		BulletIcon = new ImageIcon(workingDirectory+ "\\bullet.png");
		for(int i = 0; i <NUMALIENS; i++){
			Alien alienShip = new Alien(AlienShipIcon1.getImage(),AlienShipIcon2.getImage(), WindowSize.width);
					AliensArray[i] = alienShip;
					double xx = 50 + Math.random() * 600;
					double yy = 90 + Math.random() * 300;
					AliensArray[i].setPosition(xx,yy);
					AliensArray[i].setXSpeed(( (Math.random()*10 + 4)*waveNo)*((Math.random() <0.5)? -1:1	));
				}
		ImageIcon PlayerShipIcon = new ImageIcon(workingDirectory+ "\\player_ship.png");
		
		PlayerShip = new Spaceship(PlayerShipIcon.getImage(),WindowSize.width);
		PlayerShip.setPosition(WindowSize.width/2, 550);
		PlayerShip.setXSpeed(PlayerShipSpeed);
		
	}
	public void run(){
		if(isGameInProgress){
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {e.printStackTrace();}
			ArrayList<Boolean> outOfBoundList = new ArrayList<Boolean>();
			PlayerBullet bullet = new PlayerBullet(null,0);
			for(int i = 0; i<bulletList.size();i++){
				bullet =  bulletList.get(i);
				bullet.move();	
			}
			for(int i = 0; i<bulletList.size();i++){
				bullet =  bulletList.get(i);
				if(bullet.getY() < 0)
					bulletList.remove(i);
			}
			
			for(int i = 0; i< NUMALIENS ;i++)
				outOfBoundList.add(AliensArray[i].moveEnemy());
				
			for(int i = 0; i< outOfBoundList.size(); i++ ){
				if(outOfBoundList.get(i)){
					AliensArray[i].shiftDown();
				}
					
			}
			
			
			
						
			for(int i = 0; i < NUMALIENS ;i++){
				if(!AliensArray[i].isDead()){	
					if (
							( 
								(AliensArray[i].getX() < PlayerShip.getX()  && AliensArray[i].getX()+50 >PlayerShip.getX()) 
								||
								(PlayerShip.getX()< AliensArray[i].getX() && PlayerShip.getX()+ 6 >AliensArray[i].getX())  
							)
							&&
							(
								(AliensArray[i].getY()<PlayerShip.getY() && AliensArray[i].getY()+ 32 >PlayerShip.getY() )
								||
								(PlayerShip.getY()<AliensArray[i].getY()&& PlayerShip.getY() + 16 >AliensArray[i].getY())  
							)
					   ){
						isGameInProgress = false;
					}
				}
				for(int j = 0; j < bulletList.size(); j++)
				{
					bullet =  bulletList.get(j);
					if(!AliensArray[i].isDead()){	
						if (
								( 
									(AliensArray[i].getX() < bullet.getX()  && AliensArray[i].getX()+50 >bullet.getX()) 
									||
									(bullet.getX()< AliensArray[i].getX() && bullet.getX()+ 6 >AliensArray[i].getX())  
								)
								&&
								(
									(AliensArray[i].getY()<bullet.getY() && AliensArray[i].getY()+ 32 >bullet.getY() )
									||
									(bullet.getY()<AliensArray[i].getY()&& bullet.getY() + 16 >AliensArray[i].getY())  
								)
						   ){
							currentScore += 10;
							AliensArray[i].killAlien();
							bullet.killBullet();
							bulletList.remove(j);
						}
					}	
				}
				
			}
			boolean waveCompleteCheck = true;
			for(int i =0; i < NUMALIENS;i++){
				if(!AliensArray[i].isDead())
					waveCompleteCheck = false;
			}
			if(waveCompleteCheck)
				startNewWave();
			
			this.repaint();
			
			
				
			
		}
	}
		
		
	}
	
	

	
	
	
	
	public void keyPressed(KeyEvent e){
		if(isGameInProgress){
			if(e.getKeyCode() == 37  ){
				PlayerShip.setXSpeed(-1*PlayerShipSpeed);
				PlayerShip.movePlayer();
			}
			else if(e.getKeyCode() == 39 ){
				PlayerShip.setXSpeed(PlayerShipSpeed);
				PlayerShip.movePlayer();
				
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE){
				shootBullet();
				
			}
		}
		else{
			if(e.getKeyCode()  == KeyEvent.VK_SPACE  ){
				isGameInProgress = true;
				restartGame();
			}
		}
	}
	

	public void shootBullet(){
		PlayerBullet b = new PlayerBullet(BulletIcon.getImage() ,WindowSize.width);
		b.setPosition(PlayerShip.x+54/2, PlayerShip.y);
		bulletList.add(b);
		
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
			PlayerShip.setXSpeed(0);
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			shootBullet();
			
		}
		
	}
	
	public void keyTyped(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			shootBullet();
			
		}
		
	}
	
	
	
	public void paint(Graphics g){
		if(isGameInProgress){
			g = strategy.getDrawGraphics();
			 super.paintComponents(g);
			 
			 g.setColor(Color.BLACK);
			 g.fillRect(0, 0, 800, 600);
			 g.setColor(Color.WHITE);
			 g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
			 g.drawString("Current Score: "+String.valueOf(currentScore)+" High Score: "+String.valueOf(highScore), 250, 60);
			for(int i = 0; i< NUMALIENS; i++){
				if(!AliensArray[i].isDead())
					AliensArray[i].paint(g);
			}
			
			PlayerShip.paint(g);
			
			Iterator iterator= bulletList.iterator();
			while(iterator.hasNext()){
				PlayerBullet b = (PlayerBullet) iterator.next();
				if(!b.isDead())
					b.paint(g);
			}
			 strategy.show();
		}
		else{
			 g.setColor(Color.BLACK);
			 g.fillRect(0, 0, 800, 600);
			 g.setColor(Color.WHITE);
			 g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.drawString("GAME OVER", 100, 300);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("PRESS SPACEBAR TO PLAY AGAIN", 250, 400);
			g.drawString("[ARROW KEYS TO MOVE AND SPACEBAR TO SHOOT] ", 150, 450);
		}
	}
	
	public static void main(String[] args) {
		
		InvadersApplication testRun = new InvadersApplication();

	}

}
