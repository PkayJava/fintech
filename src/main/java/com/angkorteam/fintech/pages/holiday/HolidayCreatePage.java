package com.angkorteam.fintech.pages.holiday;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.HolidayBuilder;
import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.ReschedulingTypeProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayCreatePage extends Page {

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected MultipleChoiceProvider officeProvider;
    protected List<Option> officeValue;
    protected Select2MultipleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock fromDateBlock;
    protected WebMarkupContainer fromDateIContainer;
    protected Date fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected WebMarkupBlock toDateBlock;
    protected WebMarkupContainer toDateIContainer;
    protected Date toDateValue;
    protected DateTextField toDateField;
    protected TextFeedbackPanel toDateFeedback;

    protected WebMarkupBlock repaymentsRescheduledToBlock;
    protected WebMarkupContainer repaymentsRescheduledToIContainer;
    protected Date repaymentsRescheduledToValue;
    protected DateTextField repaymentsRescheduledToField;
    protected TextFeedbackPanel repaymentsRescheduledToFeedback;

    protected WebMarkupBlock reschedulingTypeBlock;
    protected WebMarkupContainer reschedulingTypeIContainer;
    protected ReschedulingTypeProvider reschedulingTypeProvider;
    protected Option reschedulingTypeValue;
    protected Select2SingleChoice<Option> reschedulingTypeField;
    protected TextFeedbackPanel reschedulingTypeFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", HolidayBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initDescriptionBlock();

        initFromDateBlock();

        initToDateBlock();

        initReschedulingTypeBlock();

        initRepaymentsRescheduledToBlock();

        initOfficeBlock();
    }

    @Override
    protected void configureMetaData() {
        reschedulingTypeFieldUpdate(null);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new MultipleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2MultipleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initRepaymentsRescheduledToBlock() {
        this.repaymentsRescheduledToBlock = new WebMarkupBlock("repaymentsRescheduledToBlock", Size.Six_6);
        this.form.add(this.repaymentsRescheduledToBlock);
        this.repaymentsRescheduledToIContainer = new WebMarkupContainer("repaymentsRescheduledToIContainer");
        this.repaymentsRescheduledToBlock.add(this.repaymentsRescheduledToIContainer);
        this.repaymentsRescheduledToBlock.add(this.repaymentsRescheduledToIContainer);
        this.repaymentsRescheduledToField = new DateTextField("repaymentsRescheduledToField", new PropertyModel<>(this, "repaymentsRescheduledToValue"));
        this.repaymentsRescheduledToField.setRequired(true);
        this.repaymentsRescheduledToIContainer.add(this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToFeedback = new TextFeedbackPanel("repaymentsRescheduledToFeedback", this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToIContainer.add(this.repaymentsRescheduledToFeedback);
    }

    protected void initReschedulingTypeBlock() {
        this.reschedulingTypeBlock = new WebMarkupBlock("reschedulingTypeBlock", Size.Six_6);
        this.form.add(this.reschedulingTypeBlock);
        this.reschedulingTypeIContainer = new WebMarkupContainer("reschedulingTypeIContainer");
        this.reschedulingTypeBlock.add(this.reschedulingTypeIContainer);
        this.reschedulingTypeProvider = new ReschedulingTypeProvider();
        this.reschedulingTypeField = new Select2SingleChoice<>("reschedulingTypeField", new PropertyModel<>(this, "reschedulingTypeValue"), this.reschedulingTypeProvider);
        this.reschedulingTypeField.setRequired(true);
        this.reschedulingTypeField.add(new OnChangeAjaxBehavior(this::reschedulingTypeFieldUpdate));
        this.reschedulingTypeIContainer.add(this.reschedulingTypeField);
        this.reschedulingTypeFeedback = new TextFeedbackPanel("reschedulingTypeFeedback", this.reschedulingTypeField);
        this.reschedulingTypeIContainer.add(this.reschedulingTypeFeedback);
    }

    protected void initToDateBlock() {
        this.toDateBlock = new WebMarkupBlock("toDateBlock", Size.Six_6);
        this.form.add(this.toDateBlock);
        this.toDateIContainer = new WebMarkupContainer("toDateIContainer");
        this.toDateBlock.add(this.toDateIContainer);
        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.toDateField.setRequired(true);
        this.toDateIContainer.add(this.toDateField);
        this.toDateFeedback = new TextFeedbackPanel("toDateFeedback", this.toDateField);
        this.toDateIContainer.add(this.toDateFeedback);
    }

    protected void initFromDateBlock() {
        this.fromDateBlock = new WebMarkupBlock("fromDateBlock", Size.Six_6);
        this.form.add(this.fromDateBlock);
        this.fromDateIContainer = new WebMarkupContainer("fromDateIContainer");
        this.fromDateBlock.add(this.fromDateIContainer);
        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateField.setRequired(true);
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.fromDateIContainer.add(this.fromDateFeedback);
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Six_6);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected boolean reschedulingTypeFieldUpdate(AjaxRequestTarget target) {
        ReschedulingType reschedulingType = null;
        if (this.reschedulingTypeValue != null) {
            reschedulingType = ReschedulingType.valueOf(reschedulingTypeValue.getId());
        }
        boolean visible = reschedulingType != null && reschedulingType == ReschedulingType.SpecifiedDate;
        this.repaymentsRescheduledToIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.repaymentsRescheduledToBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        HolidayBuilder builder = new HolidayBuilder();
        if (this.officeValue != null) {
            for (Option office : this.officeValue) {
                builder.withOffice(office.getId());
            }
        }

        ReschedulingType reschedulingType = null;
        if (this.reschedulingTypeValue != null) {
            reschedulingType = ReschedulingType.valueOf(reschedulingTypeValue.getId());
        }

        if (reschedulingType != null && reschedulingType == ReschedulingType.SpecifiedDate) {
            builder.withRepaymentsRescheduledTo(this.repaymentsRescheduledToValue);
        }

        builder.withReschedulingType(reschedulingType);
        builder.withDescription(this.descriptionValue);
        builder.withFromDate(this.fromDateValue);
        builder.withToDate(this.toDateValue);
        builder.withName(this.nameValue);

        JsonNode node = HolidayHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(HolidayBrowsePage.class);
    }

}
