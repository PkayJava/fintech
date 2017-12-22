package com.angkorteam.fintech.widget.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.pages.client.client.ClientFamilyMemberCreatePage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class ClientPreviewFamilyMemberPanel extends com.angkorteam.fintech.widget.Panel {

    protected String clientId;

    protected Page itemPage;

    protected BookmarkablePageLink<Void> createLink;

    protected List<IColumn<Map<String, Object>, String>> memberColumn;
    protected DataTable<Map<String, Object>, String> memberTable;
    protected JdbcProvider memberProvider;

    public ClientPreviewFamilyMemberPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
    }

    @Override
    protected void initComponent() {

        this.memberProvider = new JdbcProvider("m_family_members");
        this.memberProvider.applyJoin("relationship", "left join m_code_value relationship on m_family_members.relationship_cv_id = relationship.id");
        this.memberProvider.applyJoin("gender", "left join m_code_value gender on m_family_members.gender_cv_id = gender.id");
        this.memberProvider.boardField("m_family_members.firstname", "first_name", String.class);
        this.memberProvider.boardField("m_family_members.middlename", "middle_name", String.class);
        this.memberProvider.boardField("m_family_members.lastname", "last_name", String.class);
        this.memberProvider.boardField("m_family_members.mobile_number", "mobile_number", String.class);
        this.memberProvider.boardField("gender.code_description", "gender_description", String.class);
        this.memberProvider.boardField("relationship.code_description", "relationship_description", String.class);
        this.memberProvider.boardField("m_family_members.is_dependent", "dependent", Boolean.class);
        this.memberProvider.boardField("m_family_members.date_of_birth", "date_of_birth", Calendar.Date);
        this.memberProvider.applyWhere("client_id", "m_family_members.client_id = '" + this.clientId + "'");

        this.memberColumn = Lists.newArrayList();
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("First Name"), "first_name", "first_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Middle Name"), "middle_name", "middle_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Last Name"), "last_name", "last_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Mobile Number"), "mobile_number", "mobile_number", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Gender"), "gender_description", "gender_description", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Relationship"), "relationship_description", "relationship_description", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.Boolean, Model.of("Dependent"), "dependent", "dependent", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.Date, Model.of("Date Of Birth"), "date_of_birth", "date_of_birth", this::memberColumn));

        this.memberTable = new DefaultDataTable<>("memberTable", this.memberColumn, this.memberProvider, 20);
        add(this.memberTable);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.createLink = new BookmarkablePageLink<>("createLink", ClientFamilyMemberCreatePage.class, parameters);
        add(this.createLink);

    }

    protected ItemPanel memberColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("first_name".equals(column) || "middle_name".equals(column) || "last_name".equals(column) || "mobile_number".equals(column) || "gender_description".equals(column) || "relationship_description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("dependent".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value != null && value == 1);
        } else if ("date_of_birth".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
