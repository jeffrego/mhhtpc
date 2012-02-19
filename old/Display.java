import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Display extends JPanel implements ActionListener {
  Screen myScreen;

  public Display( Screen screen ) {
    myScreen = screen;
  }

  public void handleControlEvent(Constants.ControlEvent event) {
    switch (event) {
      case MENU: handleMenuPress(); break;
      case PLAY_PAUSE: handlePlayPausePress(); break;
      case DOWN: handleDownArrow(); break;
      case UP: handleUpArrow(); break;
    }
  }

  public void handleMenuPress() {
    myScreen.popDisplay();
  }

  public void handlePlayPausePress() {
  }

  public void handleDownArrow() {
  }

  public void handleUpArrow() {
  }

  public void actionPerformed(ActionEvent evt){
    System.out.println(evt.getActionCommand());
    if (evt.getActionCommand().equals("Next Level")) {
      myScreen.pushDisplay(new Display(myScreen));
    } else {
      myScreen.popDisplay();
    }
  }
}
