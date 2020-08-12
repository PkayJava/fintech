//package com.angkorteam.fintech.provider;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.fintech.ddl.MGroup;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.spring.JdbcTemplate;
//
///**
// * Created by socheatkhauv on 6/25/17.
// */
//public class GroupHierarchyProvider extends SortableTreeProvider<Map<String, Object>, String> {
//
//    @Override
//    public Iterator<? extends Map<String, Object>> getRoots() {
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//        List<Map<String, Object>> roots = jdbcTemplate.queryForList("select * from " + MGroup.NAME + " where " + MGroup.Field.PARENT_ID + " is NULL");
//        if (roots == null) {
//            return new java.util.ArrayList<Map<String, Object>>().listIterator();
//        } else {
//            return roots.iterator();
//        }
//    }
//
//    @Override
//    public boolean hasChildren(Map<String, Object> node) {
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//        int count = jdbcTemplate.queryForObject("select count(*) from " + MGroup.NAME + " where " + MGroup.Field.PARENT_ID + " = ?", int.class, node.get("id"));
//        return count > 0;
//    }
//
//    @Override
//    public Iterator<? extends Map<String, Object>> getChildren(Map<String, Object> node) {
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//        List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + MGroup.NAME + " where " + MGroup.Field.PARENT_ID + " = ?", node.get("id"));
//        if (children == null) {
//            return new java.util.ArrayList<Map<String, Object>>().listIterator();
//        } else {
//            return children.iterator();
//        }
//    }
//
//    @Override
//    public IModel<Map<String, Object>> model(Map<String, Object> object) {
//        return Model.ofMap(object);
//    }
//
//}
