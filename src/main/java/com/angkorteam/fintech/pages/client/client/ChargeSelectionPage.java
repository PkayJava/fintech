package com.angkorteam.fintech.pages.client.client;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ChargeSelectionPage extends Page {

    protected String clientId;
    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Option chargeValue;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initChargeBlock();
    }

    @Override
    protected void configureRequiredValidation() {
        this.chargeField.setRequired(true);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name", "concat(name, ' [', currency_code, ']')");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.Client.getLiteral());
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeField = new Select2SingleChoice<>("chargeField", new PropertyModel<>(this, "chargeValue"), this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);
    }

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.OFFICE_ID);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.clientDisplayName = (String) clientObject.get("display_name");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge Selection");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void okayButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        parameters.add("chargeId", this.chargeValue.getId());
        setResponsePage(ChargeCreatePage.class, parameters);
    }
}
