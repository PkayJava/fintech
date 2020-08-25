//package com.angkorteam.fintech.provider;
//
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
///**
// * Created by socheatkhauv on 6/25/17.
// */
//public class AccountHierarchyProvider extends SortableTreeProvider<Map<String, Object>, String> {
//
//    @Override
//    public Iterator<? extends Map<String, Object>> getRoots() {
//        List<Map<String, Object>> roots = Lists.newArrayList();
//        Map<String, Object> root = Maps.newHashMap();
//        root.put("memory", true);
//        root.put("name", "Accounting");
//        roots.add(root);
//        return roots.iterator();
//    }
//
//    @Override
//    public boolean hasChildren(Map<String, Object> node) {
//        if (Boolean.TRUE.equals(node.get("memory"))) {
//            return true;
//        } else {
//            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//            int count = jdbcTemplate.queryForObject("select count(*) from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " = ?", int.class, node.get("id"));
//            return count > 0;
//        }
//    }
//
//    @Override
//    public Iterator<? extends Map<String, Object>> getChildren(Map<String, Object> node) {
//        if (Boolean.TRUE.equals(node.get("memory"))) {
//            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//            if ("Accounting".equals(node.get("name"))) {
//                List<Map<String, Object>> roots = Lists.newArrayList();
//                for (String name : Arrays.asList("Asset", "Liability", "Equity", "Income", "Expense")) {
//                    Map<String, Object> root = Maps.newHashMap();
//                    root.put("memory", true);
//                    root.put("name", name);
//                    roots.add(root);
//                }
//                return roots.iterator();
//            } else if ("Asset".equals(node.get("name"))) {
//                List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " is null and " + AccGLAccount.Field.CLASSIFICATION_ENUM + " = 1");
//                if (children == null) {
//                    return new java.util.ArrayList<Map<String, Object>>().listIterator();
//                } else {
//                    return children.iterator();
//                }
//            } else if ("Liability".equals(node.get("name"))) {
//                List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " is null and " + AccGLAccount.Field.CLASSIFICATION_ENUM + " = 2");
//                if (children == null) {
//                    return new java.util.ArrayList<Map<String, Object>>().listIterator();
//                } else {
//                    return children.iterator();
//                }
//            } else if ("Equity".equals(node.get("name"))) {
//                List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " is null and " + AccGLAccount.Field.CLASSIFICATION_ENUM + " = 3");
//                if (children == null) {
//                    return new java.util.ArrayList<Map<String, Object>>().listIterator();
//                } else {
//                    return children.iterator();
//                }
//            } else if ("Income".equals(node.get("name"))) {
//                List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " is null and " + AccGLAccount.Field.CLASSIFICATION_ENUM + " = 4");
//                if (children == null) {
//                    return new java.util.ArrayList<Map<String, Object>>().listIterator();
//                } else {
//                    return children.iterator();
//                }
//            } else if ("Expense".equals(node.get("name"))) {
//                List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from " + AccGLAccount.NAME + " where " + AccGLAccount.Field.PARENT_ID + " is null and " + AccGLAccount.Field.CLASSIFICATION_ENUM + " = 5");
//                if (children == null) {
//                    return new java.util.ArrayList<Map<String, Object>>().listIterator();
//                } else {
//                    return children.iterator();
//                }
//            } else {
//                return new java.util.ArrayList<Map<String, Object>>().listIterator();
//            }
//        } else {
//            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//            List<Map<String, Object>> children = jdbcTemplate.queryForList("select * from acc_gl_account where parent_id = ?", node.get("id"));
//            if (children == null) {
//                return new java.util.ArrayList<Map<String, Object>>().listIterator();
//            } else {
//                return children.iterator();
//            }
//        }
//    }
//
//    @Override
//    public IModel<Map<String, Object>> model(Map<String, Object> object) {
//        return Model.ofMap(object);
//    }
//
//}
