import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;

public class DifficultChoice extends Core implements MouseMotionListener,MouseListener {
	private Resources rs;
	private SpermRace spermRace;
	private MenuOptions menu;
	private Point curMousePoint = new Point(0, 0);

	public void setup(){
		win.addMouseListener(this);
		win.addMouseMotionListener(this);

		rs = new Resources();

		spermRace = new SpermRace();

		LinkedHashMap<Runnable,String> entries = new LinkedHashMap<>();
		entries.put(() -> setDifficultAndRun(0), "Easy");
		entries.put(() -> setDifficultAndRun(1), "Normal");
		entries.put(() -> setDifficultAndRun(2), "Hard");
		entries.put(() -> setDifficultAndRun(3), "Extreme");
		menu = new MenuOptions(win, sm.getGraphics(), font, entries, new Point(0,100));
	}

	public void setDifficultAndRun(int i){
		stop();
		try{
			spermRace.setDifficult(i);
		}catch(Exception e) { }
		this.setAfter(() -> spermRace.run());
	}

	public synchronized void draw(Graphics2D g){
		//BG
		g.setColor(win.getBackground());
		g.fillRect(0,0,win.getWidth(),win.getHeight());
		g.drawImage(rs.bg, 0,0, win.getWidth(), rs.bg.getHeight(null), null);
		//UI
			//Menu
		menu.draw(g);
	}

	public void update(long timePassed){
		menu.update(timePassed, curMousePoint);
	}

	public void mouseMoved(MouseEvent e){
		curMousePoint = e.getPoint();
	}

	public void mouseClicked(MouseEvent e){
		menu.click(e.getPoint());
	}
}
