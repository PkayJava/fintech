package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MCode;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class CodeNameValidator implements IValidator<String> {

    private Long codeId;

    public CodeNameValidator() {
    }

    public CodeNameValidator(Long codeId) {
        this.codeId = codeId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MCode mCode = MCode.staticInitialize(dataContext);
            long count = 0;
            if (this.codeId == null) {
                try (DataSet rows = dataContext.query().from(mCode).select(FunctionType.COUNT, mCode.ID).where(mCode.CODE_NAME).eq(name).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            } else {
                try (DataSet rows = dataContext.query().from(mCode).select(FunctionType.COUNT, mCode.ID).where(mCode.CODE_NAME).eq(name).and(mCode.ID).ne(this.codeId).execute()) {
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
