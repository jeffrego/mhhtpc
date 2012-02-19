import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;

public class ScrollingList {
  JPanel panel;

  double width;
  double height;
  double x;
  double y;

  String title;
  ListGlow glow;
  ListText text;

  Image image;

  ListItem[] listItems = new ListItem[12];

  ScrollingListController slt = new ScrollingListController();

  public ScrollingList( JPanel panel, List<ListItem> listItems, String title, double width, double height, double x, double y ) {
    this.panel = panel;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;

    this.title = title;
    glow = new ListGlow(panel, width, height, x, y);
    text = new ListText(panel, listItems, width, height, x, y);

    slt.setG(panel, glow, text);
    slt.start();

//    for( int i = 1; i < 12; i++ ) {
//      listItems[i] = new ListItem("Movies " + i);
//    }
//    listItems[3] = new ListItem("TV Shows");
  }

  public ListItem getSelectedItem() {
    return slt.getSelectedItem();
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    FontRenderContext frc = g2d.getFontRenderContext();
    Font f = new Font("Lucida Grande",Font.BOLD, (int)(0.065*width));
    String s = new String(title);
    FontMetrics fm = g2d.getFontMetrics(f);
    TextLayout tl = new TextLayout(s, f, frc);

//drawBackground(g);

    glow.draw(g);
    text.draw(g);

    g2d.setColor(Color.white);
    double stringWidth = fm.stringWidth(s);
    double stringHeight = fm.getHeight();
    tl.draw(g2d, (int)(x+width/66.58+(width/1.17-stringWidth)/2), (int)(y+height/6.61));
  }

  public void handleControlEvent(Constants.ControlEvent event) {
    slt.handleControlEvent(event);
  }

  public void drawBackground(Graphics g) {
    ImageIcon ii = new ImageIcon(panel.getClass().getResource("smallhtpc.png"));
    Image image = ii.getImage();
    g.drawImage(image, 0, 0, panel);
  }
}
