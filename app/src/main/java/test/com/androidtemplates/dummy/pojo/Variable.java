package test.com.androidtemplates.dummy.pojo;

/**
 * Created by ajith on 14-02-2017.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * @author ComplexObject
 *
 */
@Root(name = "variable")
public class Variable implements Serializable {

    @Attribute(name = "id", required = false)
    Integer id;
    @Attribute(name = "name", required = false)
    String name;
    @Attribute(name = "value", required = false)
    Integer value;
    @Attribute(name = "minvalue", required = false)
    Integer minvalue;
    @Attribute(name = "maxvalue", required = false)
    Integer maxvalue;
    @Attribute(name = "variable_icon", required = false)
    String icon;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(Integer minvalue) {
        this.minvalue = minvalue;
    }

    public Integer getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(Integer maxvalue) {
        this.maxvalue = maxvalue;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}