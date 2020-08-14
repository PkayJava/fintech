package com.angkorteam.fintech.pages.admin.organization.funds;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.meta.tenant.MFund;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import io.github.openunirest.http.JsonNode;
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
import org.springframework.context.ApplicationContext;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FundModifyPage extends MasterPage {

    protected long fundId;

    protected Form<Void> modifyForm;

    protected UIRow row1;

    protected UIColumn externalIdColumn;
    protected UIContainer externalIdContainer;
    protected TextField<String> externalIdField;
    protected String externalIdValue;

    protected UIRow row2;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MFund mFund = MFund.staticInitialize(dataContext);

        PageParameters parameters = getPageParameters();
        this.fundId = parameters.get("fundId").toLong();

        try (DataSet rows = dataContext.query().from(mFund).selectAll().where(mFund.ID).eq(this.fundId).execute()) {
            rows.next();
            this.nameValue = (String) rows.getRow().getValue(mFund.NAME);
            this.externalIdValue = (String) rows.getRow().getValue(mFund.EXTERNAL_ID);
        }
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.modifyForm = new Form<>("modifyForm");
        body.add(this.modifyForm);

        this.row1 = UIRow.newUIRow("row1", this.modifyForm);

        this.externalIdColumn = this.row1.newUIColumn("externalIdColumn", Size.Three_3);
        this.externalIdContainer = this.externalIdColumn.newUIContainer("externalIdContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdField.add(new ExternalIdValidator(this.fundId));
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.modifyForm);

        this.nameColumn = this.row2.newUIColumn("nameColumn", Size.Three_3);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new NameValidator(this.fundId));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row2.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", FundBrowsePage.class);
        this.modifyForm.add(this.cancelButton);
    }

    protected void updateButtonSubmit() {
        FundBuilder builder = new FundBuilder();
        builder.withId(String.valueOf(this.fundId));
        builder.withName(this.nameValue);
        builder.withExternalId(this.externalIdValue);

        JsonNode node = FundHelper.update(getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(FundBrowsePage.class);
    }

}
