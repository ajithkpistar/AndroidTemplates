/**
 * 
 */
package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author ComplexObject
 *
 */
@Root(name="presentation_canvas")
public class PPT  implements Serializable {

	@Attribute(name = "id", required = false)
	Integer id;
	@Attribute(name = "bgImage", required = false)
	String bgImage;
	@Attribute(name = "evalType", required = false)
	Boolean evalType;
	@Attribute(name = "scrollable", required = false)
	Boolean scrollable;
	@Attribute(name = "gridX", required = false)
	Integer gridX;
	@Attribute(name = "gridY", required = false)
	Integer gridY;	
	
	@ElementList(name = "entity", inline = true, required = false)
	ArrayList<Entity> questions ;

	@ElementList(name = "variables", inline = true, required = false)
	ArrayList<Variable>variables;


	@Attribute(name = "audioUrl", required = false)
	String audioUrl;

	public PPT() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Entity> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Entity> questions) {
		this.questions = questions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBgImage() {
		return bgImage;
	}

	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}

	public Boolean getEvalType() {
		return evalType;
	}

	public void setEvalType(Boolean evalType) {
		this.evalType = evalType;
	}

	public Boolean getScrollable() {
		return scrollable;
	}

	public void setScrollable(Boolean scrollable) {
		this.scrollable = scrollable;
	}

	public Integer getGridX() {
		return gridX;
	}

	public void setGridX(Integer gridX) {
		this.gridX = gridX;
	}

	public Integer getGridY() {
		return gridY;
	}

	public void setGridY(Integer gridY) {
		this.gridY = gridY;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
}
