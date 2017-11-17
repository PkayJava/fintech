package com.angkorteam.fintech.provider;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

public class OptionProvider extends com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider<Option> {

    private Option[] values;

    public OptionProvider(Option... values) {
        this.values = values;
    }

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

    public Option[] getValues() {
        return values;
    }

    @Override
    public List<Option> query(String term, int page) {
        if (this.values == null) {
            return Lists.newArrayList();
        }
        List<Option> options = Lists.newArrayList();
        for (Option value : this.values) {
            options.add(value);
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

}
