import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ScrollingListController extends Thread {
  boolean scrollReset = true;
  boolean scrollWaiting = false;

  int targetIndex = 0;
  int scrollDirection = 0;

  JPanel panel;
  ListGlow glow;
  ListText text;

  public void setG(JPanel panel, ListGlow glow, ListText text) {
    this.panel = panel;
    this.glow = glow;
    this.text = text;
  }

  public void handleControlEvent(Constants.ControlEvent event) {
    switch (event) {
      case DOWN_PRESSED: startScrollingDown(); break;
      case UP_PRESSED: startScrollingUp(); break;
      default: stopScrolling(); break;
    }
  }

  public void startScrollingDown() {
    scrollReset = true;
    scrollDirection = 1;
    if( scrollWaiting ) {
      synchronized(this) {
        scrollWaiting = false;
        notify();
      }
    }
  }

  public void startScrollingUp() {
    scrollReset = true;
    scrollDirection = -1;
    if( scrollWaiting ) {
      synchronized(this) {
        scrollWaiting = false;
        notify();
      }
    }
  }

  public void stopScrolling() {
    scrollReset = true;
    scrollDirection = 0;
  }

  public ListItem getSelectedItem() {
    return text.getItem(targetIndex);
  }

  public boolean isGlowZone(int scrollDirection) {
    if(scrollDirection==1 && (targetIndex <= 5 || targetIndex >= text.getMaxIndex()-4)) {
      return true;
    } else if(scrollDirection==-1 && (targetIndex <= 4 || targetIndex >= text.getMaxIndex()-5)) {
      return true;
    }
    return false;
  }

  public void move(int scrollDirection, int scrollSpeed) throws InterruptedException {
    double scrollModifier = scrollDirection*.01;
    for( int i = 100/scrollSpeed; i != 0; i-- ) {
      if(isGlowZone(scrollDirection)) {
        glow.move(scrollModifier*scrollSpeed);
      } else {
        text.move(-1*scrollModifier*scrollSpeed);
      }

      panel.repaint();
      sleep(1);
    }
    if(!isGlowZone(scrollDirection)) {
      text.zeroOnIndex( targetIndex-5 );
    }
    panel.repaint();
  }

  public void delayAfterFirstScroll() throws InterruptedException {
    for( int i = 200; i != 0; i-- ) {
      sleep(1);
    }
  }

  public void run() {
    while( true ) {
      try {
        if( scrollDirection == 0 ) {
          scrollWaiting = true;
          synchronized(this) {
            if( scrollDirection == 0 && scrollWaiting ) {
              wait();
            }
          }
        }
        int currentScrollDirection = scrollDirection;
        scrollReset = false;
        int scrollSpeed = 1;
        int scrollCounter = 0;
        while( !scrollReset ) {
          if( scrollCounter == 1 ) {
            delayAfterFirstScroll();
          }
          synchronized(this) {
            if( scrollReset || scrollDirection == 0 || currentScrollDirection != scrollDirection ) break;
            if( targetIndex + currentScrollDirection >= 0 && targetIndex + currentScrollDirection < text.getMaxIndex() ) {
              targetIndex += currentScrollDirection;
              if( scrollCounter == 4 ) {
                scrollSpeed = 2;
              } else if ( scrollCounter == 8 ) {
                scrollSpeed = 5;
              }
              move(currentScrollDirection, scrollSpeed);
              scrollCounter++;
            }
          }
        }
      } catch( InterruptedException e ) {
        System.out.println(e);
      }
    }
  }
} 
