package com.angkorteam.fintech.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class IndexPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

}
