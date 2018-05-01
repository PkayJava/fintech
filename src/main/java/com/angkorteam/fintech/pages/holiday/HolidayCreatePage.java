package com.angkorteam.fintech.pages.holiday;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.ReschedulingTypeProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected MultipleChoiceProvider officeProvider;
    protected List<Option> officeValue;
    protected Select2MultipleChoice<Option> officeField;

    protected UIRow row2;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock descriptionBlock;
    protected UIContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;

    protected UIRow row3;

    protected UIBlock fromDateBlock;
    protected UIContainer fromDateIContainer;
    protected Date fromDateValue;
    protected DateTextField fromDateField;

    protected UIBlock toDateBlock;
    protected UIContainer toDateIContainer;
    protected Date toDateValue;
    protected DateTextField toDateField;

    protected UIRow row4;

    protected UIBlock repaymentsRescheduledToBlock;
    protected UIContainer repaymentsRescheduledToIContainer;
    protected Date repaymentsRescheduledToValue;
    protected DateTextField repaymentsRescheduledToField;

    protected UIBlock reschedulingTypeBlock;
    protected UIContainer reschedulingTypeIContainer;
    protected ReschedulingTypeProvider reschedulingTypeProvider;
    protected Option reschedulingTypeValue;
    protected Select2SingleChoice<Option> reschedulingTypeField;

    @Override
    protected void initData() {
        this.officeProvider = new MultipleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.reschedulingTypeProvider = new ReschedulingTypeProvider();
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Twelve_12);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2MultipleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.nameBlock = this.row2.newUIBlock("nameBlock", Size.Six_6);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.descriptionBlock = this.row2.newUIBlock("descriptionBlock", Size.Six_6);
        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.fromDateBlock = this.row3.newUIBlock("fromDateBlock", Size.Six_6);
        this.fromDateIContainer = this.fromDateBlock.newUIContainer("fromDateIContainer");
        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateIContainer.newFeedback("fromDateFeedback", this.fromDateField);

        this.toDateBlock = this.row3.newUIBlock("toDateBlock", Size.Six_6);
        this.toDateIContainer = this.toDateBlock.newUIContainer("toDateIContainer");
        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.toDateIContainer.add(this.toDateField);
        this.toDateIContainer.newFeedback("toDateFeedback", this.toDateField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.reschedulingTypeBlock = this.row4.newUIBlock("reschedulingTypeBlock", Size.Six_6);
        this.reschedulingTypeIContainer = this.reschedulingTypeBlock.newUIContainer("reschedulingTypeIContainer");
        this.reschedulingTypeField = new Select2SingleChoice<>("reschedulingTypeField", new PropertyModel<>(this, "reschedulingTypeValue"), this.reschedulingTypeProvider);
        this.reschedulingTypeIContainer.add(this.reschedulingTypeField);
        this.reschedulingTypeIContainer.newFeedback("reschedulingTypeFeedback", this.reschedulingTypeField);

        this.repaymentsRescheduledToBlock = this.row4.newUIBlock("repaymentsRescheduledToBlock", Size.Six_6);
        this.repaymentsRescheduledToIContainer = this.repaymentsRescheduledToBlock.newUIContainer("repaymentsRescheduledToIContainer");
        this.repaymentsRescheduledToField = new DateTextField("repaymentsRescheduledToField", new PropertyModel<>(this, "repaymentsRescheduledToValue"));
        this.repaymentsRescheduledToIContainer.add(this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToIContainer.newFeedback("repaymentsRescheduledToFeedback", this.repaymentsRescheduledToField);
    }

    @Override
    protected void configureMetaData() {
        this.repaymentsRescheduledToField.setRequired(true);
        this.reschedulingTypeField.setRequired(true);
        this.reschedulingTypeField.add(new OnChangeAjaxBehavior(this::reschedulingTypeFieldUpdate));
        this.toDateField.setRequired(true);
        this.fromDateField.setRequired(true);
        this.descriptionField.setRequired(true);
        this.nameField.setRequired(true);
        this.officeField.setRequired(true);
        reschedulingTypeFieldUpdate(null);
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
