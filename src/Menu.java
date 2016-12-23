import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

		String[] entries = new String[]{
			"Single Player","Multi Player","Option","Quit"
		};
		menu = new MenuOptions(win, sm.getGraphics(), font, entries, new Point(0,0));

		sperm = new Animation();
		sperm.addScene(new ImageIcon( rs.root+"/imgs/sperm1.png" ).getImage(),1000);
		sperm.addScene(new ImageIcon( rs.root+"/imgs/sperm2.png" ).getImage(),1000);
	}

	public synchronized void draw(Graphics2D g){
		//BG
		g.setColor(g.getBackground());
		g.fillRect(0,0,win.getWidth(),win.getHeight());
		g.drawImage(rs.bg, 0,0, win.getWidth(), rs.bg.getHeight(null), null);
		g.drawImage(sperm.getImage(), 50,( win.getHeight()/2 - sperm.getHeight()/2 ),null);
		//UI
		drawOutline(g,"SpermRace", 450,90, new Color(171, 193, 255), new Color(71, 120, 255), 2);
			//Menu
		menu.draw(g);
	}

	public void drawOutline(Graphics2D g, String text, int x, int y, Color out, Color in, int tick){
		g.setColor(out);
		g.drawString(text, x + tick, y + tick);
		g.drawString(text, x - tick, y + tick);
		g.drawString(text, x + tick, y - tick);
		g.drawString(text, x - tick, y - tick);
		g.setColor(in);
		g.drawString(text, x, y);
	}

	public void update(long timePassed){
		sperm.update(timePassed);
		menu.update(timePassed, curMousePoint);
	}

	public void mouseMoved(MouseEvent e){
		curMousePoint = e.getPoint();
	}
}
