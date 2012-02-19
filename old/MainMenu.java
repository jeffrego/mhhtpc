import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class MainMenu extends Display {

  private Image image;
  private int x = 10;
  private int y = 10;

  private ScrollingList list;

  public MainMenu(Screen myScreen) {
    super(myScreen);

    setBackground(Color.BLACK);
    setDoubleBuffered(true);
//    ImageIcon ii = new ImageIcon(this.getClass().getResource("test.png"));
//    image = ii.getImage();

    list = new ScrollingList(this, myScreen.getWidth()/2, myScreen.getHeight(), myScreen.getWidth()/2, 0);
//    setLayout(new GridBagLayout());
  }

  public void handlePlayPausePress() {
    myScreen.pushDisplay(new MediaController(myScreen));
    //myScreen.pushDisplay(new MainMenu(myScreen));
  }

  public void handleDownArrow() {
    list.handleDownArrow();
  }

  public void handleUpArrow() {
    list.handleUpArrow();
  }

  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2d = (Graphics2D)g;
//    g2d.drawImage(image, x, y, this);
    list.paint(g);

    Toolkit.getDefaultToolkit().sync();

    g.dispose();
  }
}
