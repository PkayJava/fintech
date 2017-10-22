package com.angkorteam.fintech.pages.client.client;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanSelectionPage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer loanBlock;
    protected WebMarkupContainer loanContainer;
    protected SingleChoiceProvider loanProvider;
    protected Option loanValue;
    protected Select2SingleChoice<Option> loanField;
    protected TextFeedbackPanel loanFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.loanBlock = new WebMarkupContainer("loanBlock");
        this.form.add(this.loanBlock);
        this.loanContainer = new WebMarkupContainer("loanContainer");
        this.loanBlock.add(this.loanContainer);
        this.loanProvider = new SingleChoiceProvider("m_product_loan", "id", "name");
        this.loanField = new Select2SingleChoice<>("loanField", new PropertyModel<>(this, "loanValue"), this.loanProvider);
        this.loanField.setLabel(Model.of("Product"));
        this.loanField.add(new OnChangeAjaxBehavior());
        this.loanField.setRequired(true);
        this.loanContainer.add(this.loanField);
        this.loanFeedback = new TextFeedbackPanel("loanFeedback", this.loanField);
        this.loanContainer.add(this.loanFeedback);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    protected void okayButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        parameters.add("loanId", this.loanValue.getId());
        setResponsePage(LoanCreatePage.class, parameters);
    }

}
