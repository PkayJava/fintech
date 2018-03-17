package com.angkorteam.fintech.pages.client.common;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.LoanCollateralBuilder;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCollateralCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected SingleChoiceProvider collateralTypeProvider;
    protected WebMarkupContainer collateralTypeBlock;
    protected WebMarkupContainer collateralTypeIContainer;
    protected Option collateralTypeValue;
    protected Select2SingleChoice<Option> collateralTypeField;
    protected TextFeedbackPanel collateralTypeFeedback;

    protected WebMarkupContainer valueAmountBlock;
    protected WebMarkupContainer valueAmountIContainer;
    protected Double valueAmountValue;
    protected TextField<Double> valueAmountField;
    protected TextFeedbackPanel valueAmountFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanId);
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanAccountPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.collateralTypeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
        this.collateralTypeBlock = new WebMarkupBlock("collateralTypeBlock", Size.Six_6);
        this.form.add(this.collateralTypeBlock);
        this.collateralTypeIContainer = new WebMarkupContainer("collateralTypeIContainer");
        this.collateralTypeBlock.add(this.collateralTypeIContainer);
        this.collateralTypeField = new Select2SingleChoice<>("collateralTypeField", new PropertyModel<>(this, "collateralTypeValue"), this.collateralTypeProvider);
        this.collateralTypeField.setLabel(Model.of("Collateral Type"));
        this.collateralTypeField.setRequired(true);
        this.collateralTypeIContainer.add(this.collateralTypeField);
        this.collateralTypeFeedback = new TextFeedbackPanel("collateralTypeFeedback", this.collateralTypeField);
        this.collateralTypeIContainer.add(this.collateralTypeFeedback);

        this.valueAmountBlock = new WebMarkupBlock("valueAmountBlock", Size.Six_6);
        this.form.add(this.valueAmountBlock);
        this.valueAmountIContainer = new WebMarkupContainer("valueAmountIContainer");
        this.valueAmountBlock.add(this.valueAmountIContainer);
        this.valueAmountField = new TextField<>("valueAmountField", new PropertyModel<>(this, "valueAmountValue"));
        this.valueAmountField.setLabel(Model.of("Value"));
        this.valueAmountField.setRequired(false);
        this.valueAmountIContainer.add(this.valueAmountField);
        this.valueAmountFeedback = new TextFeedbackPanel("valueAmountFeedback", this.valueAmountField);
        this.valueAmountIContainer.add(this.valueAmountFeedback);

        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Six_6);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);

    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        LoanCollateralBuilder builder = new LoanCollateralBuilder();
        builder.withLoanId(this.loanId);
        builder.withValue(this.valueAmountValue);
        builder.withDescription(this.descriptionValue);
        if (this.collateralTypeValue != null) {
            builder.withCollateralTypeId(this.collateralTypeValue.getId());
        }

        JsonNode node = null;
        try {
            node = ClientHelper.addCollateralLoanAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanId);
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
        setResponsePage(LoanAccountPreviewPage.class, parameters);
    }
}