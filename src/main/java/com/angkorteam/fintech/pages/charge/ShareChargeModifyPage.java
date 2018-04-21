package com.angkorteam.fintech.pages.charge;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareChargeModifyPage extends Page {

    protected String chargeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock currencyBlock;
    protected UIContainer currencyIContainer;
    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;

    protected UIRow row2;

    protected UIBlock chargeTimeBlock;
    protected UIContainer chargeTimeIContainer;
    protected ChargeTimeProvider chargeTimeProvider;
    protected Option chargeTimeValue;
    protected Select2SingleChoice<Option> chargeTimeField;

    protected UIBlock chargeCalculationBlock;
    protected UIContainer chargeCalculationIContainer;
    protected ChargeCalculationProvider chargeCalculationProvider;
    protected Option chargeCalculationValue;
    protected Select2SingleChoice<Option> chargeCalculationField;

    protected UIRow row3;

    protected UIBlock amountBlock;
    protected UIContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock activeBlock;
    protected UIContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock taxGroupBlock;
    protected UIContainer taxGroupIContainer;
    protected SingleChoiceProvider taxGroupProvider;
    protected Option taxGroupValue;
    protected Select2SingleChoice<Option> taxGroupField;

    protected UIBlock row5Block1;

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
            breadcrumb.setLabel("Charge");
            breadcrumb.setPage(ChargeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge Charge Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.currencyBlock = this.row1.newUIBlock("currencyBlock", Size.Six_6);
        this.currencyIContainer = this.currencyBlock.newUIContainer("currencyIContainer");
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyIContainer.add(this.currencyField);
        this.currencyIContainer.newFeedback("currencyFeedback", this.currencyField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.chargeTimeBlock = this.row2.newUIBlock("chargeTimeBlock", Size.Six_6);
        this.chargeTimeIContainer = this.chargeTimeBlock.newUIContainer("chargeTimeIContainer");
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
        this.chargeTimeIContainer.add(this.chargeTimeField);
        this.chargeTimeIContainer.newFeedback("chargeTimeFeedback", this.chargeTimeField);

        this.chargeCalculationBlock = this.row2.newUIBlock("chargeCalculationBlock", Size.Six_6);
        this.chargeCalculationIContainer = this.chargeCalculationBlock.newUIContainer("chargeCalculationIContainer");
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationIContainer.add(this.chargeCalculationField);
        this.chargeCalculationIContainer.newFeedback("chargeCalculationFeedback", this.chargeCalculationField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.amountBlock = this.row3.newUIBlock("amountBlock", Size.Six_6);
        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountIContainer.add(this.amountField);
        this.amountIContainer.newFeedback("amountFeedback", this.amountField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.activeBlock = this.row4.newUIBlock("activeBlock", Size.Six_6);
        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeIContainer.add(this.activeField);
        this.activeIContainer.newFeedback("activeFeedback", this.activeField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.taxGroupBlock = this.row5.newUIBlock("taxGroupBlock", Size.Six_6);
        this.taxGroupIContainer = this.taxGroupBlock.newUIContainer("taxGroupIContainer");
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
        this.taxGroupIContainer.add(this.taxGroupField);
        this.taxGroupIContainer.newFeedback("taxGroupFeedback", this.taxGroupField);

        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.taxGroupField.setLabel(Model.of("Tax Group"));

        this.activeField.setRequired(true);

        this.amountField.setRequired(true);
        this.amountField.setLabel(Model.of("Amount"));

        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField.add(new OnChangeAjaxBehavior());

        this.chargeTimeField.setLabel(Model.of("Charge time type"));
        this.chargeTimeField.setRequired(true);
        this.chargeTimeField.add(new OnChangeAjaxBehavior());

        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.setRequired(true);

        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
    }

    @Override
    protected void initData() {

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, this.chargeId);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.CURRENCY_CODE);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.Field.IS_ACTIVE);
        selectQuery.addField(MCharge.Field.TAX_GROUP_ID);

        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.nameValue = (String) chargeObject.get(MCharge.Field.NAME);

        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
        selectQuery.addField("concat(" + MOrganisationCurrency.Field.NAME + ",' [', " + MOrganisationCurrency.Field.CODE + ",']') text");
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, chargeObject.get(MCharge.Field.CURRENCY_CODE));
        this.currencyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        String charge_time_enum = String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM));
        this.chargeTimeValue = ChargeTime.optionLiteral(charge_time_enum);

        String charge_calculation_enum = String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(charge_calculation_enum);

        this.amountValue = (Double) chargeObject.get(MCharge.Field.AMOUNT);

        this.activeValue = (Boolean) chargeObject.get(MCharge.Field.IS_ACTIVE);

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addField(MTaxGroup.Field.ID);
        selectQuery.addField(MTaxGroup.Field.NAME + " as text");
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, chargeObject.get(MCharge.Field.TAX_GROUP_ID));
        this.taxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.taxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);

        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);

        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase, ChargeTime.ShareRedeem);

        this.currencyProvider = new CurrencyProvider();

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

        JsonNode node = ChargeHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ChargeBrowsePage.class);
    }

}
