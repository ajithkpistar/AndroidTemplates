/**
 *
 */
package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;

import java.io.Serializable;

/**
 * @author ComplexObject
 */
public class CardContent implements Serializable {

    @Attribute(name = "aBackgroundColor", required = false)
    String aBackgroundColor;
    @Attribute(name = "aBackgroundImage", required = false)
    String aBackgroundImage;
    @Attribute(name = "aForegroundImage", required = false)
    String aForegroundImage;
    @Attribute(name = "aTitle", required = false)
    String aTitle;
    @Attribute(name = "aDescription", required = false)
    String aDescription;

    @Attribute(name = "bBackgroundColor", required = false)
    String bBackgroundColor;
    @Attribute(name = "bBackgroundImage", required = false)
    String bBackgroundImage;
    @Attribute(name = "bTopleftBGImage", required = false)
    String bTopleftBGImage;
    @Attribute(name = "bTopRightBGImage", required = false)
    String bTopRightBGImage;
    @Attribute(name = "bBottomLeftBGImage", required = false)
    String bBottomLeftBGImage;
    @Attribute(name = "bBottomRightBGImage", required = false)
    String bBottomRightBGImage;


    @Attribute(name = "bTopleftFGImage", required = false)
    String bTopleftFGImage;
    @Attribute(name = "bTopRightFGImage", required = false)
    String bTopRightFGImage;
    @Attribute(name = "bBottomLeftFGImage", required = false)
    String bBottomLeftFGImage;
    @Attribute(name = "bBottomRightFGImage", required = false)
    String bBottomRightFGImage;

    @Attribute(name = "bCenterTopBackgroundImage", required = false)
    String bCenterTopBackgroundImage;
    @Attribute(name = "bCenterTopForegrounImage", required = false)
    String bCenterTopForegroundImage;
    @Attribute(name = "bCenterBottomBackgroundImage", required = false)
    String bCenterBottomBackgroundImage;
    @Attribute(name = "bCenterBottomForegroundImage", required = false)
    String bCenterBottomForegroundImage;


    @Attribute(name = "bTopLeftMediaUrl", required = false)
    String bTopLeftMediaUrl;
    @Attribute(name = "bTopRightMediaUrl", required = false)
    String bTopRightMediaUrl;
    @Attribute(name = "bBottomLeftMediaUrl", required = false)
    String bBottomLeftMediaUrl;
    @Attribute(name = "bBottomRightMediaUrl", required = false)
    String bBottomRightMediaUrl;
    @Attribute(name = "bCenterTopMediaUrl", required = false)
    String bCenterTopMediaUrl;
    @Attribute(name = "bCenterBottomMediaUrl", required = false)
    String bCenterBottomMediaUrl;

    @Attribute(name = "bTopLeftMediaType", required = false)
    String bTopLeftMediaType;
    @Attribute(name = "bTopRightMediaType", required = false)
    String bTopRightMediaType;
    @Attribute(name = "bBottomLeftMediaType", required = false)
    String bBottomLeftMediaType;
    @Attribute(name = "bBottomRightMediaType", required = false)
    String bBottomRightMediaType;
    @Attribute(name = "bCenterTopMediaType", required = false)
    String bCenterTopMediaType;
    @Attribute(name = "bCenterBottomMediaType", required = false)
    String bCenterBottomMediaType;


    public CardContent() {
        super();
        // TODO Auto-generated constructor stub
    }


    public String getaBackgroundColor() {
        return aBackgroundColor;
    }


    public void setaBackgroundColor(String aBackgroundColor) {
        this.aBackgroundColor = aBackgroundColor;
    }


    public String getaBackgroundImage() {
        return aBackgroundImage;
    }


    public void setaBackgroundImage(String aBackgroundImage) {
        this.aBackgroundImage = aBackgroundImage;
    }


    public String getaForegroundImage() {
        return aForegroundImage;
    }


    public void setaForegroundImage(String aForegroundImage) {
        this.aForegroundImage = aForegroundImage;
    }


    public String getaTitle() {
        return aTitle;
    }


    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }


    public String getaDescription() {
        return aDescription;
    }


    public void setaDescription(String aDescription) {
        this.aDescription = aDescription;
    }


    public String getbTopleftBGImage() {
        return bTopleftBGImage;
    }


    public void setbTopleftBGImage(String bTopleftBGImage) {
        this.bTopleftBGImage = bTopleftBGImage;
    }


    public String getbTopRightBGImage() {
        return bTopRightBGImage;
    }


    public void setbTopRightBGImage(String bTopRightBGImage) {
        this.bTopRightBGImage = bTopRightBGImage;
    }


    public String getbBottomLeftBGImage() {
        return bBottomLeftBGImage;
    }


    public void setbBottomLeftBGImage(String bBottomLeftBGImage) {
        this.bBottomLeftBGImage = bBottomLeftBGImage;
    }


    public String getbBottomRightBGImage() {
        return bBottomRightBGImage;
    }


    public void setbBottomRightBGImage(String bBottomRightBGImage) {
        this.bBottomRightBGImage = bBottomRightBGImage;
    }


    public String getbTopleftFGImage() {
        return bTopleftFGImage;
    }


    public void setbTopleftFGImage(String bTopleftFGImage) {
        this.bTopleftFGImage = bTopleftFGImage;
    }


    public String getbTopRightFGImage() {
        return bTopRightFGImage;
    }


    public void setbTopRightFGImage(String bTopRightFGImage) {
        this.bTopRightFGImage = bTopRightFGImage;
    }


    public String getbBottomLeftFGImage() {
        return bBottomLeftFGImage;
    }


    public void setbBottomLeftFGImage(String bBottomLeftFGImage) {
        this.bBottomLeftFGImage = bBottomLeftFGImage;
    }


    public String getbBottomRightFGImage() {
        return bBottomRightFGImage;
    }


    public void setbBottomRightFGImage(String bBottomRightFGImage) {
        this.bBottomRightFGImage = bBottomRightFGImage;
    }


    public String getbCenterTopBackgroundImage() {
        return bCenterTopBackgroundImage;
    }


    public void setbCenterTopBackgroundImage(String bCenterTopBackgroundImage) {
        this.bCenterTopBackgroundImage = bCenterTopBackgroundImage;
    }


    public String getbCenterTopForegrounImage() {
        return bCenterTopForegroundImage;
    }


    public void setbCenterTopForegroundImage(String bCenterTopForegrounImage) {
        this.bCenterTopForegroundImage = bCenterTopForegrounImage;
    }


    public String getbCenterBottomBackgroundImage() {
        return bCenterBottomBackgroundImage;
    }


    public void setbCenterBottomBackgroundImage(String bCenterBottomBackgroundImage) {
        this.bCenterBottomBackgroundImage = bCenterBottomBackgroundImage;
    }


    public String getbCenterBottomForegroundImage() {
        return bCenterBottomForegroundImage;
    }


    public void setbCenterBottomForegroundImage(String bCenterBottomForegroundImage) {
        this.bCenterBottomForegroundImage = bCenterBottomForegroundImage;
    }


    public String getbTopLeftMediaUrl() {
        return bTopLeftMediaUrl;
    }


    public void setbTopLeftMediaUrl(String bTopLeftMediaUrl) {
        this.bTopLeftMediaUrl = bTopLeftMediaUrl;
    }


    public String getbTopRightMediaUrl() {
        return bTopRightMediaUrl;
    }


    public void setbTopRightMediaUrl(String bTopRightMediaUrl) {
        this.bTopRightMediaUrl = bTopRightMediaUrl;
    }


    public String getbBottomLeftMediaUrl() {
        return bBottomLeftMediaUrl;
    }


    public void setbBottomLeftMediaUrl(String bBottomLeftMediaUrl) {
        this.bBottomLeftMediaUrl = bBottomLeftMediaUrl;
    }


    public String getbBottomRightMediaUrl() {
        return bBottomRightMediaUrl;
    }


    public void setbBottomRightMediaUrl(String bBottomRightMediaUrl) {
        this.bBottomRightMediaUrl = bBottomRightMediaUrl;
    }


    public String getbCenterTopMediaUrl() {
        return bCenterTopMediaUrl;
    }


    public void setbCenterTopMediaUrl(String bCenterTopMediaUrl) {
        this.bCenterTopMediaUrl = bCenterTopMediaUrl;
    }


    public String getbCenterBottomMediaUrl() {
        return bCenterBottomMediaUrl;
    }


    public void setbCenterBottomMediaUrl(String bCenterBottomMediaUrl) {
        this.bCenterBottomMediaUrl = bCenterBottomMediaUrl;
    }


    public String getbTopLeftMediaType() {
        return bTopLeftMediaType;
    }


    public void setbTopLeftMediaType(String bTopLeftMediaType) {
        this.bTopLeftMediaType = bTopLeftMediaType;
    }


    public String getbTopRightMediaType() {
        return bTopRightMediaType;
    }


    public void setbTopRightMediaType(String bTopRightMediaType) {
        this.bTopRightMediaType = bTopRightMediaType;
    }


    public String getbBottomLeftMediaType() {
        return bBottomLeftMediaType;
    }


    public void setbBottomLeftMediaType(String bBottomLeftMediaType) {
        this.bBottomLeftMediaType = bBottomLeftMediaType;
    }


    public String getbBottomRightMediaType() {
        return bBottomRightMediaType;
    }


    public void setbBottomRightMediaType(String bBottomRightMediaType) {
        this.bBottomRightMediaType = bBottomRightMediaType;
    }


    public String getbCenterTopMediaType() {
        return bCenterTopMediaType;
    }


    public void setbCenterTopMediaType(String bCenterTopMediaType) {
        this.bCenterTopMediaType = bCenterTopMediaType;
    }


    public String getbCenterBottomMediaType() {
        return bCenterBottomMediaType;
    }


    public void setbCenterBottomMediaType(String bCenterBottomMediaType) {
        this.bCenterBottomMediaType = bCenterBottomMediaType;
    }

    public String getbBackgroundColor() {
        return bBackgroundColor;
    }

    public void setbBackgroundColor(String bBackgroundColor) {
        this.bBackgroundColor = bBackgroundColor;
    }

    public String getbBackgroundImage() {
        return bBackgroundImage;
    }

    public void setbBackgroundImage(String bBackgroundImage) {
        this.bBackgroundImage = bBackgroundImage;
    }
}
