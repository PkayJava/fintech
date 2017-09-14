package com.angkorteam.fintech.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

public class ABCPage extends WebPage {
    private static final long serialVersionUID = 1L;

    private String pppp;

    int rows = 1;

    /**
     */
    public ABCPage() {
	this(true, 0);
    }

    public String getPppp() {
	return pppp;
    }

    public void setPppp(String pppp) {
	this.pppp = pppp;
    }

    public ABCPage(boolean enableInputField, int newPageId) {
	add(new Label("message", "If you see this message wicket is properly configured and running"));

	Form<Void> form = new Form<Void>("form");
	// WebMarkupContainer form = new WebMarkupContainer("form"); Both ways
	// do not work
	add(form);
	TextField<String> select;
	form.add(select = new TextField<String>("select", new PropertyModel<>(this, "pppp")));
	select.add(new OnChangeAjaxBehavior() {
	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (target != null) {
		    System.out.println("target is not null");
		}
		System.out.println("helllllooooohelllllooooohelllllooooohelllllooooohelllllooooo");
		System.out.println(pppp);
		// setResponsePage(ABCPage.class);
	    }
	});
    }
}
