package com.angkorteam.fintech.pages;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.QuotationTypeProvider;
import com.angkorteam.framework.models.PageHeader;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;

/**
 * Created by socheatkhauv on 6/11/17.
 */
public class IndexPage extends Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexPage.class);

    private Option typeValue;
    private Select2SingleChoice<Option> typeField;
    private TextFeedbackPanel typeFeedback;

    private Button nextButton;
    private Form<Void> form;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        QuotationTypeProvider typeProvider = new QuotationTypeProvider();
        this.typeField = new Select2SingleChoice<>("typeField", new PropertyModel<>(this, "typeValue"), typeProvider);
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonClick);
        this.form.add(this.nextButton);

    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("Quotation").setDescription("Select Quotation"));
    }

    protected void nextButtonClick(Button button) {
        if (this.typeValue.getId().equals("Newspaper")) {
            setResponsePage(NewspaperPage.class);
        } else if (this.typeValue.getId().equals("Reading book, Note book, Pass book")) {
            setResponsePage(ReadingBookPage.class);
        } else if (this.typeValue.getId().equals("Desk Calendar")) {
            setResponsePage(DeskCalendarPage.class);
        }
    }

}
