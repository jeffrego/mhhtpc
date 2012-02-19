import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;

public class MainMenu extends Menu {

  public MainMenu(Screen myScreen) {
    super(myScreen, "", "Front Row");
  }

  public void populateList() {
    addListItem(new ListItem("Movies", true));
    addListItem(new ListItem("Shows", true));
    addListItem(new ListItem("Trailers", true));
    addListItem(new ListItem("Music", true));
    addListItem(new ListItem("XBox 360", false));
    addListItem(new ListItem("Wii", false));
    addListItem(new ListItem("Power Off", false));
  }

  public void selectCurrentItem() {
    ListItem item = getSelectedItem();
    if (item.getText().equals("Movies")) {
      myScreen.pushDisplay(new MediaMenu(myScreen, "/Users/jrego/Movies", "Movies"));
//      myScreen.pushDisplay(new MediaController(myScreen));
    }
  }
}
