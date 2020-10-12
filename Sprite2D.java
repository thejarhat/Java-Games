package KindaSpaceInvaders;
import java.awt.*;
import java.lang.*;
import java.util.Iterator;


public class Sprite2D{
	
	//member data
	protected double x,y;
	protected double xSpeed;
	protected Image myImage;
	protected Image myImage2;
	private int framesDrawn = 0;
	
	
	//constructor
	public Sprite2D(Image i1, Image i2){
		
		x = y = xSpeed = 0;
		myImage = i1;
		myImage2 = i2;
		
		
	}
	public Sprite2D(Image i1){
		
		x = y = xSpeed = 0;
		myImage = i1;
		myImage2 = null;		
		
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	
	
	public void setPosition(double xx, double yy){
		x = xx;
		y = yy;
	}
	
	
	
	public void setXSpeed(double dx){
		xSpeed = dx; 
	}
	
	public void paint(Graphics g){
		framesDrawn++;
		g.drawImage(myImage, (int)x, (int)y, null);
		if(myImage2 == null){
			g.drawImage(myImage, (int)x, (int)y, null);
		}
		else{
			if( framesDrawn%100<50 )
				g.drawImage(myImage, (int)x, (int)y, null);
			else
				g.drawImage(myImage2, (int)x, (int)y, null);

		}
		
	}
}
