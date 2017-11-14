package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.Webcam;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class WebcamPopup extends PopupPanel {

    protected ModalWindow window;
    protected Object model;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupContainer webcamPreview;

    protected WebMarkupContainer snapPreview;

    protected HiddenField<String> snapDataField;
    protected PropertyModel<String> snapDataValue;
    protected TextFeedbackPanel snapDataFeedback;

    protected WebMarkupContainer takeButton;

    public WebcamPopup(String name, ModalWindow window, Object model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String js = "webcam/jpeg_camera_with_dependencies.min.js";
        response.render(JavaScriptHeaderItem.forReference(ReferenceUtilities.CACHES.computeIfAbsent(js, k -> new PackageResourceReference(Webcam.class, js))));
        String camera = this.webcamPreview.getMarkupId();
        String varCamera = "camera_" + camera;
        String takeButton = this.takeButton.getMarkupId();
        String snapPreview = this.snapPreview.getMarkupId();
        String snapDataField = this.snapDataField.getMarkupId();
        StringBuffer jsFunction = new StringBuffer("");
        jsFunction.append("var " + varCamera + " = new JpegCamera('#" + camera + "', { shutter_ogg_url: 'shutter.ogg', shutter_mp3_url: 'shutter.mp3', swf_url: 'jpeg_camera.swf'});");
        // jsFunction.append("var " + varCamera + " = new JpegCamera('#" + camera +
        // "');");
        jsFunction.append("$('#" + takeButton + "').click(");
        jsFunction.append("function(){");
        jsFunction.append("var snapshot = " + varCamera + ".capture();");
        jsFunction.append("snapshot.get_canvas(");
        jsFunction.append("function(element){");
        jsFunction.append("$('#" + snapPreview + "').attr('src', element.toDataURL());");
        jsFunction.append("$('#" + snapDataField + "').attr('value', element.toDataURL());");
        jsFunction.append("}");
        jsFunction.append(");");
        jsFunction.append("}");
        jsFunction.append(");");
        response.render(OnDomReadyHeaderItem.forScript(jsFunction.toString()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.webcamPreview = new WebMarkupContainer("webcamPreview");
        this.webcamPreview.setOutputMarkupId(true);
        this.form.add(this.webcamPreview);

        this.snapPreview = new WebMarkupContainer("snapPreview");
        this.snapPreview.setOutputMarkupId(true);
        this.form.add(this.snapPreview);

        this.snapDataValue = new PropertyModel<>(this.model, "itemSnapDataValue");
        this.snapDataField = new HiddenField<>("snapDataField", this.snapDataValue);
        this.snapDataField.setOutputMarkupId(true);
        this.snapDataField.setRequired(true);
        this.snapDataField.setLabel(Model.of("Picture"));
        this.form.add(this.snapDataField);
        this.snapDataFeedback = new TextFeedbackPanel("snapDataFeedback", this.snapDataField);
        this.form.add(this.snapDataFeedback);

        this.takeButton = new WebMarkupContainer("takeButton");
        this.takeButton.setOutputMarkupId(true);
        this.form.add(this.takeButton);
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        if (this.window != null) {
            this.window.setSignalId(ajaxButton.getId());
            this.window.close(target);
        }
        return true;
    }
}
