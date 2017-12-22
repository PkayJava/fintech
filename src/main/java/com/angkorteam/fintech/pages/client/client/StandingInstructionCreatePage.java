package com.angkorteam.fintech.pages.client.client;

import java.util.Date;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.DestinationProvider;
import com.angkorteam.fintech.provider.InstructionTypeProvider;
import com.angkorteam.fintech.provider.OfficeProvider;
import com.angkorteam.fintech.provider.PriorityProvider;
import com.angkorteam.fintech.provider.RecurrenceFrequencyProvider;
import com.angkorteam.fintech.provider.RecurrenceTypeProvider;
import com.angkorteam.fintech.provider.StandAccountTypeProvider;
import com.angkorteam.fintech.provider.StatusProvider;
import com.angkorteam.fintech.provider.TransferTypeProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class StandingInstructionCreatePage extends Page {

    protected String clientId;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock applicantBlock;
    protected WebMarkupContainer applicantVContainer;
    protected String applicantValue;
    protected ReadOnlyView applicantView;
    protected TextFeedbackPanel applicantFeedback;

    protected TransferTypeProvider typeProvider;
    protected WebMarkupBlock typeBlock;
    protected WebMarkupContainer typeIContainer;
    protected Option typeValue;
    protected Select2SingleChoice<Option> typeField;
    protected TextFeedbackPanel typeFeedback;

    protected PriorityProvider priorityProvider;
    protected WebMarkupBlock priorityBlock;
    protected WebMarkupContainer priorityIContainer;
    protected Option priorityValue;
    protected Select2SingleChoice<Option> priorityField;
    protected TextFeedbackPanel priorityFeedback;

    protected StatusProvider statusProvider;
    protected WebMarkupBlock statusBlock;
    protected WebMarkupContainer statusIContainer;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;
    protected TextFeedbackPanel statusFeedback;

    protected StandAccountTypeProvider fromAccountTypeProvider;
    protected WebMarkupBlock fromAccountTypeBlock;
    protected WebMarkupContainer fromAccountTypeIContainer;
    protected Option fromAccountTypeValue;
    protected Select2SingleChoice<Option> fromAccountTypeField;
    protected TextFeedbackPanel fromAccountTypeFeedback;

    protected StandAccountTypeProvider fromAccountProvider;
    protected WebMarkupBlock fromAccountBlock;
    protected WebMarkupContainer fromAccountIContainer;
    protected Option fromAccountValue;
    protected Select2SingleChoice<Option> fromAccountField;
    protected TextFeedbackPanel fromAccountFeedback;

    protected DestinationProvider destinationProvider;
    protected WebMarkupBlock destinationBlock;
    protected WebMarkupContainer destinationIContainer;
    protected Option destinationValue;
    protected Select2SingleChoice<Option> destinationField;
    protected TextFeedbackPanel destinationFeedback;

    protected OfficeProvider toOfficeProvider;
    protected WebMarkupBlock toOfficeBlock;
    protected WebMarkupContainer toOfficeIContainer;
    protected Option toOfficeValue;
    protected Select2SingleChoice<Option> toOfficeField;
    protected TextFeedbackPanel toOfficeFeedback;

    protected OfficeProvider beneficiaryProvider;
    protected WebMarkupBlock beneficiaryBlock;
    protected WebMarkupContainer beneficiaryIContainer;
    protected Option beneficiaryValue;
    protected Select2SingleChoice<Option> beneficiaryField;
    protected TextFeedbackPanel beneficiaryFeedback;

    protected StandAccountTypeProvider toAccountTypeProvider;
    protected WebMarkupBlock toAccountTypeBlock;
    protected WebMarkupContainer toAccountTypeIContainer;
    protected Option toAccountTypeValue;
    protected Select2SingleChoice<Option> toAccountTypeField;
    protected TextFeedbackPanel toAccountTypeFeedback;

    protected OfficeProvider toAccountProvider;
    protected WebMarkupBlock toAccountBlock;
    protected WebMarkupContainer toAccountIContainer;
    protected Option toAccountValue;
    protected Select2SingleChoice<Option> toAccountField;
    protected TextFeedbackPanel toAccountFeedback;

    protected InstructionTypeProvider standingInstructionTypeProvider;
    protected WebMarkupBlock standingInstructionTypeBlock;
    protected WebMarkupContainer standingInstructionTypeIContainer;
    protected Option standingInstructionTypeValue;
    protected Select2SingleChoice<Option> standingInstructionTypeField;
    protected TextFeedbackPanel standingInstructionTypeFeedback;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock validityFromBlock;
    protected WebMarkupContainer validityFromIContainer;
    protected Date validityFromValue;
    protected DateTextField validityFromField;
    protected TextFeedbackPanel validityFromFeedback;

    protected WebMarkupBlock validityToBlock;
    protected WebMarkupContainer validityToIContainer;
    protected Date validityToValue;
    protected DateTextField validityToField;
    protected TextFeedbackPanel validityToFeedback;

    protected RecurrenceTypeProvider recurrenceTypeProvider;
    protected WebMarkupBlock recurrenceTypeBlock;
    protected WebMarkupContainer recurrenceTypeIContainer;
    protected Option recurrenceTypeValue;
    protected Select2SingleChoice<Option> recurrenceTypeField;
    protected TextFeedbackPanel recurrenceTypeFeedback;

    protected WebMarkupBlock intervalBlock;
    protected WebMarkupContainer intervalIContainer;
    protected Long intervalValue;
    protected TextField<Long> intervalField;
    protected TextFeedbackPanel intervalFeedback;

    protected RecurrenceFrequencyProvider recurrenceFrequencyProvider;
    protected WebMarkupBlock recurrenceFrequencyBlock;
    protected WebMarkupContainer recurrenceFrequencyIContainer;
    protected Option recurrenceFrequencyValue;
    protected Select2SingleChoice<Option> recurrenceFrequencyField;
    protected TextFeedbackPanel recurrenceFrequencyFeedback;

    protected WebMarkupBlock onMonthDayBlock;
    protected WebMarkupContainer onMonthDayIContainer;
    protected Date onMonthDayValue;
    protected DateTextField onMonthDayField;
    protected TextFeedbackPanel onMonthDayFeedback;

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
