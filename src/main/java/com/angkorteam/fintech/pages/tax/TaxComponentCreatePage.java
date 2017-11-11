package com.angkorteam.fintech.pages.tax;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TaxComponentBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.TaxComponentHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxComponentCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock percentageBlock;
    protected WebMarkupContainer percentageIContainer;
    protected Double percentageValue;
    protected TextField<Double> percentageField;
    protected TextFeedbackPanel percentageFeedback;

    protected WebMarkupBlock accountTypeBlock;
    protected WebMarkupContainer accountTypeIContainer;
    protected AccountTypeProvider accountTypeProvider;
    protected Option accountTypeValue;
    protected Select2SingleChoice<Option> accountTypeField;
    protected TextFeedbackPanel accountTypeFeedback;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountIContainer;
    protected SingleChoiceProvider accountProvider;
    protected Option accountValue;
    protected Select2SingleChoice<Option> accountField;
    protected TextFeedbackPanel accountFeedback;

    protected WebMarkupBlock startDateBlock;
    protected WebMarkupContainer startDateIContainer;
    protected Date startDateValue;
    protected DateTextField startDateField;
    protected TextFeedbackPanel startDateFeedback;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
    }

    @Override
    protected void initData() {
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

        initNameBlock();

        initPercentageBlock();

        initAccountTypeBlock();

        initAccountBlock();

        initStartDateBlock();
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initPercentageBlock() {
        this.percentageBlock = new WebMarkupBlock("percentageBlock", Size.Twelve_12);
        this.form.add(this.percentageBlock);
        this.percentageIContainer = new WebMarkupContainer("percentageIContainer");
        this.percentageBlock.add(this.percentageIContainer);
        this.percentageField = new TextField<>("percentageField", new PropertyModel<>(this, "percentageValue"));
        this.percentageField.setRequired(true);
        this.percentageIContainer.add(this.percentageField);
        this.percentageFeedback = new TextFeedbackPanel("percentageFeedback", this.percentageField);
        this.percentageIContainer.add(this.percentageFeedback);
    }

    protected void initAccountTypeBlock() {
        this.accountTypeBlock = new WebMarkupBlock("accountTypeBlock", Size.Twelve_12);
        this.form.add(this.accountTypeBlock);
        this.accountTypeIContainer = new WebMarkupContainer("accountTypeIContainer");
        this.accountTypeBlock.add(this.accountTypeIContainer);
        this.accountTypeProvider = new AccountTypeProvider();
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", 0, new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate));
        this.accountTypeIContainer.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.accountTypeIContainer.add(this.accountTypeFeedback);
    }

    protected void initAccountBlock() {
        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Twelve_12);
        this.form.add(this.accountBlock);
        this.accountIContainer = new WebMarkupContainer("accountIContainer");
        this.accountBlock.add(this.accountIContainer);
        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.setDisabled(true);
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.accountIContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountIContainer.add(this.accountFeedback);
    }

    protected void initStartDateBlock() {
        this.startDateBlock = new WebMarkupBlock("startDateBlock", Size.Twelve_12);
        this.form.add(this.startDateBlock);
        this.startDateIContainer = new WebMarkupContainer("startDateIContainer");
        this.startDateBlock.add(this.startDateIContainer);
        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.startDateIContainer.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.startDateIContainer.add(this.startDateFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean accountTypeFieldUpdate(AjaxRequestTarget target) {
        this.accountValue = null;
        this.accountProvider.setDisabled(false);
        this.accountProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.valueOf(this.accountTypeValue.getId()).getLiteral());
        target.add(this.form);
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
        JsonNode node = null;
        try {
            node = TaxComponentHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxComponentBrowsePage.class);
    }
}
