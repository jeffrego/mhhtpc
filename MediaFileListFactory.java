import java.io.*;
import java.util.*;

public class MediaFileListFactory {
  private String path;
  private List<ListItem> list;

  public MediaFileListFactory( String path ) {
    super();

    this.path = path;
    buildList(path);
  }

  public static ArrayList<ListItem> buildList( String path ) {
    ArrayList<ListItem> list = new ArrayList<ListItem>();

    try {
      File dir = new File(path);
      File[] files = dir.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          File file = files[i];
          ListItem item = new ListItem(file.getName(), file.isDirectory());
          item.setPath(file.getAbsolutePath());
          list.add(item);
        }
      }
    } catch (Exception e) {
      System.out.println("Error populating media file list for directory: " + path);
      e.printStackTrace();
    }

    return list;
  }

  public String getPath() {
    return path;
  }
  public ListItem get(int index) {
    return list.get(index);
  }
  public boolean add(ListItem item) {
    return list.add(item);
  }
  public void add(int index, ListItem item) {
    list.add(index, item);
  }
  public void clear() {
    list.clear();
  }
  public ListItem remove(int index) {
    return list.remove(index);
  }
  public int size() {
    return list.size();
  }
}
