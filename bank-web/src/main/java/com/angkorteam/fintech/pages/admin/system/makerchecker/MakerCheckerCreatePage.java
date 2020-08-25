package com.angkorteam.fintech.pages.admin.system.makerchecker;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MPermission;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/makerchecker/create")
public class MakerCheckerCreatePage extends MasterPage {

    protected Form<Void> createForm;

    protected UIRow row1;

    protected UIColumn codeColumn;
    protected UIContainer codeContainer;
    protected TextField<String> codeField;
    protected String codeValue;

    protected UIRow row2;

    protected UIColumn groupingColumn;
    protected UIContainer groupingContainer;
    protected TextField<String> groupingField;
    protected String groupingValue;

    protected UIColumn entityColumn;
    protected UIContainer entityContainer;
    protected TextField<String> entityField;
    protected String entityValue;

    protected UIColumn actionColumn;
    protected UIContainer actionContainer;
    protected TextField<String> actionField;
    protected String actionValue;

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.codeColumn = this.row1.newUIColumn("codeColumn", Size.Twelve_12);
        this.codeContainer = this.codeColumn.newUIContainer("codeContainer");
        this.codeField = new TextField<>("codeField", new PropertyModel<>(this, "codeValue"));
        this.codeField.setLabel(Model.of("Code"));
        this.codeField.setRequired(true);
        this.codeField.add(new CodeValidator());
        this.codeContainer.add(this.codeField);
        this.codeContainer.newFeedback("codeFeedback", this.codeField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

        this.groupingColumn = this.row2.newUIColumn("groupingColumn", Size.Four_4);
        this.groupingContainer = this.groupingColumn.newUIContainer("groupingContainer");
        this.groupingField = new TextField<>("groupingField", new PropertyModel<>(this, "groupingValue"));
        this.groupingField.setLabel(Model.of("Description"));
        this.groupingField.setRequired(true);
        this.groupingContainer.add(this.groupingField);
        this.groupingContainer.newFeedback("groupingFeedback", this.groupingField);

        this.entityColumn = this.row2.newUIColumn("entityColumn", Size.Four_4);
        this.entityContainer = this.entityColumn.newUIContainer("entityContainer");
        this.entityField = new TextField<>("entityField", new PropertyModel<>(this, "entityValue"));
        this.entityField.setLabel(Model.of("Entity"));
        this.entityField.setRequired(true);
        this.entityContainer.add(this.entityField);
        this.entityContainer.newFeedback("entityFeedback", this.entityField);

        this.actionColumn = this.row2.newUIColumn("actionColumn", Size.Four_4);
        this.actionContainer = this.actionColumn.newUIContainer("actionContainer");
        this.actionField = new TextField<>("actionField", new PropertyModel<>(this, "actionValue"));
        this.actionField.setLabel(Model.of("Action"));
        this.actionField.setRequired(true);
        this.actionContainer.add(this.actionField);
        this.actionContainer.newFeedback("actionFeedback", this.actionField);

        this.row2.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", MakerCheckerBrowsePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        WebSession session = (WebSession) WebSession.get();
        JdbcDataContext dataContext = (JdbcDataContext) mifosDataContextManager.getDataContext(session.getIdentifier());
        MPermission mPermission = MPermission.staticInitialize(dataContext);
        dataContext.executeUpdate(callback -> {
            callback.insertInto(mPermission)
                    .value(mPermission.GROUPING, this.groupingValue)
                    .value(mPermission.CODE, this.codeValue)
                    .value(mPermission.ENTITY_NAME, this.entityValue)
                    .value(mPermission.ACTION_NAME, this.actionValue)
                    .value(mPermission.CAN_MAKER_CHECKER, 0)
                    .execute();
        });
        setResponsePage(MakerCheckerBrowsePage.class);
    }

}
