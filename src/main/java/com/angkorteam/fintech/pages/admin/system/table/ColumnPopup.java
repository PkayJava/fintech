package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.enums.ColumnType;
import com.angkorteam.fintech.data.SingleChoiceProvider;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MCode;
import com.angkorteam.fintech.provider.ColumnTypeOptionProvider;
import com.angkorteam.fintech.provider.YesNoOptionProvider;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.functional.WicketTriConsumer;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.webui.frmk.wicket.markup.html.panel.Popup;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ColumnPopup extends Popup {

    protected Form<Void> form;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected UIColumn mandatoryColumn;
    protected UIContainer mandatoryContainer;
    protected YesNoOptionProvider mandatoryProvider;
    protected Select2SingleChoice mandatoryField;
    protected Option mandatoryValue;

    protected UIRow row2;

    protected UIColumn typeColumn;
    protected UIContainer typeContainer;
    protected ColumnTypeOptionProvider typeProvider;
    protected Select2SingleChoice typeField;
    protected Option typeValue;

    protected UIColumn lengthCodeColumn;
    protected UIContainer codeContainer;
    protected SingleChoiceProvider codeProvider;
    protected Select2SingleChoice codeField;
    protected Option codeValue;

    protected UIContainer lengthContainer;
    protected TextField<Long> lengthField;
    protected Long lengthValue;

    protected AjaxLink<Void> cancelButton;
    protected AjaxButton okayButton;

    public ColumnPopup(String id) {
        super(id);
    }

    public ColumnPopup(String id, WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> event) {
        super(id, event);
    }

    @Override
    protected void initData() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(((WebSession) getSession()).getIdentifier());
        MCode mCode = MCode.staticInitialize(dataContext);

        this.typeProvider = new ColumnTypeOptionProvider();
        this.codeProvider = new SingleChoiceProvider(mCode.getName(), mCode.ID.getName(), mCode.CODE_NAME.getName());
        this.mandatoryProvider = new YesNoOptionProvider();
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.form = new Form<>("form");
        body.add(this.form);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Six_6);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameField.setLabel(Model.of("Name"));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.mandatoryColumn = this.row1.newUIColumn("mandatoryColumn", Size.Six_6);
        this.mandatoryContainer = this.mandatoryColumn.newUIContainer("mandatoryContainer");
        this.mandatoryField = new Select2SingleChoice("mandatoryField", new PropertyModel<>(this, "mandatoryValue"), this.mandatoryProvider);
        this.mandatoryField.setLabel(Model.of("Mandatory"));
        this.mandatoryField.setRequired(true);
        this.mandatoryContainer.add(this.mandatoryField);
        this.mandatoryContainer.newFeedback("mandatoryFeedback", this.mandatoryField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.lengthCodeColumn = this.row2.newUIColumn("lengthCodeColumn", Size.Six_6);

        this.lengthContainer = this.lengthCodeColumn.newUIContainer("lengthContainer");
        this.lengthField = new TextField<>("lengthField", new PropertyModel<>(this, "lengthValue"));
        this.lengthField.setType(long.class);
        this.lengthField.setLabel(Model.of("Length"));
        this.lengthField.add(RangeValidator.minimum(1L));
        this.lengthContainer.add(this.lengthField);
        this.lengthContainer.newFeedback("lengthFeedback", this.lengthField);

        this.codeContainer = this.lengthCodeColumn.newUIContainer("codeContainer");
        this.codeField = new Select2SingleChoice("codeField", new PropertyModel<>(this, "codeValue"), this.codeProvider);
        this.codeField.setLabel(Model.of("Code"));
        this.codeContainer.add(this.codeField);
        this.codeContainer.newFeedback("codeFeedback", this.codeField);

        this.typeColumn = this.row2.newUIColumn("typeColumn", Size.Six_6);
        this.typeContainer = this.typeColumn.newUIContainer("typeContainer");
        this.typeField = new Select2SingleChoice("typeField", new PropertyModel<>(this, "typeValue"), this.typeProvider);
        this.typeField.setRequired(true);
        this.typeField.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::typeFieldUpdate));
        this.typeField.setLabel(Model.of("Type"));
        this.typeContainer.add(this.typeField);
        this.typeContainer.newFeedback("typeFeedback", this.typeField);

        this.okayButton = new AjaxButton("okayButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                okayButtonSubmit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                okayButtonError(target);
            }

        };
        this.form.add(this.okayButton);

        this.cancelButton = new AjaxLink<Void>("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                cancelButtonClick(target);
            }
        };
        this.form.add(cancelButton);

        typeFieldUpdate(null);
    }

    protected void cancelButtonClick(AjaxRequestTarget target) {
        hide(target);
    }

    protected void typeFieldUpdate(AjaxRequestTarget target) {
        ColumnType columnType = null;
        if (this.typeValue != null) {
            columnType = ColumnType.valueOf(this.typeValue.getId());
        }
        boolean codeVisible = false;
        boolean lengthVisible = false;
        if (columnType != null) {
            codeVisible = columnType == ColumnType.DropDown;
            lengthVisible = columnType == ColumnType.String;
        }

        this.codeContainer.setVisible(codeVisible);
        this.lengthContainer.setVisible(lengthVisible);
        if (target != null) {
            target.add(this.lengthCodeColumn);
        }
    }

    @Override
    protected void restoreData(Map<String, Object> data) {
        this.nameValue = (String) data.get("nameValue");
        this.codeValue = (Option) data.get("codeValue");
        this.lengthValue = (Long) data.get("lengthValue");
        this.mandatoryValue = (Option) data.get("mandatoryValue");
        this.typeValue = (Option) data.get("typeValue");
    }

    protected void okayButtonSubmit(AjaxRequestTarget target) {
        hide(target);
        Map<String, Object> data = new HashMap<>();
        data.put("nameValue", this.nameValue);
        data.put("codeValue", this.codeValue);
        data.put("lengthValue", this.lengthValue);
        data.put("mandatoryValue", this.mandatoryValue);
        data.put("typeValue", this.typeValue);
        signalEvent("ok", data, target);
    }

    protected void okayButtonError(AjaxRequestTarget target) {
        target.add(this.form);
    }
}