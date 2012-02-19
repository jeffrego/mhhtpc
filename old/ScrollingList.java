import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ScrollingList {

  JPanel panel;

  int width;
  int height;
  int x;
  int y;

  int glowX;
  int glowY;
  int glowWidth;
  int glowHeight;

  Image image;

  public ScrollingList( JPanel panel, int width, int height, int x, int y ) {
    this.panel = panel;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;

    glowX = x+((int)(width/10.0));
    glowY = (int)(height/10.0*1.5);
    glowWidth = (int)(width/10.0*8);
    glowHeight = (int)(height/10.0);
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;

    FontRenderContext frc = g2d.getFontRenderContext();
    Font f = new Font("Helvetica Neue",Font.BOLD, (int)(0.075*width));
    String s = new String("Front Row");
    FontMetrics fm = g2d.getFontMetrics(f);
    TextLayout tl = new TextLayout(s, f, frc);
    g2d.setColor(Color.white);
    int stringWidth = fm.stringWidth(s);
    int stringHeight = fm.getHeight();

    drawGlow(g, glowX, glowY, glowWidth, glowHeight);

    tl.draw(g2d, x+(width-stringWidth)/2, (int)(height/10.0));

    drawString(g2d, "Movies", x+((int)(width/10.0*1.5)), (int)(height/10.0*2));
  }

  public void handleDownArrow() {
    glowY += glowHeight;

    panel.repaint();
  }

  public void handleUpArrow() {
    glowY -= glowHeight;

    panel.repaint();
  }

  public void drawString(Graphics2D g2d, String s, int x, int y) {
    FontRenderContext frc = g2d.getFontRenderContext();
    Font f = new Font("Helvetica Neue", Font.BOLD, (int)(0.065*width));
    FontMetrics fm = g2d.getFontMetrics(f);
    TextLayout tl = new TextLayout(s,f,frc);
    g2d.setColor(Color.white);
    int stringWidth = fm.stringWidth(s);
    int stringHeight = fm.getHeight();

    tl.draw(g2d, x, y);
  }

  public void drawGlow(Graphics g, int x, int y, int width, int height) {
    if (image == null) {
      ImageIcon ii = new ImageIcon(panel.getClass().getResource("glowbg.jpg"));
      image = ii.getImage();
      image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    g.drawImage(image, x, y, panel);
  }
}
