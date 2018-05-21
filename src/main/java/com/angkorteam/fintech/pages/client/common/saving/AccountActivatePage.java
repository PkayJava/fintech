package com.angkorteam.fintech.pages.client.common.saving;

import java.util.Date;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.saving.ActivateBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountActivatePage extends Page {

    protected ClientEnum client;

    protected String clientId;

    protected String savingId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock activatedOnBlock;
    protected UIContainer activatedOnIContainer;
    protected DateTextField activatedOnField;
    protected Date activatedOnValue;

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

        this.activatedOnBlock = this.row1.newUIBlock("activatedOnBlock", Size.Six_6);
        this.activatedOnIContainer = this.activatedOnBlock.newUIContainer("activatedOnIContainer");
        this.activatedOnField = new DateTextField("activatedOnField", new PropertyModel<>(this, "activatedOnValue"));
        this.activatedOnIContainer.add(this.activatedOnField);
        this.activatedOnIContainer.newFeedback("activatedOnFeedback", this.activatedOnField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.activatedOnField.setLabel(Model.of("Activated On"));
        this.activatedOnField.setRequired(true);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();

        this.savingId = getPageParameters().get("savingId").toString();
        this.activatedOnValue = DateTime.now().toDate();
    }

    protected void saveButtonSubmit(Button button) {
        ActivateBuilder builder = new ActivateBuilder();
        builder.withId(this.savingId);
        builder.withActivatedOnDate(this.activatedOnValue);

        JsonNode node = ClientHelper.activateSavingAccount((Session) getSession(), builder.build());

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
