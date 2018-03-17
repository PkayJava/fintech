package com.angkorteam.fintech.pages.client.client;

import java.util.Arrays;
import java.util.List;

import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.client.ClientPreviewDocument;
import com.angkorteam.fintech.widget.client.client.ClientPreviewFamilyMember;
import com.angkorteam.fintech.widget.client.client.ClientPreviewGeneral;
import com.angkorteam.fintech.widget.client.client.ClientPreviewIdentity;
import com.angkorteam.fintech.widget.client.client.ClientPreviewNote;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientPreviewPage extends Page {

    protected AjaxTabbedPanel<ITab> tab;

    protected String clientId;
    protected String clientDisplayName;

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
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.clientDisplayName);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.clientId = parameters.get("clientId").toString();
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        this.clientDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new ClientPreviewGeneral(this), new ClientPreviewFamilyMember(this), new ClientPreviewIdentity(this), new ClientPreviewDocument(this), new ClientPreviewNote(this)));
        add(this.tab);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
