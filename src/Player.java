import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class Player extends Sprite {
	private Animation playerA;
	private char nextStep;
	private float pushForce = -25f; //going up
	private float pullForce = 25f; //going down
	private boolean isExtreme = false;
	private int howManyRight;
	private int howManyWrong;

	//Costructor//{{{
	public Player(String[] frames){
		super(null);

		playerA = new Animation();

		for(String frame: frames){
			Image i = new ImageIcon(frame).getImage();
			playerA.addScene(i,250);
		}

		setAnimation(playerA);
		planNextStep();
	}
	public Player(String[] frames, boolean isExtreme){
		this(frames);
		this.isExtreme = isExtreme;
	}
	//}}}

	public void update(long timePassed){
		super.update(timePassed);

		setYVelocity(getYVelocity() + ( pullForce * ( timePassed * 0.001f )));
	}

	public void tryPush(char input){
		if (input == nextStep){
			//if its in extreme mode, increment pushForce, otherwise just set it
			setYVelocity(isExtreme ? getYVelocity() + pushForce : pushForce);
			howManyRight++;
		}
		else {
			//if its in extreme mode, increment pushForce, otherwise just set it
			setYVelocity(getYVelocity() + pullForce);
			howManyWrong++;
		}
		planNextStep();
	}

	public void doPush(){
		Random rnd = new Random();
		int result = rnd.nextInt(99);

		doPush(result < 65);
	}
	public void doPush(boolean result){
		if(result){
			setYVelocity(isExtreme ? getYVelocity() + pushForce : pushForce);
			howManyRight++;
		}
		else{
			setYVelocity(getYVelocity() + pullForce);
			howManyWrong++;
		}
	}

	private void planNextStep(){
		Random rnd = new Random();
		String alphabet = "abcdefghijklmnopqrstuvxywz";
		char last = nextStep;

		while (last == nextStep){
			nextStep = alphabet.charAt(rnd.nextInt(alphabet.length()));
		}
	}

	//Get/set//{{{
	public char getNextStep(){
		return nextStep;
	}

	public void setPullForce(float force){
		pullForce = force;
	}

	public int getHowManyRight(){
		return howManyRight;
	}
	public int getHowManyWrong(){
		return howManyWrong;
	}

	public Point getStepLocation(Graphics2D g, Font font){
		Point stepLocation;
		FontMetrics metrics = g.getFontMetrics(font);
		stepLocation = new Point(
				Math.round( getX() + ( getWidth()/2 ) - metrics.stringWidth(String.valueOf(nextStep))/2),
				Math.round( getY() + 25 ));
		return stepLocation;
	}
	//}}}
}
