import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;

public class Menu extends Display {

  private Image image;
  private int x = 10;
  private int y = 10;

  private ScrollingList list;
  private List<ListItem> listItems;

  private String title;
  private String path;

  public Menu(Screen myScreen, String path, String title) {
    super(myScreen);

    this.path = path;
    this.title = title;

    listItems = new ArrayList();
    populateList();

    setBackground(Color.BLACK);
    setDoubleBuffered(true);

    double origHeight = myScreen.getHeight();
    double origWidth = myScreen.getWidth();
    double height = origHeight;
    double width = origWidth;
    double xOffset = 0;
    double yOffset = 0;
    if( width*9/16 < height ) {
      height = width*9/16;
      yOffset = (origHeight-height)/2;
    } else if( height*16/9 < width ) {
      width = height*16/9;
      xOffset = (origWidth-width)/2;
    }
    list = new ScrollingList(this, listItems, title, width/2, height, xOffset+width/2, yOffset);
  }

  public void populateList() {
  }

  public void addListItem( ListItem item ) {
    listItems.add(item);
  }

  public void setListItems( List<ListItem> listItems ) {
    this.listItems = listItems;
  }

  public String getPath() {
    return path;
  }
  public void setPath( String path ) {
    this.path = path;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public void handleControlEvent(Constants.ControlEvent event) {
    super.handleControlEvent(event);
    list.handleControlEvent(event);
    switch (event) {
      case PLAY_PAUSE: selectCurrentItem(); break;
    }
  }

  public void selectCurrentItem() {
  }

  public ListItem getSelectedItem() {
    return list.getSelectedItem();
  }

  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2d = (Graphics2D)g;
    list.paint(g);

    Toolkit.getDefaultToolkit().sync();

    g.dispose();
  }
}
