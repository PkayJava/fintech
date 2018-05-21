package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.client.client.ClientChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ChargeCreatePage extends Page {

    protected String clientId;
    protected String chargeId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button saveButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock chargeBlock;
    protected UIContainer chargeVContainer;
    protected String chargeValue;
    protected ReadOnlyView chargeView;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock chargeCalculationBlock;
    protected UIContainer chargeCalculationVContainer;
    protected Option chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected UIBlock chargeTypeBlock;
    protected UIContainer chargeTypeVContainer;
    protected Option chargeTypeValue;
    protected ReadOnlyView chargeTypeView;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock collectOnBlock;
    protected UIContainer collectOnIContainer;
    protected Date collectOnValue;
    protected DateTextField collectOnField;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock amountBlock;
    protected UIContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;

    protected UIBlock row5Block1;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.chargeId = getPageParameters().get("chargeId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.OFFICE_ID);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.clientDisplayName = (String) clientObject.get("display_name");

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, this.chargeId);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.chargeTypeValue = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
        this.chargeValue = (String) chargeObject.get("name");
        this.amountValue = (Double) chargeObject.get("amount");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge Selection");
            breadcrumb.setPage(ChargeSelectionPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Six_6);
        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
        this.chargeView = new ReadOnlyView("chargeView", new PropertyModel<>(this, "chargeValue"));
        this.chargeVContainer.add(this.chargeView);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.amountBlock = this.row2.newUIBlock("amountBlock", Size.Six_6);
        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountIContainer.add(this.amountField);
        this.amountIContainer.newFeedback("amountFeedback", this.amountField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.chargeCalculationBlock = this.row3.newUIBlock("chargeCalculationBlock", Size.Six_6);
        this.chargeCalculationVContainer = this.chargeCalculationBlock.newUIContainer("chargeCalculationVContainer");
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.chargeTypeBlock = this.row4.newUIBlock("chargeTypeBlock", Size.Six_6);
        this.chargeTypeVContainer = this.chargeTypeBlock.newUIContainer("chargeTypeVContainer");
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", new PropertyModel<>(this, "chargeTypeValue"));
        this.chargeTypeVContainer.add(this.chargeTypeView);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.collectOnBlock = this.row5.newUIBlock("collectOnBlock", Size.Six_6);
        this.collectOnIContainer = this.collectOnBlock.newUIContainer("collectOnIContainer");
        this.collectOnField = new DateTextField("collectOnField", new PropertyModel<>(this, "collectOnValue"));
        this.collectOnIContainer.add(this.collectOnField);
        this.collectOnIContainer.newFeedback("collectOnFeedback", this.collectOnField);

        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.amountField.setLabel(Model.of("Amount"));
        this.collectOnField.setLabel(Model.of("Collect On"));

    }

    protected void saveButtonSubmit(Button button) {
        ClientChargeBuilder builder = new ClientChargeBuilder();
        builder.withClientId(this.clientId);
        builder.withAmount(this.amountValue);
        builder.withDueDate(this.collectOnValue);
        builder.withChargeId(this.chargeId);

        JsonNode node = ClientHelper.postClientCharge((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
