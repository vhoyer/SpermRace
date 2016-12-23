import java.awt.*;

public class MenuOptions {
	private Graphics2D g;
	private MenuItem[] itens;
	private FontMetrics metrics;
	private Dimension padding = new Dimension(20,10);
	private Point offset;

	public MenuOptions(Window w, Graphics2D g, Font font, String[] entries, Point offset){
		if(offset != null) this.offset = offset;
		else this.offset = new Point(0,0);

		this.g = g;
		metrics = g.getFontMetrics(font);

		Rectangle defaultEntryBox = getBigestEntryBox(entries);

		itens = new MenuItem[entries.length];
		for(int i = 0;i < itens.length; i++){
			Rectangle thisBox = defaultEntryBox.getBounds();

			Point itemOffset = new Point(
					w.getWidth()/2 + offset.x,
					w.getHeight()/2+(i - itens.length/2)*100 + offset.y);
			thisBox.setLocation(itemOffset.x, itemOffset.y);

			itens[i] = new MenuItem(
					w,
					metrics,
					entries[i],
					thisBox,
					itemOffset);
		}
	}

	public Rectangle getBigestEntryBox(String[] entries){//{{{
		int biggestStringWidth = 0;
		int biggestStringHeight = metrics.getHeight();
		for(int i = 0; i < entries.length; i++){
			int currentStringWidth = metrics.stringWidth(entries[i]);

			if(currentStringWidth > biggestStringWidth)
				biggestStringWidth = currentStringWidth;
		}

		return new Rectangle(
				biggestStringWidth + padding.width,
				biggestStringHeight + padding.height);
	}//}}}

	public void draw(Graphics2D g){
		for(int i = 0; i < itens.length; i++){
			itens[i].draw(g);
		}
	}

	public void update(long timePassed, Point mouse){
		int i;
		for(i = 0; i < itens.length; i++){
			itens[i].isHover(itens[i].getBox().contains(mouse), timePassed);
		}
	}

	class MenuItem {//{{{
		private FontMetrics metrics;
		private Rectangle entryBox;
		private Dimension minSize;
		private Window w;
		private String text;
		private float maxGrow;
		private float width;
		private float height;
		private Point offset;

		public MenuItem(Window w, FontMetrics metrics, String text, Rectangle entryBox, Point offset){
			this.w = w;
			this.text = text;
			this.entryBox = entryBox;
			this.metrics = metrics;
			this.offset = offset;
			this.minSize = entryBox.getSize();
			this.maxGrow = 1.1f;
			this.width = minSize.width;
			this.height = minSize.height;
		}

		public void isHover(boolean inHover, long timePassed){
			float timeMod = 0.1f;
			if(inHover){
				if(entryBox.getSize().width < minSize.width * maxGrow){
					this.width += maxGrow * timePassed * timeMod;
					this.height += maxGrow * timePassed * timeMod;
				}
			}
			else{
				if(entryBox.getSize().width > minSize.width){
					this.width -= maxGrow * timePassed * timeMod;
					this.height -= maxGrow * timePassed * timeMod;
				}
			}
			entryBox.setRect(
					offset.x - this.width/2,
					offset.y - this.height/2,
					this.width, this.height);
		}

		public void draw(Graphics2D g){
			g.setColor(w.getBackground());
			g.fillRect(
					entryBox.getLocation().x,entryBox.getLocation().y,
					entryBox.width,entryBox.height);
			g.setColor(w.getForeground());
			g.drawRect(
					entryBox.getLocation().x,entryBox.getLocation().y,
					entryBox.width,entryBox.height);
			g.drawString(text,
					entryBox.getLocation().x + this.width/2 - metrics.stringWidth(text)/2,
					entryBox.getLocation().y + this.height/2 + metrics.getHeight()/2.9f);
		}

		public Rectangle getBox(){
			return entryBox;
		}
	}//}}}
}
