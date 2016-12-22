import java.awt.Image;

public class Sprite{

	private Animation a;
	private float x;
	private float y;
	private float vx;
	private float vy;

	public Sprite(Animation a){
		this.a = a;
	}

	public void update(long timePassed){
		x += vx * ( timePassed * 0.001 );
		y += vy * ( timePassed * 0.001 );

		a.update(timePassed);
	}

	//get/set Sprite{{{
	public Image getSprit(){
		return a.getImage();
	}

	public int getWidth(){
		return a.getImage().getWidth(null);
	}

	public int getHeight(){
		return a.getImage().getHeight(null);
	}

	public void setAnimation(Animation a){
		this.a = a;
	}
	//}}}
	//get/set vx/vy{{{
	public float getXVelocity(){
		return vx;
	}

	public float getYVelocity(){
		return vy;
	}

	public void setXVelocity(float x){
		this.vx = x;
	}

	public void setYVelocity(float y){
		this.vy = y;
	}
	//}}}
	//get/set x/y {{{
	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public void setX(float x){
		this.x = x;
	}

	public void setY(float y){
		this.y = y;
	}
	//}}}
}
