package com.angkorteam.fintech.pages.office;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.request.OfficeBuilder;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
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

import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/25/17.
 */
public class OfficeModifyPage extends Page {

    private String officeId;

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

    private Date openingDateValue;
    private DateTextField openingDateField;
    private TextFeedbackPanel openingDateFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.officeId = parameters.get("officeId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

	Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_office where id = ?", this.officeId);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", OfficeBrowsePage.class);
	this.form.add(this.closeLink);

	this.externalIdValue = (String) object.get("external_id");
	this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
	this.externalIdField.setRequired(true);
	this.form.add(this.externalIdField);
	this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
	this.form.add(this.externalIdFeedback);

	this.nameValue = (String) object.get("name");
	this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
	this.nameField.setRequired(true);
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);

	this.openingDateValue = (Date) object.get("opening_date");
	this.openingDateField = new DateTextField("openingDateField", new PropertyModel<>(this, "openingDateValue"));
	this.openingDateField.setRequired(true);
	this.form.add(this.openingDateField);
	this.openingDateFeedback = new TextFeedbackPanel("openingDateFeedback", this.openingDateField);
	this.form.add(this.openingDateFeedback);

	this.parentValue = jdbcTemplate.queryForObject("select id, name as text from m_office where id = ?",
		new OptionMapper(), object.get("parent_id"));
	this.parentProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
	this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"),
		this.parentProvider);
	this.parentField.setRequired(true);
	this.form.add(this.parentField);
	this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
	this.form.add(this.parentFeedback);
    }

    private void saveButtonSubmit(Button button) {
	OfficeBuilder builder = new OfficeBuilder();
	builder.withName(this.nameValue);
	if (this.parentValue != null) {
	    builder.withParentId(this.parentValue.getId());
	}
	builder.withOpeningDate(this.openingDateValue);
	builder.withExternalId(this.externalIdValue);
	builder.withId(this.officeId);

	JsonNode node = null;
	try {
	    node = OfficeHelper.update((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(OfficeBrowsePage.class);

    }

}
