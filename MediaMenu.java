import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;

public class MediaMenu extends Menu {

  public MediaMenu(Screen myScreen, String path, String title) {
    super(myScreen, path, title);
  }

  public void populateList() {
    setListItems(MediaFileListFactory.buildList(getPath()));
  }

  public void selectCurrentItem() {
    ListItem item = getSelectedItem();
    if (item.isDirectory()) {
      myScreen.pushDisplay(new MediaMenu(myScreen, item.getPath(), item.getText()));
    } else {
      myScreen.pushDisplay(new MediaController(myScreen, item.getPath()));
    }
  }
}
