/**
 *
 */
package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author ComplexObject
 */
@Root(name = "card_entity")
public class Entity implements Serializable {

    @Attribute(name = "id", required = false)
    Integer id;
    @Attribute(name = "image_url", required = false)
    String backgroundImage;
    @Attribute(name = "question_grid", required = false)
    String grid;
    @Attribute(name = "correct_opt_message", required = false)
    String correctMessage;
    @Attribute(name = "incorrect_opt_message", required = false)
    String incorrectMessage;
    @Attribute(name = "action", required = false)
    String actionType;
    @Attribute(name = "q_text", required = false)
    String text;
    @Attribute(name = "bg_color", required = false)
    String bgColor;
    @ElementMap(entry = "option_entity", key = "key", attribute = true, inline = false, required = false)
    HashMap<Integer, EntityOption> options;

    @Attribute(name = "transition_image", required = false)
    String transitionImage;

    public String getBgColor() {
        return bgColor;
    }


    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }


    public Entity() {
        super();
        // TODO Auto-generated constructor stub
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getActionType() {
        return actionType;
    }


    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCorrectMessage() {
        return correctMessage;
    }

    public void setCorrectMessage(String correctMessage) {
        this.correctMessage = correctMessage;
    }

    public String getIncorrectMessage() {
        return incorrectMessage;
    }

    public void setIncorrectMessage(String incorrectMessage) {
        this.incorrectMessage = incorrectMessage;
    }


    public HashMap<Integer, EntityOption> getOptions() {
        return options;
    }


    public void setOptions(HashMap<Integer, EntityOption> options) {
        this.options = options;
    }

    public String getTransitionImage() {
        return transitionImage;
    }

    public void setTransitionImage(String transitionImage) {
        this.transitionImage = transitionImage;
    }
}
