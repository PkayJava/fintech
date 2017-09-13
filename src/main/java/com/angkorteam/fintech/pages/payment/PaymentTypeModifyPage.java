package com.angkorteam.fintech.pages.payment;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class PaymentTypeModifyPage extends Page {

    private String paymentTypeId;

    private String descriptionValue;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Integer positionValue;
    private TextField<Integer> positionField;
    private TextFeedbackPanel positionFeedback;

    private Boolean cashValue;
    private CheckBox cashField;
    private TextFeedbackPanel cashFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;
    
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
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.paymentTypeId = parameters.get("paymentTypeId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_payment_type where id = ?",
                this.paymentTypeId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", PaymentTypeBrowsePage.class);
        this.form.add(this.closeLink);

        this.descriptionValue = (String) object.get("description");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.nameValue = (String) object.get("value");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.cashValue = (Boolean) object.get("is_cash_payment");
        this.cashField = new CheckBox("cashField", new PropertyModel<>(this, "cashValue"));
        this.cashField.setRequired(true);
        this.form.add(this.cashField);
        this.cashFeedback = new TextFeedbackPanel("cashFeedback", this.cashField);
        this.form.add(this.cashFeedback);

        this.positionValue = (Integer) object.get("order_position");
        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionField.setRequired(true);
        this.form.add(this.positionField);
        this.positionFeedback = new TextFeedbackPanel("positionFeedback", this.positionField);
        this.form.add(this.positionFeedback);
    }

    private void saveButtonSubmit(Button button) {
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
