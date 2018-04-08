package com.angkorteam.fintech.widget.product.share;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.MinimumActivePeriodProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class SettingsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorSetting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock settingSharePerClientMinimumBlock;
    protected WebMarkupContainer settingSharePerClientMinimumIContainer;
    protected TextField<Long> settingSharePerClientMinimumField;
    protected TextFeedbackPanel settingSharePerClientMinimumFeedback;

    protected WebMarkupBlock settingSharePerClientDefaultBlock;
    protected WebMarkupContainer settingSharePerClientDefaultIContainer;
    protected TextField<Long> settingSharePerClientDefaultField;
    protected TextFeedbackPanel settingSharePerClientDefaultFeedback;

    protected WebMarkupBlock settingSharePerClientMaximumBlock;
    protected WebMarkupContainer settingSharePerClientMaximumIContainer;
    protected TextField<Long> settingSharePerClientMaximumField;
    protected TextFeedbackPanel settingSharePerClientMaximumFeedback;

    protected WebMarkupBlock settingMinimumActivePeriodBlock;
    protected WebMarkupContainer settingMinimumActivePeriodIContainer;
    protected TextField<Long> settingMinimumActivePeriodField;
    protected TextFeedbackPanel settingMinimumActivePeriodFeedback;

    protected WebMarkupBlock settingMinimumActiveTypeBlock;
    protected WebMarkupContainer settingMinimumActiveTypeIContainer;
    protected MinimumActivePeriodProvider settingMinimumActiveTypeProvider;
    protected Select2SingleChoice<Option> settingMinimumActiveTypeField;
    protected TextFeedbackPanel settingMinimumActiveTypeFeedback;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected TextField<Long> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingAllowDividendForInactiveClientBlock;
    protected WebMarkupContainer settingAllowDividendForInactiveClientIContainer;
    protected CheckBox settingAllowDividendForInactiveClientField;
    protected TextFeedbackPanel settingAllowDividendForInactiveClientFeedback;

    public SettingsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.form.add(this.closeLink);

        this.settingAllowDividendForInactiveClientBlock = new WebMarkupBlock("settingAllowDividendForInactiveClientBlock", Size.Four_4);
        this.form.add(this.settingAllowDividendForInactiveClientBlock);
        this.settingAllowDividendForInactiveClientIContainer = new WebMarkupContainer("settingAllowDividendForInactiveClientIContainer");
        this.settingAllowDividendForInactiveClientBlock.add(this.settingAllowDividendForInactiveClientIContainer);
        this.settingAllowDividendForInactiveClientField = new CheckBox("settingAllowDividendForInactiveClientField", new PropertyModel<>(this.itemPage, "settingAllowDividendForInactiveClientValue"));
        this.settingAllowDividendForInactiveClientField.add(new OnChangeAjaxBehavior());
        this.settingAllowDividendForInactiveClientIContainer.add(this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientFeedback = new TextFeedbackPanel("settingAllowDividendForInactiveClientFeedback", this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientIContainer.add(this.settingAllowDividendForInactiveClientFeedback);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);

        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);

        this.settingMinimumActiveTypeBlock = new WebMarkupBlock("settingMinimumActiveTypeBlock", Size.Four_4);
        this.form.add(this.settingMinimumActiveTypeBlock);
        this.settingMinimumActiveTypeIContainer = new WebMarkupContainer("settingMinimumActiveTypeIContainer");
        this.settingMinimumActiveTypeBlock.add(this.settingMinimumActiveTypeIContainer);
        this.settingMinimumActiveTypeProvider = new MinimumActivePeriodProvider();
        this.settingMinimumActiveTypeField = new Select2SingleChoice<>("settingMinimumActiveTypeField", new PropertyModel<>(this.itemPage, "settingMinimumActiveTypeValue"), this.settingMinimumActiveTypeProvider);
        this.settingMinimumActiveTypeField.setLabel(Model.of("Type"));
        this.settingMinimumActiveTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActiveTypeIContainer.add(this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeFeedback = new TextFeedbackPanel("settingMinimumActiveTypeFeedback", this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeIContainer.add(this.settingMinimumActiveTypeFeedback);

        this.settingMinimumActivePeriodBlock = new WebMarkupBlock("settingMinimumActivePeriodBlock", Size.Four_4);
        this.form.add(this.settingMinimumActivePeriodBlock);
        this.settingMinimumActivePeriodIContainer = new WebMarkupContainer("settingMinimumActivePeriodIContainer");
        this.settingMinimumActivePeriodBlock.add(this.settingMinimumActivePeriodIContainer);
        this.settingMinimumActivePeriodField = new TextField<>("settingMinimumActivePeriodField", new PropertyModel<>(this.itemPage, "settingMinimumActivePeriodValue"));
        this.settingMinimumActivePeriodField.setLabel(Model.of("Minimum Active Period"));
        this.settingMinimumActivePeriodField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActivePeriodIContainer.add(this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodFeedback = new TextFeedbackPanel("settingMinimumActivePeriodFeedback", this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodIContainer.add(this.settingMinimumActivePeriodFeedback);

        this.settingSharePerClientMaximumBlock = new WebMarkupBlock("settingSharePerClientMaximumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMaximumBlock);
        this.settingSharePerClientMaximumIContainer = new WebMarkupContainer("settingSharePerClientMaximumIContainer");
        this.settingSharePerClientMaximumBlock.add(this.settingSharePerClientMaximumIContainer);
        this.settingSharePerClientMaximumField = new TextField<>("settingSharePerClientMaximumField", new PropertyModel<>(this.itemPage, "settingSharePerClientMaximumValue"));
        this.settingSharePerClientMaximumField.setLabel(Model.of("Shares per Client Maximum"));
        this.settingSharePerClientMaximumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMaximumIContainer.add(this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumFeedback = new TextFeedbackPanel("settingSharePerClientMaximumFeedback", this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumIContainer.add(this.settingSharePerClientMaximumFeedback);

        this.settingSharePerClientDefaultBlock = new WebMarkupBlock("settingSharePerClientDefaultBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientDefaultBlock);
        this.settingSharePerClientDefaultIContainer = new WebMarkupContainer("settingSharePerClientDefaultIContainer");
        this.settingSharePerClientDefaultBlock.add(this.settingSharePerClientDefaultIContainer);
        this.settingSharePerClientDefaultField = new TextField<>("settingSharePerClientDefaultField", new PropertyModel<>(this.itemPage, "settingSharePerClientDefaultValue"));
        this.settingSharePerClientDefaultField.setLabel(Model.of("Shares per Client Default"));
        this.settingSharePerClientDefaultField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientDefaultIContainer.add(this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultFeedback = new TextFeedbackPanel("settingSharePerClientDefaultFeedback", this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultIContainer.add(this.settingSharePerClientDefaultFeedback);

        this.settingSharePerClientMinimumBlock = new WebMarkupBlock("settingSharePerClientMinimumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumIContainer = new WebMarkupContainer("settingSharePerClientMinimumIContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumIContainer);
        this.settingSharePerClientMinimumField = new TextField<>("settingSharePerClientMinimumField", new PropertyModel<>(this.itemPage, "settingSharePerClientMinimumValue"));
        this.settingSharePerClientMinimumField.setLabel(Model.of("Shares per Client Minimum"));
        this.settingSharePerClientMinimumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMinimumIContainer.add(this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumFeedback = new TextFeedbackPanel("settingSharePerClientMinimumFeedback", this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumIContainer.add(this.settingSharePerClientMinimumFeedback);

    }

    @Override
    protected void configureMetaData() {
        this.settingSharePerClientDefaultField.setRequired(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_MARKET_PRICE);
        this.errorSetting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
