/**
 *
 */
package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author ComplexObject
 */

public class EntityOption implements Serializable {

    @Attribute(name = "id", required = false)
    Integer id;
    @Attribute(name = "option_text", required = false)
    String optionText;
    @Attribute(name = "image_url", required = false)
    String backgroundImage;
    @Attribute(name = "option_grid", required = false)
    String grid;
    @Attribute(name = "iscorrect", required = false)
    boolean isCorrect;
    @Attribute(name = "bg_color", required = false)
    String backgroundColor;

    @Attribute(name = "next_entity", required = false)
    Integer nextEntity;

    @ElementMap(entry = "info_cards", key = "key", attribute = true, inline = false, required = false)
    HashMap<Integer, InfoCard> cards = new HashMap<>();

    @Attribute(name = "eval_script", required = false)
    String evaluationScript;

    @Attribute(name = "media_url", required = false)
    String mediaUrl;
    @Attribute(name = "media_type", required = false)
    String mediaType;

    public HashMap<Integer, InfoCard> getCards() {
        return cards;
    }

    public void setCards(HashMap<Integer, InfoCard> cards) {
        this.cards = cards;
    }


    public EntityOption() {
        super();
        // TODO Auto-generated constructor stub
    }


    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }


    public Integer getNextEntity() {
        return nextEntity;
    }

    public void setNextEntity(Integer nextEntity) {
        this.nextEntity = nextEntity;
    }

    public String getEvaluationScript() {
        return evaluationScript;
    }

    public void setEvaluationScript(String evaluationScript) {
        this.evaluationScript = evaluationScript;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
