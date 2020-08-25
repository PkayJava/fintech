//package com.angkorteam.fintech.provider;
//
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMultipleChoiceProvider;
//
//public class MultipleChoiceProvider extends OptionMultipleChoiceProvider {
//
//    public MultipleChoiceProvider(String table, String idField) {
//        super(table, idField);
//    }
//
//    public MultipleChoiceProvider(String table, String idField, String queryField) {
//        super(table, idField, queryField);
//    }
//
//    public MultipleChoiceProvider(String table, String idField, String queryField, String orderBy) {
//        super(table, idField, queryField, orderBy);
//    }
//
//    @Override
//    protected JdbcNamed getNamed() {
//        return SpringBean.getBean(JdbcNamed.class);
//    }
//
//    public MultipleChoiceProvider(String table, String idField, String queryField, String orderBy, String labelField) {
//        super(table, idField, queryField, orderBy, labelField);
//    }
//
//}
