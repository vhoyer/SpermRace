import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpermRace extends Core implements KeyListener {
	private Player player;
	private Player[] npc;
	private Window win;
	private int difficult = 1;
	private String placementN;
	private String score;
	private boolean died = false;
	private Resources rs;

	public synchronized void setup(){
		//initializers
		win = sm.getFullScreenWindow();
		win.addKeyListener(this);
		rs = new Resources();

		//difficulty settings
		boolean isExtreme = false;
		float pull;
		switch(difficult){
			case 0: pull = 10f; break;
			default:
			case 1: pull = 25f; break;
			case 3: isExtreme = true;
			case 2: pull = 50f; break;
		}

		//create player
		player = new Player(new String[] {
			rs.root+"/imgs/sperm1small.png",
			rs.root+"/imgs/sperm2small.png"
		}, isExtreme);
		player.setY(win.getHeight() - player.getHeight());
		player.setX(win.getWidth()/2 - player.getWidth()/2);
		player.setPullForce(pull);

		//instantiate npcs
		npc = new Player[8];
		for(int i = 0; i < npc.length; i++){
			npc[i] = new Player(new String[] {
				rs.root+"/imgs/sperm1small.png",
				rs.root+"/imgs/sperm2small.png"
			});
			//set up offset
			int offset = offset = (i - 8) * 100;
			if (i < 4) offset = (i + 1) * 100;
			npc[i].setY(win.getHeight() - npc[i].getHeight());
			npc[i].setX(win.getWidth()/2 - npc[i].getWidth()/2 - offset);
		}

		player.doPush(true);
	}

	public synchronized void draw(Graphics2D g){
		//BG
		g.setColor(win.getBackground());
		g.fillRect(0, 0, win.getWidth(), win.getHeight());
		g.drawImage(rs.bg, 0,0, win.getWidth(), rs.bg.getHeight(null), null);

		//Rest
		g.drawImage(
				player.getSprit(),
				Math.round( player.getX() ),
				Math.round( player.getY() ),
				null);
		for(int i = 0; i < npc.length; i++){
			g.drawImage(
					npc[i].getSprit(),
					Math.round( npc[i].getX() ),
					Math.round( npc[i].getY() ),
					null);
		}

		//UI
		Point placement = new Point(win.getWidth() - 120, win.getHeight() - 120),
			  scores = new Point(win.getWidth() - 220, 20),
			  nextStep = player.getStepLocation(g, font);

		g.setColor(win.getForeground());
		g.fillRect(placement.x, placement.y, 100, 100);
		g.fillRect(scores.x, scores.y, 200, 100);

		g.setColor(Color.black);
		g.drawString(placementN, placement.x + 40, placement.y + 57);
		g.drawString(score, scores.x + 40, scores.y + 57);
		g.drawString(String.valueOf( player.getNextStep() ), nextStep.x, nextStep.y);
	}

	public void update(long timePassed){
		player.update(timePassed);
		npcsControler(timePassed);
		placementN = calcPlacement();
		score = String.valueOf( calculatePoints() );

		if (player.getY() > win.getHeight()){
			died = true;
			stop();
		}
		else if (player.getY() <= 0){
			stop();
		}
	}

	private String calcPlacement(){
		int place = npc.length + 1;
		for(int i = 0; i < npc.length; i++){
			if(player.getY() < npc[i].getY()){
				place--;
			}
		}
		switch(place % 10){
			case 1: return place + "st";
			case 2: return place + "nd";
			default: return place + "th";
		}
	}

	public void npcsControler(long timePassed){
		for(int i = 0; i < npc.length; i++){
			if(npc[i].getY() <= win.getHeight() + npc[i].getHeight()){
				npc[i].doPush();
				npc[i].update(timePassed);
			}
		}
	}

	public int calculatePoints(){
		int r = player.getHowManyRight(), w = player.getHowManyWrong();
		int p = 1000 * r;
		p -= ( p * w ) * 0.03;
		return p;
	}

	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == KeyEvent.VK_ESCAPE){
			stop();
		}
		else{
			player.tryPush(Character.toLowerCase( (char)KeyCode ));
		}
	}

	public void setDifficult(int i) throws Exception {
		if (!getRunning()){
			difficult = i;
		}
		else throw new Exception("Can't change difficult while running.");
	}

	public Results getResults() throws Exception {
		if (!getRunning()){
			int r = player.getHowManyRight();
			int w = player.getHowManyWrong();
			int p = calculatePoints();
			return new Results(p, r, w, placementN, died);
		}
		else throw new Exception("Can't get results while running.");
	}
}
