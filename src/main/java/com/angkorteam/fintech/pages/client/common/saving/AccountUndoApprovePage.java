package com.angkorteam.fintech.pages.client.common.saving;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.saving.UndoApproveBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountUndoApprovePage extends Page {

    protected ClientEnum client;

    protected String clientId;

    protected String savingId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock noteBlock;
    protected UIContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;

    protected UIBlock row1Block1;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        }

        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.noteBlock = this.row1.newUIBlock("noteBlock", Size.Six_6);
        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteIContainer.add(this.noteField);
        this.noteIContainer.newFeedback("noteFeedback", this.noteField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.noteField.setLabel(Model.of("Note"));
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();

        this.savingId = getPageParameters().get("savingId").toString();
    }

    protected void saveButtonSubmit(Button button) {
        UndoApproveBuilder builder = new UndoApproveBuilder();
        builder.withId(this.savingId);
        builder.withNote(this.noteValue);

        JsonNode node = ClientHelper.undoApproveSavingAccount((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            setResponsePage(GroupPreviewPage.class, parameters);
        }
    }

}
