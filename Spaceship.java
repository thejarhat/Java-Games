package KindaSpaceInvaders;

import java.awt.Image;

public class Spaceship extends Sprite2D{
	
	private int windowWidth;
	public Spaceship(Image i, int windowWidth) {
		super(i);
		this.windowWidth = windowWidth;
		
	}
	
	
	public void movePlayer(){
		
		if(x <= 10){
			x = windowWidth-1;
		}
		else if(x >= windowWidth){
			x = 11;
		}
		x += xSpeed;
	}
	
	

}
