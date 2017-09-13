package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.LaminationProvider;
import com.angkorteam.fintech.provider.UvProvider;
import com.angkorteam.framework.models.PageHeader;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 6/18/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ReadingBookPage extends Page {

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    //

    private Integer numberOfPageValue;
    private TextField<Integer> numberOfPageField;
    private TextFeedbackPanel numberOfPageFeedback;

    private String customerSizeValue;
    private TextField<String> customerSizeField;
    private TextFeedbackPanel customerSizeFeedback;

    private String collectionValue;
    private TextField<String> collectionField;
    private TextFeedbackPanel collectionFeedback;

    //

    private String textPaperValue;
    private TextField<String> textPaperField;
    private TextFeedbackPanel textPaperFeedback;

    private String textColorValue;
    private TextField<String> textColorField;
    private TextFeedbackPanel textColorFeedback;

    private String coverPaperValue;
    private TextField<String> coverPaperField;
    private TextFeedbackPanel coverPaperFeedback;

    private String coverColorValue;
    private TextField<String> coverColorField;
    private TextFeedbackPanel coverColorFeedback;

    //

    private Option uvValue;
    private Select2SingleChoice<Option> uvField;
    private TextFeedbackPanel uvFeedback;

    private Option laminationValue;
    private Select2SingleChoice<Option> laminationField;
    private TextFeedbackPanel laminationFeedback;

    private Boolean waterBaseVanishValue;
    private CheckBox waterBaseVanishField;
    private TextFeedbackPanel waterBaseVanishFeedback;

    private Boolean embossingValue;
    private CheckBox embossingField;
    private TextFeedbackPanel embossingFeedback;

    private Boolean hotStampingValue;
    private CheckBox hotStampingField;
    private TextFeedbackPanel hotStampingFeedback;

    //

    private Boolean sewingValue;
    private CheckBox sewingField;
    private TextFeedbackPanel sewingFeedback;

    private Boolean hotGlueValue;
    private CheckBox hotGlueField;
    private TextFeedbackPanel hotGlueFeedback;

    private Boolean doubleORingValue;
    private CheckBox doubleORingField;
    private TextFeedbackPanel doubleORingFeedback;

    private Boolean cuttingValue;
    private CheckBox cuttingField;
    private TextFeedbackPanel cuttingFeedback;

    private Boolean packingValue;
    private CheckBox packingField;
    private TextFeedbackPanel packingFeedback;

    private String perforatingNumberValue;
    private TextField<String> perforatingNumberField;
    private TextFeedbackPanel perforatingNumberFeedback;

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

        this.collectionField = new TextField<>("collectionField", new PropertyModel<>(this, "collectionValue"));
        this.collectionField.setRequired(true);
        this.form.add(this.collectionField);
        this.collectionFeedback = new TextFeedbackPanel("collectionFeedback", this.collectionField);
        this.form.add(this.collectionFeedback);

        //

        this.textPaperField = new TextField<>("textPaperField", new PropertyModel<>(this, "textPaperValue"));
        this.textPaperField.setRequired(true);
        this.form.add(this.textPaperField);
        this.textPaperFeedback = new TextFeedbackPanel("textPaperFeedback", this.textPaperField);
        this.form.add(this.textPaperFeedback);

        this.textColorField = new TextField<>("textColorField", new PropertyModel<>(this, "textColorValue"));
        this.textColorField.setRequired(true);
        this.form.add(this.textColorField);
        this.textColorFeedback = new TextFeedbackPanel("textColorFeedback", this.textColorField);
        this.form.add(this.textColorFeedback);

        this.coverPaperField = new TextField<>("coverPaperField", new PropertyModel<>(this, "coverPaperValue"));
        this.coverPaperField.setRequired(true);
        this.form.add(this.coverPaperField);
        this.coverPaperFeedback = new TextFeedbackPanel("coverPaperFeedback", this.coverPaperField);
        this.form.add(this.coverPaperFeedback);

        this.coverColorField = new TextField<>("coverColorField", new PropertyModel<>(this, "coverColorValue"));
        this.coverColorField.setRequired(true);
        this.form.add(this.coverColorField);
        this.coverColorFeedback = new TextFeedbackPanel("coverColorFeedback", this.coverColorField);
        this.form.add(this.coverColorFeedback);

        //

        UvProvider uvProvider = new UvProvider();
        this.uvField = new Select2SingleChoice<>("uvField", new PropertyModel<>(this, "uvValue"), uvProvider);
        this.uvField.setRequired(true);
        this.form.add(this.uvField);
        this.uvFeedback = new TextFeedbackPanel("uvFeedback", this.uvField);
        this.form.add(this.uvFeedback);

        LaminationProvider laminationProvider = new LaminationProvider();
        this.laminationField = new Select2SingleChoice<>("laminationField",
                new PropertyModel<>(this, "laminationValue"), laminationProvider);
        this.laminationField.setRequired(true);
        this.form.add(this.laminationField);
        this.laminationFeedback = new TextFeedbackPanel("laminationFeedback", this.laminationField);
        this.form.add(this.laminationFeedback);

        this.waterBaseVanishField = new CheckBox("waterBaseVanishField",
                new PropertyModel<>(this, "waterBaseVanishValue"));
        this.waterBaseVanishField.setRequired(true);
        this.form.add(this.waterBaseVanishField);
        this.waterBaseVanishFeedback = new TextFeedbackPanel("waterBaseVanishFeedback", this.waterBaseVanishField);
        this.form.add(this.waterBaseVanishFeedback);

        this.embossingField = new CheckBox("embossingField", new PropertyModel<>(this, "embossingValue"));
        this.embossingField.setRequired(true);
        this.form.add(this.embossingField);
        this.embossingFeedback = new TextFeedbackPanel("embossingFeedback", this.embossingField);
        this.form.add(this.embossingFeedback);

        this.hotStampingField = new CheckBox("hotStampingField", new PropertyModel<>(this, "hotStampingValue"));
        this.hotStampingField.setRequired(true);
        this.form.add(this.hotStampingField);
        this.hotStampingFeedback = new TextFeedbackPanel("hotStampingFeedback", this.hotStampingField);
        this.form.add(this.hotStampingFeedback);

        //

        this.sewingField = new CheckBox("sewingField", new PropertyModel<>(this, "sewingValue"));
        this.sewingField.setRequired(true);
        this.form.add(this.sewingField);
        this.sewingFeedback = new TextFeedbackPanel("sewingFeedback", this.sewingField);
        this.form.add(this.sewingFeedback);

        this.hotGlueField = new CheckBox("hotGlueField", new PropertyModel<>(this, "hotGlueValue"));
        this.hotGlueField.setRequired(true);
        this.form.add(this.hotGlueField);
        this.hotGlueFeedback = new TextFeedbackPanel("hotGlueFeedback", this.hotGlueField);
        this.form.add(this.hotGlueFeedback);

        this.doubleORingField = new CheckBox("doubleORingField", new PropertyModel<>(this, "doubleORingValue"));
        this.doubleORingField.setRequired(true);
        this.form.add(this.doubleORingField);
        this.doubleORingFeedback = new TextFeedbackPanel("doubleORingFeedback", this.doubleORingField);
        this.form.add(this.doubleORingFeedback);

        this.cuttingField = new CheckBox("cuttingField", new PropertyModel<>(this, "cuttingValue"));
        this.cuttingField.setRequired(true);
        this.form.add(this.cuttingField);
        this.cuttingFeedback = new TextFeedbackPanel("cuttingFeedback", this.cuttingField);
        this.form.add(this.cuttingFeedback);

        this.packingField = new CheckBox("packingField", new PropertyModel<>(this, "packingValue"));
        this.packingField.setRequired(true);
        this.form.add(this.packingField);
        this.packingFeedback = new TextFeedbackPanel("packingFeedback", this.packingField);
        this.form.add(this.packingFeedback);

        this.perforatingNumberField = new TextField<String>("perforatingNumberField",
                new PropertyModel<>(this, "perforatingNumberValue"));
        this.perforatingNumberField.setRequired(true);
        this.form.add(this.perforatingNumberField);
        this.perforatingNumberFeedback = new TextFeedbackPanel("perforatingNumberFeedback",
                this.perforatingNumberField);
        this.form.add(this.perforatingNumberFeedback);

    }

    protected void saveButtonOnSubmit(Button button) {

    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("Reading Book")
                .setDescription("Reading book, Note book, Pass book Quotation"));
    }

}
