package com.angkorteam.fintech.pages.admin;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.meta.tenant.MAppUser;
import com.angkorteam.fintech.meta.tenant.MOffice;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.Column;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.Expression;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/users/browse")
public class UserBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn firstNameColumn;
    protected UIContainer firstNameContainer;
    protected TextField<String> firstNameField;
    protected String firstNameValue;

    protected UIColumn lastNameColumn;
    protected UIContainer lastNameContainer;
    protected TextField<String> lastNameField;
    protected String lastNameValue;

    protected UIColumn emailColumn;
    protected UIContainer emailContainer;
    protected TextField<String> emailField;
    protected String emailValue;

    protected UIColumn officeColumn;
    protected UIContainer officeContainer;
    protected TextField<String> officeField;
    protected String officeValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> userBrowseForm;
    protected QueryDataProvider userBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> userBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> userBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MAppUser mAppUser = MAppUser.staticInitialize(dataContext);
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.userBrowseProvider = new MySQLDataProvider(mAppUser.getName());
        this.userBrowseProvider.applyJoin("m_office", "LEFT JOIN " + mOffice.getName() + " ON " + mAppUser.getName() + "." + mAppUser.OFFICE_ID.getName() + " = " + mOffice.getName() + "." + mOffice.ID.getName());

        this.userBrowseProvider.setCountField(mAppUser.getName() + "." + mAppUser.ID.getName());
        this.userBrowseProvider.selectField(mAppUser.getName() + "." + mAppUser.ID.getName(), "userId");

        this.userBrowseColumn = new ArrayList<>();
        this.userBrowseColumn.add(Column.text(Model.of("First Name"), mAppUser.getName() + "." + mAppUser.FIRST_NAME.getName(), "firstName", this.userBrowseProvider));
        this.userBrowseColumn.add(Column.text(Model.of("Last Name"), mAppUser.getName() + "." + mAppUser.LAST_NAME.getName(), "lastName", this.userBrowseProvider));
        this.userBrowseColumn.add(Column.text(Model.of("Email"), mAppUser.getName() + "." + mAppUser.EMAIL.getName(), "email", this.userBrowseProvider));
        this.userBrowseColumn.add(Column.text(Model.of("Office"), mOffice.getName() + "." + mOffice.NAME.getName(), "office", this.userBrowseProvider));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.filterForm = new Form<>("filterForm");
        body.add(this.filterForm);

        this.row1 = UIRow.newUIRow("row1", this.filterForm);

        this.firstNameColumn = this.row1.newUIColumn("firstNameColumn", Size.Three_3);
        this.firstNameContainer = this.firstNameColumn.newUIContainer("firstNameContainer");
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameContainer.add(this.firstNameField);
        this.firstNameContainer.newFeedback("firstNameFeedback", this.firstNameField);

        this.lastNameColumn = this.row1.newUIColumn("lastNameColumn", Size.Three_3);
        this.lastNameContainer = this.lastNameColumn.newUIContainer("lastNameContainer");
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameContainer.add(this.lastNameField);
        this.lastNameContainer.newFeedback("lastNameFeedback", this.lastNameField);

        this.emailColumn = this.row1.newUIColumn("emailColumn", Size.Three_3);
        this.emailContainer = this.emailColumn.newUIContainer("emailContainer");
        this.emailField = new TextField<>("emailField", new PropertyModel<>(this, "emailValue"));
        this.emailField.setLabel(Model.of("Email"));
        this.emailContainer.add(this.emailField);
        this.emailContainer.newFeedback("emailFeedback", this.emailField);

        this.officeColumn = this.row1.newUIColumn("officeColumn", Size.Three_3);
        this.officeContainer = this.officeColumn.newUIContainer("officeContainer");
        this.officeField = new TextField<>("officeField", new PropertyModel<>(this, "officeValue"));
        this.officeField.setLabel(Model.of("Office"));
        this.officeContainer.add(this.officeField);
        this.officeContainer.newFeedback("officeFeedback", this.officeField);

        this.row1.newUIColumn("lastColumn");

        this.searchButton = new Button("searchButton") {
            @Override
            public void onSubmit() {
                searchButtonSubmit();
            }
        };
        this.filterForm.add(this.searchButton);

        this.clearButton = new BookmarkablePageLink<>("clearButton", UserBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", UserBrowsePage.class);
        body.add(this.createLink);

        this.userBrowseForm = new FilterForm<>("userBrowseForm", this.userBrowseProvider);
        body.add(this.userBrowseForm);

        this.userBrowseTable = new DataTable<>("userBrowseTable", this.userBrowseColumn, this.userBrowseProvider, 20);
        this.userBrowseForm.add(this.userBrowseTable);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MAppUser mAppUser = MAppUser.staticInitialize(dataContext);
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.userBrowseProvider.removeWhere("firstName");
        this.userBrowseProvider.removeWhere("lastName");
        this.userBrowseProvider.removeWhere("email");
        this.userBrowseProvider.removeWhere("office");
        if (this.firstNameValue != null && !"".equals(this.firstNameValue)) {
            this.userBrowseProvider.applyWhere("firstName", mAppUser.getName() + "." + mAppUser.FIRST_NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.firstNameValue) + "%'");
        }
        if (this.lastNameValue != null && !"".equals(this.lastNameValue)) {
            this.userBrowseProvider.applyWhere("lastName", mAppUser.getName() + "." + mAppUser.LAST_NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.lastNameValue) + "%'");
        }
        if (this.emailValue != null && !"".equals(this.emailValue)) {
            this.userBrowseProvider.applyWhere("email", mAppUser.getName() + "." + mAppUser.EMAIL.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.emailValue) + "%'");
        }
        if (this.officeValue != null && !"".equals(this.officeValue)) {
            this.userBrowseProvider.applyWhere("office", mOffice.getName() + "." + mOffice.NAME.getName() + " = '" + StringEscapeUtils.escapeSql(this.officeValue) + "%'");
        }
    }

}

