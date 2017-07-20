//package com.angkorteam.fintech.pages.tax;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.dto.AccountType;
//import com.angkorteam.fintech.dto.AccountUsage;
//import com.angkorteam.fintech.dto.request.TaxComponentBuilder;
//import com.angkorteam.fintech.helper.TaxComponentHelper;
//import com.angkorteam.fintech.provider.AccountTypeProvider;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.PropertyModel;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by socheatkhauv on 7/16/17.
// */
//public class TaxGroupCreatePage extends Page {
//
//    private Form<Void> taxForm;
//    private AjaxButton addButton;
//
//    private OptionSingleChoiceProvider taxProvider;
//    private Option taxValue;
//    private Select2SingleChoice<Option> taxField;
//    private TextFeedbackPanel taxFeedback;
//
//    private Date startDateValue;
//    private DateTextField startDateField;
//    private TextFeedbackPanel startDateFeedback;
//
//    private Date endDateValue;
//    private DateTextField endDateField;
//    private TextFeedbackPanel endDateFeedback;
//
//    private Form<Void> form;
//    private Button saveButton;
//    private BookmarkablePageLink<Void> closeLink;
//
//    private String nameValue;
//    private TextField<String> nameField;
//    private TextFeedbackPanel nameFeedback;
//
//    private List<Map<String, Object>> taxComponentValue;
//    private DataTable<Map<String, Object>, String> taxComponentTable;
//    private ListDataProvider taxComponentProvider;
//
//    @Override
//    protected void onInitialize() {
//        super.onInitialize();
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxComponentBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameField.setRequired(true);
//        this.form.add(this.nameField);
//        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
//        this.form.add(this.nameFeedback);
//
//        this.percentageField = new TextField<>("percentageField", new PropertyModel<>(this, "percentageValue"));
//        this.percentageField.setRequired(true);
//        this.form.add(this.percentageField);
//        this.percentageFeedback = new TextFeedbackPanel("percentageFeedback", this.percentageField);
//        this.form.add(this.percentageFeedback);
//
//        this.accountTypeProvider = new AccountTypeProvider();
//        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", 0, new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
//        this.accountTypeField.setRequired(true);
//        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate, this::accountTypeFieldError));
//        this.form.add(this.accountTypeField);
//        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
//        this.form.add(this.accountTypeFeedback);
//
//        this.accountProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
//        this.accountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.accountProvider.setDisabled(true);
//        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"), this.accountProvider);
//        this.form.add(this.accountField);
//        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
//        this.form.add(this.accountFeedback);
//
//        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
//        this.startDateField.setRequired(true);
//        this.form.add(this.startDateField);
//        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
//        this.form.add(this.startDateFeedback);
//
//    }
//
//    private void accountTypeFieldUpdate(AjaxRequestTarget target) {
//        this.accountValue = null;
//        this.accountProvider.setDisabled(false);
//        this.accountProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.valueOf(this.accountTypeValue.getId()).getLiteral());
//        target.add(this.form);
//    }
//
//    private void accountTypeFieldError(AjaxRequestTarget target, RuntimeException error) {
//        this.accountValue = null;
//        this.accountProvider.setDisabled(true);
//        target.add(this.form);
//    }
//
//    private void saveButtonSubmit(Button button) {
//        TaxComponentBuilder builder = new TaxComponentBuilder();
//        builder.withName(this.nameValue);
//        if (this.accountValue != null) {
//            builder.withCreditAccountId(this.accountValue.getId());
//        }
//        if (this.accountTypeValue != null) {
//            builder.withCreditAccountType(AccountType.valueOf(this.accountTypeValue.getId()).getLiteral());
//        }
//        builder.withPercentage(this.percentageValue);
//        builder.withStartDate(this.startDateValue);
//        JsonNode node = null;
//        try {
//            node = TaxComponentHelper.createTaxComponent(builder.build());
//        } catch (UnirestException e) {
//            error(e.getMessage());
//            return;
//        }
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(TaxComponentBrowsePage.class);
//    }
//}
