package com.angkorteam.fintech.pages.role;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.RoleBuilder;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RoleCreatePage extends Page {

    private String descriptionValue;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", RoleBrowsePage.class);
	this.form.add(this.closeLink);

	this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
	this.descriptionField.setRequired(true);
	this.form.add(this.descriptionField);
	this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
	this.form.add(this.descriptionFeedback);

	this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
	this.nameField.setRequired(true);
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);
    }

    private void saveButtonSubmit(Button button) {
	RoleBuilder builder = new RoleBuilder();
	builder.withName(this.nameValue);
	builder.withDescription(this.descriptionValue);

	JsonNode node = null;
	try {
	    node = RoleHelper.create((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(RoleBrowsePage.class);
    }

}
