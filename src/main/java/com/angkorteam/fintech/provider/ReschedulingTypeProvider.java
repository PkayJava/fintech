package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.ISingleChoiceProvider;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import org.apache.wicket.model.IModel;

import java.util.List;

public class ReschedulingTypeProvider extends ISingleChoiceProvider {

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
        for (ReschedulingType value : ReschedulingType.values()) {
            options.add(value.toOption());
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
