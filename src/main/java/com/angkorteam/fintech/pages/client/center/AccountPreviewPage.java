package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.widget.center.CenterPreviewGeneral;
import com.angkorteam.fintech.widget.center.CenterPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

public class AccountPreviewPage extends Page {

    protected String interestEarnedValue;
    protected Label interestEarnedField;

    protected String interestValue;
    protected Label interestField;

    protected String nominalInterestRateValue;
    protected Label nominalInterestRateField;

    protected String interestCompoundingPeriodValue;
    protected Label interestCompoundingPeriodField;

    protected String interestPostingPeriodValue;
    protected Label interestPostingPeriodField;

    protected String interestCalculatedUsingValue;
    protected Label interestCalculatedUsingField;

    protected String lastActiveTransactionDateValue;
    protected Label lastActiveTransactionDateField;

    protected String interestRecalculationDateValue;
    protected Label interestRecalculationDateField;

    protected String activationDateValue;
    protected Label activationDateField;

    protected String officerValue;
    protected Label officerField;

    protected String externalIdValue;
    protected Label externalIdField;

    protected String currencyValue;
    protected Label currencyField;

    protected String detailNominalInterestRateValue;
    protected Label detailNominalInterestRateField;

    protected String totalDepositValue;
    protected Label totalDepositField;

    private AjaxTabbedPanel<ITab> tab;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.interestEarnedField = new Label("interestEarnedField", new PropertyModel<>(this, "interestEarnedValue"));
        this.interestField = new Label("interestField", new PropertyModel<>(this, "interestValue"));
        this.nominalInterestRateField = new Label("nominalInterestRateField", new PropertyModel<>(this, "nominalInterestRateValue"));
        this.interestCompoundingPeriodField = new Label("interestCompoundingPeriodField", new PropertyModel<>(this, "interestCompoundingPeriodValue"));
        this.interestPostingPeriodField = new Label("interestPostingPeriodField", new PropertyModel<>(this, "interestPostingPeriodValue"));
        this.interestCalculatedUsingField = new Label("interestCalculatedUsingField", new PropertyModel<>(this, "interestCalculatedUsingValue"));
        this.lastActiveTransactionDateField = new Label("lastActiveTransactionDateField", new PropertyModel<>(this, "lastActiveTransactionDateValue"));
        this.interestRecalculationDateField = new Label("interestRecalculationDateField", new PropertyModel<>(this, "interestRecalculationDateValue"));
        this.activationDateField = new Label("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.officerField = new Label("officerField", new PropertyModel<>(this, "officerValue"));
        this.externalIdField = new Label("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.currencyField = new Label("currencyField", new PropertyModel<>(this, "currencyValue"));
        this.detailNominalInterestRateField = new Label("detailNominalInterestRateField", new PropertyModel<>(this, "detailNominalInterestRateValue"));
        this.totalDepositField = new Label("totalDepositField", new PropertyModel<>(this, "totalDepositValue"));

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterPreviewGeneral(this), new CenterPreviewNote(this)));

        add(this.tab);
    }

}
