package com.angkorteam.fintech.pages.client.client;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.lang.Bytes;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.Webcam;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientWebcamPage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button okayButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer webcamPreview;

    protected WebMarkupContainer snapPreview;

    protected HiddenField<String> snapDataField;
    protected String snapDataValue;
    protected TextFeedbackPanel snapDataFeedback;

    protected WebMarkupContainer takeButton;

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

        String shutter_ogg = RequestCycle.get().urlFor(new PackageResourceReference(Webcam.class, "webcam/shutter.ogg"), null).toString();
        String shutter_mp3 = RequestCycle.get().urlFor(new PackageResourceReference(Webcam.class, "webcam/shutter.mp3"), null).toString();
        String jpeg_camera = RequestCycle.get().urlFor(new PackageResourceReference(Webcam.class, "webcam/jpeg_camera.swf"), null).toString();

        StringBuffer jsFunction = new StringBuffer("");
        jsFunction.append("var " + varCamera + " = new JpegCamera('#" + camera + "', { shutter_ogg_url: '" + shutter_ogg + "', shutter_mp3_url: '" + shutter_mp3 + "', swf_url: '" + jpeg_camera + "'});");
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
    protected void initComponent() {
        this.form = new Form<>("form");
        this.form.setMaxSize(Bytes.megabytes(10));
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(closeLink);

        this.webcamPreview = new WebMarkupContainer("webcamPreview");
        this.webcamPreview.setOutputMarkupId(true);
        this.form.add(this.webcamPreview);

        this.snapPreview = new WebMarkupContainer("snapPreview");
        this.snapPreview.setOutputMarkupId(true);
        this.form.add(this.snapPreview);

        this.snapDataField = new HiddenField<>("snapDataField", new PropertyModel<>(this, "snapDataValue"));
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

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.clientDisplayName = (String) clientObject.get("display_name");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Webcam");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void okayButtonSubmit(Button ajaxButton) {
        JsonNode node = ClientHelper.uploadClientImage((Session) getSession(), this.clientId, this.snapDataValue);

        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
