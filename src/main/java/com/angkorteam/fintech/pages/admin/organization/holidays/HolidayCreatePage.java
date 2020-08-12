package com.angkorteam.fintech.pages.admin.organization.holidays;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MultipleChoiceProvider;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.HolidayBuilder;
import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.meta.MOffice;
import com.angkorteam.fintech.pages.admin.organization.funds.NameValidator;
import com.angkorteam.fintech.provider.ReschedulingTypeProvider;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.DateTextField;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import io.github.openunirest.http.JsonNode;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayCreatePage extends MasterPage {

    protected Form<Void> createForm;

    protected UIRow row1;

    protected UIColumn officeColumn;
    protected UIContainer officeContainer;
    protected Select2MultipleChoice officeField;
    protected MultipleChoiceProvider officeProvider;
    protected List<Option> officeValue;

    protected UIRow row2;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected UIColumn descriptionColumn;
    protected UIContainer descriptionContainer;
    protected TextField<String> descriptionField;
    protected String descriptionValue;

    protected UIRow row3;

    protected UIColumn fromDateColumn;
    protected UIContainer fromDateContainer;
    protected DateTextField fromDateField;
    protected Date fromDateValue;

    protected UIColumn toDateColumn;
    protected UIContainer toDateContainer;
    protected DateTextField toDateField;
    protected Date toDateValue;

    protected UIRow row4;

    protected UIColumn reschedulingTypeColumn;
    protected UIContainer reschedulingTypeContainer;
    protected Select2SingleChoice reschedulingTypeField;
    protected ReschedulingTypeProvider reschedulingTypeProvider;
    protected Option reschedulingTypeValue;

    protected UIColumn repaymentsRescheduledToColumn;
    protected UIContainer repaymentsRescheduledToContainer;
    protected DateTextField repaymentsRescheduledToField;
    protected Date repaymentsRescheduledToValue;

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.reschedulingTypeProvider = new ReschedulingTypeProvider();
        this.officeProvider = new MultipleChoiceProvider(mOffice.getName(), mOffice.ID.getName(), mOffice.NAME.getName());
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.officeColumn = this.row1.newUIColumn("officeColumn", Size.Six_6);
        this.officeContainer = this.officeColumn.newUIContainer("officeContainer");
        this.officeField = new Select2MultipleChoice("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.setRequired(true);
        this.officeContainer.add(this.officeField);
        this.officeContainer.newFeedback("officeFeedback", this.officeField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

        this.nameColumn = this.row2.newUIColumn("nameColumn", Size.Three_3);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new NameValidator());
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.descriptionColumn = this.row2.newUIColumn("descriptionColumn", Size.Three_3);
        this.descriptionContainer = this.descriptionColumn.newUIContainer("descriptionContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);
        this.descriptionContainer.add(this.descriptionField);
        this.descriptionContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row2.newUIColumn("lastColumn");

        this.row3 = UIRow.newUIRow("row3", this.createForm);

        this.fromDateColumn = this.row3.newUIColumn("fromDateColumn", Size.Three_3);
        this.fromDateContainer = this.fromDateColumn.newUIContainer("fromDateContainer");
        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateField.setLabel(Model.of("From Date"));
        this.fromDateField.setRequired(true);
        this.fromDateContainer.add(this.fromDateField);
        this.fromDateContainer.newFeedback("fromDateFeedback", this.fromDateField);

        this.toDateColumn = this.row3.newUIColumn("toDateColumn", Size.Three_3);
        this.toDateContainer = this.toDateColumn.newUIContainer("toDateContainer");
        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.toDateField.setLabel(Model.of("To Date"));
        this.toDateField.setRequired(true);
        this.toDateContainer.add(this.toDateField);
        this.toDateContainer.newFeedback("toDateFeedback", this.toDateField);

        this.row3.newUIColumn("lastColumn");

        this.row4 = UIRow.newUIRow("row4", this.createForm);

        this.reschedulingTypeColumn = this.row4.newUIColumn("reschedulingTypeColumn", Size.Three_3);
        this.reschedulingTypeContainer = this.reschedulingTypeColumn.newUIContainer("reschedulingTypeContainer");
        this.reschedulingTypeField = new Select2SingleChoice("reschedulingTypeField", new PropertyModel<>(this, "reschedulingTypeValue"), this.reschedulingTypeProvider);
        this.reschedulingTypeField.setLabel(Model.of("Repayment Scheduling Type"));
        this.reschedulingTypeField.setRequired(true);
        this.reschedulingTypeField.add(OnChangeAjaxBehavior.onChange(this::reschedulingTypeFieldUpdate));
        this.reschedulingTypeContainer.add(this.reschedulingTypeField);
        this.reschedulingTypeContainer.newFeedback("reschedulingTypeFeedback", this.reschedulingTypeField);

        this.repaymentsRescheduledToColumn = this.row4.newUIColumn("repaymentsRescheduledToColumn", Size.Three_3);
        this.repaymentsRescheduledToContainer = this.repaymentsRescheduledToColumn.newUIContainer("repaymentsRescheduledToContainer");
        this.repaymentsRescheduledToField = new DateTextField("repaymentsRescheduledToField", new PropertyModel<>(this, "repaymentsRescheduledToValue"));
        this.repaymentsRescheduledToField.setLabel(Model.of("Repayment Scheduled To"));
        this.repaymentsRescheduledToField.setRequired(true);
        this.repaymentsRescheduledToContainer.add(this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToContainer.newFeedback("repaymentsRescheduledToFeedback", this.repaymentsRescheduledToField);

        this.row4.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", HolidayBrowsePage.class);
        this.createForm.add(this.cancelButton);

        reschedulingTypeFieldUpdate(null);
    }

    protected boolean reschedulingTypeFieldUpdate(AjaxRequestTarget target) {
        ReschedulingType reschedulingType = null;
        if (this.reschedulingTypeValue != null) {
            reschedulingType = ReschedulingType.valueOf(reschedulingTypeValue.getId());
        }
        boolean visible = reschedulingType == ReschedulingType.SpecifiedDate;
        this.repaymentsRescheduledToContainer.setVisible(visible);
        if (target != null) {
            target.add(this.repaymentsRescheduledToColumn);
        }
        return false;
    }

    protected void createButtonSubmit() {
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

        if (reschedulingType == ReschedulingType.SpecifiedDate) {
            builder.withRepaymentsRescheduledTo(this.repaymentsRescheduledToValue);
        }

        builder.withReschedulingType(reschedulingType);
        builder.withDescription(this.descriptionValue);
        builder.withFromDate(this.fromDateValue);
        builder.withToDate(this.toDateValue);
        builder.withName(this.nameValue);

        JsonNode node = HolidayHelper.create(getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(HolidayBrowsePage.class);
    }

}
