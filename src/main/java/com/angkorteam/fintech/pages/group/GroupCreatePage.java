package com.angkorteam.fintech.pages.group;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GroupBuilder;
import com.angkorteam.fintech.helper.GroupHelper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupCreatePage extends Page {

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock parentBlock;
    protected WebMarkupContainer parentIContainer;
    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;
    protected TextFeedbackPanel parentFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", GroupBrowsePage.class);
        this.form.add(this.closeLink);

        initExternalIdBlock();

        initNameBlock();

        initParentBlock();

        initOfficeBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initExternalIdBlock() {
        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", WebMarkupBlock.Size.Twelve_12);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setRequired(true);
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", WebMarkupBlock.Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initParentBlock() {
        this.parentBlock = new WebMarkupBlock("parentBlock", WebMarkupBlock.Size.Twelve_12);
        this.form.add(this.parentBlock);
        this.parentIContainer = new WebMarkupContainer("parentIContainer");
        this.parentBlock.add(this.parentIContainer);
        this.parentProvider = new SingleChoiceProvider("m_group", "id", "display_name");
        this.parentField = new Select2SingleChoice<>("parentField", 0, new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setRequired(true);
        this.parentIContainer.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.parentIContainer.add(this.parentFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", WebMarkupBlock.Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        GroupBuilder builder = new GroupBuilder();
        builder.withName(this.nameValue);
        if (this.parentValue != null) {
            builder.withParentId(this.parentValue.getId());
        }
        builder.withActive(false);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withExternalId(this.externalIdValue);

        JsonNode node = null;
        try {
            node = GroupHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(GroupBrowsePage.class);
    }
}
