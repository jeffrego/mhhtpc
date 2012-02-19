import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;

public class ListText {
  JPanel panel;

  double x;
  double y;
  double yPercentOffset = 0.0;
  double width;
  double height;

  double startY;
  double yOffset;

  List<ListItem> listItems;

  int topIndex = 0;
  int maxIndex = 16;

  Image folder;

  public ListText( JPanel panel, List<ListItem> listItems, double width, double height, double x, double y ) {
    this.panel = panel;
    this.listItems = listItems;
    this.maxIndex = listItems.size();
    this.x = x;
    this.y = y;
    this.startY = this.y;
    this.yOffset = height/13.85;
    this.width = width;
    this.height = height;

    try {
      folder = ImageIO.read(panel.getClass().getResource("folder.png"));
      folder = folder.getScaledInstance((int)(width/1.17), (int)(height/9.0), Image.SCALE_SMOOTH);
    } catch( IOException e ) {
      e.printStackTrace();
    }
  }

  public ListItem getItem(int index) {
    return listItems.get(index);
  }

  public int getMaxIndex() {
    return maxIndex;
  }

  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;

    // These could change in the middle of rendering and screw us up
    // don't want to hold up updates though if rendering is slow
    // cache these values locally synchronously so they all match
    double y;
    double yPercentOffset;
    int topIndex;
    synchronized(this) {
      y = this.y;
      yPercentOffset = this.yPercentOffset;
      topIndex = this.topIndex;
    }

    int startOffset = 0;
    if( yPercentOffset < 0 ) {
      startOffset--;
    }
    startOffset = topIndex;
    if( startOffset > 0 ) {
      drawItem( g2d, startOffset-1, -1, y );
    }
    for( int i = 0; i < Math.min(10, maxIndex-startOffset); i++ ) {
      drawItem( g2d, startOffset+i, i, y );
    }
    if( topIndex+10 < maxIndex ) {
      drawItem( g2d, startOffset+10, 10, y );
    }

    Font f = new Font("Lucida Grande", Font.BOLD, (int)(0.0453*width));
    FontMetrics fm = g2d.getFontMetrics(f);
    double fontHeight = fm.getHeight();

    g2d.setColor(Color.black);
    //g2d.fill(new Rectangle2D.Double(x,startY+height/3.96-2*yOffset,width,2*yOffset-height/70.5));
    g2d.fill(new Rectangle2D.Double(x,startY+height/3.96-3*yOffset,width,2*yOffset));
    g2d.fill(new Rectangle2D.Double(x,startY+height/3.96+10*yOffset-(fontHeight*.75),width,3*yOffset));
  }

  public void drawItem( Graphics2D g2d, int index, int offset, double y ) {
    ListItem item = getItem(index);
    if( item.isDirectory() ) {
      g2d.drawImage(folder, (int)(x+width/12.9-width/16.0), (int)(y+height/3.96-height/14.2+yOffset*offset), panel);
    }
    drawString( g2d, item.getText(), (int)(x+width/12.9), (int)(y+height/3.96+yOffset*offset) );
  }

  public void drawString(Graphics2D g2d, String s, int x, int y) {
    FontRenderContext frc = g2d.getFontRenderContext();
    Font f = new Font("Lucida Grande", Font.BOLD, (int)(0.0453*width));
    //FontMetrics fm = g2d.getFontMetrics(f);
    TextLayout tl = new TextLayout(s,f,frc);
    g2d.setColor(Color.white);
    //double stringWidth = fm.stringWidth(s);
    //double stringHeight = fm.getHeight();

    tl.draw(g2d, x, y);
  }

  public synchronized void zeroOnIndex(int index) {
    synchronized(this) {
      setTopIndex(index);
      yPercentOffset = 0;
      y = startY;
    }
  }

  public void setTopIndex(int topIndex) {
    this.topIndex = topIndex;
  }

  public void move(double percentMove) {
    yPercentOffset += percentMove;
    synchronized(this) {
      y = startY + yOffset*yPercentOffset;
    }
  }
}
