package com.angkorteam.fintech.pages.client.center;

import java.util.Date;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.acount.ActivateBuilder;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountActivatePage extends Page {

    private String centerId;
    private String accountId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer activatedOnBlock;
    protected WebMarkupContainer activatedOnContainer;
    protected Date activatedOnValue;
    protected DateTextField activatedOnField;
    protected TextFeedbackPanel activatedOnFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.activatedOnBlock = new WebMarkupContainer("activatedOnBlock");
        this.activatedOnBlock.setOutputMarkupId(true);
        this.form.add(this.activatedOnBlock);
        this.activatedOnContainer = new WebMarkupContainer("activatedOnContainer");
        this.activatedOnBlock.add(this.activatedOnContainer);
        this.activatedOnField = new DateTextField("activatedOnField", new PropertyModel<>(this, "activatedOnValue"));
        this.activatedOnField.setLabel(Model.of("Activated On"));
        this.activatedOnContainer.add(this.activatedOnField);
        this.activatedOnFeedback = new TextFeedbackPanel("activatedOnFeedback", this.activatedOnField);
        this.activatedOnContainer.add(this.activatedOnFeedback);
    }

    protected void initData() {
        this.centerId = getPageParameters().get("centerId").toString();
        this.accountId = getPageParameters().get("accountId").toString();
        this.activatedOnValue = DateTime.now().toDate();
    }

    protected void saveButtonSubmit(Button button) {
        ActivateBuilder builder = new ActivateBuilder();
        builder.withId(this.accountId);
        builder.withActivatedOnDate(this.activatedOnValue);

        JsonNode node = null;
        try {
            node = ClientHelper.activateSavingAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        setResponsePage(CenterPreviewPage.class, parameters);
    }

}
