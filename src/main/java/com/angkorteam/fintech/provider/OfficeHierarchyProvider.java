package com.angkorteam.fintech.provider;

import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/25/17.
 */
public class OfficeHierarchyProvider extends SortableTreeProvider<Map<String, Object>, String> {

    @Override
    public Iterator<? extends Map<String, Object>> getRoots() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> roots = jdbcTemplate.queryForList("select * from m_office where parent_id is NULL");
        if (roots == null) {
            return new java.util.ArrayList<Map<String, Object>>().listIterator();
        } else {
            return roots.iterator();
        }
    }

    @Override
    public boolean hasChildren(Map<String, Object> node) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        int count = jdbcTemplate.queryForObject("select count(*) from m_office where parent_id = ?", int.class, node.get("id"));
        return count > 0;
    }

    @Override
    public Iterator<? extends Map<String, Object>> getChildren(Map<String, Object> node) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from m_office where parent_id = ?", node.get("id"));
        if (children == null) {
            return new java.util.ArrayList<Map<String, Object>>().listIterator();
        } else {
            return children.iterator();
        }
    }

    @Override
    public IModel<Map<String, Object>> model(Map<String, Object> object) {
        return Model.ofMap(object);
    }

}
