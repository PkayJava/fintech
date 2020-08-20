package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.PutOfficeRequest;
import com.angkorteam.fintech.data.OptionMapper;
import com.angkorteam.fintech.data.SingleChoiceProvider;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MOffice;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.DateTextField;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/organization/offices/modify")
public class OfficeModifyPage extends MasterPage {

    protected Long officeId;

    protected Form<Void> modifyForm;

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

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifosDataSource = context.getBean(MifosDataSourceManager.class);
        MifosDataContextManager mifosDataContext = context.getBean(MifosDataContextManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifosDataSource.getDataSource(session.getIdentifier());
        DataContext dataContext = mifosDataContext.getDataContext(session.getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(dataSource);

        PageParameters parameters = getPageParameters();
        this.officeId = parameters.get("officeId").toLong();
        this.parentOfficeProvider = new SingleChoiceProvider(mOffice.getName(), mOffice.ID.getName(), mOffice.NAME.getName());

        try (DataSet rows = dataContext.query().from(mOffice).selectAll().where(mOffice.ID).eq(this.officeId).execute()) {
            rows.next();
            this.officeValue = (String) rows.getRow().getValue(mOffice.NAME);
            this.openedOnValue = (Date) rows.getRow().getValue(mOffice.OPENING_DATE);
            this.externalIdValue = (String) rows.getRow().getValue(mOffice.EXTERNAL_ID);

            SelectQuery selectQuery = new SelectQuery(mOffice.getName());
            selectQuery.addWhere(mOffice.ID.getName() + " = :" + mOffice.ID.getName(), rows.getRow().getValue(mOffice.PARENT_ID));
            selectQuery.addField(mOffice.ID.getName() + " as id");
            selectQuery.addField(mOffice.NAME.getName() + " as text");
            try {
                this.parentOfficeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), new OptionMapper());
            } catch (EmptyResultDataAccessException e) {
            }
        }
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.modifyForm = new Form<>("modifyForm");
        body.add(this.modifyForm);

        this.row1 = UIRow.newUIRow("row1", this.modifyForm);

        this.officeColumn = this.row1.newUIColumn("officeColumn", Size.Three_3);
        this.officeContainer = this.officeColumn.newUIContainer("officeContainer");
        this.officeField = new TextField<>("officeField", new PropertyModel<>(this, "officeValue"));
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.setRequired(true);
        this.officeField.add(StringValidator.maximumLength(100));
        this.officeField.add(new NameValidator(this.officeId));
        this.officeContainer.add(this.officeField);
        this.officeContainer.newFeedback("officeFeedback", this.officeField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.modifyForm);

        this.parentOfficeColumn = this.row2.newUIColumn("parentOfficeColumn", Size.Three_3);
        this.parentOfficeContainer = this.parentOfficeColumn.newUIContainer("parentOfficeContainer");
        this.parentOfficeField = new Select2SingleChoice("parentOfficeField", new PropertyModel<>(this, "parentOfficeValue"), this.parentOfficeProvider);
        this.parentOfficeField.setLabel(Model.of("Parent Office"));
        this.parentOfficeField.setRequired(true);
        this.parentOfficeContainer.add(this.parentOfficeField);
        this.parentOfficeContainer.newFeedback("parentOfficeFeedback", this.parentOfficeField);

        this.row2.newUIColumn("lastColumn");

        this.row3 = UIRow.newUIRow("row3", this.modifyForm);

        this.openedOnColumn = this.row3.newUIColumn("openedOnColumn", Size.Three_3);
        this.openedOnContainer = this.openedOnColumn.newUIContainer("openedOnContainer");
        this.openedOnField = new DateTextField("openedOnField", new PropertyModel<>(this, "openedOnValue"));
        this.openedOnField.setLabel(Model.of("Opened On"));
        this.openedOnField.setRequired(true);
        this.openedOnContainer.add(this.openedOnField);
        this.openedOnContainer.newFeedback("openedOnFeedback", this.openedOnField);

        this.row3.newUIColumn("lastColumn");

        this.row4 = UIRow.newUIRow("row4", this.modifyForm);

        this.externalIdColumn = this.row4.newUIColumn("externalIdColumn", Size.Three_3);
        this.externalIdContainer = this.externalIdColumn.newUIContainer("externalIdContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdField.add(StringValidator.maximumLength(100));
        this.externalIdField.add(new ExternalIdValidator(this.officeId));
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.row4.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", OfficeTablePage.class);
        this.modifyForm.add(this.cancelButton);
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        PutOfficeRequest request = new PutOfficeRequest();
        request.setName(this.officeValue);
        request.setParentId(Long.valueOf(this.parentOfficeValue.getId()));
        request.setOpeningDate(this.openedOnValue);
        request.setExternalId(this.externalIdValue);

        try {
            client.officeUpdate(getSession().getIdentifier(), getSession().getToken(), this.officeId, request);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        setResponsePage(OfficeTablePage.class);
    }

}
