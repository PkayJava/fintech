package com.angkorteam.fintech.pages.tax;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.request.TaxComponentBuilder;
import com.angkorteam.fintech.helper.TaxComponentHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/16/17.
 */
public class TaxComponentModifyPage extends Page {

    private String tagId;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Double percentageValue;
    private TextField<Double> percentageField;
    private TextFeedbackPanel percentageFeedback;

    private String accountTypeValue;
    private Label accountTypeField;

    private String accountValue;
    private Label accountField;

    private Date startDateValue;
    private DateTextField startDateField;
    private TextFeedbackPanel startDateFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.tagId = parameters.get("taxId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> taxObject = jdbcTemplate.queryForMap("select * from m_tax_component where id = ?", this.tagId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxComponentBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameValue = (String) taxObject.get("name");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        if (taxObject.get("percentage") == null) {
            this.percentageValue = 0d;
        } else {
            this.percentageValue = ((Number) taxObject.get("percentage")).doubleValue();
        }
        this.percentageField = new TextField<>("percentageField", new PropertyModel<>(this, "percentageValue"));
        this.percentageField.setRequired(true);
        this.form.add(this.percentageField);
        this.percentageFeedback = new TextFeedbackPanel("percentageFeedback", this.percentageField);
        this.form.add(this.percentageFeedback);

        if (taxObject.get("credit_account_type_enum") != null) {
            for (AccountType type : AccountType.values()) {
                if (type.getLiteral().equals(String.valueOf(taxObject.get("credit_account_type_enum")))) {
                    this.accountTypeValue = type.getDescription();
                    break;
                }
            }
        }
        this.accountTypeField = new Label("accountTypeField", new PropertyModel<>(this, "accountTypeValue"));
        this.form.add(this.accountTypeField);

        this.accountValue = jdbcTemplate.queryForObject("select name from acc_gl_account WHERE id = ?", String.class, taxObject.get("credit_account_id"));
        this.accountField = new Label("accountField", new PropertyModel<>(this, "accountValue"));
        this.form.add(this.accountField);

        this.startDateValue = (Date) taxObject.get("start_date");
        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.form.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.form.add(this.startDateFeedback);

    }

    private void saveButtonSubmit(Button button) {
        TaxComponentBuilder builder = new TaxComponentBuilder();
        builder.withId(this.tagId);
        builder.withName(this.nameValue);
        builder.withPercentage(this.percentageValue);
        builder.withStartDate(this.startDateValue);
        JsonNode node = null;
        try {
            node = TaxComponentHelper.update(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxComponentBrowsePage.class);
    }

}
