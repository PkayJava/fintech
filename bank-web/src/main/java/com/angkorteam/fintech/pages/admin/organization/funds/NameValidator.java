package com.angkorteam.fintech.pages.admin.organization.funds;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MFund;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class NameValidator implements IValidator<String> {

    private Long fundId;

    public NameValidator() {
    }

    public NameValidator(Long fundId) {
        this.fundId = fundId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MFund mFund = MFund.staticInitialize(dataContext);
            long count = 0;
            if (this.fundId == null) {
                try (DataSet rows = dataContext.query().from(mFund).select(FunctionType.COUNT, mFund.ID).where(mFund.NAME).eq(name).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            } else {
                try (DataSet rows = dataContext.query().from(mFund).select(FunctionType.COUNT, mFund.ID).where(mFund.NAME).eq(name).and(mFund.ID).ne(this.fundId).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            }
            if (count > 0) {
                validatable.error(new ValidationError(name + " is not available."));
            }
        }
    }

}
