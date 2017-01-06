import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Core implements KeyListener,MouseMotionListener,MouseListener,MouseWheelListener {
	private static final DisplayMode modes[] = {
		new DisplayMode(800, 600,32,0),
		new DisplayMode(800, 600,24,0),
		new DisplayMode(800, 600,16,0),
		new DisplayMode(640, 480,32,0),
		new DisplayMode(640, 480,24,0),
		new DisplayMode(640, 480,16,0)
	};
	private boolean running;
	protected ScreenManager sm;
	protected Window win;
	public Font font;
	private Runnable after;

	//stop Method
	public void stop(){
		running = false;
	}

	//call init and gameloop
	public void run(){
		try{
			init();
			setup();
			gameLoop();
		}catch(Exception err){
			System.out.println("Internal error in method run()");
			err.printStackTrace(System.out);
		}finally{
			sm.restoreScreen();
		}
	}

	//set to full screen
	public synchronized void init(){
		sm = new ScreenManager();
		DisplayMode dm = sm.findFirsCompatibleMode(modes);
		sm.setFullScreen(dm);

		win = sm.getFullScreenWindow(dm);
		font = new Font("Arial", Font.PLAIN, 20);
		win.setFont(font);
		win.setBackground(Color.black);
		win.setForeground(Color.white);
		running = true;
	}

	public synchronized void gameLoop(){
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;

		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;

			update(timePassed);

			//draw and update the screen
			Graphics2D g = sm.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			draw(g);
			g.dispose();
			sm.update();

			try{
				Thread.sleep(20);
			}catch(Exception e){ System.out.println("Thread slp20 error"); }
		}
		executeAfter();
	}

	public synchronized void executeAfter() {
		try{
			if (after != null)
				(new Thread(after)).start();
			else
				System.out.println("Exited successfuly");
		}catch(java.lang.NullPointerException e){
			System.out.println("Error: ");
			System.out.println(e);
		}
	}

	public void update(long timePassed) { }
	public abstract void draw(Graphics2D g);
	public abstract void setup();

	//////////////////////////////////////
	public boolean getRunning(){
		return running;
	}
	public void setAfter(Runnable r){
		after = r;
	}
	//////////////////////////////////////

	public void mousePressed(MouseEvent e){ }
	public void mouseReleased(MouseEvent e){ }
	public void mouseClicked(MouseEvent e){ }
	public void mouseEntered(MouseEvent e){ }
	public void mouseExited(MouseEvent e){ }

	public void mouseDragged(MouseEvent e){ }
	public void mouseMoved(MouseEvent e){ }

	public void mouseWheelMoved(MouseWheelEvent e){ }

	//key pressed
	public void keyPressed(KeyEvent e){ stop(); }
	public void keyReleased(KeyEvent e){ }
	public void keyTyped(KeyEvent e){ }
}
