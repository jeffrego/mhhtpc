import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ListGlow {
  JPanel panel;

  double x;
  double y;
  double yPercentOffset = 0.0;
  double width;
  double height;

  double startY;
  double yOffset;

  Image glow;

  public ListGlow( JPanel panel, double width, double height, double x, double y ) {
    this.panel = panel;
    this.x = x+width/12.9-width/16.0;
    this.y = y+height/3.96-height/14.2;
    this.startY = this.y;
    this.yOffset = height/13.85;
    this.width = width/1.17;
    this.height = height/9.0;

    System.out.println("Image init");
    ImageIcon ii = new ImageIcon(panel.getClass().getResource("glowbg.jpg"));
    glow = ii.getImage();
    glow = glow.getScaledInstance((int)this.width, (int)this.height, Image.SCALE_SMOOTH);
    System.out.println("Done init");
  }

  public void draw(Graphics g) {
    g.drawImage(glow, (int)x, (int)y, panel);
  }

  public synchronized void move(double percentMove) {
    yPercentOffset += percentMove;
    y = startY + yOffset*yPercentOffset;
  }
}
