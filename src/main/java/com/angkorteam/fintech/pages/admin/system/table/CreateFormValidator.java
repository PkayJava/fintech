package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.webui.frmk.wicket.layout.MasterPage;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class CreateFormValidator extends AbstractFormValidator {

    protected Select2SingleChoice associatedTableField;

    protected Select2SingleChoice multiRowField;

    protected List<Map<String, Object>> columns;

    protected FormComponent[] components;

    public CreateFormValidator(List<Map<String, Object>> columns, Select2SingleChoice associatedTableField, Select2SingleChoice multiRowField) {
        this.columns = columns;
        this.associatedTableField = associatedTableField;
        this.multiRowField = multiRowField;
        this.components = new FormComponent[]{this.associatedTableField, this.multiRowField};
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.components;
    }

    @Override
    public void validate(Form<?> form) {
        Option associatedValue = this.associatedTableField.getConvertedInput();
        if (this.columns == null || this.columns.isEmpty()) {
            form.error(new ValidationError("error"));
            WebSession.get().setAttribute(MasterPage.MESSAGE_KEY, "column is required.");
        } else {
            for (Map<String, Object> column : this.columns) {
                String name = (String) column.get("name");
                if ("id".equals(name) || (TableTypeEnum.valueOf(associatedValue.getId()).getLiteral().substring(2) + "_id").equals(name)) {
                    form.error(new ValidationError("error"));
                    WebSession.get().setAttribute(MasterPage.MESSAGE_KEY, name + " is reserved.");
                    break;
                }
            }
        }
    }
}
