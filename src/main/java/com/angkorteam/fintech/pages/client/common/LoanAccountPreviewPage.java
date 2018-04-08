package com.angkorteam.fintech.pages.client.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.LabelView;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewCharge;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewDetail;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewDocument;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewLoanCollateral;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewNote;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewRepaymentSchedule;
import com.angkorteam.fintech.widget.client.common.LoanAccountPreviewTransaction;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountPreviewPage extends Page {

    protected String loanStatus;

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String clientDisplayName;
    protected String groupDisplayName;
    protected String centerDisplayName;

    protected String loanId;
    protected String loanAccountNo;
    protected Long officerId;

    protected LabelView currentBalanceView;
    protected Double currentBalanceValue;

    protected LabelView originalPrincipleView;
    protected Double originalPrincipleValue;

    protected LabelView paidPrincipleView;
    protected Double paidPrincipleValue;

    protected LabelView waivedPrincipleView;
    protected Double waivedPrincipleValue;

    protected LabelView writtenPrincipleView;
    protected Double writtenPrincipleValue;

    protected LabelView outstandingPrincipleView;
    protected Double outstandingPrincipleValue;

    protected LabelView overduePrincipleView;
    protected Double overduePrincipleValue;

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

    protected WebMarkupContainer buttonGroups;

    protected BookmarkablePageLink<Void> moreAddLoanCharge;
    protected BookmarkablePageLink<Void> menuAddLoanCharge;
    protected BookmarkablePageLink<Void> menuApprove;
    protected BookmarkablePageLink<Void> menuModifyApplication;
    protected BookmarkablePageLink<Void> menuReject;
    protected BookmarkablePageLink<Void> menuUndoApprove;
    protected BookmarkablePageLink<Void> menuDisburseToSaving;
    protected BookmarkablePageLink<Void> menuDisburse;
    protected BookmarkablePageLink<Void> menuAssignLoanOfficer;
    protected BookmarkablePageLink<Void> menuUnAssignLoanOfficer;
    protected BookmarkablePageLink<Void> menuUndoDisbursal;
    protected BookmarkablePageLink<Void> menuMakeRepayment;
    protected BookmarkablePageLink<Void> menuForeclosure;
    protected BookmarkablePageLink<Void> menuPrepayLoan;

    protected BookmarkablePageLink<Void> moreWaiveInterest;
    protected BookmarkablePageLink<Void> moreReschedule;
    protected BookmarkablePageLink<Void> moreWriteOff;
    protected BookmarkablePageLink<Void> moreCloseAsReschedule;
    protected BookmarkablePageLink<Void> moreClose;
    protected BookmarkablePageLink<Void> moreAssignLoanOfficer;
    protected BookmarkablePageLink<Void> moreUnAssignLoanOfficer;
    protected BookmarkablePageLink<Void> moreWithdrawnByClient;
    protected BookmarkablePageLink<Void> moreAddCollateral;
    protected BookmarkablePageLink<Void> moreViewGuarantor;
    protected BookmarkablePageLink<Void> moreRecoverFromGuarantor;
    protected BookmarkablePageLink<Void> moreLoanScreenReport;

    @Override
    protected void configureMetaData() {

        this.moreAddLoanCharge.setVisible(false);
        this.menuAddLoanCharge.setVisible(false);
        this.menuApprove.setVisible(false);
        this.menuModifyApplication.setVisible(false);
        this.menuReject.setVisible(false);
        this.menuUndoApprove.setVisible(false);
        this.menuDisburseToSaving.setVisible(false);
        this.menuDisburse.setVisible(false);
        this.menuAssignLoanOfficer.setVisible(false);
        this.menuUnAssignLoanOfficer.setVisible(false);
        this.menuUndoDisbursal.setVisible(false);
        this.menuMakeRepayment.setVisible(false);
        this.menuForeclosure.setVisible(false);
        this.menuPrepayLoan.setVisible(false);

        this.moreWaiveInterest.setVisible(false);
        this.moreReschedule.setVisible(false);
        this.moreWriteOff.setVisible(false);
        this.moreCloseAsReschedule.setVisible(false);
        this.moreClose.setVisible(false);
        this.moreAssignLoanOfficer.setVisible(false);
        this.moreUnAssignLoanOfficer.setVisible(false);
        this.moreWithdrawnByClient.setVisible(false);
        this.moreAddCollateral.setVisible(false);
        this.moreViewGuarantor.setVisible(false);
        this.moreRecoverFromGuarantor.setVisible(false);
        this.moreLoanScreenReport.setVisible(false);

        // 100 mean created
        // 200 mean approved
        // 300 mean disbursed

        this.menuPrepayLoan.setVisible("300".equals(this.loanStatus));
        this.menuForeclosure.setVisible("300".equals(this.loanStatus));
        this.menuMakeRepayment.setVisible("300".equals(this.loanStatus));
        this.menuUndoDisbursal.setVisible("300".equals(this.loanStatus));
        this.menuAddLoanCharge.setVisible("100".equals(this.loanStatus) || "300".equals(this.loanStatus));
        this.menuApprove.setVisible("100".equals(this.loanStatus));
        this.menuModifyApplication.setVisible("100".equals(this.loanStatus));
        this.menuReject.setVisible("100".equals(this.loanStatus));
        this.menuAssignLoanOfficer.setVisible("200".equals(this.loanStatus));
        this.menuUnAssignLoanOfficer.setVisible(this.officerId != null && "200".equals(this.loanStatus));
        this.menuDisburse.setVisible("200".equals(this.loanStatus));
        this.menuDisburseToSaving.setVisible("200".equals(this.loanStatus));
        this.menuUndoApprove.setVisible("200".equals(this.loanStatus));

        this.moreWaiveInterest.setVisible("300".equals(this.loanStatus));
        this.moreReschedule.setVisible("300".equals(this.loanStatus));
        this.moreWriteOff.setVisible("300".equals(this.loanStatus));
        this.moreClose.setVisible("300".equals(this.loanStatus));
        this.moreCloseAsReschedule.setVisible("300".equals(this.loanStatus));
        this.moreAssignLoanOfficer.setVisible("100".equals(this.loanStatus));
        this.moreUnAssignLoanOfficer.setVisible(this.officerId != null && "100".equals(this.loanStatus));
        this.moreWithdrawnByClient.setVisible("100".equals(this.loanStatus));
        this.moreAddCollateral.setVisible("100".equals(this.loanStatus));
        this.moreViewGuarantor.setVisible("100".equals(this.loanStatus) || "200".equals(this.loanStatus) || "300".equals(this.loanStatus));
        this.moreLoanScreenReport.setVisible("100".equals(this.loanStatus) || "200".equals(this.loanStatus) || "300".equals(this.loanStatus));
        this.moreAddLoanCharge.setVisible("200".equals(this.loanStatus));
        this.moreRecoverFromGuarantor.setVisible("300".equals(this.loanStatus));
    }

    @Override
    protected void initComponent() {

        this.buttonGroups = new WebMarkupContainer("buttonGroups");
        this.buttonGroups.setOutputMarkupId(true);
        add(this.buttonGroups);

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreWaiveInterest = new BookmarkablePageLink<>("moreWaiveInterest", LoanAccountWaiveInterestPage.class, parameters);
            this.buttonGroups.add(this.moreWaiveInterest);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreReschedule = new BookmarkablePageLink<>("moreReschedule", LoanAccountReschedulePage.class, parameters);
            this.buttonGroups.add(this.moreReschedule);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreWriteOff = new BookmarkablePageLink<>("moreWriteOff", LoanAccountWriteOffPage.class, parameters);
            this.buttonGroups.add(this.moreWriteOff);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreCloseAsReschedule = new BookmarkablePageLink<>("moreCloseAsReschedule", LoanAccountCloseAsSchedulePage.class, parameters);
            this.buttonGroups.add(this.moreCloseAsReschedule);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreClose = new BookmarkablePageLink<>("moreClose", LoanAccountClosePage.class, parameters);
            this.buttonGroups.add(this.moreClose);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreWithdrawnByClient = new BookmarkablePageLink<>("moreWithdrawnByClient", LoanChargeCreatePage.class);
            this.buttonGroups.add(this.moreWithdrawnByClient);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreAddCollateral = new BookmarkablePageLink<>("moreAddCollateral", LoanCollateralCreatePage.class, parameters);
            this.buttonGroups.add(this.moreAddCollateral);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreViewGuarantor = new BookmarkablePageLink<>("moreViewGuarantor", LoanGuarantorBrowsePage.class, parameters);
            this.buttonGroups.add(this.moreViewGuarantor);
        }

        this.moreLoanScreenReport = new BookmarkablePageLink<>("moreLoanScreenReport", LoanChargeCreatePage.class);
        this.buttonGroups.add(this.moreLoanScreenReport);

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.moreRecoverFromGuarantor = new BookmarkablePageLink<>("moreRecoverFromGuarantor", LoanAccountRecoverGuaranteePage.class, parameters);
            this.buttonGroups.add(this.moreRecoverFromGuarantor);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuPrepayLoan = new BookmarkablePageLink<>("menuPrepayLoan", LoanAccountPrepayPage.class, parameters);
            this.buttonGroups.add(this.menuPrepayLoan);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuForeclosure = new BookmarkablePageLink<>("menuForeclosure", LoanAccountForeclosurePage.class, parameters);
            this.buttonGroups.add(this.menuForeclosure);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuMakeRepayment = new BookmarkablePageLink<>("menuMakeRepayment", LoanAccountRepaymentPage.class, parameters);
            this.buttonGroups.add(this.menuMakeRepayment);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuUndoDisbursal = new BookmarkablePageLink<>("menuUndoDisbursal", LoanAccountUndoDisbursePage.class, parameters);
            this.buttonGroups.add(this.menuUndoDisbursal);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuUnAssignLoanOfficer = new BookmarkablePageLink<>("menuUnAssignLoanOfficer", LoanOfficerUnAssignPage.class, parameters);
            this.buttonGroups.add(this.menuUnAssignLoanOfficer);

            this.moreUnAssignLoanOfficer = new BookmarkablePageLink<>("moreUnAssignLoanOfficer", LoanOfficerUnAssignPage.class, parameters);
            this.buttonGroups.add(this.moreUnAssignLoanOfficer);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);

            this.menuAssignLoanOfficer = new BookmarkablePageLink<>("menuAssignLoanOfficer", LoanOfficerAssignPage.class, parameters);
            this.buttonGroups.add(this.menuAssignLoanOfficer);

            this.moreAssignLoanOfficer = new BookmarkablePageLink<>("moreAssignLoanOfficer", LoanOfficerAssignPage.class, parameters);
            this.buttonGroups.add(this.moreAssignLoanOfficer);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuDisburse = new BookmarkablePageLink<>("menuDisburse", LoanAccountDisbursePage.class, parameters);
            this.buttonGroups.add(this.menuDisburse);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuDisburseToSaving = new BookmarkablePageLink<>("menuDisburseToSaving", LoanAccountDisburseSavingPage.class, parameters);
            this.buttonGroups.add(this.menuDisburseToSaving);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuApprove = new BookmarkablePageLink<>("menuApprove", LoanAccountApprovePage.class, parameters);
            this.buttonGroups.add(this.menuApprove);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);
            this.menuUndoApprove = new BookmarkablePageLink<>("menuUndoApprove", LoanAccountUndoApprovalPage.class, parameters);
            this.buttonGroups.add(this.menuUndoApprove);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);

            this.moreAddLoanCharge = new BookmarkablePageLink<>("moreAddLoanCharge", LoanChargeCreatePage.class, parameters);
            this.buttonGroups.add(this.moreAddLoanCharge);

            this.menuAddLoanCharge = new BookmarkablePageLink<>("menuAddLoanCharge", LoanChargeCreatePage.class, parameters);
            this.buttonGroups.add(this.menuAddLoanCharge);
        }

        {
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("loanId", this.loanId);

            this.menuReject = new BookmarkablePageLink<>("menuReject", LoanAccountRejectPage.class, parameters);
            this.buttonGroups.add(this.menuReject);
        }

        this.menuModifyApplication = new BookmarkablePageLink<>("menuModifyApplication", LoanChargeCreatePage.class);
        this.buttonGroups.add(this.menuModifyApplication);

        this.currentBalanceView = new LabelView("currentBalanceView", new PropertyModel<>(this, "currentBalanceValue"));
        add(this.currentBalanceView);

        this.originalPrincipleView = new LabelView("originalPrincipleView", new PropertyModel<>(this, "originalPrincipleValue"));
        add(this.originalPrincipleView);

        this.paidPrincipleView = new LabelView("paidPrincipleView", new PropertyModel<>(this, "paidPrincipleValue"));
        add(this.paidPrincipleView);

        this.waivedPrincipleView = new LabelView("waivedPrincipleView", new PropertyModel<>(this, "waivedPrincipleValue"));
        add(this.waivedPrincipleView);

        this.writtenPrincipleView = new LabelView("writtenPrincipleView", new PropertyModel<>(this, "writtenPrincipleValue"));
        add(this.writtenPrincipleView);

        this.outstandingPrincipleView = new LabelView("outstandingPrincipleView", new PropertyModel<>(this, "outstandingPrincipleValue"));
        add(this.outstandingPrincipleView);

        this.overduePrincipleView = new LabelView("overduePrincipleView", new PropertyModel<>(this, "overduePrincipleValue"));
        add(this.overduePrincipleView);

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

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new LoanAccountPreviewDetail(this), new LoanAccountPreviewRepaymentSchedule(this), new LoanAccountPreviewTransaction(this), new LoanAccountPreviewLoanCollateral(this), new LoanAccountPreviewCharge(this), new LoanAccountPreviewDocument(this), new LoanAccountPreviewNote(this)));
        add(this.tab);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addJoin("LEFT JOIN " + MCodeValue.NAME + " loanpurpose ON " + MLoan.NAME + "." + MLoan.Field.LOAN_PURPOSE_CV_ID + " = loanpurpose." + MCodeValue.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MStaff.NAME + " ON " + MLoan.NAME + "." + MLoan.Field.LOAN_OFFICER_ID + " = " + MStaff.NAME + "." + MStaff.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MOrganisationCurrency.NAME + " ON " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE + " = " + MLoan.NAME + "." + MLoan.Field.CURRENCY_CODE);

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.LOAN_STATUS_ID);

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.DISBURSED_ON_DATE);
        selectQuery.addField(MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME + " loan_officer");
        selectQuery.addField("loanpurpose." + MCodeValue.Field.CODE_VALUE + " loan_purpose");
        selectQuery.addField("CONCAT(" + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.NAME + ", ' [', " + MLoan.NAME + "." + MLoan.Field.CURRENCY_CODE + ", ']') currency");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.EXTERNAL_ID);
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_AMOUNT_PROPOSED + " proposed_amount");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.APPROVED_PRINCIPAL + " approved_amount");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_AMOUNT + " disbursed_amount");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.MATURED_ON_DATE);
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.LOAN_OFFICER_ID);
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.ACCOUNT_NO);

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_DISBURSED_DERIVED + " original_principle");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.INTEREST_CHARGED_DERIVED + " original_interest");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.FEE_CHARGES_CHARGED_DERIVED + " original_fee");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PENALTY_CHARGES_CHARGED_DERIVED + " original_penalty");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.TOTAL_EXPECTED_REPAYMENT_DERIVED + " original_total");

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_REPAID_DERIVED + " paid_principle");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.INTEREST_REPAID_DERIVED + " paid_interest");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.FEE_CHARGES_REPAID_DERIVED + " paid_fee");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PENALTY_CHARGES_REPAID_DERIVED + " paid_penalty");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.TOTAL_REPAYMENT_DERIVED + " paid_total");

        selectQuery.addField("NULL waived_principle");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.INTEREST_WAIVED_DERIVED + " waived_interest");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.FEE_CHARGES_WAIVED_DERIVED + " waived_fee");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PENALTY_CHARGES_WAIVED_DERIVED + " waived_penalty");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.TOTAL_WAIVED_DERIVED + " waived_total");

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_WRITTEN_OFF_DERIVED + " writtenoff_principle");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.INTEREST_WRITTEN_OFF_DERIVED + " writtenoff_interest");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.FEE_CHARGES_WRITTEN_OFF_DERIVED + " writtenoff_fee");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PENALTY_CHARGES_WRITTEN_OFF_DERIVED + " writtenoff_penalty");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.TOTAL_WRITTEN_OFF_DERIVED + " writtenoff_total");

        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PRINCIPAL_OUTSTANDING_DERIVED + " outstanding_principle");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.INTEREST_OUTSTANDING_DERIVED + " outstanding_interest");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.FEE_CHARGES_OUTSTANDING_DERIVED + " outstanding_fee");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.PENALTY_CHARGES_OUTSTANDING_DERIVED + " outstanding_penalty");
        selectQuery.addField(MLoan.NAME + "." + MLoan.Field.TOTAL_OUTSTANDING_DERIVED + " outstanding_total");

        selectQuery.addField("0.0 overdue_principle");
        selectQuery.addField("0.0 overdue_interest");
        selectQuery.addField("0.0 overdue_fee");
        selectQuery.addField("0.0 overdue_penalty");
        selectQuery.addField("0.0 overdue_total");

        selectQuery.addWhere(MLoan.NAME + "." + MLoan.Field.ID + " = '" + this.loanId + "'");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        Map<String, Object> loanDetailObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.loanStatus = String.valueOf(loanDetailObject.get("loan_status_id"));

        this.officerId = (Long) loanDetailObject.get("loan_officer_id");

        this.currentBalanceValue = (Double) loanDetailObject.get("outstanding_total");

        this.originalPrincipleValue = (Double) loanDetailObject.get("original_principle");
        this.paidPrincipleValue = (Double) loanDetailObject.get("paid_principle");
        this.waivedPrincipleValue = (Double) loanDetailObject.get("waived_principle");
        this.writtenPrincipleValue = (Double) loanDetailObject.get("writtenoff_principle");
        this.outstandingPrincipleValue = (Double) loanDetailObject.get("outstanding_principle");
        this.overduePrincipleValue = (Double) loanDetailObject.get("overdue_principle");

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

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.groupDisplayName = (String) groupObject.get("display_name");
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.centerDisplayName = (String) centerObject.get("display_name");
        }

        this.loanAccountNo = (String) loanDetailObject.get("account_no");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
                breadcrumb.setLabel(this.clientDisplayName);
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
                breadcrumb.setLabel(this.groupDisplayName);
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
                breadcrumb.setLabel(this.centerDisplayName);
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.loanAccountNo);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

}
