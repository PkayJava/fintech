package com.angkorteam.fintech.provider;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.angkorteam.fintech.dto.EntityStatus;
import com.angkorteam.fintech.dto.EntityType;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public class EntityStatusProvider extends SingleChoiceProvider<Option> {

    private EntityType type;

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
        if (this.type != null) {
            for (EntityStatus status : this.type.getStatus()) {
                options.add(new Option(status.name(), status.getDescription()));
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

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

}
