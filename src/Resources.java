import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Resources {
	public String root = Resources.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/..";
	public Image bg = new ImageIcon(root+"/imgs/ovulo.jpg").getImage();
	public Image title = new ImageIcon(root+"/imgs/title.png").getImage();
}
