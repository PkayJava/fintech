//package com.angkorteam.fintech.junit;
//
//import java.util.List;
//
//import org.apache.wicket.Component;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.markup.html.form.Form;
//import org.apache.wicket.markup.html.form.FormComponent;
//import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
//import org.apache.wicket.util.lang.Args;
//import org.apache.wicket.util.string.StringValue;
//import org.apache.wicket.util.tester.BaseWicketTester;
//import org.apache.wicket.util.tester.FormTester;
//
//import com.google.common.collect.Lists;
//
//public class JUnitFormTester extends FormTester {
//
//    private BaseWicketTester wicket;
//
//    private Form<?> workingForm;
//
//    protected JUnitFormTester(String path, Form<?> workingForm, BaseWicketTester wicketTester, boolean fillBlankString) {
//        super(path, workingForm, wicketTester, fillBlankString);
//        this.wicket = wicketTester;
//        this.workingForm = workingForm;
//    }
//
//    public JUnitFormTester setValue(Component formComponent, List<String> values) {
//        Args.notNull(formComponent, "formComponent");
//
//        List<StringValue> value = Lists.newArrayList();
//        for (String v : values) {
//            value.add(StringValue.valueOf(v));
//        }
//        if (formComponent instanceof IFormSubmittingComponent) {
//            this.wicket.getRequest().getPostParameters().setParameterValues(((IFormSubmittingComponent) formComponent).getInputName(), value);
//        } else if (formComponent instanceof FormComponent) {
//            this.wicket.getRequest().getPostParameters().setParameterValues(((IFormSubmittingComponent) formComponent).getInputName(), value);
//        } else {
//            fail("Component with id: " + formComponent.getId() + " is not a FormComponent");
//        }
//
//        return this;
//    }
//
//    public JUnitFormTester setValue(String formComponentId, List<String> values) {
//
//        Component formComponent = this.workingForm.get(formComponentId);
//
//        setValue(formComponent, values);
//
//        return this;
//    }
//
//    @Override
//    public JUnitFormTester setValue(Component formComponent, String value) {
//        return (JUnitFormTester) super.setValue(formComponent, value);
//    }
//
//    @Override
//    public JUnitFormTester setValue(String checkBoxId, boolean value) {
//        return (JUnitFormTester) super.setValue(checkBoxId, value);
//    }
//
//    @Override
//    public JUnitFormTester setValue(String formComponentId, String value) {
//        return (JUnitFormTester) super.setValue(formComponentId, value);
//    }
//
//    public JUnitFormTester setValue(String formComponentId, int value) {
//        return (JUnitFormTester) super.setValue(formComponentId, String.valueOf(value));
//    }
//
//    public JUnitFormTester setValue(String formComponentId, long value) {
//        return (JUnitFormTester) super.setValue(formComponentId, String.valueOf(value));
//    }
//
//    public JUnitFormTester setValue(String formComponentId, double value) {
//        return (JUnitFormTester) super.setValue(formComponentId, String.valueOf(value));
//    }
//
//    public JUnitFormTester setValue(String formComponentId, float value) {
//        return (JUnitFormTester) super.setValue(formComponentId, String.valueOf(value));
//    }
//
//    public JUnitFormTester setValue(String formComponentId, Enum<?> value) {
//        return (JUnitFormTester) super.setValue(formComponentId, value.name());
//    }
//
//    protected void fail(String message) {
//        throw new WicketRuntimeException(message);
//    }
//
//}
