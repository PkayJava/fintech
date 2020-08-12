package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.PostOfficeRequest;
import com.angkorteam.fintech.data.SingleChoiceProvider;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.meta.MOffice;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.DateTextField;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeCreatePage extends MasterPage {

    protected Form<Void> createForm;

    protected UIRow row1;

    protected UIColumn officeColumn;
    protected UIContainer officeContainer;
    protected TextField<String> officeField;
    protected String officeValue;

    protected UIRow row2;

    protected UIColumn parentOfficeColumn;
    protected UIContainer parentOfficeContainer;
    protected Select2SingleChoice parentOfficeField;
    protected SingleChoiceProvider parentOfficeProvider;
    protected Option parentOfficeValue;

    protected UIRow row3;

    protected UIColumn openedOnColumn;
    protected UIContainer openedOnContainer;
    protected DateTextField openedOnField;
    protected Date openedOnValue;

    protected UIRow row4;

    protected UIColumn externalIdColumn;
    protected UIContainer externalIdContainer;
    protected TextField<String> externalIdField;
    protected String externalIdValue;

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        this.openedOnValue = new Date();
        this.parentOfficeProvider = new SingleChoiceProvider(mOffice.getName(), mOffice.ID.getName(), mOffice.NAME.getName());
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.officeColumn = this.row1.newUIColumn("officeColumn", Size.Three_3);
        this.officeContainer = this.officeColumn.newUIContainer("officeContainer");
        this.officeField = new TextField<>("officeField", new PropertyModel<>(this, "officeValue"));
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.setRequired(true);
        this.officeField.add(StringValidator.maximumLength(100));
        this.officeField.add(new NameValidator());
        this.officeContainer.add(this.officeField);
        this.officeContainer.newFeedback("officeFeedback", this.officeField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

        this.parentOfficeColumn = this.row2.newUIColumn("parentOfficeColumn", Size.Three_3);
        this.parentOfficeContainer = this.parentOfficeColumn.newUIContainer("parentOfficeContainer");
        this.parentOfficeField = new Select2SingleChoice("parentOfficeField", new PropertyModel<>(this, "parentOfficeValue"), this.parentOfficeProvider);
        this.parentOfficeField.setLabel(Model.of("Parent Office"));
        this.parentOfficeField.setRequired(true);
        this.parentOfficeContainer.add(this.parentOfficeField);
        this.parentOfficeContainer.newFeedback("parentOfficeFeedback", this.parentOfficeField);

        this.row2.newUIColumn("lastColumn");

        this.row3 = UIRow.newUIRow("row3", this.createForm);

        this.openedOnColumn = this.row3.newUIColumn("openedOnColumn", Size.Three_3);
        this.openedOnContainer = this.openedOnColumn.newUIContainer("openedOnContainer");
        this.openedOnField = new DateTextField("openedOnField", new PropertyModel<>(this, "openedOnValue"));
        this.openedOnField.setLabel(Model.of("Opened On"));
        this.openedOnField.setRequired(true);
        this.openedOnContainer.add(this.openedOnField);
        this.openedOnContainer.newFeedback("openedOnFeedback", this.openedOnField);

        this.row3.newUIColumn("lastColumn");

        this.row4 = UIRow.newUIRow("row4", this.createForm);

        this.externalIdColumn = this.row4.newUIColumn("externalIdColumn", Size.Three_3);
        this.externalIdContainer = this.externalIdColumn.newUIContainer("externalIdContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdField.add(new ExternalIdValidator());
        this.externalIdField.add(StringValidator.maximumLength(100));
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.row4.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", OfficeTablePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        PostOfficeRequest request = new PostOfficeRequest();
        request.setName(this.officeValue);
        request.setParentId(Long.valueOf(this.parentOfficeValue.getId()));
        request.setOpeningDate(this.openedOnValue);
        request.setExternalId(this.externalIdValue);

        try {
            client.officeCreate(getSession().getIdentifier(), getSession().getToken(), request);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        setResponsePage(OfficeTablePage.class);
    }

}
