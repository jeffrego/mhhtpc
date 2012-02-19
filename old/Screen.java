import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Screen extends JFrame 
                                 implements ActionListener, KeyListener{

  private GraphicsDevice graphicsDevice;
  private DisplayMode origDisplayMode;
  private Stack<Display> displays = new Stack();
  
  public static void main(String[] args){
    //Get and display a list of graphics devices solely for
    // information purposes.
    GraphicsEnvironment graphicsEnvironment = 
         GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] devices = 
                    graphicsEnvironment.getScreenDevices();
    for(int cnt = 0;cnt < 1;cnt++){
      System.out.println(devices[cnt]);
    }//end for loop
    
    //Construct a full-screen object using
    // graphicsDevice 0.
    new Screen(devices[0]);
  }//end main

  //Constructor
  public Screen(GraphicsDevice graphicsDevice){
    //Save a reference to the graphics device as an
    // instance variable so that it can be used later to
    // exit the full-screen mode.
    this.graphicsDevice = graphicsDevice;
    
    setTitle("This title will be hidden (undecorated)");
    
    //Get and save a reference to the original display
    // mode as an instance variable so that it can be
    // restored later.
    origDisplayMode = graphicsDevice.getDisplayMode();
    
    //newContainer();
    //pushDisplay(new Display(this));

    if (graphicsDevice.isFullScreenSupported()){
      // Enter full-screen mode witn an undecorated,
      // non-resizable JFrame object.
      setUndecorated(true);
      setResizable(false);
      getContentPane().setBackground(Color.BLACK);

      //Make it happen!
      graphicsDevice.setFullScreenWindow(this);

      validate();

      addKeyListener(this);

      pushDisplay(new MainMenu(this));
      repaint();
//      validate();
    }else{
      System.out.println("Full-screen mode not supported");
    }//end else
  }

  public void toggleFullScreenOff() {
    graphicsDevice.setDisplayMode(origDisplayMode);
    validate();
  }

  public void toggleFullScreenOn() {
    graphicsDevice.setFullScreenWindow(this);
    validate();
  }

  public void pushDisplay(Display display) {
    displays.push(display);
    setContentPane(display);
    validate();
  }//end constructor

  public void popDisplay() {
    if( displays.size() > 1 ) {
      displays.pop();
      setContentPane(displays.peek());
      validate();
    } else {
      popMainMenu();
    }
  }

  public void popMainMenu() {
    graphicsDevice.setDisplayMode(origDisplayMode);
    System.exit(0);
  }

  public void keyPressed(KeyEvent e) {
    System.out.println("Pressed " + e.getKeyCode());
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ESCAPE: handleControlEvent( Constants.ControlEvent.MENU ); break;
      case KeyEvent.VK_ENTER: handleControlEvent( Constants.ControlEvent.PLAY_PAUSE ); break;
      case KeyEvent.VK_DOWN: handleControlEvent( Constants.ControlEvent.DOWN ); break;
      case KeyEvent.VK_UP: handleControlEvent( Constants.ControlEvent.UP ); break;
    }
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }

  public void handleControlEvent( Constants.ControlEvent event ) {
    if( displays.size() > 0 ) {
      displays.peek().handleControlEvent(event);
    }
  }

  //The following method is invoked when the used clicks
  // the exitButton
  public void actionPerformed(ActionEvent evt){
/*    System.out.println(evt.getActionCommand());
    if (evt.getActionCommand().equals("Next Level")) {
      containers.push(getContentPane());
      newContainer();
      validate();
      System.out.println("Down");
    } else {
      oldContainer();
      validate();
      //Restore the original display mode
//      graphicsDevice.setDisplayMode(origDisplayMode);
      //Terminate the program
//      System.exit(0);
    }*/
  }//end actionPerformed
}//end class
