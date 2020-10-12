package KindaSpaceInvaders;

import java.awt.Image;

public class Alien extends Sprite2D {
private int windowWidth;
private boolean dead; 
	public Alien(Image i1,Image i2, int windowWidth) {
		super(i1,i2);
		this.windowWidth = windowWidth;
		dead = false;
		
	}
	
	
	public boolean moveEnemy(){
		x += xSpeed;
		if(x <=  0|| x >= windowWidth - myImage.getWidth(null))
			return true;
		else
			return false;
	   
	}
	
	public void shiftDown(){
		y += 20;
		xSpeed = -xSpeed;
	}
	
	public void killAlien(){
		dead = true; 
	}
	
	public boolean isDead(){
		return dead;
	}
}
