package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.AccrualBuilder;
import com.angkorteam.fintech.helper.AccrualHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccrualAccountingPage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private Date tillDateValue;
    private DateTextField tillDateField;
    private TextFeedbackPanel tillDateFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClosureBrowsePage.class);
        this.form.add(this.closeLink);

        this.tillDateValue = new Date();
        this.tillDateField = new DateTextField("tillDateField", new PropertyModel<>(this, "tillDateValue"));
        this.tillDateField.setRequired(true);
        this.form.add(this.tillDateField);
        this.tillDateFeedback = new TextFeedbackPanel("tillDateFeedback", this.tillDateField);
        this.form.add(this.tillDateFeedback);

    }

    private void saveButtonSubmit(Button button) {
        AccrualBuilder builder = new AccrualBuilder();
        builder.withTillDate(this.tillDateValue);
        JsonNode node = null;
        try {
            node = AccrualHelper.submit((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(AccountingPage.class);

    }

}
