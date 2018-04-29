package com.angkorteam.fintech.widget.product.savings;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class DetailsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorDetail;

    protected Form<Void> form;
    protected Button nextButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected UIRow row1;

    protected UIBlock detailProductNameBlock;
    protected UIContainer detailProductNameIContainer;
    protected TextField<String> detailProductNameField;

    protected UIBlock detailShortNameBlock;
    protected UIContainer detailShortNameIContainer;
    protected TextField<String> detailShortNameField;

    protected UIRow row2;

    protected UIBlock detailDescriptionBlock;
    protected UIContainer detailDescriptionIContainer;
    protected TextField<String> detailDescriptionField;

    protected UIBlock row2Block1;

    public DetailsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.detailShortNameBlock = this.row1.newUIBlock("detailShortNameBlock", Size.Six_6);
        this.detailShortNameIContainer = this.detailShortNameBlock.newUIContainer("detailShortNameIContainer");
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameIContainer.newFeedback("detailShortNameFeedback", this.detailShortNameField);

        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
        this.detailProductNameIContainer = this.detailProductNameBlock.newUIContainer("detailProductNameIContainer");
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameIContainer.newFeedback("detailProductNameFeedback", this.detailProductNameField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.detailDescriptionBlock = this.row2.newUIBlock("detailDescriptionBlock", Size.Six_6);
        this.detailDescriptionIContainer = this.detailDescriptionBlock.newUIContainer("detailDescriptionIContainer");
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionIContainer.newFeedback("detailDescriptionFeedback", this.detailDescriptionField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailProductNameField.setRequired(true);
        this.detailShortNameField.setRequired(true);
        this.detailDescriptionField.setRequired(true);
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_CURRENCY);
        this.errorDetail.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorDetail.setObject(true);
    }

}
