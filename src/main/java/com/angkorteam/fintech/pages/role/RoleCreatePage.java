package com.angkorteam.fintech.pages.role;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.RoleBuilder;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RoleCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Role");
            breadcrumb.setPage(RoleBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Role Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", RoleBrowsePage.class);
        this.form.add(this.closeLink);

        initDescriptionBlock();

        initNameBlock();
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Twelve_12);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        RoleBuilder builder = new RoleBuilder();
        builder.withName(this.nameValue);
        builder.withDescription(this.descriptionValue);

        JsonNode node = RoleHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(RoleBrowsePage.class);
    }

}
