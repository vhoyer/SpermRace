import java.awt.*;

public class MenuOptions {
	private Graphics2D g;
	private MenuItem[] itens;
	private FontMetrics metrics;
	private Dimension padding = new Dimension(20,10);
	private Point offset;

	public MenuOptions(Graphics2D g, Font font, String[] entries, Point offset){//{{{
		this(g, font, entries);
		this.offset = offset;
	}//}}}
	public MenuOptions(Graphics2D g, Font font, String[] entries){
		offset = new Point(0,0);
		this.g = g;
		metrics = g.getFontMetrics(font);

		Rectangle defaultEntryBox = getBigestEntryBox(entries);

		itens = new MenuItem[entries.length];
		for(int i = 0;i < itens.length; i++){
			itens[i] = new MenuItem(entries[i], defaultEntryBox);
		}
	}

	public Rectangle getBigestEntryBox(String[] entries){//{{{
		int biggestStringWidth = 0;
		int biggestStringHeight = 0;
		for(String entry: entries){
			int currentStringWidth = metrics.stringWidth(entry);
			int currentStringHeight = metrics.getHeight();

			if(currentStringWidth > biggestStringWidth)
				biggestStringWidth = currentStringWidth;
			if(currentStringHeight > biggestStringHeight)
				biggestStringHeight = biggestStringHeight;
		}

		return new Rectangle(
				biggestStringWidth + padding.width,
				biggestStringHeight + padding.height);
	}//}}}

	public void update(Point mouse){
		for(int i = 0; i < intens.length; i++){
			if (itens[i].contains(mouse){
				//change the state of the rectangle to show it's been hovered
			}
		}
	}

	class MenuItem {//{{{
		private Rectangle entryBox;

		public MenuItem(String text, Rectangle entryBox){
		}
	}//}}}
}
