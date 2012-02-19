public class ListItem {
  String text;
  String path; // For media files and directories
  boolean isDirectory;

  public ListItem( String text, boolean isDirectory ) {
    this.text = text;
    this.isDirectory = isDirectory;
  }

  public String getText() {
    return text;
  }
  public void setText( String text ) {
    this.text = text;
  }

  public String getPath() {
    return path;
  }
  public void setPath( String path ) {
    this.path = path;
  }

  public boolean isDirectory() {
    return isDirectory;
  }
  public void setIsDirectory( boolean isDirectory ) {
    this.isDirectory = isDirectory;
  }
}
