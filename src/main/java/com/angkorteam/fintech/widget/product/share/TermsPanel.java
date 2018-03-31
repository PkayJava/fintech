package com.angkorteam.fintech.widget.product.share;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class TermsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock termTotalNumberOfShareBlock;
    protected WebMarkupContainer termTotalNumberOfShareIContainer;
    protected TextField<Long> termTotalNumberOfShareField;
    protected TextFeedbackPanel termTotalNumberOfShareFeedback;

    protected WebMarkupBlock termShareToBeIssuedBlock;
    protected WebMarkupContainer termShareToBeIssuedIContainer;
    protected TextField<Long> termShareToBeIssuedField;
    protected TextFeedbackPanel termShareToBeIssuedFeedback;

    protected WebMarkupBlock termNominalPriceBlock;
    protected WebMarkupContainer termNominalPriceIContainer;
    protected TextField<Double> termNominalPriceField;
    protected TextFeedbackPanel termNominalPriceFeedback;

    protected WebMarkupBlock termCapitalBlock;
    protected WebMarkupContainer termCapitalVContainer;
    protected ReadOnlyView termCapitalView;

    public TermsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
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

        this.termCapitalBlock = new WebMarkupBlock("termCapitalBlock", Size.Six_6);
        this.form.add(this.termCapitalBlock);
        this.termCapitalVContainer = new WebMarkupContainer("termCapitalVContainer");
        this.termCapitalBlock.add(this.termCapitalVContainer);
        this.termCapitalView = new ReadOnlyView("termCapitalView", new PropertyModel<>(this.itemPage, "termCapitalValue"));
        this.termCapitalVContainer.add(this.termCapitalView);

        this.termNominalPriceBlock = new WebMarkupBlock("termNominalPriceBlock", Size.Six_6);
        this.form.add(this.termNominalPriceBlock);
        this.termNominalPriceIContainer = new WebMarkupContainer("termNominalPriceIContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceIContainer);
        this.termNominalPriceField = new TextField<>("termNominalPriceField", new PropertyModel<>(this.itemPage, "termNominalPriceValue"));
        this.termNominalPriceField.setLabel(Model.of("Nominal Price"));
        this.termNominalPriceField.add(new OnChangeAjaxBehavior(this::termNominalPriceFieldUpdate));
        this.termNominalPriceIContainer.add(this.termNominalPriceField);
        this.termNominalPriceFeedback = new TextFeedbackPanel("termNominalPriceFeedback", this.termNominalPriceField);
        this.termNominalPriceIContainer.add(this.termNominalPriceFeedback);

        this.termShareToBeIssuedBlock = new WebMarkupBlock("termShareToBeIssuedBlock", Size.Six_6);
        this.form.add(this.termShareToBeIssuedBlock);
        this.termShareToBeIssuedIContainer = new WebMarkupContainer("termShareToBeIssuedIContainer");
        this.termShareToBeIssuedBlock.add(this.termShareToBeIssuedIContainer);
        this.termShareToBeIssuedField = new TextField<>("termShareToBeIssuedField", new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue"));
        this.termShareToBeIssuedField.setLabel(Model.of("Shares to be issued"));
        this.termShareToBeIssuedField.add(new OnChangeAjaxBehavior());
        this.termShareToBeIssuedIContainer.add(this.termShareToBeIssuedField);
        this.termShareToBeIssuedFeedback = new TextFeedbackPanel("termShareToBeIssuedFeedback", this.termShareToBeIssuedField);
        this.termShareToBeIssuedIContainer.add(this.termShareToBeIssuedFeedback);

        this.termTotalNumberOfShareBlock = new WebMarkupBlock("termTotalNumberOfShareBlock", Size.Six_6);
        this.form.add(this.termTotalNumberOfShareBlock);
        this.termTotalNumberOfShareIContainer = new WebMarkupContainer("termTotalNumberOfShareIContainer");
        this.termTotalNumberOfShareBlock.add(this.termTotalNumberOfShareIContainer);
        this.termTotalNumberOfShareField = new TextField<>("termTotalNumberOfShareField", new PropertyModel<>(this.itemPage, "termTotalNumberOfShareValue"));
        this.termTotalNumberOfShareField.setLabel(Model.of("Total Number of Shares"));
        this.termTotalNumberOfShareField.add(new OnChangeAjaxBehavior(this::termNominalPriceFieldUpdate));
        this.termTotalNumberOfShareIContainer.add(this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareFeedback = new TextFeedbackPanel("termTotalNumberOfShareFeedback", this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareIContainer.add(this.termTotalNumberOfShareFeedback);
    }

    protected boolean termNominalPriceFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Double> termNominalPriceValue = new PropertyModel<>(this.itemPage, "termNominalPriceValue");
        PropertyModel<Long> termShareToBeIssuedValue = new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue");
        PropertyModel<Double> termCapitalValue = new PropertyModel<>(this.itemPage, "termCapitalValue");
        if (termNominalPriceValue.getObject() != null && termShareToBeIssuedValue.getObject() != null) {
            termCapitalValue.setObject(termNominalPriceValue.getObject() * termShareToBeIssuedValue.getObject());
        } else {
            termCapitalValue.setObject(0d);
        }

        if (target != null) {
            target.add(this.termCapitalBlock);
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_SETTING);
        this.errorTerm.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorTerm.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_CURRENCY);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    @Override
    protected void configureMetaData() {
        this.termTotalNumberOfShareField.setRequired(true);
        this.termNominalPriceField.setRequired(true);
        this.termShareToBeIssuedField.setRequired(true);
    }

}
