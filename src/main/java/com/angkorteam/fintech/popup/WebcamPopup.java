package com.angkorteam.fintech.popup;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class WebcamPopup extends Panel {

    protected ModalWindow window;
    protected Object model;

    protected HiddenField<String> snapData;
    
    protected WebMarkupContainer snapPreivew;

    protected WebMarkupContainer webcamPreview;

    public WebcamPopup(String id, ModalWindow window, Object model) {
        super(id);
        this.model = model;
        this.window = window;
        
        this.webcamPreview = new WebMarkupContainer("webcamPreview");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // https://demo.openmf.org/fineract-provider/api/v1/clients/6/documents/84/attachment?tenantIdentifier=default
    }
}
