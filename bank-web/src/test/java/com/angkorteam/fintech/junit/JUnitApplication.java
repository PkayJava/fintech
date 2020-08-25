//package com.angkorteam.fintech.junit;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.wicket.request.cycle.IRequestCycleListener;
//import org.apache.wicket.request.cycle.RequestCycle;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.RequestContextListener;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.angkorteam.fintech.Application;
//
//public class JUnitApplication extends Application implements IRequestCycleListener {
//
//    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";
//
//    @Override
//    protected void init() {
//        super.init();
//        getRequestCycleListeners().add(this);
//    }
//
//    @Override
//    public void onBeginRequest(RequestCycle cycle) {
//        HttpServletRequest request = (HttpServletRequest) cycle.getRequest().getContainerRequest();
//        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
//        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
//        LocaleContextHolder.setLocale(request.getLocale());
//        RequestContextHolder.setRequestAttributes(attributes);
//    }
//
//    @Override
//    public void onEndRequest(RequestCycle cycle) {
//        HttpServletRequest request = (HttpServletRequest) cycle.getRequest().getContainerRequest();
//        ServletRequestAttributes attributes = null;
//        Object reqAttr = request.getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
//        if (reqAttr instanceof ServletRequestAttributes) {
//            attributes = (ServletRequestAttributes) reqAttr;
//        }
//        RequestAttributes threadAttributes = RequestContextHolder.getRequestAttributes();
//        if (threadAttributes != null) {
//            // We're assumably within the original request thread...
//            LocaleContextHolder.resetLocaleContext();
//            RequestContextHolder.resetRequestAttributes();
//            if (attributes == null && threadAttributes instanceof ServletRequestAttributes) {
//                attributes = (ServletRequestAttributes) threadAttributes;
//            }
//        }
//        if (attributes != null) {
//            attributes.requestCompleted();
//        }
//    }
//
//}
