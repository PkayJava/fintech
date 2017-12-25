package com.angkorteam.fintech.pages.client.client;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.client.ClientPreviewDocument;
import com.angkorteam.fintech.widget.client.client.ClientPreviewFamilyMember;
import com.angkorteam.fintech.widget.client.client.ClientPreviewGeneral;
import com.angkorteam.fintech.widget.client.client.ClientPreviewIdentity;
import com.angkorteam.fintech.widget.client.client.ClientPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientPreviewPage extends Page {

    protected AjaxTabbedPanel<ITab> tab;

    protected String clientId;

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.clientId = parameters.get("clientId").toString();
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
