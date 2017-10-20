package com.angkorteam.fintech.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.popup.WebcamPopup;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class IndexPage extends Page {

    protected WebcamPopup webcamPopup;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.webcamPopup = new WebcamPopup("webcamPopup", null, null);
        add(this.webcamPopup);
    }

}
