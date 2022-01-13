package org.thoughtcrime.securesms.postcreation;

public class PostRecipient {
  String title = null;
  String name = null;
  boolean selected = false;

  public PostRecipient(String title, String name, boolean selected) {
    super();
    this.title = title;
    this.name = name;
    this.selected = selected;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public boolean isSelected() {
    return selected;
  }
  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
