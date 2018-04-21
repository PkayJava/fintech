package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
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

    protected UIRow row1;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;

    protected UIRow row2;

    protected UIBlock closingDateBlock;
    protected UIContainer closingDateIContainer;
    protected Date closingDateValue;
    protected DateTextField closingDateField;

    protected UIRow row3;

    protected UIBlock commentBlock;
    protected UIContainer commentIContainer;
    protected String commentValue;
    protected TextArea<String> commentField;

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
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Twelve_12);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.closingDateBlock = this.row2.newUIBlock("closingDateBlock", Size.Twelve_12);
        this.closingDateIContainer = this.closingDateBlock.newUIContainer("closingDateIContainer");
        this.closingDateField = new DateTextField("closingDateField", new PropertyModel<>(this, "closingDateValue"));
        this.closingDateIContainer.add(this.closingDateField);
        this.closingDateIContainer.newFeedback("closingDateFeedback", this.closingDateField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.commentBlock = this.row3.newUIBlock("commentBlock", Size.Twelve_12);
        this.commentIContainer = this.commentBlock.newUIContainer("commentIContainer");
        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentIContainer.add(this.commentField);
        this.commentIContainer.newFeedback("commentFeedback", this.commentField);
    }

    @Override
    protected void configureMetaData() {
        this.closingDateField.setRequired(true);
        this.commentField.setRequired(true);
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
