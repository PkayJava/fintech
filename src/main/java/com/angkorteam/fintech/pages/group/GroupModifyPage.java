package com.angkorteam.fintech.pages.group;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.request.GroupBuilder;
import com.angkorteam.fintech.helper.GroupHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GroupModifyPage extends Page {

    private String groupId;

    private String externalIdValue;
    private TextField<String> externalIdField;
    private TextFeedbackPanel externalIdFeedback;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private OptionSingleChoiceProvider parentProvider;
    private Option parentValue;
    private Select2SingleChoice<Option> parentField;
    private TextFeedbackPanel parentFeedback;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.groupId = parameters.get("groupId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

	Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_group where id = ?", this.groupId);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", GroupBrowsePage.class);
	this.form.add(this.closeLink);

	this.externalIdValue = (String) object.get("external_id");
	this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
	this.externalIdField.setRequired(true);
	this.form.add(this.externalIdField);
	this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
	this.form.add(this.externalIdFeedback);

	this.nameValue = (String) object.get("display_name");
	this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
	this.nameField.setRequired(true);
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);

	this.parentValue = jdbcTemplate.queryForObject("select id, display_name as text from m_group where id = ?",
		new OptionMapper(), object.get("parent_id"));
	this.parentProvider = new OptionSingleChoiceProvider("m_group", "id", "display_name");
	this.parentField = new Select2SingleChoice<>("parentField", 0, new PropertyModel<>(this, "parentValue"),
		this.parentProvider);
	this.parentField.setRequired(true);
	this.form.add(this.parentField);
	this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
	this.form.add(this.parentFeedback);

	this.officeValue = jdbcTemplate.queryForObject("select id, name as text from m_office where id = ?",
		new OptionMapper(), object.get("office_id"));
	this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
	this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"),
		this.officeProvider);
	this.officeField.setRequired(true);
	this.form.add(this.officeField);
	this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
	this.form.add(this.officeFeedback);
    }

    private void saveButtonSubmit(Button button) {
	GroupBuilder builder = new GroupBuilder();
	builder.withName(this.nameValue);
	builder.withExternalId(this.externalIdValue);
	builder.withId(this.groupId);

	JsonNode node = null;
	try {
	    node = GroupHelper.update((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(GroupBrowsePage.class);
    }
}
