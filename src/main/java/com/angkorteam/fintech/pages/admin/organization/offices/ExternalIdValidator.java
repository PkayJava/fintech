package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MOffice;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class ExternalIdValidator implements IValidator<String> {

    private Long id;

    public ExternalIdValidator() {
    }

    public ExternalIdValidator(Long id) {
        this.id = id;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String externalId = validatable.getValue();
        if (externalId != null && !"".equals(externalId)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MOffice mOffice = MOffice.staticInitialize(dataContext);
            long count = 0;
            if (this.id == null) {
                try (DataSet rows = dataContext.query().from(mOffice).select(FunctionType.COUNT, mOffice.ID).where(mOffice.EXTERNAL_ID).eq(externalId).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            } else {
                try (DataSet rows = dataContext.query().from(mOffice).select(FunctionType.COUNT, mOffice.ID).where(mOffice.EXTERNAL_ID).eq(externalId).and(mOffice.ID).ne(this.id).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            }
            if (count > 0) {
                validatable.error(new ValidationError(externalId + " is not available."));
            }
        }
    }

}
