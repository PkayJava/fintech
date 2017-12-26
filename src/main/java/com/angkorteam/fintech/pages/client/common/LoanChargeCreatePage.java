package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.LoanPurposeProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanChargeCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected LoanPurposeProvider chargeProvider;
    protected WebMarkupContainer chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected Option chargeValue;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;

    protected WebMarkupContainer amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationVContainer;
    protected String chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected WebMarkupBlock chargeTimeBlock;
    protected WebMarkupContainer chargeTimeVContainer;
    protected String chargeTimeValue;
    protected ReadOnlyView chargeTimeView;

    protected WebMarkupContainer dueDateBlock;
    protected WebMarkupContainer dueDateIContainer;
    protected Date dueDateValue;
    protected DateTextField dueDateField;
    protected TextFeedbackPanel dueDateFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.chargeProvider = new LoanPurposeProvider();
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeField = new Select2SingleChoice<>("chargeField", new PropertyModel<>(this, "chargeValue"), this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeField.setRequired(false);
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);

        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setLabel(Model.of("Amount"));
        this.amountField.setRequired(false);
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);

        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationVContainer = new WebMarkupContainer("chargeCalculationVContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationVContainer);
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);

        this.chargeTimeBlock = new WebMarkupBlock("chargeTimeBlock", Size.Six_6);
        this.form.add(this.chargeTimeBlock);
        this.chargeTimeVContainer = new WebMarkupContainer("chargeTimeVContainer");
        this.chargeTimeBlock.add(this.chargeTimeVContainer);
        this.chargeTimeView = new ReadOnlyView("chargeTimeView", new PropertyModel<>(this, "chargeTimeValue"));
        this.chargeTimeVContainer.add(this.chargeTimeView);

        this.dueDateBlock = new WebMarkupBlock("dueDateBlock", Size.Six_6);
        this.form.add(this.dueDateBlock);
        this.dueDateIContainer = new WebMarkupContainer("dueDateIContainer");
        this.dueDateBlock.add(this.dueDateIContainer);
        this.dueDateField = new DateTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
        this.dueDateField.setLabel(Model.of("Due Date"));
        this.dueDateField.setRequired(false);
        this.dueDateIContainer.add(this.dueDateField);
        this.dueDateFeedback = new TextFeedbackPanel("dueDateFeedback", this.dueDateField);
        this.dueDateIContainer.add(this.dueDateFeedback);

        this.form.add(this.closeLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {

    }
}