package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.AccountClosureBuilder;
import com.angkorteam.fintech.helper.AccountingClosureHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClosureCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private SingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private Date closingDateValue;
    private DateTextField closingDateField;
    private TextFeedbackPanel closingDateFeedback;

    private String commentValue;
    private TextArea<String> commentField;
    private TextFeedbackPanel commentFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Account Closure");
            breadcrumb.setPage(ClosureBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Account Closure Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

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

        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"),
                this.officeProvider);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.closingDateField = new DateTextField("closingDateField", new PropertyModel<>(this, "closingDateValue"));
        this.closingDateField.setRequired(true);
        this.form.add(this.closingDateField);
        this.closingDateFeedback = new TextFeedbackPanel("closingDateFeedback", this.closingDateField);
        this.form.add(this.closingDateFeedback);

        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentField.setRequired(true);
        this.form.add(this.commentField);
        this.commentFeedback = new TextFeedbackPanel("commentFeedback", this.commentField);
        this.form.add(this.commentFeedback);
    }

    private void saveButtonSubmit(Button button) {
        AccountClosureBuilder builder = new AccountClosureBuilder();
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withClosingDate(this.closingDateValue);
        builder.withComments(this.commentValue);
        JsonNode node = null;
        try {
            node = AccountingClosureHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ClosureBrowsePage.class);

    }

}
