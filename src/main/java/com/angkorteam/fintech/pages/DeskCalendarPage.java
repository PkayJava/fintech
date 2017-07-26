package com.angkorteam.fintech.pages;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.LaminationProvider;
import com.angkorteam.fintech.provider.UvProvider;
import com.angkorteam.framework.models.PageHeader;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;

/**
 * Created by socheatkhauv on 6/18/17.
 */
public class DeskCalendarPage extends Page {

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    // calendar sheet

    private Integer numberOfSheetValue;
    private TextField<Integer> numberOfSheetField;
    private TextFeedbackPanel numberOfSheetFeedback;

    private String customerSizeValue;
    private TextField<String> customerSizeField;
    private TextFeedbackPanel customerSizeFeedback;

    private String collectionValue;
    private TextField<String> collectionField;
    private TextFeedbackPanel collectionFeedback;

    private String paperValue;
    private TextField<String> paperField;
    private TextFeedbackPanel paperFeedback;

    private String colorValue;
    private TextField<String> colorField;
    private TextFeedbackPanel colorFeedback;

    // stand

    private String cartonValue;
    private TextField<String> cartonField;
    private TextFeedbackPanel cartonFeedback;

    // stand value add

    private String standPaperValue;
    private TextField<String> standPaperField;
    private TextFeedbackPanel standPaperFeedback;

    private String standColorValue;
    private TextField<String> standColorField;
    private TextFeedbackPanel standColorFeedback;

    private Option standLaminationValue;
    private Select2SingleChoice<Option> standLaminationField;
    private TextFeedbackPanel standLaminationFeedback;

    private Option standUvValue;
    private Select2SingleChoice<Option> standUvField;
    private TextFeedbackPanel standUvFeedback;

    private Boolean standHotStampingValue;
    private CheckBox standHotStampingField;
    private TextFeedbackPanel standHotStampingFeedback;

    private Boolean standEmbossingValue;
    private CheckBox standEmbossingField;
    private TextFeedbackPanel standEmbossingFeedback;

    private Boolean standPunchingValue;
    private CheckBox standPunchingField;
    private TextFeedbackPanel standPunchingFeedback;

    private Boolean standDoubleORingValue;
    private CheckBox standDoubleORingField;
    private TextFeedbackPanel standDoubleORingFeedback;

    private Boolean standSizeValue;
    private CheckBox standSizeField;
    private TextFeedbackPanel standSizeFeedback;

    private Boolean standPackingValue;
    private CheckBox standPackingField;
    private TextFeedbackPanel standPackingFeedback;

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

        //

        this.numberOfSheetField = new TextField<>("numberOfSheetField",
                new PropertyModel<>(this, "numberOfSheetValue"));
        this.numberOfSheetField.setRequired(true);
        this.form.add(this.numberOfSheetField);
        this.numberOfSheetFeedback = new TextFeedbackPanel("numberOfSheetFeedback", this.numberOfSheetField);
        this.form.add(this.numberOfSheetFeedback);

        this.customerSizeField = new TextField<>("customerSizeField", new PropertyModel<>(this, "customerSizeValue"));
        this.customerSizeField.setRequired(true);
        this.form.add(this.customerSizeField);
        this.customerSizeFeedback = new TextFeedbackPanel("customerSizeFeedback", this.customerSizeField);
        this.form.add(this.customerSizeFeedback);

        this.collectionField = new TextField<>("collectionField", new PropertyModel<>(this, "collectionValue"));
        this.collectionField.setRequired(true);
        this.form.add(this.collectionField);
        this.collectionFeedback = new TextFeedbackPanel("collectionFeedback", this.collectionField);
        this.form.add(this.collectionFeedback);

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

        // stand

        this.cartonField = new TextField<>("cartonField", new PropertyModel<>(this, "cartonValue"));
        this.cartonField.setRequired(true);
        this.form.add(this.cartonField);
        this.cartonFeedback = new TextFeedbackPanel("cartonFeedback", this.cartonField);
        this.form.add(this.cartonFeedback);

        // stand value add

        this.standPaperField = new TextField<>("standPaperField", new PropertyModel<>(this, "standPaperValue"));
        this.standPaperField.setRequired(true);
        this.form.add(this.standPaperField);
        this.standPaperFeedback = new TextFeedbackPanel("standPaperFeedback", this.standPaperField);
        this.form.add(this.standPaperFeedback);

        this.standColorField = new TextField<>("standColorField", new PropertyModel<>(this, "standColorValue"));
        this.standColorField.setRequired(true);
        this.form.add(this.standColorField);
        this.standColorFeedback = new TextFeedbackPanel("standColorFeedback", this.standColorField);
        this.form.add(this.standColorFeedback);

        LaminationProvider laminationProvider = new LaminationProvider();
        this.standLaminationField = new Select2SingleChoice<>("standLaminationField",
                new PropertyModel<>(this, "standLaminationValue"), laminationProvider);
        this.standLaminationField.setRequired(true);
        this.form.add(this.standLaminationField);
        this.standLaminationFeedback = new TextFeedbackPanel("standLaminationFeedback", this.standLaminationField);
        this.form.add(this.standLaminationFeedback);

        UvProvider uvProvider = new UvProvider();
        this.standUvField = new Select2SingleChoice<>("standUvField", new PropertyModel<>(this, "standUvValue"),
                uvProvider);
        this.standUvField.setRequired(true);
        this.form.add(this.standUvField);
        this.standUvFeedback = new TextFeedbackPanel("standUvFeedback", this.standUvField);
        this.form.add(this.standUvFeedback);

        this.standHotStampingField = new CheckBox("standHotStampingField",
                new PropertyModel<>(this, "standHotStampingValue"));
        this.standHotStampingField.setRequired(true);
        this.form.add(this.standHotStampingField);
        this.standHotStampingFeedback = new TextFeedbackPanel("standHotStampingFeedback", this.standHotStampingField);
        this.form.add(this.standHotStampingFeedback);

        this.standEmbossingField = new CheckBox("standEmbossingField",
                new PropertyModel<>(this, "standEmbossingValue"));
        this.standEmbossingField.setRequired(true);
        this.form.add(this.standEmbossingField);
        this.standEmbossingFeedback = new TextFeedbackPanel("standEmbossingFeedback", this.standEmbossingField);
        this.form.add(this.standEmbossingFeedback);

        this.standPunchingField = new CheckBox("standPunchingField", new PropertyModel<>(this, "standPunchingValue"));
        this.standPunchingField.setRequired(true);
        this.form.add(this.standPunchingField);
        this.standPunchingFeedback = new TextFeedbackPanel("standPunchingFeedback", this.standPunchingField);
        this.form.add(this.standPunchingFeedback);

        this.standDoubleORingField = new CheckBox("standDoubleORingField",
                new PropertyModel<>(this, "standDoubleORingValue"));
        this.standDoubleORingField.setRequired(true);
        this.form.add(this.standDoubleORingField);
        this.standDoubleORingFeedback = new TextFeedbackPanel("standDoubleORingFeedback", this.standDoubleORingField);
        this.form.add(this.standDoubleORingFeedback);

        this.standSizeField = new CheckBox("standSizeField", new PropertyModel<>(this, "standSizeValue"));
        this.standSizeField.setRequired(true);
        this.form.add(this.standSizeField);
        this.standSizeFeedback = new TextFeedbackPanel("standSizeFeedback", this.standSizeField);
        this.form.add(this.standSizeFeedback);

        this.standPackingField = new CheckBox("standPackingField", new PropertyModel<>(this, "standPackingValue"));
        this.standPackingField.setRequired(true);
        this.form.add(this.standPackingField);
        this.standPackingFeedback = new TextFeedbackPanel("standPackingFeedback", this.standPackingField);
        this.form.add(this.standPackingFeedback);

    }

    protected void saveButtonOnSubmit(Button button) {

    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("Desk Calendar").setDescription("Desk Calendar Quotation"));
    }

}
