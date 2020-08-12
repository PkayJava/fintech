//package com.angkorteam.fintech.popup.client.client;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//
//import com.angkorteam.fintech.popup.PopupPanel;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Maps;
//
//public class ClientUnassignStaffPopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton cancelButton;
//    protected AjaxButton confirmButton;
//
//    public ClientUnassignStaffPopup(String name) {
//        super(name, Maps.newHashMap());
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.cancelButton = new AjaxButton("cancelButton");
//        this.cancelButton.setOnSubmit(this::cancelButtonSubmit);
//        this.form.add(this.cancelButton);
//
//        this.confirmButton = new AjaxButton("confirmButton");
//        this.confirmButton.setOnSubmit(this::confirmButtonSubmit);
//        this.form.add(this.confirmButton);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected boolean cancelButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        closeWindow(ajaxButton.getId(), target);
//        return true;
//    }
//
//    protected boolean confirmButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        closeWindow(ajaxButton.getId(), target);
//        return true;
//    }
//
//}
