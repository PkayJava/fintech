package com.angkorteam.fintech.provider;

import java.util.List;

import com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider;
import org.apache.wicket.model.IModel;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

public class ChargeCalculationProvider extends SingleChoiceProvider<Option> {

    private ChargeCalculation[] values;

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
            for (ChargeCalculation option : this.values) {
                options.add(new Option(option.name(), option.getDescription()));
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

    public ChargeCalculation[] getValues() {
        return values;
    }

    public void setValues(ChargeCalculation... values) {
        this.values = values;
    }

}