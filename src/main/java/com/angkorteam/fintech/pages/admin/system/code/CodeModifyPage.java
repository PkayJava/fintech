package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.CodeRequest;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MCode;
import com.angkorteam.fintech.meta.tenant.MCodeValue;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/code/modify")
public class CodeModifyPage extends MasterPage {

    protected String codeId;

    protected Form<Void> modifyForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> codevalueBrowseForm;
    protected QueryDataProvider codevalueBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> codevalueBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> codevalueBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.codeId = getPageParameters().get("codeId").toString();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MCode mCode = MCode.staticInitialize(dataContext);
        MCodeValue mCodeValue = MCodeValue.staticInitialize(dataContext);

        try (DataSet rows = dataContext.query().from(mCode).select(mCode.CODE_NAME).where(mCode.ID).eq(this.codeId).execute()) {
            rows.next();
            this.nameValue = (String) rows.getRow().getValue(mCode.CODE_NAME);
        }

        this.codevalueBrowseProvider = new MySQLDataProvider(mCodeValue.getName());
        this.codevalueBrowseProvider.applyWhere("codeId", mCodeValue.getName() + "." + mCodeValue.CODE_ID.getName() + " = " + this.codeId);
        this.codevalueBrowseProvider.setCountField(mCodeValue.getName() + "." + mCodeValue.ID.getName());
        this.codevalueBrowseProvider.selectField(mCodeValue.getName() + "." + mCodeValue.ID.getName(), "codevalueId");

        this.codevalueBrowseColumn = new ArrayList<>();
        this.codevalueBrowseColumn.add(Column.number(Model.of("Position"), mCodeValue.getName() + "." + mCodeValue.ORDER_POSITION.getName(), "position", this.codevalueBrowseProvider));
        this.codevalueBrowseColumn.add(Column.text(Model.of("Value"), mCodeValue.getName() + "." + mCodeValue.CODE_VALUE.getName(), "value", this.codevalueBrowseProvider));
        this.codevalueBrowseColumn.add(Column.yesno(Model.of("Active"), mCodeValue.getName() + "." + mCodeValue.ACTIVE.getName(), "active", this.codevalueBrowseProvider));
        this.codevalueBrowseColumn.add(Column.yesno(Model.of("Mandatory"), mCodeValue.getName() + "." + mCodeValue.MANDATORY.getName(), "mandatory", this.codevalueBrowseProvider));
        this.codevalueBrowseColumn.add(Column.text(Model.of("Description"), mCodeValue.getName() + "." + mCodeValue.CODE_DESCRIPTION.getName(), "description", this.codevalueBrowseProvider));
        this.codevalueBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::codevalueBrowseActionLink, this::codevalueBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.modifyForm = new Form<>("modifyForm");
        body.add(this.modifyForm);

        this.row1 = UIRow.newUIRow("row1", this.modifyForm);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Four_4);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new CodeNameValidator(Long.valueOf(this.codeId)));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", CodeBrowsePage.class);
        this.modifyForm.add(this.cancelButton);

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);
        this.createLink = new BookmarkablePageLink<>("createLink", CodeValueCreatePage.class, parameters);
        body.add(this.createLink);

        this.codevalueBrowseForm = new FilterForm<>("codevalueBrowseForm", this.codevalueBrowseProvider);
        body.add(this.codevalueBrowseForm);

        this.codevalueBrowseTable = new DataTable<>("codevalueBrowseTable", this.codevalueBrowseColumn, this.codevalueBrowseProvider, 20);
        this.codevalueBrowseForm.add(this.codevalueBrowseTable);
    }

    protected List<ActionItem> codevalueBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.WARNING));
        return actions;
    }

    protected void codevalueBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        int codevalueId = (int) model.get("codevalueId");
        if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("codeId", this.codeId);
            parameters.add("codeValueId", codevalueId);
            setResponsePage(CodeValueModifyPage.class, parameters);
        } else if ("Delete".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            client.codeValueDelete(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.codeId), codevalueId);
            target.add(this.codevalueBrowseTable);
        }
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        CodeRequest request = new CodeRequest();
        request.setName(this.nameValue);
        client.codeUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.codeId), request);

        setResponsePage(CodeBrowsePage.class);
    }

}
