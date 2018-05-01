package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.Webcam;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class WebcamPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected UIBlock row1Block1;

    protected WebMarkupContainer webcamPreview;

    protected UIBlock row1Block2;

    protected WebMarkupContainer snapPreview;

    protected HiddenField<String> snapDataField;
    protected PropertyModel<String> snapDataValue;
    protected TextFeedbackPanel snapDataFeedback;

    protected WebMarkupContainer takeButton;

    public WebcamPopup(String name, Map<String, Object> model) {
        super(name, model);
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
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
        this.webcamPreview = new WebMarkupContainer("webcamPreview");
        this.webcamPreview.setOutputMarkupId(true);
        this.row1Block1.add(this.webcamPreview);

        this.row1Block2 = this.row1.newUIBlock("row1Block2", Size.Six_6);

        this.snapPreview = new WebMarkupContainer("snapPreview");
        this.snapPreview.setOutputMarkupId(true);
        this.row1Block2.add(this.snapPreview);

        this.snapDataValue = new PropertyModel<>(this.model, "itemSnapDataValue");
        this.snapDataField = new HiddenField<>("snapDataField", this.snapDataValue);
        this.snapDataField.setOutputMarkupId(true);
        this.row1Block2.add(this.snapDataField);
        this.snapDataFeedback = new TextFeedbackPanel("snapDataFeedback", this.snapDataField);
        this.row1Block2.add(this.snapDataFeedback);

        this.takeButton = new WebMarkupContainer("takeButton");
        this.takeButton.setOutputMarkupId(true);
        this.form.add(this.takeButton);
    }

    @Override
    protected void configureMetaData() {
        this.snapDataField.setRequired(true);
        this.snapDataField.setLabel(Model.of("Picture"));
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        if (this.window != null) {
            this.window.setSignalId(ajaxButton.getId());
            this.window.close(target);
        }
        return true;
    }
}
