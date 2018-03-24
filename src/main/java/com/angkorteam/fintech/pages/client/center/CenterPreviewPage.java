package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.center.CenterPreviewGeneral;
import com.angkorteam.fintech.widget.client.center.CenterPreviewNote;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterPreviewPage extends Page {

    protected AjaxTabbedPanel<ITab> tab;

    protected String centerId;
    protected String centerDisplayName;

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.centerId = parameters.get("centerId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MGroup.NAME);
        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.centerDisplayName = (String) centerObject.get("display_name");
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
            breadcrumb.setLabel("Centers");
            breadcrumb.setPage(CenterBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.centerDisplayName);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterPreviewGeneral(this), new CenterPreviewNote(this)));
        add(this.tab);
    }

    @Override
    protected void configureMetaData() {

    }

}
