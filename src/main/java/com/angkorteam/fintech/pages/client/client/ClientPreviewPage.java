package com.angkorteam.fintech.pages.client.client;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.ClientPreviewDocument;
import com.angkorteam.fintech.widget.client.ClientPreviewFamilyMember;
import com.angkorteam.fintech.widget.client.ClientPreviewGeneral;
import com.angkorteam.fintech.widget.client.ClientPreviewIdentity;
import com.angkorteam.fintech.widget.client.ClientPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientPreviewPage extends Page {

    private AjaxTabbedPanel<ITab> tab;

    protected String clientId;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();

        this.clientId = parameters.get("clientId").toString();

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new ClientPreviewGeneral(this), new ClientPreviewFamilyMember(this), new ClientPreviewIdentity(this), new ClientPreviewDocument(this), new ClientPreviewNote(this)));

        add(this.tab);
    }

}
