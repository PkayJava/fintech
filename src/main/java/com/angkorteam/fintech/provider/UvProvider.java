package com.angkorteam.fintech.provider;

import java.util.List;

import com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider;
import org.apache.wicket.model.IModel;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class UvProvider extends SingleChoiceProvider<Option> {

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
        options.add(new Option("None", "None"));
        options.add(new Option("Matt Vanish", "Matt Vanish"));
        options.add(new Option("Glossy Vanish", "Glossy Vanish"));
        options.add(new Option("Sandy Vanish", "Sandy Vanish"));
        options.add(new Option("Spot Vanish", "Spot Vanish"));
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
