package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;

/**
 * Created by ajith on 17-02-2017.
 */

public class PPTFile {
    @Attribute(name = "name", required = false)
    String name;
    @Attribute(name = "id", required = false)
    Integer id;
    @Attribute(name = "createdAt", required = false)
    String createdAt;

    public PPTFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
