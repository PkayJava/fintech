package com.angkorteam.fintech.pages.client.client;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientPreviewPage extends Page {

}
