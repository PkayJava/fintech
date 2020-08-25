//package com.angkorteam.fintech.widget.product.share;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.validation.validator.RangeValidator;
//
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
//import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//
//public class TermsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorTerm;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock termTotalNumberOfShareBlock;
//    protected UIContainer termTotalNumberOfShareIContainer;
//    protected TextField<Long> termTotalNumberOfShareField;
//
//    protected UIBlock termShareToBeIssuedBlock;
//    protected UIContainer termShareToBeIssuedIContainer;
//    protected TextField<Long> termShareToBeIssuedField;
//
//    protected UIRow row2;
//
//    protected UIBlock termNominalPriceBlock;
//    protected UIContainer termNominalPriceIContainer;
//    protected TextField<Double> termNominalPriceField;
//
//    protected UIBlock termCapitalBlock;
//    protected UIContainer termCapitalVContainer;
//    protected ReadOnlyView termCapitalView;
//
//    public TermsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
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
//        this.termShareToBeIssuedBlock = this.row1.newUIBlock("termShareToBeIssuedBlock", Size.Six_6);
//        this.termShareToBeIssuedIContainer = this.termShareToBeIssuedBlock.newUIContainer("termShareToBeIssuedIContainer");
//        this.termShareToBeIssuedField = new TextField<>("termShareToBeIssuedField", new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue"));
//        this.termShareToBeIssuedIContainer.add(this.termShareToBeIssuedField);
//        this.termShareToBeIssuedIContainer.newFeedback("termShareToBeIssuedFeedback", this.termShareToBeIssuedField);
//
//        this.termTotalNumberOfShareBlock = this.row1.newUIBlock("termTotalNumberOfShareBlock", Size.Six_6);
//        this.termTotalNumberOfShareIContainer = this.termTotalNumberOfShareBlock.newUIContainer("termTotalNumberOfShareIContainer");
//        this.termTotalNumberOfShareField = new TextField<>("termTotalNumberOfShareField", new PropertyModel<>(this.itemPage, "termTotalNumberOfShareValue"));
//        this.termTotalNumberOfShareIContainer.add(this.termTotalNumberOfShareField);
//        this.termTotalNumberOfShareIContainer.newFeedback("termTotalNumberOfShareFeedback", this.termTotalNumberOfShareField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.termCapitalBlock = this.row2.newUIBlock("termCapitalBlock", Size.Six_6);
//        this.termCapitalVContainer = this.termCapitalBlock.newUIContainer("termCapitalVContainer");
//        this.termCapitalView = new ReadOnlyView("termCapitalView", new PropertyModel<>(this.itemPage, "termCapitalValue"));
//        this.termCapitalVContainer.add(this.termCapitalView);
//
//        this.termNominalPriceBlock = this.row2.newUIBlock("termNominalPriceBlock", Size.Six_6);
//        this.termNominalPriceIContainer = this.termNominalPriceBlock.newUIContainer("termNominalPriceIContainer");
//        this.termNominalPriceField = new TextField<>("termNominalPriceField", new PropertyModel<>(this.itemPage, "termNominalPriceValue"));
//        this.termNominalPriceIContainer.add(this.termNominalPriceField);
//        this.termNominalPriceIContainer.newFeedback("termNominalPriceFeedback", this.termNominalPriceField);
//
//    }
//
//    protected boolean termNominalPriceFieldUpdate(AjaxRequestTarget target) {
//        PropertyModel<Double> termNominalPriceValue = new PropertyModel<>(this.itemPage, "termNominalPriceValue");
//        PropertyModel<Long> termShareToBeIssuedValue = new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue");
//        PropertyModel<Double> termCapitalValue = new PropertyModel<>(this.itemPage, "termCapitalValue");
//        if (termNominalPriceValue.getObject() != null && termShareToBeIssuedValue.getObject() != null) {
//            termCapitalValue.setObject(termNominalPriceValue.getObject() * termShareToBeIssuedValue.getObject());
//        } else {
//            termCapitalValue.setObject(0d);
//        }
//
//        if (target != null) {
//            target.add(this.termCapitalBlock);
//        }
//        return false;
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorTerm.setObject(false);
//        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_SETTING);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorTerm.setObject(true);
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_CURRENCY);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.termNominalPriceField.setLabel(Model.of("Nominal Price"));
//        this.termNominalPriceField.add(new OnChangeAjaxBehavior(this::termNominalPriceFieldUpdate));
//        this.termNominalPriceField.setRequired(true);
//        this.termNominalPriceField.add(RangeValidator.minimum(0d));
//
//        this.termTotalNumberOfShareField.setLabel(Model.of("Total Number of Shares"));
//        this.termTotalNumberOfShareField.add(new OnChangeAjaxBehavior(this::termNominalPriceFieldUpdate));
//        this.termTotalNumberOfShareField.setRequired(true);
//        this.termTotalNumberOfShareField.add(RangeValidator.minimum(1l));
//
//        this.termShareToBeIssuedField.setLabel(Model.of("Shares to be issued"));
//        this.termShareToBeIssuedField.add(new OnChangeAjaxBehavior());
//        this.termShareToBeIssuedField.setRequired(true);
//        this.termShareToBeIssuedField.add(RangeValidator.minimum(1l));
//
//    }
//
//}
