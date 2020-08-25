//package com.angkorteam.fintech.widget.client.common.saving;
//
//import java.util.Date;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class DetailsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorDetail;
//
//    protected PropertyModel<String> officeId;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected ClientEnum client;
//    protected String clientId;
//
//    // Detail
//
//    protected UIRow row1;
//
//    protected UIBlock detailProductNameBlock;
//    protected UIContainer detailProductNameVContainer;
//    protected ReadOnlyView detailProductNameView;
//    protected PropertyModel<String> detailProductNameValue;
//
//    protected UIBlock detailSubmittedOnBlock;
//    protected UIContainer detailSubmittedOnIContainer;
//    protected DateTextField detailSubmittedOnField;
//    protected PropertyModel<Date> detailSubmittedOnValue;
//
//    protected UIRow row2;
//
//    protected UIBlock detailOfficerBlock;
//    protected UIContainer detailOfficerIContainer;
//    protected Select2SingleChoice<Option> detailOfficerField;
//    protected PropertyModel<Option> detailOfficerValue;
//    protected SingleChoiceProvider detailOfficerProvider;
//
//    protected UIBlock detailExternalIdBlock;
//    protected UIContainer detailExternalIdIContainer;
//    protected TextField<String> detailExternalIdField;
//    protected PropertyModel<String> detailExternalIdValue;
//
//    public DetailsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
//
//        this.officeId = new PropertyModel<>(this.itemPage, "officeId");
//
//        this.detailOfficerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.detailOfficerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//        this.detailOfficerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId.getObject());
//
//        this.detailProductNameValue = new PropertyModel<>(this.itemPage, "detailProductNameValue");
//        this.detailSubmittedOnValue = new PropertyModel<>(this.itemPage, "detailSubmittedOnValue");
//        this.detailOfficerValue = new PropertyModel<>(this.itemPage, "detailOfficerValue");
//        this.detailExternalIdValue = new PropertyModel<>(this.itemPage, "detailExternalIdValue");
//
//        this.client = new PropertyModel<ClientEnum>(this.itemPage, "client").getObject();
//        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.nextButton = new Button("nextButton");
//        this.nextButton.setOnSubmit(this::nextButtonSubmit);
//        this.nextButton.setOnError(this::nextButtonError);
//        this.form.add(this.nextButton);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        if (this.client == ClientEnum.Client) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
//        }
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
//        this.detailProductNameVContainer = this.detailProductNameBlock.newUIContainer("detailProductNameVContainer");
//        this.detailProductNameView = new ReadOnlyView("detailProductNameView", this.detailProductNameValue);
//        this.detailProductNameVContainer.add(this.detailProductNameView);
//
//        this.detailSubmittedOnBlock = this.row1.newUIBlock("detailSubmittedOnBlock", Size.Six_6);
//        this.detailSubmittedOnIContainer = this.detailSubmittedOnBlock.newUIContainer("detailSubmittedOnIContainer");
//        this.detailSubmittedOnField = new DateTextField("detailSubmittedOnField", this.detailSubmittedOnValue);
//        this.detailSubmittedOnIContainer.add(this.detailSubmittedOnField);
//        this.detailSubmittedOnIContainer.newFeedback("detailSubmittedOnFeedback", this.detailSubmittedOnField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.detailOfficerBlock = this.row2.newUIBlock("detailOfficerBlock", Size.Six_6);
//        this.detailOfficerIContainer = this.detailOfficerBlock.newUIContainer("detailOfficerIContainer");
//        this.detailOfficerField = new Select2SingleChoice<>("detailOfficerField", this.detailOfficerValue, this.detailOfficerProvider);
//        this.detailOfficerIContainer.add(this.detailOfficerField);
//        this.detailOfficerIContainer.newFeedback("detailOfficerFeedback", this.detailOfficerField);
//
//        this.detailExternalIdBlock = this.row2.newUIBlock("detailExternalIdBlock", Size.Six_6);
//        this.detailExternalIdIContainer = this.detailExternalIdBlock.newUIContainer("detailExternalIdIContainer");
//        this.detailExternalIdField = new TextField<>("detailExternalIdField", this.detailExternalIdValue);
//        this.detailExternalIdIContainer.add(this.detailExternalIdField);
//        this.detailExternalIdIContainer.newFeedback("detailExternalIdFeedback", this.detailExternalIdField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.detailSubmittedOnField.setLabel(Model.of("Submitted On"));
//        this.detailSubmittedOnField.setRequired(true);
//
//        this.detailOfficerField.setLabel(Model.of("Field Officer"));
//        this.detailOfficerField.setRequired(true);
//
//        this.detailExternalIdField.setLabel(Model.of("External ID"));
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorDetail.setObject(false);
//        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_TERM);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorDetail.setObject(true);
//    }
//
//}
