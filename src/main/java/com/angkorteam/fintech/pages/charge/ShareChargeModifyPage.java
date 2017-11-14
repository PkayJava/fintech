package com.angkorteam.fintech.pages.charge;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareChargeModifyPage extends Page {

    protected String chargeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyIContainer;
    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected WebMarkupBlock chargeTimeBlock;
    protected WebMarkupContainer chargeTimeIContainer;
    protected ChargeTimeProvider chargeTimeProvider;
    protected Option chargeTimeValue;
    protected Select2SingleChoice<Option> chargeTimeField;
    protected TextFeedbackPanel chargeTimeFeedback;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationIContainer;
    protected ChargeCalculationProvider chargeCalculationProvider;
    protected Option chargeCalculationValue;
    protected Select2SingleChoice<Option> chargeCalculationField;
    protected TextFeedbackPanel chargeCalculationFeedback;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupBlock taxGroupBlock;
    protected WebMarkupContainer taxGroupIContainer;
    protected SingleChoiceProvider taxGroupProvider;
    protected Option taxGroupValue;
    protected Select2SingleChoice<Option> taxGroupField;
    protected TextFeedbackPanel taxGroupFeedback;

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
            breadcrumb.setLabel("Charge");
            breadcrumb.setPage(ChargeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge Charge Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initCurrencyBlock();

        initChargeTimeBlock();

        initChargeCalculationBlock();

        initAmountBlock();

        initActiveBlock();

        initTaxGroupBlock();
    }

    protected void initTaxGroupBlock() {
        this.taxGroupBlock = new WebMarkupBlock("taxGroupBlock", Size.Six_6);
        this.form.add(this.taxGroupBlock);
        this.taxGroupIContainer = new WebMarkupContainer("taxGroupIContainer");
        this.taxGroupBlock.add(this.taxGroupIContainer);
        this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
        this.taxGroupField.setLabel(Model.of("Tax Group"));
        this.taxGroupIContainer.add(this.taxGroupField);
        this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
        this.taxGroupIContainer.add(this.taxGroupFeedback);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Six_6);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initAmountBlock() {
        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setRequired(true);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);
    }

    protected void initChargeCalculationBlock() {
        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationIContainer = new WebMarkupContainer("chargeCalculationIContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationIContainer);
        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField.add(new OnChangeAjaxBehavior());
        this.chargeCalculationIContainer.add(this.chargeCalculationField);
        this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback", this.chargeCalculationField);
        this.chargeCalculationIContainer.add(this.chargeCalculationFeedback);
    }

    protected void initChargeTimeBlock() {
        this.chargeTimeBlock = new WebMarkupBlock("chargeTimeBlock", Size.Six_6);
        this.form.add(this.chargeTimeBlock);
        this.chargeTimeIContainer = new WebMarkupContainer("chargeTimeIContainer");
        this.chargeTimeBlock.add(this.chargeTimeIContainer);
        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase, ChargeTime.ShareRedeem);
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
        this.chargeTimeField.setLabel(Model.of("Charge time type"));
        this.chargeTimeField.setRequired(true);
        this.chargeTimeField.add(new OnChangeAjaxBehavior());
        this.chargeTimeIContainer.add(this.chargeTimeField);
        this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
        this.chargeTimeIContainer.add(this.chargeTimeFeedback);
    }

    protected void initCurrencyBlock() {
        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        this.form.add(this.currencyBlock);
        this.currencyIContainer = new WebMarkupContainer("currencyIContainer");
        this.currencyBlock.add(this.currencyIContainer);
        this.currencyProvider = new CurrencyProvider();
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.setRequired(true);
        this.currencyIContainer.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.currencyIContainer.add(this.currencyFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {

        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeId);

        this.nameValue = (String) chargeObject.get("name");

        this.currencyValue = jdbcTemplate.queryForObject("select code id, concat(name,' [', code,']') text from m_organisation_currency where code = ?", Option.MAPPER, chargeObject.get("currency_code"));

        String charge_time_enum = String.valueOf(chargeObject.get("charge_time_enum"));
        this.chargeTimeValue = ChargeTime.optionLiteral(charge_time_enum);

        String charge_calculation_enum = String.valueOf(chargeObject.get("charge_calculation_enum"));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(charge_calculation_enum);

        this.amountValue = (Double) chargeObject.get("amount");

        this.activeValue = (Boolean) chargeObject.get("is_active");

        this.taxGroupValue = jdbcTemplate.queryForObject("select id, name text from m_tax_group where id = ?", Option.MAPPER, chargeObject.get("tax_group_id"));

    }

    protected void saveButtonSubmit(Button button) {
        ChargeTime chargeTime = null;

        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        ChargeBuilder builder = new ChargeBuilder();
        builder.withId(this.chargeId);
        builder.withName(this.nameValue);
        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        } else {
            builder.withCurrencyCode(null);
        }
        builder.withChargeTimeType(chargeTime);
        if (this.chargeCalculationValue != null) {
            builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
        } else {
            builder.withChargeCalculationType(null);
        }
        builder.withAmount(this.amountValue);
        builder.withActive(this.activeValue);
        if (this.taxGroupValue != null) {
            builder.withTaxGroupId(this.taxGroupValue.getId());
        } else {
            builder.withTaxGroupId(null);
        }

        JsonNode node = null;
        try {
            node = ChargeHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ChargeBrowsePage.class);
    }

}
