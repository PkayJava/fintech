package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountClosureBuilder;
import com.angkorteam.fintech.helper.AccountingClosureHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClosureCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock closingDateBlock;
    protected WebMarkupContainer closingDateIContainer;
    protected Date closingDateValue;
    protected DateTextField closingDateField;
    protected TextFeedbackPanel closingDateFeedback;

    protected WebMarkupBlock commentBlock;
    protected WebMarkupContainer commentIContainer;
    protected String commentValue;
    protected TextArea<String> commentField;
    protected TextFeedbackPanel commentFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.closingDateValue = DateTime.now().toDate();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClosureBrowsePage.class);
        this.form.add(this.closeLink);

        initOfficeBlock();

        initClosingDateBlock();

        initCommentBlock();
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initCommentBlock() {
        this.commentBlock = new WebMarkupBlock("commentBlock", Size.Twelve_12);
        this.form.add(this.commentBlock);
        this.commentIContainer = new WebMarkupContainer("commentIContainer");
        this.commentBlock.add(this.commentIContainer);
        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentField.setRequired(true);
        this.commentIContainer.add(this.commentField);
        this.commentFeedback = new TextFeedbackPanel("commentFeedback", this.commentField);
        this.commentIContainer.add(this.commentFeedback);
    }

    protected void initClosingDateBlock() {
        this.closingDateBlock = new WebMarkupBlock("closingDateBlock", Size.Twelve_12);
        this.form.add(this.closingDateBlock);
        this.closingDateIContainer = new WebMarkupContainer("closingDateIContainer");
        this.closingDateBlock.add(this.closingDateIContainer);
        this.closingDateField = new DateTextField("closingDateField", new PropertyModel<>(this, "closingDateValue"));
        this.closingDateField.setRequired(true);
        this.closingDateIContainer.add(this.closingDateField);
        this.closingDateFeedback = new TextFeedbackPanel("closingDateFeedback", this.closingDateField);
        this.closingDateIContainer.add(this.closingDateFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        AccountClosureBuilder builder = new AccountClosureBuilder();
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withClosingDate(this.closingDateValue);
        builder.withComments(this.commentValue);
        JsonNode node = AccountingClosureHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ClosureBrowsePage.class);

    }

}
