package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.LoanPurposeProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanGuarantorCreateInternalPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected LoanPurposeProvider nameProvider;
    protected WebMarkupContainer nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected Option nameValue;
    protected Select2SingleChoice<Option> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected LoanPurposeProvider relationshipProvider;
    protected WebMarkupContainer relationshipBlock;
    protected WebMarkupContainer relationshipIContainer;
    protected Option relationshipValue;
    protected Select2SingleChoice<Option> relationshipField;
    protected TextFeedbackPanel relationshipFeedback;

    protected WebMarkupBlock guarantorIdBlock;
    protected WebMarkupContainer guarantorIdVContainer;
    protected String guarantorIdValue;
    protected ReadOnlyView guarantorIdView;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeVContainer;
    protected String officeValue;
    protected ReadOnlyView officeView;

    protected WebMarkupBlock activationDateBlock;
    protected WebMarkupContainer activationDateVContainer;
    protected Date activationDateValue;
    protected ReadOnlyView activationDateView;

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
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.nameProvider = new LoanPurposeProvider();
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new Select2SingleChoice<>("nameField", new PropertyModel<>(this, "nameValue"), this.nameProvider);
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(false);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);

        this.relationshipProvider = new LoanPurposeProvider();
        this.relationshipBlock = new WebMarkupBlock("relationshipBlock", Size.Six_6);
        this.form.add(this.relationshipBlock);
        this.relationshipIContainer = new WebMarkupContainer("relationshipIContainer");
        this.relationshipBlock.add(this.relationshipIContainer);
        this.relationshipField = new Select2SingleChoice<>("relationshipField", new PropertyModel<>(this, "relationshipValue"), this.relationshipProvider);
        this.relationshipField.setLabel(Model.of("Relationship"));
        this.relationshipField.setRequired(false);
        this.relationshipIContainer.add(this.relationshipField);
        this.relationshipFeedback = new TextFeedbackPanel("relationshipFeedback", this.relationshipField);
        this.relationshipIContainer.add(this.relationshipFeedback);

        this.guarantorIdBlock = new WebMarkupBlock("guarantorIdBlock", Size.Six_6);
        this.form.add(this.guarantorIdBlock);
        this.guarantorIdVContainer = new WebMarkupContainer("guarantorIdVContainer");
        this.guarantorIdBlock.add(this.guarantorIdVContainer);
        this.guarantorIdView = new ReadOnlyView("guarantorIdView", new PropertyModel<>(this, "guarantorIdValue"));
        this.guarantorIdVContainer.add(this.guarantorIdView);

        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeVContainer = new WebMarkupContainer("officeVContainer");
        this.officeBlock.add(this.officeVContainer);
        this.officeView = new ReadOnlyView("officeView", new PropertyModel<>(this, "officeValue"));
        this.officeVContainer.add(this.officeView);

        this.activationDateBlock = new WebMarkupBlock("activationDateBlock", Size.Six_6);
        this.form.add(this.activationDateBlock);
        this.activationDateVContainer = new WebMarkupContainer("activationDateVContainer");
        this.activationDateBlock.add(this.activationDateVContainer);
        this.activationDateView = new ReadOnlyView("activationDateView", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateVContainer.add(this.activationDateView);

        this.form.add(this.closeLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {

    }
}