package com.angkorteam.fintech.widget;

import org.apache.http.util.Args;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class WebMarkupBlock extends WebMarkupContainer {
    
    protected Size size;

    public WebMarkupBlock(String id, Size size) {
        super(id);
        this.size = size;
        Args.notNull(size, "size");
        setOutputMarkupId(true);
    } 
    
    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        String clazz = tag.getAttribute("class");
        if (clazz == null || "".equals(clazz)) {
            clazz = this.size.getCss();
        } else {
            if (!clazz.contains(this.size.getCss())) {
                clazz = clazz + " " + this.size.getCss();
            }
        }
        if (clazz != null && !"".equals(clazz)) {
            tag.put("class", clazz);
        }
    }

    public static enum Size {
        One_1(1, "col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"), 
        Two_2(2, "col-xl-2 col-lg-2 col-md-2 col-sm-2 col-xs-2"), 
        Three_3(3, "col-xl-3 col-lg-3 col-md-3 col-sm-3 col-xs-3"), 
        Four_4(4, "col-xl-4 col-lg-4 col-md-4 col-sm-4 col-xs-4"), 
        Five_5(5, "col-xl-5 col-lg-5 col-md-5 col-sm-5 col-xs-5"), 
        Six_6(6, "col-xl-6 col-lg-6 col-md-6 col-sm-6 col-xs-6"), 
        Seven_7(7, "col-xl-7 col-lg-7 col-md-7 col-sm-7 col-xs-7"), 
        Eight_8(8, "col-xl-8 col-lg-8 col-md-8 col-sm-8 col-xs-8"), 
        Nine_9(9, "col-xl-9 col-lg-9 col-md-9 col-sm-9 col-xs-9"), 
        Ten_10(10, "col-xl-10 col-lg-10 col-md-10 col-sm-10 col-xs-10"), 
        Eleven_11(11, "col-xl-11 col-lg-11 col-md-11 col-sm-11 col-xs-11"), 
        Twelve_12(12, "col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12");

        protected String css;

        protected int size;

        Size(int size, String css) {
            this.css = css;
            this.size = size;
        }

        public String getCss() {
            return css;
        }

        public int getSize() {
            return size;
        }
    }

}
