package com.angkorteam.fintech.pages.staff;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.angkorteam.fintech.helper.StaffHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class StaffModifyPage extends Page {

    private String staffId;

    private String firstNameValue;
    private TextField<String> firstNameField;
    private TextFeedbackPanel firstNameFeedback;

    private String lastNameValue;
    private TextField<String> lastNameField;
    private TextFeedbackPanel lastNameFeedback;

    private String mobileNoValue;
    private TextField<String> mobileNoField;
    private TextFeedbackPanel mobileNoFeedback;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private Date joinedDateValue;
    private DateTextField joinedDateField;
    private TextFeedbackPanel joinedDateFeedback;

    private Boolean loanOfficerValue;
    private CheckBox loanOfficerField;
    private TextFeedbackPanel loanOfficerFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();

        this.staffId = parameters.get("staffId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_staff where id = ?", this.staffId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", StaffBrowsePage.class);
        this.form.add(this.closeLink);

        this.firstNameValue = (String) object.get("firstname");
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setRequired(true);
        this.form.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.form.add(this.firstNameFeedback);

        this.lastNameValue = (String) object.get("lastname");
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setRequired(true);
        this.form.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.form.add(this.lastNameFeedback);

        this.joinedDateValue = (Date) object.get("joining_date");
        this.joinedDateField = new DateTextField("joinedDateField", new PropertyModel<>(this, "joinedDateValue"));
        this.joinedDateField.setRequired(true);
        this.form.add(this.joinedDateField);
        this.joinedDateFeedback = new TextFeedbackPanel("joinedDateFeedback", this.joinedDateField);
        this.form.add(this.joinedDateFeedback);

        this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?", new OptionMapper(), object.get("office_id"));
        this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.mobileNoValue = (String) object.get("mobile_no");
        this.mobileNoField = new TextField<>("mobileNoField", new PropertyModel<>(this, "mobileNoValue"));
        this.mobileNoField.setRequired(true);
        this.form.add(this.mobileNoField);
        this.mobileNoFeedback = new TextFeedbackPanel("mobileNoFeedback", this.mobileNoField);
        this.form.add(this.mobileNoFeedback);

        this.loanOfficerValue = (Boolean) object.get("is_loan_officer");
        this.loanOfficerField = new CheckBox("loanOfficerField", new PropertyModel<>(this, "loanOfficerValue"));
        this.loanOfficerField.setRequired(true);
        this.form.add(this.loanOfficerField);
        this.loanOfficerFeedback = new TextFeedbackPanel("loanOfficerFeedback", this.loanOfficerField);
        this.form.add(this.loanOfficerFeedback);
    }

    private void saveButtonSubmit(Button button) {
        StaffBuilder builder = new StaffBuilder();
        builder.withId(this.staffId);
        builder.withFirstName(this.firstNameValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withLastName(this.lastNameValue);
        builder.withMobileNo(this.mobileNoValue);
        builder.withLoanOfficer(this.loanOfficerValue);
        builder.withJoiningDate(this.joinedDateValue);

        JsonNode node = null;
        try {
            node = StaffHelper.updateStaff(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(StaffBrowsePage.class);
    }

}
