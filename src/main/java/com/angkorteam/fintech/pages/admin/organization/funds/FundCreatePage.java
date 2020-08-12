package com.angkorteam.fintech.pages.admin.organization.funds;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import io.github.openunirest.http.JsonNode;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FundCreatePage extends MasterPage {

    protected Form<Void> createForm;

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

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.externalIdColumn = this.row1.newUIColumn("externalIdColumn", Size.Three_3);
        this.externalIdContainer = this.externalIdColumn.newUIContainer("externalIdContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdField.add(new ExternalIdValidator());
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdContainer.newFeedback("externalIdFeedback", this.externalIdField);

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

        this.row2.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", FundBrowsePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        FundBuilder builder = new FundBuilder();
        builder.withName(this.nameValue);
        builder.withExternalId(this.externalIdValue);

        JsonNode node = FundHelper.create(getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(FundBrowsePage.class);
    }

}
