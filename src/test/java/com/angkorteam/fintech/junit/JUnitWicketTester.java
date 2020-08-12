//package com.angkorteam.fintech.junit;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.apache.wicket.Component;
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxEventBehavior;
//import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
//import org.apache.wicket.behavior.AbstractAjaxBehavior;
//import org.apache.wicket.behavior.Behavior;
//import org.apache.wicket.core.request.handler.BookmarkableListenerRequestHandler;
//import org.apache.wicket.core.request.handler.ListenerRequestHandler;
//import org.apache.wicket.core.request.handler.PageAndComponentProvider;
//import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
//import org.apache.wicket.feedback.FeedbackMessage;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.Form;
//import org.apache.wicket.markup.html.form.RadioGroup;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
//import org.apache.wicket.request.IRequestHandler;
//import org.apache.wicket.request.Url;
//import org.apache.wicket.request.mapper.parameter.INamedParameters;
//import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.apache.wicket.util.lang.Args;
//import org.apache.wicket.util.tester.FormTester;
//import org.apache.wicket.util.tester.Result;
//import org.apache.wicket.util.tester.WicketTester;
//import org.apache.wicket.util.tester.WicketTesterHelper;
//import org.junit.Assert;
//
//import com.angkorteam.fintech.Application;
//import com.angkorteam.fintech.Constants;
//import com.angkorteam.fintech.MifosDataSourceManager;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.pages.LoginPage;
//import com.angkorteam.fintech.spring.NumberGenerator;
//import com.angkorteam.fintech.spring.NumberGeneratorImpl;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.spring.StringGeneratorImpl;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class JUnitWicketTester extends WicketTester {
//
//    private StringGenerator stringGenerator;
//
//    private NumberGenerator numberGenerator;
//
//    public JUnitWicketTester() {
//        super(JUnit.getApplication(), JUnit.getServletContext());
//        this.stringGenerator = new StringGeneratorImpl();
//        this.numberGenerator = new NumberGeneratorImpl();
//    }
//
//    @Override
//    public Session getSession() {
//        return (Session) super.getSession();
//    }
//
//    @Override
//    public Application getApplication() {
//        return (Application) super.getApplication();
//    }
//
//    public void login() {
//        this.startPage(LoginPage.class);
//
//        FormTester form = this.newFormTester("form");
//
//        form.setValue("identifierField", Constants.AID);
//        form.setValue("loginField", Constants.UID);
//        form.setValue("passwordField", Constants.PWD);
//        form.submit("loginButton");
//
//    }
//
//    public List<String> collectFeedbacks(Component component) {
//        List<String> feedbacks = new ArrayList<>();
//        List<FeedbackMessage> fs = getFeedbackMessages(new ComponentFeedbackMessageFilter(component));
//        if (fs != null) {
//            for (FeedbackMessage f : fs) {
//                if (f.getMessage() instanceof ValidationErrorFeedback) {
//                    feedbacks.add((String) ((ValidationErrorFeedback) f.getMessage()).getMessage());
//                } else {
//                    feedbacks.add((String) f.getMessage());
//                }
//            }
//        }
//        return feedbacks;
//    }
//
//    public void executeBehavior(Select2SingleChoice<?> select) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(select, OnChangeAjaxBehavior.EVENT_CHANGE);
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public void executeBehavior(CheckBox checkbox) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(checkbox, OnChangeAjaxBehavior.EVENT_CHANGE);
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public void executeBehavior(RadioGroup<?> radio) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(radio, "click");
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public void executeBehavior(TextField<?> text) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(text, OnChangeAjaxBehavior.EVENT_CHANGE);
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public void executeModalWindowOnClose(ModalWindow window) {
//        List<? extends Behavior> behaviors = window.getBehaviors();
//        for (Behavior behavior : behaviors) {
//            String clazzName = behavior.getClass().getName();
//            if (clazzName.contains("WindowClosedBehavior")) {
//                super.executeBehavior((AbstractAjaxBehavior) behavior);
//            }
//        }
//    }
//
//    public void executeAjaxButton(AjaxButton button) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(button, "click");
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public void executeAjaxLink(AjaxLink<?> link) {
//        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(link, "click");
//        if (behavior != null) {
//            super.executeBehavior(behavior);
//        }
//    }
//
//    public <T> T getComponentFromLastRenderedPage(String path, Class<T> clazz) {
//        Component component = super.getComponentFromLastRenderedPage(path);
//        return (T) component;
//    }
//
//    public void executeListener(final Component component, PageParameters parameters) {
//        Args.notNull(component, "component");
//
//        // there are two ways to do this. RequestCycle could be forced to call
//        // the
//        // handler
//        // directly but constructing and parsing the URL increases the chance of
//        // triggering bugs
//        Page page = component.getPage();
//        PageAndComponentProvider pageAndComponentProvider = new PageAndComponentProvider(page, component);
//
//        IRequestHandler handler = null;
//        if (page.isPageStateless() || (page.isBookmarkable() && page.wasCreatedBookmarkable())) {
//            handler = new BookmarkableListenerRequestHandler(pageAndComponentProvider);
//        } else {
//            handler = new ListenerRequestHandler(pageAndComponentProvider);
//        }
//
//        Url url = urlFor(handler);
//        if (parameters != null) {
//            for (NamedPair namedPair : parameters.getAllNamed()) {
//                if (namedPair.getType() == INamedParameters.Type.MANUAL) {
//                    url.addQueryParameter(namedPair.getKey(), namedPair.getValue());
//                }
//            }
//        }
//        getRequest().setUrl(url);
//
//        // Process the request
//        processRequest(getRequest(), null);
//    }
//
//    public StringGenerator getStringGenerator() {
//        return stringGenerator;
//    }
//
//    public NumberGenerator getNumberGenerator() {
//        return numberGenerator;
//    }
//
//    public JdbcTemplate getJdbcTemplate() {
//        MifosDataSourceManager manager = SpringBean.getBean(MifosDataSourceManager.class);
//        DataSource dataSource = manager.getDataSource(Constants.AID);
//        if (dataSource instanceof BasicDataSource) {
//            ((BasicDataSource) dataSource).setDefaultAutoCommit(true);
//        }
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        return jdbcTemplate;
//    }
//
//    public JdbcNamed getJdbcNamed() {
//        MifosDataSourceManager manager = SpringBean.getBean(MifosDataSourceManager.class);
//        DataSource dataSource = manager.getDataSource(Constants.AID);
//        JdbcNamed jdbcTemplate = new JdbcNamed(dataSource);
//        return jdbcTemplate;
//    }
//
//    @Override
//    public JUnitFormTester newFormTester(String path) {
//        return (JUnitFormTester) super.newFormTester(path);
//    }
//
//    public void assertErrorMessage() {
//        int level = FeedbackMessage.ERROR;
//        Result result = hasNoFeedbackMessage(level);
//        Assert.assertTrue(result.getMessage(), result.wasFailed());
//    }
//
//    @Override
//    public JUnitFormTester newFormTester(final String path, final boolean fillBlankString) {
//        return new JUnitFormTester(path, (Form<?>) getComponentFromLastRenderedPage(path), this, fillBlankString);
//    }
//
//}
