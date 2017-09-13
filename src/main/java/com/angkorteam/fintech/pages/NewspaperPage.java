package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.framework.models.PageHeader;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 6/17/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class NewspaperPage extends Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexPage.class);

    private Integer numberOfPageValue;
    private TextField<Integer> numberOfPageField;
    private TextFeedbackPanel numberOfPageFeedback;

    private String customerSizeValue;
    private TextField<String> customerSizeField;
    private TextFeedbackPanel customerSizeFeedback;

    private String paperValue;
    private TextField<String> paperField;
    private TextFeedbackPanel paperFeedback;

    private String colorValue;
    private TextField<String> colorField;
    private TextFeedbackPanel colorFeedback;

    private String collectionValue;
    private TextField<String> collectionField;
    private TextFeedbackPanel collectionFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", IndexPage.class);
        this.form.add(this.closeButton);

        this.numberOfPageField = new TextField<>("numberOfPageField", new PropertyModel<>(this, "numberOfPageValue"));
        this.numberOfPageField.setRequired(true);
        this.form.add(this.numberOfPageField);
        this.numberOfPageFeedback = new TextFeedbackPanel("numberOfPageFeedback", this.numberOfPageField);
        this.form.add(this.numberOfPageFeedback);

        this.customerSizeField = new TextField<>("customerSizeField", new PropertyModel<>(this, "customerSizeValue"));
        this.customerSizeField.setRequired(true);
        this.form.add(this.customerSizeField);
        this.customerSizeFeedback = new TextFeedbackPanel("customerSizeFeedback", this.customerSizeField);
        this.form.add(this.customerSizeFeedback);

        this.paperField = new TextField<>("paperField", new PropertyModel<>(this, "paperValue"));
        this.paperField.setRequired(true);
        this.form.add(this.paperField);
        this.paperFeedback = new TextFeedbackPanel("paperFeedback", this.paperField);
        this.form.add(this.paperFeedback);

        this.colorField = new TextField<>("colorField", new PropertyModel<>(this, "colorValue"));
        this.colorField.setRequired(true);
        this.form.add(this.colorField);
        this.colorFeedback = new TextFeedbackPanel("colorFeedback", this.colorField);
        this.form.add(this.colorFeedback);

        this.collectionField = new TextField<>("collectionField", new PropertyModel<>(this, "collectionValue"));
        this.collectionField.setRequired(true);
        this.form.add(this.collectionField);
        this.collectionFeedback = new TextFeedbackPanel("collectionFeedback", this.collectionField);
        this.form.add(this.collectionFeedback);

    }

    protected void saveButtonOnSubmit(Button button) {

    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("Newspaper").setDescription("Newspaper Quotation"));
    }

}
