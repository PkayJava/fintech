package com.angkorteam.fintech.pages.payment;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.PaymentTypeBuilder;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class PaymentTypeModifyPage extends Page {

    protected String paymentTypeId;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock positionBlock;
    protected WebMarkupContainer positionIContainer;
    protected Long positionValue;
    protected TextField<Long> positionField;
    protected TextFeedbackPanel positionFeedback;

    protected WebMarkupBlock cashBlock;
    protected WebMarkupContainer cashIContainer;
    protected Boolean cashValue;
    protected CheckBox cashField;
    protected TextFeedbackPanel cashFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

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
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Payment");
            breadcrumb.setPage(PaymentTypeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Payment Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.paymentTypeId = parameters.get("paymentTypeId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MPaymentType.NAME);
        selectQuery.addField(MPaymentType.Field.DESCRIPTION);
        selectQuery.addField(MPaymentType.Field.VALUE);
        selectQuery.addField(MPaymentType.Field.IS_CASH_PAYMENT);
        selectQuery.addField(MPaymentType.Field.ORDER_POSITION);
        selectQuery.addWhere(MPaymentType.Field.ID + " = :" + MPaymentType.Field.ID, this.paymentTypeId);

        Map<String, Object> object = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.descriptionValue = (String) object.get("description");
        this.nameValue = (String) object.get("value");
        this.cashValue = (Boolean) object.get("is_cash_payment");
        this.positionValue = (Long) object.get("order_position");

    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", PaymentTypeBrowsePage.class);
        this.form.add(this.closeLink);

        initDescriptionBlock();

        initNameBlock();

        initCashBlock();

        initPositionBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initPositionBlock() {
        this.positionBlock = new WebMarkupBlock("positionBlock", Size.Twelve_12);
        this.form.add(this.positionBlock);
        this.positionIContainer = new WebMarkupContainer("positionIContainer");
        this.positionBlock.add(this.positionIContainer);
        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionField.setRequired(true);
        this.positionIContainer.add(this.positionField);
        this.positionFeedback = new TextFeedbackPanel("positionFeedback", this.positionField);
        this.positionIContainer.add(this.positionFeedback);
    }

    protected void initCashBlock() {
        this.cashBlock = new WebMarkupBlock("cashBlock", Size.Twelve_12);
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);
        this.cashField = new CheckBox("cashField", new PropertyModel<>(this, "cashValue"));
        this.cashField.setRequired(true);
        this.cashIContainer.add(this.cashField);
        this.cashFeedback = new TextFeedbackPanel("cashFeedback", this.cashField);
        this.cashIContainer.add(this.cashFeedback);
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

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Twelve_12);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        PaymentTypeBuilder builder = new PaymentTypeBuilder();
        builder.withId(this.paymentTypeId);
        builder.withName(this.nameValue);
        builder.withPosition(this.positionValue);
        builder.withCashPayment(this.cashValue);
        builder.withDescription(this.descriptionValue);

        JsonNode node = null;
        try {
            node = PaymentTypeHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(PaymentTypeBrowsePage.class);
    }

}
