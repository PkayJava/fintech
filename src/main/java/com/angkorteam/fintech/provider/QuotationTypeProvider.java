package com.angkorteam.fintech.provider;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.SingleChoiceProvider;
import com.google.common.collect.Lists;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class QuotationTypeProvider extends SingleChoiceProvider<Option> {

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
        options.add(new Option("Newspaper", "Newspaper"));
        options.add(new Option("Reading book, Note book, Pass book", "Reading book, Note book, Pass book"));
        options.add(new Option("Desk Calendar", "Desk Calendar"));
        options.add(new Option("Wall Calendar", "Wall Calendar"));
        options.add(new Option("Sticker", "Sticker"));
        options.add(new Option("Bank Slip", "Bank Slip"));
        options.add(new Option("Box", "Box"));
        options.add(new Option("Hag tag", "Hag tag"));
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
