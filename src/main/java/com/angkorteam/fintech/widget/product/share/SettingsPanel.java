//package com.angkorteam.fintech.widget.product.share;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.validation.ValidationError;
//import org.apache.wicket.validation.validator.RangeValidator;
//
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
//import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
//import com.angkorteam.fintech.provider.LockInTypeProvider;
//import com.angkorteam.fintech.provider.MinimumActivePeriodProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;
//
//public class SettingsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorSetting;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock settingSharePerClientMinimumBlock;
//    protected UIContainer settingSharePerClientMinimumIContainer;
//    protected TextField<Long> settingSharePerClientMinimumField;
//    protected PropertyModel<Long> settingSharePerClientMinimumValue;
//
//    protected UIBlock settingSharePerClientDefaultBlock;
//    protected UIContainer settingSharePerClientDefaultIContainer;
//    protected TextField<Long> settingSharePerClientDefaultField;
//    protected PropertyModel<Long> settingSharePerClientDefaultValue;
//
//    protected UIBlock settingSharePerClientMaximumBlock;
//    protected UIContainer settingSharePerClientMaximumIContainer;
//    protected TextField<Long> settingSharePerClientMaximumField;
//    protected PropertyModel<Long> settingSharePerClientMaximumValue;
//
//    protected UIRow row2;
//
//    protected UIBlock settingMinimumActivePeriodBlock;
//    protected UIContainer settingMinimumActivePeriodIContainer;
//    protected TextField<Long> settingMinimumActivePeriodField;
//
//    protected UIBlock settingMinimumActiveTypeBlock;
//    protected UIContainer settingMinimumActiveTypeIContainer;
//    protected MinimumActivePeriodProvider settingMinimumActiveTypeProvider;
//    protected Select2SingleChoice<Option> settingMinimumActiveTypeField;
//
//    protected UIBlock row2Block1;
//
//    protected UIRow row3;
//
//    protected UIBlock settingLockInPeriodBlock;
//    protected UIContainer settingLockInPeriodIContainer;
//    protected TextField<Long> settingLockInPeriodField;
//
//    protected UIBlock settingLockInTypeBlock;
//    protected UIContainer settingLockInTypeIContainer;
//    protected LockInTypeProvider settingLockInTypeProvider;
//    protected Select2SingleChoice<Option> settingLockInTypeField;
//
//    protected UIBlock row3Block1;
//
//    protected UIRow row4;
//
//    protected UIBlock settingAllowDividendForInactiveClientBlock;
//    protected UIContainer settingAllowDividendForInactiveClientIContainer;
//    protected CheckBox settingAllowDividendForInactiveClientField;
//
//    protected UIBlock row4Block1;
//
//    public SettingsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.settingMinimumActiveTypeProvider = new MinimumActivePeriodProvider();
//        this.settingLockInTypeProvider = new LockInTypeProvider();
//
//        this.settingSharePerClientMinimumValue = new PropertyModel<>(this.itemPage, "settingSharePerClientMinimumValue");
//        this.settingSharePerClientDefaultValue = new PropertyModel<>(this.itemPage, "settingSharePerClientDefaultValue");
//        this.settingSharePerClientMaximumValue = new PropertyModel<>(this.itemPage, "settingSharePerClientMaximumValue");
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
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.settingSharePerClientMinimumBlock = this.row1.newUIBlock("settingSharePerClientMinimumBlock", Size.Four_4);
//        this.settingSharePerClientMinimumIContainer = this.settingSharePerClientMinimumBlock.newUIContainer("settingSharePerClientMinimumIContainer");
//        this.settingSharePerClientMinimumField = new TextField<>("settingSharePerClientMinimumField", this.settingSharePerClientMinimumValue);
//        this.settingSharePerClientMinimumIContainer.add(this.settingSharePerClientMinimumField);
//        this.settingSharePerClientMinimumIContainer.newFeedback("settingSharePerClientMinimumFeedback", this.settingSharePerClientMinimumField);
//
//        this.settingSharePerClientDefaultBlock = this.row1.newUIBlock("settingSharePerClientDefaultBlock", Size.Four_4);
//        this.settingSharePerClientDefaultIContainer = this.settingSharePerClientDefaultBlock.newUIContainer("settingSharePerClientDefaultIContainer");
//        this.settingSharePerClientDefaultField = new TextField<>("settingSharePerClientDefaultField", this.settingSharePerClientDefaultValue);
//        this.settingSharePerClientDefaultIContainer.add(this.settingSharePerClientDefaultField);
//        this.settingSharePerClientDefaultIContainer.newFeedback("settingSharePerClientDefaultFeedback", this.settingSharePerClientDefaultField);
//
//        this.settingSharePerClientMaximumBlock = this.row1.newUIBlock("settingSharePerClientMaximumBlock", Size.Four_4);
//        this.settingSharePerClientMaximumIContainer = this.settingSharePerClientMaximumBlock.newUIContainer("settingSharePerClientMaximumIContainer");
//        this.settingSharePerClientMaximumField = new TextField<>("settingSharePerClientMaximumField", this.settingSharePerClientMaximumValue);
//        this.settingSharePerClientMaximumIContainer.add(this.settingSharePerClientMaximumField);
//        this.settingSharePerClientMaximumIContainer.newFeedback("settingSharePerClientMaximumFeedback", this.settingSharePerClientMaximumField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.settingMinimumActivePeriodBlock = this.row2.newUIBlock("settingMinimumActivePeriodBlock", Size.Four_4);
//        this.settingMinimumActivePeriodIContainer = this.settingMinimumActivePeriodBlock.newUIContainer("settingMinimumActivePeriodIContainer");
//        this.settingMinimumActivePeriodField = new TextField<>("settingMinimumActivePeriodField", new PropertyModel<>(this.itemPage, "settingMinimumActivePeriodValue"));
//        this.settingMinimumActivePeriodIContainer.add(this.settingMinimumActivePeriodField);
//        this.settingMinimumActivePeriodIContainer.newFeedback("settingMinimumActivePeriodFeedback", this.settingMinimumActivePeriodField);
//
//        this.settingMinimumActiveTypeBlock = this.row2.newUIBlock("settingMinimumActiveTypeBlock", Size.Four_4);
//        this.settingMinimumActiveTypeIContainer = this.settingMinimumActiveTypeBlock.newUIContainer("settingMinimumActiveTypeIContainer");
//        this.settingMinimumActiveTypeField = new Select2SingleChoice<>("settingMinimumActiveTypeField", new PropertyModel<>(this.itemPage, "settingMinimumActiveTypeValue"), this.settingMinimumActiveTypeProvider);
//        this.settingMinimumActiveTypeIContainer.add(this.settingMinimumActiveTypeField);
//        this.settingMinimumActiveTypeIContainer.newFeedback("settingMinimumActiveTypeFeedback", this.settingMinimumActiveTypeField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Four_4);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.settingLockInPeriodBlock = this.row3.newUIBlock("settingLockInPeriodBlock", Size.Four_4);
//        this.settingLockInPeriodIContainer = this.settingLockInPeriodBlock.newUIContainer("settingLockInPeriodIContainer");
//        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
//        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
//        this.settingLockInPeriodIContainer.newFeedback("settingLockInPeriodFeedback", this.settingLockInPeriodField);
//
//        this.settingLockInTypeBlock = this.row3.newUIBlock("settingLockInTypeBlock", Size.Four_4);
//        this.settingLockInTypeIContainer = this.settingLockInTypeBlock.newUIContainer("settingLockInTypeIContainer");
//        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
//        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
//        this.settingLockInTypeIContainer.newFeedback("settingLockInTypeFeedback", this.settingLockInTypeField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Four_4);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.settingAllowDividendForInactiveClientBlock = this.row4.newUIBlock("settingAllowDividendForInactiveClientBlock", Size.Four_4);
//        this.settingAllowDividendForInactiveClientIContainer = this.settingAllowDividendForInactiveClientBlock.newUIContainer("settingAllowDividendForInactiveClientIContainer");
//        this.settingAllowDividendForInactiveClientField = new CheckBox("settingAllowDividendForInactiveClientField", new PropertyModel<>(this.itemPage, "settingAllowDividendForInactiveClientValue"));
//        this.settingAllowDividendForInactiveClientIContainer.add(this.settingAllowDividendForInactiveClientField);
//        this.settingAllowDividendForInactiveClientIContainer.newFeedback("settingAllowDividendForInactiveClientFeedback", this.settingAllowDividendForInactiveClientField);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Eight_8);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.settingAllowDividendForInactiveClientField.add(new OnChangeAjaxBehavior());
//
//        this.settingLockInTypeField.setLabel(Model.of("Type"));
//        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
//        this.settingLockInTypeField.setRequired(true);
//
//        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
//        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
//        this.settingLockInPeriodField.setRequired(true);
//        this.settingLockInPeriodField.add(RangeValidator.minimum(0l));
//
//        this.settingMinimumActiveTypeField.setLabel(Model.of("Type"));
//        this.settingMinimumActiveTypeField.add(new OnChangeAjaxBehavior());
//        this.settingMinimumActiveTypeField.setRequired(true);
//
//        this.settingMinimumActivePeriodField.setLabel(Model.of("Minimum Active Period"));
//        this.settingMinimumActivePeriodField.add(new OnChangeAjaxBehavior());
//        this.settingMinimumActivePeriodField.setRequired(true);
//        this.settingMinimumActivePeriodField.add(RangeValidator.minimum(0l));
//
//        this.settingSharePerClientMaximumField.setLabel(Model.of("Shares per Client Maximum"));
//        this.settingSharePerClientMaximumField.add(new OnChangeAjaxBehavior());
//
//        this.settingSharePerClientDefaultField.setLabel(Model.of("Shares per Client Default"));
//        this.settingSharePerClientDefaultField.add(new OnChangeAjaxBehavior());
//        this.settingSharePerClientDefaultField.setRequired(true);
//
//        this.settingSharePerClientMinimumField.setLabel(Model.of("Shares per Client Minimum"));
//        this.settingSharePerClientMinimumField.add(new OnChangeAjaxBehavior());
//
//        this.form.add(new LamdaFormValidator(this::settingSharePerClientValidation, this.settingSharePerClientMaximumField, this.settingSharePerClientDefaultField, this.settingSharePerClientMinimumField));
//    }
//
//    protected void settingSharePerClientValidation(Form<?> form) {
//        if (this.settingSharePerClientMaximumValue.getObject() != null && this.settingSharePerClientMinimumValue.getObject() != null) {
//            if (this.settingSharePerClientMaximumValue.getObject() <= this.settingSharePerClientMinimumValue.getObject()) {
//                this.settingSharePerClientMinimumField.error(new ValidationError("Invalid"));
//                this.settingSharePerClientMaximumField.error(new ValidationError("Invalid"));
//            }
//        } else if (this.settingSharePerClientMaximumValue.getObject() == null && this.settingSharePerClientMinimumValue.getObject() == null) {
//        } else {
//            if (this.settingSharePerClientMinimumValue.getObject() == null) {
//                this.settingSharePerClientMinimumField.error(new ValidationError("Required"));
//            }
//            if (this.settingSharePerClientMaximumValue.getObject() == null) {
//                this.settingSharePerClientMaximumField.error(new ValidationError("Required"));
//            }
//        }
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_TERM);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorSetting.setObject(false);
//        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_MARKET_PRICE);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorSetting.setObject(true);
//    }
//
//}
