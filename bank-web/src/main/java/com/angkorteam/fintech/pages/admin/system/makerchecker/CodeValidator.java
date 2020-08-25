package com.angkorteam.fintech.pages.admin.system.makerchecker;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MPermission;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class CodeValidator implements IValidator<String> {

    public CodeValidator() {
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MPermission mPermission = MPermission.staticInitialize(dataContext);
            long count = 0;
            try (DataSet rows = dataContext.query().from(mPermission).select(FunctionType.COUNT, mPermission.ID).where(mPermission.CODE).eq(name).execute()) {
                rows.next();
                count = (long) rows.getRow().getValue(0);
            }
            if (count > 0) {
                validatable.error(new ValidationError(name + " is not available."));
            }
        }
    }

}
