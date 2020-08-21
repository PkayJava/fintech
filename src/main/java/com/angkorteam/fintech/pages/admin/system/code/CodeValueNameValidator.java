package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MCodeValue;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class CodeValueNameValidator implements IValidator<String> {

    private long codeId;

    private Long codeValueId;

    public CodeValueNameValidator(long codeId) {
        this.codeId = codeId;
    }

    public CodeValueNameValidator(long codeId, Long codeValueId) {
        this.codeId = codeId;
        this.codeValueId = codeValueId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String codeValueName = validatable.getValue();
        if (codeValueName != null && !"".equals(codeValueName)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            WebSession session = (WebSession) WebSession.get();
            DataContext dataContext = mifosDataContextManager.getDataContext(session.getIdentifier());
            MCodeValue mCodeValue = MCodeValue.staticInitialize(dataContext);
            long count = 0;
            if (this.codeValueId == null) {
                try (DataSet rows = dataContext.query().from(mCodeValue).select(FunctionType.COUNT, mCodeValue.ID).where(mCodeValue.CODE_ID).eq(this.codeId).and(mCodeValue.CODE_VALUE).eq(codeValueName).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            } else {
                try (DataSet rows = dataContext.query().from(mCodeValue).select(FunctionType.COUNT, mCodeValue.ID).where(mCodeValue.CODE_ID).eq(this.codeId).and(mCodeValue.CODE_VALUE).eq(codeValueName).and(mCodeValue.ID).ne(this.codeValueId).execute()) {
                    rows.next();
                    count = (long) rows.getRow().getValue(0);
                }
            }
            if (count > 0) {
                validatable.error(new ValidationError(codeValueName + " is not available."));
            }
        }
    }

}
