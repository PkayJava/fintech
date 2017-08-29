package com.angkorteam.fintech.pages.charge;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareChargeModifyPage extends Page {

    private String chargeId;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private SingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private ChargeTimeProvider chargeTimeProvider;
    private Option chargeTimeValue;
    private Select2SingleChoice<Option> chargeTimeField;
    private TextFeedbackPanel chargeTimeFeedback;

    private ChargeCalculationProvider chargeCalculationProvider;
    private Option chargeCalculationValue;
    private Select2SingleChoice<Option> chargeCalculationField;
    private TextFeedbackPanel chargeCalculationFeedback;

    private Double amountValue;
    private TextField<Double> amountField;
    private TextFeedbackPanel amountFeedback;

    private boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private SingleChoiceProvider taxGroupProvider;
    private Option taxGroupValue;
    private Select2SingleChoice<Option> taxGroupField;
    private TextFeedbackPanel taxGroupFeedback;
    
    private static final List<PageBreadcrumb> BREADCRUMB;

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
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?",
                this.chargeId);

        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameValue = (String) chargeObject.get("name");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.currencyValue = jdbcTemplate.queryForObject(
                "select code id, concat(name,' [', code,']') text from m_organisation_currency where code = ?",
                new OptionMapper(), chargeObject.get("currency_code"));
        this.currencyProvider = new SingleChoiceProvider("m_currency", "id", "name");
        this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
                this.currencyProvider);
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        String charge_time_enum = String.valueOf(chargeObject.get("charge_time_enum"));
        for (ChargeTime time : ChargeTime.values()) {
            if (time.getLiteral().equals(charge_time_enum)) {
                this.chargeTimeValue = new Option(time.name(), time.getDescription());
                break;
            }
        }
        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase,
                ChargeTime.ShareRedeem);
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", 0,
                new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
        this.chargeTimeField.setRequired(true);
        this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate, this::chargeTimeFieldError));
        this.form.add(this.chargeTimeField);
        this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
        this.form.add(this.chargeTimeFeedback);

        String charge_calculation_enum = String.valueOf(chargeObject.get("charge_calculation_enum"));
        for (ChargeCalculation calculation : ChargeCalculation.values()) {
            if (calculation.getLiteral().equals(charge_calculation_enum)) {
                this.chargeCalculationValue = new Option(calculation.name(), calculation.getDescription());
                break;
            }
        }
        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0,
                new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField
                .add(new OnChangeAjaxBehavior(this::chargeCalculationFieldUpdate, this::chargeCalculationFieldError));
        this.form.add(this.chargeCalculationField);
        this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback",
                this.chargeCalculationField);
        this.form.add(this.chargeCalculationFeedback);

        if (chargeObject.get("amount") instanceof BigDecimal) {
            this.amountValue = ((BigDecimal) chargeObject.get("amount")).doubleValue();
        } else if (chargeObject.get("amount") instanceof Double) {
            this.amountValue = (Double) chargeObject.get("amount");
        }
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setRequired(true);
        this.form.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.form.add(this.amountFeedback);

        this.activeValue = (Boolean) chargeObject.get("is_active");
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.form.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.form.add(this.activeFeedback);

        this.taxGroupValue = jdbcTemplate.queryForObject("select id, name text from m_tax_group where id = ?",
                new OptionMapper(), chargeObject.get("tax_group_id"));
        this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"),
                this.taxGroupProvider);
        this.form.add(this.taxGroupField);
        this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
        this.form.add(this.taxGroupFeedback);

    }

    protected boolean chargeCalculationFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
        return false;
    }

    protected boolean chargeCalculationFieldError(AjaxRequestTarget target, RuntimeException error) {
        target.add(this.form);
        return false;
    }

    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
        return false;
    }

    protected boolean chargeTimeFieldError(AjaxRequestTarget target, RuntimeException error) {
        target.add(this.form);
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());

        ChargeBuilder builder = new ChargeBuilder();
        builder.withId(this.chargeId);
        builder.withName(this.nameValue);
        builder.withCurrencyCode(this.currencyValue.getId());
        builder.withChargeTimeType(chargeTime);
        builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
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
