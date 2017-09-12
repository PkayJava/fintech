package com.angkorteam.fintech.provider;

import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.Delegate;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;

public class MifosSingleChoiceProvider extends OptionSingleChoiceProvider {

    public MifosSingleChoiceProvider(String table, String idField) {
        super(table, idField);
    }

    public MifosSingleChoiceProvider(String table, String idField, String queryField) {
        super(table, idField, queryField);
    }

    public MifosSingleChoiceProvider(String table, String idField, String queryField, String labelField) {
        super(table, idField, queryField, labelField);
    }

    @Override
    protected JdbcNamed getNamed() { 
        Delegate delegate = SpringBean.getBean(Delegate.class);
        return new JdbcNamed(delegate);
    }

}
