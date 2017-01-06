import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import javax.swing.*;

public class ResultScreen extends Core implements MouseMotionListener,MouseListener,KeyListener {
	private SpermRace.Results result;
	private Resources rs;
	private MenuOptions menu;
	private Animation sperm;
	private Point curMousePoint = new Point(0, 0);

	public ResultScreen(SpermRace.Results result){
		this.result = result;
	}

	public void setup(){
		win.addMouseListener(this);
		win.addMouseMotionListener(this);
		win.addKeyListener(this);

		rs = new Resources();

		LinkedHashMap<Runnable,String> entries = new LinkedHashMap<>();
		entries.put(() -> runNext(new DifficultChoice()), "Play Again");
		entries.put(() -> runNext(new Menu()), "Menu");
		entries.put(() -> runNext(null), "Quit");
		menu = new MenuOptions(win, sm.getGraphics(), font, entries, new Point(win.getWidth()/2 - 200,0));

		sperm = new Animation();
		sperm.addScene(new ImageIcon( rs.root+"/imgs/sperm1.png" ).getImage(),1000);
		sperm.addScene(new ImageIcon( rs.root+"/imgs/sperm2.png" ).getImage(),1000);
	}

	public void runNext(Core next){
		stop();
		if (next != null){
			next.setAfter(() -> (new Menu().run()));
			this.setAfter(() -> next.run());
		}
	}

	public synchronized void draw(Graphics2D g){
		//BG
		g.setColor(win.getBackground());
		g.fillRect(0,0,win.getWidth(),win.getHeight());
		g.drawImage(rs.bg, 0,0, win.getWidth(), rs.bg.getHeight(null), null);
		g.drawImage(sperm.getImage(), 50,( win.getHeight()/2 - sperm.getHeight()/2 ),null);
		//UI
		g.setColor(win.getForeground());
		result.draw(g, new Point(
				win.getWidth()/2 - 200,
				win.getHeight()/2 - 100));
			//Menu
		menu.draw(g);
	}

	public void update(long timePassed){
		sperm.update(timePassed);
		menu.update(timePassed, curMousePoint);
	}

	public void mouseMoved(MouseEvent e){
		curMousePoint = e.getPoint();
	}

	public void mouseClicked(MouseEvent e){
		menu.click(e.getPoint());
	}

	public void keyPressed(KeyEvent e){
		int KeyCode = e.getKeyCode();
		if (KeyCode == KeyEvent.VK_ESCAPE)
			stop();
	}
}
