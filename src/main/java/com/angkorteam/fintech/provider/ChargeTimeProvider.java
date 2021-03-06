package com.angkorteam.fintech.provider;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider;
import com.google.common.collect.Lists;

public class ChargeTimeProvider extends SingleChoiceProvider<Option> {

    private ChargeTime[] values;

    @Override
    public Option toChoice(String id) {
        List<Option> options = query("", 0);
        for (Option option : options) {
            if (option.getId().equals(id)) {
                return option;
            }
        }
        return null;
    }

    @Override
    public List<Option> query(String term, int page) {
        List<Option> options = Lists.newArrayList();
        if (this.values != null && this.values.length > 0) {
            for (ChargeTime value : this.values) {
                options.add(value.toOption());
            }
        }
        return options;
    }

    @Override
    public boolean hasMore(String term, int page) {
        return false;
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        for (Option option : choices.getObject()) {
            if (option.getId().equals(id)) {
                return option;
            }
        }
        return null;
    }

    public ChargeTime[] getValues() {
        return values;
    }

    public void setValues(ChargeTime... values) {
        this.values = values;
    }

}