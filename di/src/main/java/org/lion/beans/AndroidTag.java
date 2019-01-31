package org.lion.beans;

/**
 * Created by lion on 17-9-15.
 */
public class AndroidTag {
   private String id;
   private String name;

    public AndroidTag() {
    }

    public AndroidTag(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AndroidTag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
