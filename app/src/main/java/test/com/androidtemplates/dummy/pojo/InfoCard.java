/**
 * 
 */
package test.com.androidtemplates.dummy.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * @author ComplexObject
 *
 */

@Root(name="info_card")
public class InfoCard    implements Serializable {

	@Attribute(name = "id", required = false)
	Integer id;
	@Attribute(name = "template", required = false)
	String template;
	
	@Element(name="content", required=false)
	CardContent content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public CardContent getContent() {
		return content;
	}

	public void setContent(CardContent content) {
		this.content = content;
	}

	public InfoCard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
