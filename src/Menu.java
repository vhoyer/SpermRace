import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;

public class Menu extends Core implements MouseMotionListener,MouseListener,KeyListener{
	private Window win;
	private MenuOptions menu;
	private Animation sperm;
	private Resources rs = new Resources();
	private Point curMousePoint = new Point(0,0);

	public synchronized void setup(){
		win = sm.getFullScreenWindow();
		//win.setFont(font.deriveFont(100f));
		win.addMouseListener(this);
		win.addMouseMotionListener(this);
		win.addKeyListener(this);

		LinkedHashMap<Runnable,String> entries = new LinkedHashMap<>();
		entries.put(() -> runNext(new SpermRace()), "Single Player");
		entries.put(() -> runNext(null), "Multi Player");
		entries.put(() -> runNext(null), "Options");
		entries.put(() -> runNext(null), "Quit");
		menu = new MenuOptions(win, sm.getGraphics(), font, entries, new Point(0,100));

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
		g.setColor(g.getBackground());
		g.fillRect(0,0,win.getWidth(),win.getHeight());
		g.drawImage(rs.bg, 0,0, win.getWidth(), rs.bg.getHeight(null), null);
		g.drawImage(sperm.getImage(), 50,( win.getHeight()/2 - sperm.getHeight()/2 ),null);
		//UI
		g.drawImage(
				rs.title,
				win.getWidth()/2 - rs.title.getWidth(null)/2,
				10,
				null);
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
	}
}
