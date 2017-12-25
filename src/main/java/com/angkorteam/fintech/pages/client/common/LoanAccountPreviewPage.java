package com.angkorteam.fintech.pages.client.common;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.LabelView;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.client.client.ClientPreviewDocument;
import com.angkorteam.fintech.widget.client.client.ClientPreviewFamilyMember;
import com.angkorteam.fintech.widget.client.client.ClientPreviewGeneral;
import com.angkorteam.fintech.widget.client.client.ClientPreviewIdentity;
import com.angkorteam.fintech.widget.client.client.ClientPreviewNote;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountPreviewPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected LabelView currentBalanceView;
    protected Double currentBalanceValue;

    protected LabelView originalPrincipalView;
    protected Double originalPrincipalValue;

    protected LabelView paidPrincipalView;
    protected Double paidPrincipalValue;

    protected LabelView waivedPrincipalView;
    protected Double waivedPrincipalValue;

    protected LabelView writtenPrincipalView;
    protected Double writtenPrincipalValue;

    protected LabelView outstandingPrincipalView;
    protected Double outstandingPrincipalValue;

    protected LabelView overduePrincipalView;
    protected Double overduePrincipalValue;

    protected LabelView originalInterestView;
    protected Double originalInterestValue;

    protected LabelView paidInterestView;
    protected Double paidInterestValue;

    protected LabelView waivedInterestView;
    protected Double waivedInterestValue;

    protected LabelView writtenInterestView;
    protected Double writtenInterestValue;

    protected LabelView outstandingInterestView;
    protected Double outstandingInterestValue;

    protected LabelView overdueInterestView;
    protected Double overdueInterestValue;

    protected LabelView originalFeeView;
    protected Double originalFeeValue;

    protected LabelView paidFeeView;
    protected Double paidFeeValue;

    protected LabelView waivedFeeView;
    protected Double waivedFeeValue;

    protected LabelView writtenFeeView;
    protected Double writtenFeeValue;

    protected LabelView outstandingFeeView;
    protected Double outstandingFeeValue;

    protected LabelView overdueFeeView;
    protected Double overdueFeeValue;

    protected LabelView originalPenaltyView;
    protected Double originalPenaltyValue;

    protected LabelView paidPenaltyView;
    protected Double paidPenaltyValue;

    protected LabelView waivedPenaltyView;
    protected Double waivedPenaltyValue;

    protected LabelView writtenPenaltyView;
    protected Double writtenPenaltyValue;

    protected LabelView outstandingPenaltyView;
    protected Double outstandingPenaltyValue;

    protected LabelView overduePenaltyView;
    protected Double overduePenaltyValue;

    protected LabelView originalTotalView;
    protected Double originalTotalValue;

    protected LabelView paidTotalView;
    protected Double paidTotalValue;

    protected LabelView waivedTotalView;
    protected Double waivedTotalValue;

    protected LabelView writtenTotalView;
    protected Double writtenTotalValue;

    protected LabelView outstandingTotalView;
    protected Double outstandingTotalValue;

    protected LabelView overdueTotalView;
    protected Double overdueTotalValue;

    protected LabelView numberRepaymentView;
    protected Long numberRepaymentValue;

    protected LabelView maturityDateView;
    protected Date maturityDateValue;

    protected ReadOnlyView disbursedAmountView;
    protected Double disbursedAmountValue;

    protected ReadOnlyView approvedAmountView;
    protected Double approvedAmountValue;

    protected ReadOnlyView proposedAmountView;
    protected Double proposedAmountValue;

    protected ReadOnlyView externalIdView;
    protected String externalIdValue;

    protected ReadOnlyView currencyView;
    protected String currencyValue;

    protected ReadOnlyView loanOfficerView;
    protected String loanOfficerValue;

    protected ReadOnlyView loanPurposeView;
    protected String loanPurposeValue;

    protected ReadOnlyView disbursedDateView;
    protected Date disbursedDateValue;

    protected AjaxTabbedPanel<ITab> tab;

    @Override
    protected void initComponent() {

        this.currentBalanceView = new LabelView("currentBalanceView", new PropertyModel<>(this, "currentBalanceValue"));
        add(this.currentBalanceView);

        this.originalPrincipalView = new LabelView("originalPrincipalView", new PropertyModel<>(this, "originalPrincipalValue"));
        add(this.originalPrincipalView);

        this.paidPrincipalView = new LabelView("paidPrincipalView", new PropertyModel<>(this, "paidPrincipalValue"));
        add(this.paidPrincipalView);

        this.waivedPrincipalView = new LabelView("waivedPrincipalView", new PropertyModel<>(this, "waivedPrincipalValue"));
        add(this.waivedPrincipalView);

        this.writtenPrincipalView = new LabelView("writtenPrincipalView", new PropertyModel<>(this, "writtenPrincipalValue"));
        add(this.writtenPrincipalView);

        this.outstandingPrincipalView = new LabelView("outstandingPrincipalView", new PropertyModel<>(this, "outstandingPrincipalValue"));
        add(this.outstandingPrincipalView);

        this.overduePrincipalView = new LabelView("overduePrincipalView", new PropertyModel<>(this, "overduePrincipalValue"));
        add(this.overduePrincipalView);

        this.originalInterestView = new LabelView("originalInterestView", new PropertyModel<>(this, "originalInterestValue"));
        add(this.originalInterestView);

        this.paidInterestView = new LabelView("paidInterestView", new PropertyModel<>(this, "paidInterestValue"));
        add(this.paidInterestView);

        this.waivedInterestView = new LabelView("waivedInterestView", new PropertyModel<>(this, "waivedInterestValue"));
        add(this.waivedInterestView);

        this.writtenInterestView = new LabelView("writtenInterestView", new PropertyModel<>(this, "writtenInterestValue"));
        add(this.writtenInterestView);

        this.outstandingInterestView = new LabelView("outstandingInterestView", new PropertyModel<>(this, "outstandingInterestValue"));
        add(this.outstandingInterestView);

        this.overdueInterestView = new LabelView("overdueInterestView", new PropertyModel<>(this, "overdueInterestValue"));
        add(this.overdueInterestView);

        this.originalFeeView = new LabelView("originalFeeView", new PropertyModel<>(this, "originalFeeValue"));
        add(this.originalFeeView);

        this.paidFeeView = new LabelView("paidFeeView", new PropertyModel<>(this, "paidFeeValue"));
        add(this.paidFeeView);

        this.waivedFeeView = new LabelView("waivedFeeView", new PropertyModel<>(this, "waivedFeeValue"));
        add(this.waivedFeeView);

        this.writtenFeeView = new LabelView("writtenFeeView", new PropertyModel<>(this, "writtenFeeValue"));
        add(this.writtenFeeView);

        this.outstandingFeeView = new LabelView("outstandingFeeView", new PropertyModel<>(this, "outstandingFeeValue"));
        add(this.outstandingFeeView);

        this.overdueFeeView = new LabelView("overdueFeeView", new PropertyModel<>(this, "overdueFeeValue"));
        add(this.overdueFeeView);

        this.originalPenaltyView = new LabelView("originalPenaltyView", new PropertyModel<>(this, "originalPenaltyValue"));
        add(this.originalPenaltyView);

        this.paidPenaltyView = new LabelView("paidPenaltyView", new PropertyModel<>(this, "paidPenaltyValue"));
        add(this.paidPenaltyView);

        this.waivedPenaltyView = new LabelView("waivedPenaltyView", new PropertyModel<>(this, "waivedPenaltyValue"));
        add(this.waivedPenaltyView);

        this.writtenPenaltyView = new LabelView("writtenPenaltyView", new PropertyModel<>(this, "writtenPenaltyValue"));
        add(this.writtenPenaltyView);

        this.outstandingPenaltyView = new LabelView("outstandingPenaltyView", new PropertyModel<>(this, "outstandingPenaltyValue"));
        add(this.outstandingPenaltyView);

        this.overduePenaltyView = new LabelView("overduePenaltyView", new PropertyModel<>(this, "overduePenaltyValue"));
        add(this.overduePenaltyView);

        this.originalTotalView = new LabelView("originalTotalView", new PropertyModel<>(this, "originalTotalValue"));
        add(this.originalTotalView);

        this.paidTotalView = new LabelView("paidTotalView", new PropertyModel<>(this, "paidTotalValue"));
        add(this.paidTotalView);

        this.waivedTotalView = new LabelView("waivedTotalView", new PropertyModel<>(this, "waivedTotalValue"));
        add(this.waivedTotalView);

        this.writtenTotalView = new LabelView("writtenTotalView", new PropertyModel<>(this, "writtenTotalValue"));
        add(this.writtenTotalView);

        this.outstandingTotalView = new LabelView("outstandingTotalView", new PropertyModel<>(this, "outstandingTotalValue"));
        add(this.outstandingTotalView);

        this.overdueTotalView = new LabelView("overdueTotalView", new PropertyModel<>(this, "overdueTotalValue"));
        add(this.overdueTotalView);

        this.numberRepaymentView = new LabelView("numberRepaymentView", new PropertyModel<>(this, "numberRepaymentValue"));
        add(this.numberRepaymentView);

        this.maturityDateView = new LabelView("maturityDateView", new PropertyModel<>(this, "maturityDateValue"), "yyyy-MM-dd");
        add(this.maturityDateView);

        this.disbursedAmountView = new ReadOnlyView("disbursedAmountView", new PropertyModel<>(this, "disbursedAmountValue"));
        add(this.disbursedAmountView);

        this.approvedAmountView = new ReadOnlyView("approvedAmountView", new PropertyModel<>(this, "approvedAmountValue"));
        add(this.approvedAmountView);

        this.proposedAmountView = new ReadOnlyView("proposedAmountView", new PropertyModel<>(this, "proposedAmountValue"));
        add(this.proposedAmountView);

        this.externalIdView = new ReadOnlyView("externalIdView", new PropertyModel<>(this, "externalIdValue"));
        add(this.externalIdView);

        this.currencyView = new ReadOnlyView("currencyView", new PropertyModel<>(this, "currencyValue"));
        add(this.currencyView);

        this.loanOfficerView = new ReadOnlyView("loanOfficerView", new PropertyModel<>(this, "loanOfficerValue"));
        add(this.loanOfficerView);

        this.loanPurposeView = new ReadOnlyView("loanPurposeView", new PropertyModel<>(this, "loanPurposeValue"));
        add(this.loanPurposeView);

        this.disbursedDateView = new ReadOnlyView("disbursedDateView", new PropertyModel<>(this, "disbursedDateValue"), "yyyy-MM-dd");
        add(this.disbursedDateView);

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new ClientPreviewGeneral(this), new ClientPreviewFamilyMember(this), new ClientPreviewIdentity(this), new ClientPreviewDocument(this), new ClientPreviewNote(this)));
        add(this.tab);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

        SelectQuery loanDetailQuery = new SelectQuery("m_loan");
        loanDetailQuery.addJoin("left join m_code_value loanpurpose on m_loan.loanpurpose_cv_id = loanpurpose.id");
        loanDetailQuery.addJoin("left join m_staff on m_loan.loan_officer_id = m_staff.id");
        loanDetailQuery.addJoin("left join m_organisation_currency on m_organisation_currency.code = m_loan.currency_code");

        loanDetailQuery.addField("m_loan.disbursedon_date");
        loanDetailQuery.addField("m_staff.display_name loan_officer");
        loanDetailQuery.addField("loanpurpose.code_value loan_purpose");
        loanDetailQuery.addField("concat(m_organisation_currency.name, ' [', m_loan.currency_code, ']') currency");
        loanDetailQuery.addField("m_loan.external_id");
        loanDetailQuery.addField("m_loan.principal_amount_proposed proposed_amount");
        loanDetailQuery.addField("m_loan.approved_principal approved_amount");
        loanDetailQuery.addField("m_loan.principal_amount disbursed_amount");
        loanDetailQuery.addField("m_loan.number_of_repayments");
        loanDetailQuery.addField("m_loan.maturedon_date");

        loanDetailQuery.addField("m_loan.principal_disbursed_derived original_principal");
        loanDetailQuery.addField("m_loan.interest_charged_derived original_interest");
        loanDetailQuery.addField("m_loan.fee_charges_charged_derived original_fee");
        loanDetailQuery.addField("m_loan.penalty_charges_charged_derived original_penalty");
        loanDetailQuery.addField("m_loan.total_expected_repayment_derived original_total");

        loanDetailQuery.addField("m_loan.principal_repaid_derived paid_principal");
        loanDetailQuery.addField("m_loan.interest_repaid_derived paid_interest");
        loanDetailQuery.addField("m_loan.fee_charges_repaid_derived paid_fee");
        loanDetailQuery.addField("m_loan.penalty_charges_repaid_derived paid_penalty");
        loanDetailQuery.addField("m_loan.total_repayment_derived paid_total");

        loanDetailQuery.addField("null waived_principal");
        loanDetailQuery.addField("m_loan.interest_waived_derived waived_interest");
        loanDetailQuery.addField("m_loan.fee_charges_waived_derived waived_fee");
        loanDetailQuery.addField("m_loan.penalty_charges_waived_derived waived_penalty");
        loanDetailQuery.addField("m_loan.total_waived_derived waived_total");

        loanDetailQuery.addField("m_loan.principal_writtenoff_derived writtenoff_principal");
        loanDetailQuery.addField("m_loan.interest_writtenoff_derived writtenoff_interest");
        loanDetailQuery.addField("m_loan.fee_charges_writtenoff_derived writtenoff_fee");
        loanDetailQuery.addField("m_loan.penalty_charges_writtenoff_derived writtenoff_penalty");
        loanDetailQuery.addField("m_loan.total_writtenoff_derived writtenoff_total");

        loanDetailQuery.addField("m_loan.principal_outstanding_derived outstanding_principal");
        loanDetailQuery.addField("m_loan.interest_outstanding_derived outstanding_interest");
        loanDetailQuery.addField("m_loan.fee_charges_outstanding_derived outstanding_fee");
        loanDetailQuery.addField("m_loan.penalty_charges_outstanding_derived outstanding_penalty");
        loanDetailQuery.addField("m_loan.total_outstanding_derived outstanding_total");

        loanDetailQuery.addField("0.0 overdue_principal");
        loanDetailQuery.addField("0.0 overdue_interest");
        loanDetailQuery.addField("0.0 overdue_fee");
        loanDetailQuery.addField("0.0 overdue_penalty");
        loanDetailQuery.addField("0.0 overdue_total");

        loanDetailQuery.addWhere("m_loan.id = '" + this.loanId + "'");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> loanDetailObject = jdbcTemplate.queryForMap(loanDetailQuery.toSQL());

        this.currentBalanceValue = (Double) loanDetailObject.get("outstanding_total");

        this.originalPrincipalValue = (Double) loanDetailObject.get("original_principal");
        this.paidPrincipalValue = (Double) loanDetailObject.get("paid_principal");
        this.waivedPrincipalValue = (Double) loanDetailObject.get("waived_principal");
        this.writtenPrincipalValue = (Double) loanDetailObject.get("writtenoff_principal");
        this.outstandingPrincipalValue = (Double) loanDetailObject.get("outstanding_principal");
        this.overduePrincipalValue = (Double) loanDetailObject.get("overdue_principal");

        this.originalInterestValue = (Double) loanDetailObject.get("original_interest");
        this.paidInterestValue = (Double) loanDetailObject.get("paid_interest");
        this.waivedInterestValue = (Double) loanDetailObject.get("waived_interest");
        this.writtenInterestValue = (Double) loanDetailObject.get("writtenoff_interest");
        this.outstandingInterestValue = (Double) loanDetailObject.get("outstanding_interest");
        this.overdueInterestValue = (Double) loanDetailObject.get("overdue_interest");

        this.originalFeeValue = (Double) loanDetailObject.get("original_fee");
        this.paidFeeValue = (Double) loanDetailObject.get("paid_fee");
        this.waivedFeeValue = (Double) loanDetailObject.get("waived_fee");
        this.writtenFeeValue = (Double) loanDetailObject.get("writtenoff_fee");
        this.outstandingFeeValue = (Double) loanDetailObject.get("outstanding_fee");
        this.overdueFeeValue = (Double) loanDetailObject.get("overdue_fee");

        this.originalPenaltyValue = (Double) loanDetailObject.get("original_penalty");
        this.paidPenaltyValue = (Double) loanDetailObject.get("paid_penalty");
        this.waivedPenaltyValue = (Double) loanDetailObject.get("waived_penalty");
        this.writtenPenaltyValue = (Double) loanDetailObject.get("writtenoff_penalty");
        this.outstandingPenaltyValue = (Double) loanDetailObject.get("outstanding_penalty");
        this.overduePenaltyValue = (Double) loanDetailObject.get("overdue_penalty");

        this.originalTotalValue = (Double) loanDetailObject.get("original_total");
        this.paidTotalValue = (Double) loanDetailObject.get("paid_total");
        this.waivedTotalValue = (Double) loanDetailObject.get("waived_total");
        this.writtenTotalValue = (Double) loanDetailObject.get("writtenoff_total");
        this.outstandingTotalValue = (Double) loanDetailObject.get("outstanding_total");
        this.overdueTotalValue = (Double) loanDetailObject.get("overdue_total");

        this.numberRepaymentValue = (Long) loanDetailObject.get("number_of_repayments");
        this.maturityDateValue = (Date) loanDetailObject.get("maturedon_date");
        this.disbursedAmountValue = (Double) loanDetailObject.get("disbursed_amount");
        this.approvedAmountValue = (Double) loanDetailObject.get("approved_amount");
        this.proposedAmountValue = (Double) loanDetailObject.get("proposed_amount");
        this.externalIdValue = (String) loanDetailObject.get("external_id");
        this.currencyValue = (String) loanDetailObject.get("currency");
        this.loanOfficerValue = (String) loanDetailObject.get("loan_officer");
        this.loanPurposeValue = (String) loanDetailObject.get("loan_purpose");
        this.disbursedDateValue = (Date) loanDetailObject.get("disbursedon_date");
    }

}
