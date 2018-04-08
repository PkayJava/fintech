package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.loan.RescheduleBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.LoanPurposeProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountReschedulePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock rescheduleFromInstallmentOnBlock;
    protected WebMarkupContainer rescheduleFromInstallmentOnIContainer;
    protected DateTextField rescheduleFromInstallmentOnField;
    protected TextFeedbackPanel rescheduleFromInstallmentOnFeedback;
    protected Date rescheduleFromInstallmentOnValue;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;
    protected Date submittedOnValue;

    protected LoanPurposeProvider reasonForReschedulingProvider;
    protected WebMarkupContainer reasonForReschedulingBlock;
    protected WebMarkupContainer reasonForReschedulingIContainer;
    protected Option reasonForReschedulingValue;
    protected Select2SingleChoice<Option> reasonForReschedulingField;
    protected TextFeedbackPanel reasonForReschedulingFeedback;

    protected WebMarkupBlock commentBlock;
    protected WebMarkupContainer commentIContainer;
    protected String commentValue;
    protected TextArea<String> commentField;
    protected TextFeedbackPanel commentFeedback;

    protected WebMarkupBlock changeRepaymentDateBlock;
    protected WebMarkupContainer changeRepaymentDateIContainer;
    protected Boolean changeRepaymentDateValue;
    protected CheckBox changeRepaymentDateField;
    protected TextFeedbackPanel changeRepaymentDateFeedback;

    protected WebMarkupBlock installmentRescheduledToBlock;
    protected WebMarkupContainer installmentRescheduledToIContainer;
    protected DateTextField installmentRescheduledToField;
    protected TextFeedbackPanel installmentRescheduledToFeedback;
    protected Date installmentRescheduledToValue;

    protected WebMarkupBlock introduceGracePeriodBlock;
    protected WebMarkupContainer introduceGracePeriodIContainer;
    protected Boolean introduceGracePeriodValue;
    protected CheckBox introduceGracePeriodField;
    protected TextFeedbackPanel introduceGracePeriodFeedback;

    protected WebMarkupBlock principleGracePeriodBlock;
    protected WebMarkupContainer principleGracePeriodIContainer;
    protected TextField<Double> principleGracePeriodField;
    protected TextFeedbackPanel principleGracePeriodFeedback;
    protected Date principleGracePeriodValue;

    protected WebMarkupBlock interestGracePeriodBlock;
    protected WebMarkupContainer interestGracePeriodIContainer;
    protected TextField<Double> interestGracePeriodField;
    protected TextFeedbackPanel interestGracePeriodFeedback;
    protected Double interestGracePeriodValue;

    protected WebMarkupBlock extendRepaymentPeriodBlock;
    protected WebMarkupContainer extendRepaymentPeriodIContainer;
    protected Boolean extendRepaymentPeriodValue;
    protected CheckBox extendRepaymentPeriodField;
    protected TextFeedbackPanel extendRepaymentPeriodFeedback;

    protected WebMarkupBlock numberOfNewRepaymentBlock;
    protected WebMarkupContainer numberOfNewRepaymentIContainer;
    protected TextField<Long> numberOfNewRepaymentField;
    protected TextFeedbackPanel numberOfNewRepaymentFeedback;
    protected Long numberOfNewRepaymentValue;

    protected WebMarkupBlock adjustInterestRateBlock;
    protected WebMarkupContainer adjustInterestRateIContainer;
    protected Boolean adjustInterestRateValue;
    protected CheckBox adjustInterestRateField;
    protected TextFeedbackPanel adjustInterestRateFeedback;

    protected WebMarkupBlock newInterestRateBlock;
    protected WebMarkupContainer newInterestRateIContainer;
    protected TextField<Double> newInterestRateField;
    protected TextFeedbackPanel newInterestRateFeedback;
    protected Double newInterestRateValue;

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

        this.rescheduleFromInstallmentOnBlock = new WebMarkupBlock("rescheduleFromInstallmentOnBlock", Size.Six_6);
        this.form.add(this.rescheduleFromInstallmentOnBlock);
        this.rescheduleFromInstallmentOnIContainer = new WebMarkupContainer("rescheduleFromInstallmentOnIContainer");
        this.rescheduleFromInstallmentOnBlock.add(this.rescheduleFromInstallmentOnIContainer);
        this.rescheduleFromInstallmentOnField = new DateTextField("rescheduleFromInstallmentOnField", new PropertyModel<>(this, "rescheduleFromInstallmentOnValue"));
        this.rescheduleFromInstallmentOnField.setLabel(Model.of("Reschedule From Installment On"));
        this.rescheduleFromInstallmentOnField.setRequired(false);
        this.rescheduleFromInstallmentOnIContainer.add(this.rescheduleFromInstallmentOnField);
        this.rescheduleFromInstallmentOnFeedback = new TextFeedbackPanel("rescheduleFromInstallmentOnFeedback", this.rescheduleFromInstallmentOnField);
        this.rescheduleFromInstallmentOnIContainer.add(this.rescheduleFromInstallmentOnFeedback);

        this.submittedOnBlock = new WebMarkupBlock("submittedOnBlock", Size.Six_6);
        this.form.add(this.submittedOnBlock);
        this.submittedOnIContainer = new WebMarkupContainer("submittedOnIContainer");
        this.submittedOnBlock.add(this.submittedOnIContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.setRequired(false);
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnIContainer.add(this.submittedOnFeedback);

        this.reasonForReschedulingProvider = new LoanPurposeProvider();
        this.reasonForReschedulingBlock = new WebMarkupBlock("reasonForReschedulingBlock", Size.Six_6);
        this.form.add(this.reasonForReschedulingBlock);
        this.reasonForReschedulingIContainer = new WebMarkupContainer("reasonForReschedulingIContainer");
        this.reasonForReschedulingBlock.add(this.reasonForReschedulingIContainer);
        this.reasonForReschedulingField = new Select2SingleChoice<>("reasonForReschedulingField", new PropertyModel<>(this, "reasonForReschedulingValue"), this.reasonForReschedulingProvider);
        this.reasonForReschedulingField.setLabel(Model.of("Reason For Rescheduling"));
        this.reasonForReschedulingField.setRequired(false);
        this.reasonForReschedulingIContainer.add(this.reasonForReschedulingField);
        this.reasonForReschedulingFeedback = new TextFeedbackPanel("reasonForReschedulingFeedback", this.reasonForReschedulingField);
        this.reasonForReschedulingIContainer.add(this.reasonForReschedulingFeedback);

        this.commentBlock = new WebMarkupBlock("commentBlock", Size.Six_6);
        this.form.add(this.commentBlock);
        this.commentIContainer = new WebMarkupContainer("commentIContainer");
        this.commentBlock.add(this.commentIContainer);
        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentField.setLabel(Model.of("comment"));
        this.commentIContainer.add(this.commentField);
        this.commentFeedback = new TextFeedbackPanel("commentFeedback", this.commentField);
        this.commentIContainer.add(this.commentFeedback);

        this.changeRepaymentDateBlock = new WebMarkupBlock("changeRepaymentDateBlock", Size.Six_6);
        this.form.add(this.changeRepaymentDateBlock);
        this.changeRepaymentDateIContainer = new WebMarkupContainer("changeRepaymentDateIContainer");
        this.changeRepaymentDateBlock.add(this.changeRepaymentDateIContainer);
        this.changeRepaymentDateField = new CheckBox("changeRepaymentDateField", new PropertyModel<>(this, "changeRepaymentDateValue"));
        this.changeRepaymentDateIContainer.add(this.changeRepaymentDateField);
        this.changeRepaymentDateFeedback = new TextFeedbackPanel("changeRepaymentDateFeedback", this.changeRepaymentDateField);
        this.changeRepaymentDateIContainer.add(this.changeRepaymentDateFeedback);

        this.installmentRescheduledToBlock = new WebMarkupBlock("installmentRescheduledToBlock", Size.Six_6);
        this.form.add(this.installmentRescheduledToBlock);
        this.installmentRescheduledToIContainer = new WebMarkupContainer("installmentRescheduledToIContainer");
        this.installmentRescheduledToBlock.add(this.installmentRescheduledToIContainer);
        this.installmentRescheduledToField = new DateTextField("installmentRescheduledToField", new PropertyModel<>(this, "installmentRescheduledToValue"));
        this.installmentRescheduledToField.setLabel(Model.of("Installment Rescheduled To"));
        this.installmentRescheduledToField.setRequired(false);
        this.installmentRescheduledToIContainer.add(this.installmentRescheduledToField);
        this.installmentRescheduledToFeedback = new TextFeedbackPanel("installmentRescheduledToFeedback", this.installmentRescheduledToField);
        this.installmentRescheduledToIContainer.add(this.installmentRescheduledToFeedback);

        this.introduceGracePeriodBlock = new WebMarkupBlock("introduceGracePeriodBlock", Size.Six_6);
        this.form.add(this.introduceGracePeriodBlock);
        this.introduceGracePeriodIContainer = new WebMarkupContainer("introduceGracePeriodIContainer");
        this.introduceGracePeriodBlock.add(this.introduceGracePeriodIContainer);
        this.introduceGracePeriodField = new CheckBox("introduceGracePeriodField", new PropertyModel<>(this, "introduceGracePeriodValue"));
        this.introduceGracePeriodIContainer.add(this.introduceGracePeriodField);
        this.introduceGracePeriodFeedback = new TextFeedbackPanel("introduceGracePeriodFeedback", this.introduceGracePeriodField);
        this.introduceGracePeriodIContainer.add(this.introduceGracePeriodFeedback);

        this.principleGracePeriodBlock = new WebMarkupBlock("principleGracePeriodBlock", Size.Four_4);
        this.form.add(this.principleGracePeriodBlock);
        this.principleGracePeriodIContainer = new WebMarkupContainer("principleGracePeriodIContainer");
        this.principleGracePeriodBlock.add(this.principleGracePeriodIContainer);
        this.principleGracePeriodField = new TextField<>("principleGracePeriodField", new PropertyModel<>(this, "principleGracePeriodValue"));
        this.principleGracePeriodField.setLabel(Model.of("Principle Grace Period"));
        this.principleGracePeriodField.setRequired(false);
        this.principleGracePeriodIContainer.add(this.principleGracePeriodField);
        this.principleGracePeriodFeedback = new TextFeedbackPanel("principleGracePeriodFeedback", this.principleGracePeriodField);
        this.principleGracePeriodIContainer.add(this.principleGracePeriodFeedback);

        this.interestGracePeriodBlock = new WebMarkupBlock("interestGracePeriodBlock", Size.Four_4);
        this.form.add(this.interestGracePeriodBlock);
        this.interestGracePeriodIContainer = new WebMarkupContainer("interestGracePeriodIContainer");
        this.interestGracePeriodBlock.add(this.interestGracePeriodIContainer);
        this.interestGracePeriodField = new TextField<>("interestGracePeriodField", new PropertyModel<>(this, "interestGracePeriodValue"));
        this.interestGracePeriodField.setLabel(Model.of("Interest Grace Period"));
        this.interestGracePeriodField.setRequired(false);
        this.interestGracePeriodIContainer.add(this.interestGracePeriodField);
        this.interestGracePeriodFeedback = new TextFeedbackPanel("interestGracePeriodFeedback", this.interestGracePeriodField);
        this.interestGracePeriodIContainer.add(this.interestGracePeriodFeedback);

        this.extendRepaymentPeriodBlock = new WebMarkupBlock("extendRepaymentPeriodBlock", Size.Six_6);
        this.form.add(this.extendRepaymentPeriodBlock);
        this.extendRepaymentPeriodIContainer = new WebMarkupContainer("extendRepaymentPeriodIContainer");
        this.extendRepaymentPeriodBlock.add(this.extendRepaymentPeriodIContainer);
        this.extendRepaymentPeriodField = new CheckBox("extendRepaymentPeriodField", new PropertyModel<>(this, "extendRepaymentPeriodValue"));
        this.extendRepaymentPeriodIContainer.add(this.extendRepaymentPeriodField);
        this.extendRepaymentPeriodFeedback = new TextFeedbackPanel("extendRepaymentPeriodFeedback", this.extendRepaymentPeriodField);
        this.extendRepaymentPeriodIContainer.add(this.extendRepaymentPeriodFeedback);

        this.numberOfNewRepaymentBlock = new WebMarkupBlock("numberOfNewRepaymentBlock", Size.Four_4);
        this.form.add(this.numberOfNewRepaymentBlock);
        this.numberOfNewRepaymentIContainer = new WebMarkupContainer("numberOfNewRepaymentIContainer");
        this.numberOfNewRepaymentBlock.add(this.numberOfNewRepaymentIContainer);
        this.numberOfNewRepaymentField = new TextField<>("numberOfNewRepaymentField", new PropertyModel<>(this, "numberOfNewRepaymentValue"));
        this.numberOfNewRepaymentField.setLabel(Model.of("Number Of New Repayment"));
        this.numberOfNewRepaymentField.setRequired(false);
        this.numberOfNewRepaymentIContainer.add(this.numberOfNewRepaymentField);
        this.numberOfNewRepaymentFeedback = new TextFeedbackPanel("numberOfNewRepaymentFeedback", this.numberOfNewRepaymentField);
        this.numberOfNewRepaymentIContainer.add(this.numberOfNewRepaymentFeedback);

        this.adjustInterestRateBlock = new WebMarkupBlock("adjustInterestRateBlock", Size.Six_6);
        this.form.add(this.adjustInterestRateBlock);
        this.adjustInterestRateIContainer = new WebMarkupContainer("adjustInterestRateIContainer");
        this.adjustInterestRateBlock.add(this.adjustInterestRateIContainer);
        this.adjustInterestRateField = new CheckBox("adjustInterestRateField", new PropertyModel<>(this, "adjustInterestRateValue"));
        this.adjustInterestRateIContainer.add(this.adjustInterestRateField);
        this.adjustInterestRateFeedback = new TextFeedbackPanel("adjustInterestRateFeedback", this.adjustInterestRateField);
        this.adjustInterestRateIContainer.add(this.adjustInterestRateFeedback);

        this.newInterestRateBlock = new WebMarkupBlock("newInterestRateBlock", Size.Four_4);
        this.form.add(this.newInterestRateBlock);
        this.newInterestRateIContainer = new WebMarkupContainer("newInterestRateIContainer");
        this.newInterestRateBlock.add(this.newInterestRateIContainer);
        this.newInterestRateField = new TextField<>("newInterestRateField", new PropertyModel<>(this, "newInterestRateValue"));
        this.newInterestRateField.setLabel(Model.of("New Interest Rate"));
        this.newInterestRateField.setRequired(false);
        this.newInterestRateIContainer.add(this.newInterestRateField);
        this.newInterestRateFeedback = new TextFeedbackPanel("newInterestRateFeedback", this.newInterestRateField);
        this.newInterestRateIContainer.add(this.newInterestRateFeedback);

    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
    }

    protected void saveButtonSubmit(Button button) {
        RescheduleBuilder builder = new RescheduleBuilder();
        builder.withLoanId(this.loanId);
        builder.withRescheduleFromDate(this.rescheduleFromInstallmentOnValue);
        if (this.reasonForReschedulingValue != null) {
            builder.withRescheduleReasonId(this.reasonForReschedulingValue.getId());
        }
        builder.withSubmittedOnDate(this.submittedOnValue);
        builder.withRescheduleReasonComment(this.commentValue);
        if (this.changeRepaymentDateValue != null && this.changeRepaymentDateValue) {
            builder.withAdjustedDueDate(this.installmentRescheduledToValue);
        }
        if (this.introduceGracePeriodValue != null && this.introduceGracePeriodValue) {
            builder.withGraceOnInterest(this.interestGracePeriodValue);
            builder.withGraceOnPrincipal(this.interestGracePeriodValue);
        }
        if (this.extendRepaymentPeriodValue != null && this.extendRepaymentPeriodValue) {
            builder.withExtraTerms(this.numberOfNewRepaymentValue);
        }
        if (this.adjustInterestRateValue != null && this.adjustInterestRateValue) {
            builder.withNewInterestRate(this.newInterestRateValue);
        }

        JsonNode node = ClientHelper.rescheduleLoanAccount((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        if (this.client == ClientEnum.Client) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            setResponsePage(GroupPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
    }

}
