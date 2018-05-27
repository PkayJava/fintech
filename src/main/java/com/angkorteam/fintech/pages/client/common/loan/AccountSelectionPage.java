package com.angkorteam.fintech.pages.client.common.loan;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MProductLoan;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountSelectionPage extends Page {

    protected ClientEnum client;

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock loanBlock;
    protected UIContainer loanIContainer;
    protected SingleChoiceProvider loanProvider;
    protected Option loanValue;
    protected Select2SingleChoice<Option> loanField;

    protected UIBlock row1Block1;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        }
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.loanBlock = this.row1.newUIBlock("loanBlock", Size.Six_6);
        this.loanIContainer = this.loanBlock.newUIContainer("loanIContainer");
        this.loanField = new Select2SingleChoice<>("loanField", new PropertyModel<>(this, "loanValue"), this.loanProvider);
        this.loanIContainer.add(this.loanField);
        this.loanIContainer.newFeedback("loanFeedback", this.loanField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.loanField.setLabel(Model.of("Product"));
        this.loanField.add(new OnChangeAjaxBehavior());
        this.loanField.setRequired(true);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.loanProvider = new SingleChoiceProvider(MProductLoan.NAME, MProductLoan.Field.ID, MProductLoan.Field.NAME);
        this.client = ClientEnum.valueOf(parameters.get("client").toString());
        this.clientId = parameters.get("clientId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group || this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.clientId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) groupObject.get("display_name");
        }
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            if (this.client == ClientEnum.Client) {
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Loan Selection");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void okayButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanValue.getId());
        parameters.add("clientId", this.clientId);
        setResponsePage(AccountCreatePage.class, parameters);
    }

}
