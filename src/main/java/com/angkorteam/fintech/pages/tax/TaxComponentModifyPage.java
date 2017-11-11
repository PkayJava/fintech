package com.angkorteam.fintech.pages.tax;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TaxComponentBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.helper.TaxComponentHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxComponentModifyPage extends Page {

    protected String tagId;

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
    protected WebMarkupContainer accountTypeVContainer;
    protected Option accountTypeValue;
    protected ReadOnlyView accountTypeView;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountVContainer;
    protected Option accountValue;
    protected ReadOnlyView accountView;

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
            breadcrumb.setLabel("Tax Component Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.tagId = parameters.get("taxId").toString("");
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> taxObject = jdbcTemplate.queryForMap("select * from m_tax_component where id = ?", this.tagId);
        this.nameValue = (String) taxObject.get("name");
        this.percentageValue = (Double) taxObject.get("percentage");
        this.accountTypeValue = AccountType.optionLiteral(String.valueOf(taxObject.get("credit_account_type_enum")));
        this.accountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account WHERE id = ?", Option.MAPPER, taxObject.get("credit_account_id"));
        this.startDateValue = (Date) taxObject.get("start_date");
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
        this.accountTypeVContainer = new WebMarkupContainer("accountTypeVContainer");
        this.accountTypeBlock.add(this.accountTypeVContainer);
        this.accountTypeView = new ReadOnlyView("accountTypeView", new PropertyModel<>(this, "accountTypeValue.text"));
        this.accountTypeVContainer.add(this.accountTypeView);
    }

    protected void initAccountBlock() {
        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Twelve_12);
        this.form.add(this.accountBlock);
        this.accountVContainer = new WebMarkupContainer("accountVContainer");
        this.accountBlock.add(this.accountVContainer);
        this.accountView = new ReadOnlyView("accountView", new PropertyModel<>(this, "accountValue.text"));
        this.accountVContainer.add(this.accountView);
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

    protected void saveButtonSubmit(Button button) {
        TaxComponentBuilder builder = new TaxComponentBuilder();
        builder.withId(this.tagId);
        builder.withName(this.nameValue);
        builder.withPercentage(this.percentageValue);
        builder.withStartDate(this.startDateValue);
        JsonNode node = null;
        try {
            node = TaxComponentHelper.update((Session) getSession(), builder.build());
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
