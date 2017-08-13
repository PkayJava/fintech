package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/11/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleSelectPage extends Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectPage.class);

    private SingleChoiceProvider ruleProvider;
    private Option ruleValue;
    private Select2SingleChoice<Option> ruleField;
    private TextFeedbackPanel ruleFeedback;

    private Button nextButton;
    private Form<Void> form;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule Selection");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.ruleProvider = new SingleChoiceProvider("acc_accounting_rule", "id", "name");
        this.ruleField = new Select2SingleChoice<>("ruleField", new PropertyModel<>(this, "ruleValue"),
                this.ruleProvider);
        this.ruleField.setRequired(true);
        this.form.add(this.ruleField);
        this.ruleFeedback = new TextFeedbackPanel("ruleFeedback", this.ruleField);
        this.form.add(this.ruleFeedback);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonClick);
        this.form.add(this.nextButton);

    }

    protected void nextButtonClick(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", this.ruleValue.getId());
        setResponsePage(FrequentPostPage.class, parameters);
    }

}
