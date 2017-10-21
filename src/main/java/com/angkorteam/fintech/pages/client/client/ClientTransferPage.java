package com.angkorteam.fintech.pages.client.client;

import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientTransferPage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer officeBlock;
    protected WebMarkupContainer officeContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupContainer noteBlock;
    protected WebMarkupContainer noteContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.officeBlock = new WebMarkupContainer("officeBlock");
        this.form.add(this.officeBlock);
        this.officeContainer = new WebMarkupContainer("officeContainer");
        this.officeBlock.add(this.officeContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeContainer.add(this.officeFeedback);

        this.noteBlock = new WebMarkupContainer("noteBlock");
        this.noteBlock.setOutputMarkupId(true);
        this.form.add(this.noteBlock);
        this.noteContainer = new WebMarkupContainer("noteContainer");
        this.noteBlock.add(this.noteContainer);
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setLabel(Model.of("Note"));
        this.noteContainer.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.noteContainer.add(this.noteFeedback);
    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.clientId = getPageParameters().get("clientId").toString();
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.officeValue = jdbcTemplate.queryForObject("select id, name as text from m_office where id = ?", Option.MAPPER, clientObject.get("office_id"));
    }

    protected void saveButtonSubmit(Button button) {
    }
}
