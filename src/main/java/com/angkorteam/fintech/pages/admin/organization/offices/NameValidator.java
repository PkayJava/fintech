package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.MOffice;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class NameValidator implements IValidator<String> {

    private Long id;

    public NameValidator() {
    }

    public NameValidator(Long id) {
        this.id = id;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MOffice mOffice = MOffice.staticInitialize(dataContext);
            long count = 0;
            if (this.id == null) {
                try (DataSet rows = dataContext.query().from(mOffice).select(FunctionType.COUNT, mOffice.ID).where(mOffice.NAME).eq(name).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            } else {
                try (DataSet rows = dataContext.query().from(mOffice).select(FunctionType.COUNT, mOffice.ID).where(mOffice.NAME).eq(name).and(mOffice.ID).ne(this.id).execute()) {
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
