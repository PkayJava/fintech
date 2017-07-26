package com.angkorteam.fintech.pages.fund;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FundModifyPage extends Page {

    private String fundId;

    private String externalIdValue;
    private TextField<String> externalIdField;
    private TextFeedbackPanel externalIdFeedback;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.fundId = parameters.get("fundId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

	Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_fund where id = ?", this.fundId);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", FundBrowsePage.class);
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
    }

    private void saveButtonSubmit(Button button) {
	FundBuilder builder = new FundBuilder();
	builder.withName(this.nameValue);
	builder.withExternalId(this.externalIdValue);
	builder.withId(this.fundId);

	JsonNode node = null;
	try {
	    node = FundHelper.update((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(FundBrowsePage.class);
    }
}
