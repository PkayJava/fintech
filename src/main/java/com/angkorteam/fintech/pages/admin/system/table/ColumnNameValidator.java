package com.angkorteam.fintech.pages.admin.system.table;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class ColumnNameValidator implements IValidator<String> {

    public ColumnNameValidator() {
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String name = validatable.getValue();
        if (name != null && !"".equals(name)) {
            if (name.length() <= 2) {
                validatable.error(new ValidationError(name + " is not allowed"));
            } else {
                if (name.matches("[a-z]+")) {
                } else {
                    validatable.error(new ValidationError(name + " is not allowed"));
                }
            }
        }
    }

}
