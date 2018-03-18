package com.angkorteam.fintech.pages.payment;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.PaymentTypeBuilder;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class PaymentTypeCreatePage extends Page {

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
            breadcrumb.setLabel("Payment Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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
        builder.withName(this.nameValue);
        builder.withPosition(this.positionValue);
        builder.withCashPayment(this.cashValue);
        builder.withDescription(this.descriptionValue);

        JsonNode node = PaymentTypeHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(PaymentTypeBrowsePage.class);
    }

}
