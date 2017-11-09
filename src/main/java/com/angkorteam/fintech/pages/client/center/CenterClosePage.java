package com.angkorteam.fintech.pages.client.center;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterClosePage extends DeprecatedPage {

}
