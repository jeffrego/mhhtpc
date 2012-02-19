import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import org.apache.commons.net.telnet.*;

public class MediaController extends Display {

  public MediaController(Screen myScreen) {
    super(myScreen);

    setBackground(Color.BLACK);
    setDoubleBuffered(true);

    startPlayer();
    runCommands();
  }

  public void runCommands() {
    TelnetClient tl = new TelnetClient();
    try {
        tl.connect("localhost", 4444);
        if(tl.isConnected()) {
            System.out.println("Connected successfully!");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(tl.getOutputStream()));
            bw.write("add /Users/jrego/Movies/Coraline.m4v");
            bw.flush();
        } else {
            System.err.println("Problem with connection");
        }
    } catch(Exception e) {
        System.err.println("Telnet connection threw an exception: "+e.getMessage());
    }
  }

  public void startPlayer() {
    try {
      ProcessBuilder pb = new ProcessBuilder("/Applications/VLC.app/Contents/MacOS/VLC", "--extraintf", "oldrc", "--rc-host", "localhost:4444", "--rc-fake-tty", "--video-on-top", "--fullscreen");
      Process p = pb.start();

      StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), false);
      StreamGobbler inputGobbler = new StreamGobbler(p.getInputStream(), false);
      errorGobbler.start();
      inputGobbler.start(); 
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }

  public void handlePlayPausePress() {
  }

  public void handleDownArrow() {
  }

  public void handleUpArrow() {
  }

  public void paint(Graphics g) {
    super.paint(g);

    Toolkit.getDefaultToolkit().sync();

    g.dispose();
  }

  class StreamGobbler extends Thread {
    InputStream is;
    boolean discard;
    StreamGobbler(InputStream is, boolean discard) {
      this.is = is;
      this.discard = discard;
    }
    public void run() {
      try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line=null;
        while ( (line = br.readLine()) != null)
          if( line.indexOf("Running vlc") != -1 ) {
            runCommands();
            myScreen.toggleFullScreenOff();
          }
          if(!discard)
            System.out.println(line);
        }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }
}
