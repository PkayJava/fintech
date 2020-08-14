package com.angkorteam.fintech.data;

import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.tenant.MOffice;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.metamodel.DataContext;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/25/17.
 */
public class OfficeHierarchyProvider extends SortableTreeProvider<Map<String, Object>, String> {

    @Override
    public Iterator<? extends Map<String, Object>> getRoots() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifosDataSource = context.getBean(MifosDataSourceManager.class);
        MifosDataContextManager mifosDataContext = context.getBean(MifosDataContextManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifosDataSource.getDataSource(session.getIdentifier());
        DataContext dataContext = mifosDataContext.getDataContext(session.getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> roots = jdbcTemplate.queryForList("SELECT * FROM " + mOffice.getName() + " WHERE " + mOffice.PARENT_ID.getName() + " is NULL");
        return roots.iterator();
    }

    @Override
    public boolean hasChildren(Map<String, Object> node) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifosDataSource = context.getBean(MifosDataSourceManager.class);
        MifosDataContextManager mifosDataContext = context.getBean(MifosDataContextManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifosDataSource.getDataSource(session.getIdentifier());
        DataContext dataContext = mifosDataContext.getDataContext(session.getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(" + mOffice.ID.getName() + ") FROM " + mOffice.getName() + " WHERE " + mOffice.PARENT_ID.getName() + " = ?", int.class, node.get("id"));
        return count != null && count > 0;
    }

    @Override
    public Iterator<? extends Map<String, Object>> getChildren(Map<String, Object> node) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifosDataSource = context.getBean(MifosDataSourceManager.class);
        MifosDataContextManager mifosDataContext = context.getBean(MifosDataContextManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifosDataSource.getDataSource(session.getIdentifier());
        DataContext dataContext = mifosDataContext.getDataContext(session.getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> children = jdbcTemplate.queryForList("SELECT * FROM " + mOffice.getName() + " WHERE " + mOffice.PARENT_ID.getName() + " = ?", node.get("id"));
        return children.iterator();
    }

    @Override
    public IModel<Map<String, Object>> model(Map<String, Object> object) {
        return Model.ofMap(object);
    }

}
