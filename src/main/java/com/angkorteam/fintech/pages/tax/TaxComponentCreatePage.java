package com.angkorteam.fintech.pages.tax;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TaxComponentBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.TaxComponentHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxComponentCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIRow row2;

    protected UIBlock percentageBlock;
    protected UIContainer percentageIContainer;
    protected Double percentageValue;
    protected TextField<Double> percentageField;

    protected UIRow row3;

    protected UIBlock accountTypeBlock;
    protected UIContainer accountTypeIContainer;
    protected AccountTypeProvider accountTypeProvider;
    protected Option accountTypeValue;
    protected Select2SingleChoice<Option> accountTypeField;

    protected UIRow row4;

    protected UIBlock accountBlock;
    protected UIContainer accountIContainer;
    protected SingleChoiceProvider accountProvider;
    protected Option accountValue;
    protected Select2SingleChoice<Option> accountField;

    protected UIRow row5;

    protected UIBlock startDateBlock;
    protected UIContainer startDateIContainer;
    protected Date startDateValue;
    protected DateTextField startDateField;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax");
            breadcrumb.setPage(TaxDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax Component");
            breadcrumb.setPage(TaxComponentBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax Component Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.accountTypeProvider = new AccountTypeProvider();

        this.accountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.setDisabled(true);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxComponentBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Twelve_12);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.percentageBlock = this.row2.newUIBlock("percentageBlock", Size.Twelve_12);
        this.percentageIContainer = this.percentageBlock.newUIContainer("percentageIContainer");
        this.percentageField = new TextField<>("percentageField", new PropertyModel<>(this, "percentageValue"));
        this.percentageIContainer.add(this.percentageField);
        this.percentageIContainer.newFeedback("percentageFeedback", this.percentageField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.accountTypeBlock = this.row3.newUIBlock("accountTypeBlock", Size.Twelve_12);
        this.accountTypeIContainer = this.accountTypeBlock.newUIContainer("accountTypeIContainer");
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeIContainer.add(this.accountTypeField);
        this.accountTypeIContainer.newFeedback("accountTypeFeedback", this.accountTypeField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.accountBlock = this.row4.newUIBlock("accountBlock", Size.Twelve_12);
        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
        this.accountField = new Select2SingleChoice<>("accountField", new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.accountIContainer.add(this.accountField);
        this.accountIContainer.newFeedback("accountFeedback", this.accountField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.startDateBlock = this.row5.newUIBlock("startDateBlock", Size.Twelve_12);
        this.startDateIContainer = this.startDateBlock.newUIContainer("startDateIContainer");
        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateIContainer.add(this.startDateField);
        this.startDateIContainer.newFeedback("startDateFeedback", this.startDateField);
    }

    @Override
    protected void configureMetaData() {
        this.startDateField.setRequired(true);
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate));
        this.percentageField.setRequired(true);
        this.nameField.setRequired(true);
    }

    protected boolean accountTypeFieldUpdate(AjaxRequestTarget target) {
        this.accountValue = null;
        this.accountProvider.setDisabled(false);
        this.accountProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.valueOf(this.accountTypeValue.getId()).getLiteral());
        if (target != null) {
            target.add(this.accountBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        TaxComponentBuilder builder = new TaxComponentBuilder();
        builder.withName(this.nameValue);
        if (this.accountValue != null) {
            builder.withCreditAccountId(this.accountValue.getId());
        }
        if (this.accountTypeValue != null) {
            builder.withCreditAccountType(AccountType.valueOf(this.accountTypeValue.getId()));
        }
        builder.withPercentage(this.percentageValue);
        builder.withStartDate(this.startDateValue);
        JsonNode node = TaxComponentHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxComponentBrowsePage.class);
    }
}
