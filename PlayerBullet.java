package KindaSpaceInvaders;
import java.awt.Image;

public class PlayerBullet extends Sprite2D{
	private int WindowWidth;
	private boolean dead;
	public PlayerBullet(Image i, int WindowWidth){
		super(i);
		dead = false;
		this.WindowWidth = WindowWidth;
		
	}
	
	public void killBullet(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	public void move(){
		y-=20;
	}
}
