package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.models.*;
import com.angkorteam.framework.wicket.DashboardPage;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.*;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/11/17.
 */
public class BIndexPage extends DashboardPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BIndexPage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private List<Option> relatedProduct;
    private Select2MultipleChoice<Option> relatedProductField;
    private TextFeedbackPanel relatedProductFeedback;

    private Double normalPrice;
    private TextField<Double> normalPriceField;
    private TextFeedbackPanel normalPriceFeedback;

    private Option category;
    private Select2SingleChoice<Option> categoryField;
    private TextFeedbackPanel categoryFeedback;

    private Option brand;
    private Select2SingleChoice<Option> brandField;
    private TextFeedbackPanel brandFeedback;

    private Option color;
    private Select2SingleChoice<Option> colorField;
    private TextFeedbackPanel colorFeedback;

    private Option size;
    private Select2SingleChoice<Option> sizeField;
    private TextFeedbackPanel sizeFeedback;

    private Double discountPrice;
    private TextField<Double> discountPriceField;
    private TextFeedbackPanel discountPriceFeedback;

    private String reference;
    private TextField<String> referenceField;
    private TextFeedbackPanel referenceFeedback;

    private Integer quantity = 1;
    private TextField<Integer> quantityField;
    private TextFeedbackPanel quantityFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<FileUpload> mainImage;
    private FileUploadField mainImageField;
    private TextFeedbackPanel mainImageFeedback;

    private List<FileUpload> mainImageHighRes;
    private FileUploadField mainImageHighResField;
    private TextFeedbackPanel mainImageHighResFeedback;

    private List<FileUpload> variantImage;
    private MultiFileUploadField variantImageField;
    private TextFeedbackPanel variantImageFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
//        this.nameField.add(new UniqueRecordValidator<>("ecommerce_product", "name"));
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.normalPriceField = new TextField<>("normalPriceField", new PropertyModel<>(this, "normalPrice"));
        this.normalPriceField.setRequired(true);
        this.form.add(this.normalPriceField);
        this.normalPriceFeedback = new TextFeedbackPanel("normalPriceFeedback", this.normalPriceField);
        this.form.add(this.normalPriceFeedback);

        OptionSingleChoiceProvider categoryOption = new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "name", "path");
        categoryOption.applyWhere("enabled", "enabled = true");
        this.categoryField = new Select2SingleChoice<>("categoryField", new PropertyModel<>(this, "category"), categoryOption);
        this.categoryField.setRequired(true);
        this.form.add(this.categoryField);
        this.categoryFeedback = new TextFeedbackPanel("categoryFeedback", this.categoryField);
        this.form.add(this.categoryFeedback);

        OptionSingleChoiceProvider brandOption = new OptionSingleChoiceProvider("ecommerce_brand", "ecommerce_brand_id", "name");
        brandOption.applyWhere("enabled", "enabled = true");
        this.brandField = new Select2SingleChoice<>("brandField", new PropertyModel<>(this, "brand"), brandOption);
        this.form.add(this.brandField);
        this.brandFeedback = new TextFeedbackPanel("brandFeedback", this.brandField);
        this.form.add(this.brandFeedback);

        this.discountPriceField = new TextField<>("discountPriceField", new PropertyModel<>(this, "discountPrice"));
        this.form.add(this.discountPriceField);
        this.discountPriceFeedback = new TextFeedbackPanel("discountPriceFeedback", this.discountPriceField);
        this.form.add(this.discountPriceFeedback);

        this.reference = StringUtils.upperCase(RandomStringUtils.randomAlphabetic(6));
        this.referenceField = new TextField<>("referenceField", new PropertyModel<>(this, "reference"));
//        this.referenceField.add(new UniqueRecordValidator<>("ecommerce_product", "reference"));
        this.referenceField.setRequired(true);
        this.form.add(this.referenceField);
        this.referenceFeedback = new TextFeedbackPanel("referenceFeedback", this.referenceField);
        this.form.add(this.referenceFeedback);

        this.mainImageField = new FileUploadField("mainImageField", new PropertyModel<>(this, "mainImage"));
        this.mainImageField.setRequired(true);
        this.form.add(this.mainImageField);
        this.mainImageFeedback = new TextFeedbackPanel("mainImageFeedback", this.mainImageField);
        this.form.add(this.mainImageFeedback);

        OptionSingleChoiceProvider colorOption = new OptionSingleChoiceProvider("ecommerce_color", "ecommerce_color_id", "CONCAT(value, ' -> ', reference)");
        colorOption.applyWhere("enabled", "enabled = true");
        this.colorField = new Select2SingleChoice<>("colorField", new PropertyModel<>(this, "color"), colorOption);
        this.colorField.setRequired(true);
        this.form.add(this.colorField);
        this.colorFeedback = new TextFeedbackPanel("colorFeedback", this.colorField);
        this.form.add(this.colorFeedback);

        OptionSingleChoiceProvider sizeOption = new OptionSingleChoiceProvider("ecommerce_size", "ecommerce_size_id", "CONCAT(value, ' -> ', reference)");
        sizeOption.applyWhere("enabled", "enabled = true");
        this.sizeField = new Select2SingleChoice<>("sizeField", new PropertyModel<>(this, "size"), sizeOption);
        this.sizeField.setRequired(true);
        this.form.add(this.sizeField);
        this.sizeFeedback = new TextFeedbackPanel("sizeFeedback", this.sizeField);
        this.form.add(this.sizeFeedback);

        this.mainImageHighResField = new FileUploadField("mainImageHighResField", new PropertyModel<>(this, "mainImageHighRes"));
        this.mainImageHighResField.setRequired(true);
        this.form.add(this.mainImageHighResField);
        this.mainImageHighResFeedback = new TextFeedbackPanel("mainImageHighResFeedback", this.mainImageHighResField);
        this.form.add(this.mainImageHighResFeedback);

        OptionMultipleChoiceProvider relatedOption = new OptionMultipleChoiceProvider("ecommerce_product", "ecommerce_product_id", "name");
        relatedOption.applyWhere("enabled","enabled = true");
        this.relatedProductField = new Select2MultipleChoice<>("relatedProductField", new PropertyModel<>(this, "relatedProduct"), relatedOption);
        this.form.add(this.relatedProductField);
        this.relatedProductFeedback = new TextFeedbackPanel("relatedProductFeedback", this.relatedProductField);
        this.form.add(this.relatedProductFeedback);

        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.<Integer>minimum(1));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.variantImageField = new MultiFileUploadField("variantImageField", new PropertyModel<>(this, "variantImage"));
        this.variantImageField.setRequired(true);
        this.form.add(this.variantImageField);
        this.variantImageFeedback = new TextFeedbackPanel("variantImageFeedback", this.variantImageField);
        this.form.add(this.variantImageFeedback);

        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

//        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);

    }

    private void itemClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
    }

    private List<ActionItem> actions(String s, Map<String, Object> stringObjectMap) {
        return null;
    }

    private ItemPanel pppp(String s, IModel<String> stringIModel, Map<String, Object> stringObjectMap) {
        return new TextCell(Model.of((String) stringObjectMap.get("name")));
    }

    @Override
    public IModel<List<SideMenu>> buildSideMenu() {
        List<SideMenu> sideMenus = Lists.newArrayList();
        sideMenus.add(new SideMenu().buildTypeHeader("MAIN NAVIGATION"));
//        sideMenus.add(new SideMenu().buildTypeMenu(NewspaperPage.class, null, "Quotation"));
        return Model.ofList(sideMenus);
    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("Quotation").setDescription("New Quotation"));
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return null;
    }

    @Override
    public IModel<List<NavBarMenu>> buildNavBarMenu() {
        return null;
    }

    @Override
    public IModel<UserInfo> buildUserInfo() {
        return null;
    }

    @Override
    protected IModel<PageLogo> buildPageLogo() {
        PageLogo pageLogo = new PageLogo("<b>A</b>NG", "<b>Ang</b>kor");
        return Model.of(pageLogo);
    }

    @Override
    protected IModel<PageFooter> buildPageFooter() {
        PageFooter pageFooter = new PageFooter();
        pageFooter.setCompany("Angkor Offset");
        return Model.of(pageFooter);
    }

    @Override
    public IModel<Boolean> hasSearchForm() {
        return Model.of(false);
    }

    @Override
    public void onSearchClick(String searchValue) {

    }

}
