package test.com.androidtemplates;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;

import test.com.androidtemplates.dummy.pojo.PPTFile;

/**
 * Created by ajith on 17-02-2017.
 */

@Root(name="XMLS")
public class XMLS   implements Serializable {

    @ElementList(name = "files", inline = true, required = false)
    ArrayList<PPTFile> files ;

    public ArrayList<PPTFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<PPTFile> files) {
        this.files = files;
    }
}
