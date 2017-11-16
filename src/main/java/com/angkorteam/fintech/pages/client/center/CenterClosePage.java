package com.angkorteam.fintech.pages.client.center;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterClosePage extends Page {

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
