package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.XRegisteredTable;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.metamodel.schema.Table;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.context.ApplicationContext;

public class TableNameValidator implements IValidator<String> {

    public TableNameValidator() {
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            if (name.length() <= 2) {
                validatable.error(new ValidationError(name + " is not allowed"));
            } else {
                if (name.matches("[a-z]+")) {
                    ApplicationContext context = WicketFactory.getApplicationContext();
                    MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
                    DataContext dataContext = mifosDataContextManager.getDataContext(((WebSession) WebSession.get()).getIdentifier());
                    Table table = dataContext.getDefaultSchema().getTableByName(name);
                    if (table != null) {
                        validatable.error(new ValidationError(name + " is not allowed"));
                        return;
                    }
                    XRegisteredTable xRegisteredTable = XRegisteredTable.staticInitialize(dataContext);
                    long count = 0;
                    try (DataSet rows = dataContext.query().from(xRegisteredTable).select(FunctionType.COUNT, xRegisteredTable.REGISTERED_TABLE_NAME).where(xRegisteredTable.REGISTERED_TABLE_NAME).eq(name).execute()) {
                        rows.next();
                        count = (long) rows.getRow().getValue(0);
                    }
                    if (count > 0) {
                        validatable.error(new ValidationError(name + " is not allowed"));
                        return;
                    }
                } else {
                    validatable.error(new ValidationError(name + " is not allowed"));
                }
            }
        }
    }

}
