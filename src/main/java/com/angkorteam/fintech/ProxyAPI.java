package com.angkorteam.fintech;

import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class ProxyAPI implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyAPI.class);

    @RequestMapping(path = "/**")
    public void doProxy(HttpServletRequest request, HttpServletResponse response) throws IOException, UnirestException {
        HttpSession session = request.getSession(true);
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        String infoPath = request.getPathInfo();
        if ("/v1/authentication".equals(infoPath)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String identifier = request.getHeader("Fineract-Platform-TenantId");
            if (identifier == null || "".equals(identifier)) {
                identifier = request.getParameter("tenantIdentifier");
            }
            if (username == null || "".equals(username) || password == null || "".equals(password) || identifier == null || "".equals(identifier)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                MifosDataSourceManager mifos = applicationContext.getBean(MifosDataSourceManager.class);
                String mifosUrl = mifos.getMifosUrl() + "/api/v1/authentication?username=" + username + "&password=" + password;
                HttpResponse<InputStream> resp = Unirest.post(mifosUrl).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asBinary();
                IOUtils.copy(resp.getBody(), response.getOutputStream());
                response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                response.setStatus(resp.getStatus());
            }
            return;
        } else {
            ServletContext servletContext = request.getServletContext();
            String identifier = request.getHeader("Fineract-Platform-TenantId");
            String authorization = request.getHeader("Authorization");
            if (identifier == null || "".equals(identifier) || authorization == null || "".equals(authorization)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            session.setAttribute(Session.IDENTIFIER, identifier);
            MifosDataSourceManager mifos = applicationContext.getBean(MifosDataSourceManager.class);
            Gson gson = applicationContext.getBean(Gson.class);
            if (infoPath == null || "".equals(infoPath) || "/".equals(infoPath)) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                Map<String, String> json = new HashMap<>();
                json.put("server-time", DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
                String manifest = "/META-INF/MANIFEST.MF";
                String absoluteDiskPath = servletContext.getRealPath(manifest);
                File file = new File(absoluteDiskPath);
                if (file.exists()) {
                    try {
                        List<String> lines = FileUtils.readLines(file, "UTF-8");
                        for (String line : lines) {
                            if (line != null && !"".equals(line)) {
                                if (line.startsWith("Version")) {
                                    json.put("version", StringUtils.trimToEmpty(StringUtils.substring(line, "Version:".length())));
                                } else if (line.startsWith("Build-Time")) {
                                    json.put("build-time", StringUtils.trimToEmpty(StringUtils.substring(line, "Build-Time:".length())));
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new WicketRuntimeException(e);
                    }
                } else {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = null;
                    try {
                        builder = factory.newDocumentBuilder();
                    } catch (ParserConfigurationException e) {
                        throw new WicketRuntimeException(e);
                    }
                    File popFile = new File("pom.xml");
                    Document document = null;
                    try {
                        document = builder.parse(popFile);
                    } catch (SAXException | IOException e) {
                        throw new WicketRuntimeException(e);
                    }
                    json.put("version", document.getDocumentElement().getElementsByTagName("version").item(0).getTextContent());
                    json.put("build-time", DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
                }
                gson.toJson(json, response.getWriter());
                return;
            }
            String mifosUrl = null;
            if (request.getQueryString() != null && !"".equals(request.getQueryString())) {
                mifosUrl = mifos.getMifosUrl() + "/api" + infoPath + "?" + request.getQueryString();
            } else {
                mifosUrl = mifos.getMifosUrl() + "/api" + infoPath;
            }
            LOGGER.info("mifos url {}", mifosUrl);
            String requestContentType = StringUtils.lowerCase(request.getContentType());
            String method = StringUtils.upperCase(request.getMethod());
            String accept = StringUtils.lowerCase(request.getHeader("Accept"));

            HttpResponse<String> resp = null;
            String responseContentType = null;
            String requestBody = null;

            boolean handled = true;

            if (method.equals("POST")) {
                if (requestContentType != null && !"".equals(requestContentType)) {
                    if ("application/json".equals(requestContentType)) {
                        requestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
                        resp = Unirest.post(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).body(requestBody).asString();
                        responseContentType = StringUtils.lowerCase(resp.getHeaders().getFirst("Content-Type"));
                        response.getWriter().write(resp.getBody());
                        response.setContentType(responseContentType);
                        response.setStatus(resp.getStatus());
                    } else {
                        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                        HttpResponse<InputStream> temp = Unirest.post(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).body(bytes).asBinary();
                        IOUtils.copy(temp.getBody(), response.getOutputStream());
                        response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                        response.setStatus(temp.getStatus());
                    }
                } else {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> temp = Unirest.post(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).body(bytes).asBinary();
                    IOUtils.copy(temp.getBody(), response.getOutputStream());
                    response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(temp.getStatus());
                }
            } else if (method.equals("PUT")) {
                if (requestContentType != null && !"".equals(requestContentType)) {
                    if ("application/json".equals(requestContentType)) {
                        requestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
                        resp = Unirest.put(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).body(requestBody).asString();
                        responseContentType = StringUtils.lowerCase(resp.getHeaders().getFirst("Content-Type"));
                        response.getWriter().write(resp.getBody());
                        response.setContentType(responseContentType);
                        response.setStatus(resp.getStatus());
                    } else {
                        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                        HttpResponse<InputStream> temp = Unirest.put(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).body(bytes).asBinary();
                        IOUtils.copy(temp.getBody(), response.getOutputStream());
                        response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                        response.setStatus(temp.getStatus());
                    }
                } else {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> temp = Unirest.put(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).body(bytes).asBinary();
                    IOUtils.copy(temp.getBody(), response.getOutputStream());
                    response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(temp.getStatus());
                }
            } else if (method.equals("DELETE")) {
                if (requestContentType != null && !"".equals(requestContentType)) {
                    if ("application/json".equals(requestContentType)) {
                        resp = Unirest.delete(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).asString();
                        responseContentType = StringUtils.lowerCase(resp.getHeaders().getFirst("Content-Type"));
                        response.getWriter().write(resp.getBody());
                        response.setContentType(responseContentType);
                        response.setStatus(resp.getStatus());
                    } else {
                        HttpResponse<InputStream> temp = Unirest.delete(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).asBinary();
                        IOUtils.copy(temp.getBody(), response.getOutputStream());
                        response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                        response.setStatus(temp.getStatus());
                    }
                } else {
                    HttpResponse<InputStream> temp = Unirest.delete(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).asBinary();
                    IOUtils.copy(temp.getBody(), response.getOutputStream());
                    response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(temp.getStatus());
                }
            } else if (method.equals("GET")) {
                if (requestContentType != null && !"".equals(requestContentType)) {
                    if (requestContentType.equals("application/json")) {
                        resp = Unirest.get(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).asString();
                        responseContentType = StringUtils.lowerCase(resp.getHeaders().getFirst("Content-Type"));
                        response.getWriter().write(resp.getBody());
                        response.setContentType(responseContentType);
                        response.setStatus(resp.getStatus());
                    } else {
                        HttpResponse<InputStream> temp = Unirest.get(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", requestContentType).asBinary();
                        IOUtils.copy(temp.getBody(), response.getOutputStream());
                        response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                        response.setStatus(temp.getStatus());
                    }
                } else {
                    if (accept.contains("application/json")) {
                        resp = Unirest.get(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).asString();
                        responseContentType = StringUtils.lowerCase(resp.getHeaders().getFirst("Content-Type"));
                        response.getWriter().write(resp.getBody());
                        response.setContentType(responseContentType);
                        response.setStatus(resp.getStatus());
                    } else {
                        HttpResponse<InputStream> temp = Unirest.get(mifosUrl).header("Accept", accept).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).asBinary();
                        IOUtils.copy(temp.getBody(), response.getOutputStream());
                        response.setContentType(temp.getHeaders().getFirst("Content-Type"));
                        response.setStatus(temp.getStatus());
                    }
                }
            } else {
                handled = false;
            }

            if (!handled) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if (resp != null) {
                    JdbcNamed named = applicationContext.getBean(JdbcNamed.class);
                    String uuid = named.queryForObject("select uuid() from dual", new HashMap<>(), String.class);
                    InsertQuery insertQuery = null;
                    insertQuery = new InsertQuery("tbl_proxy");
                    insertQuery.addValue("id = :id", uuid);
                    insertQuery.addValue("path = :path", mifosUrl);
                    insertQuery.addValue("method = :method", method);
                    insertQuery.addValue("accept = :accept", accept);
                    insertQuery.addValue("request_content_type = :request_content_type", requestContentType);
                    if (requestBody != null) {
                        insertQuery.addValue("request_body = :request_body", requestBody);
                    }
                    insertQuery.addValue("response_body = :response_body", resp.getBody());
                    insertQuery.addValue("response_status = :response_status", resp.getStatus());
                    insertQuery.addValue("response_status_text = :response_status_text", resp.getStatusText());
                    insertQuery.addValue("response_content_type = :response_content_type", responseContentType);
                    insertQuery.addValue("created_datetime = :created_datetime", new Date());
                    named.update(insertQuery.toSQL(), insertQuery.getParam());

                    Enumeration<String> headers = request.getHeaderNames();
                    while (headers.hasMoreElements()) {
                        insertQuery = new InsertQuery("tbl_proxy_request_header");
                        String header = headers.nextElement();
                        insertQuery.addValue("id = uuid()");
                        insertQuery.addValue("proxy_id = :proxy_id", uuid);
                        insertQuery.addValue("h_name = :h_name", header);
                        insertQuery.addValue("h_value = :h_value", request.getHeader(header));
                        named.update(insertQuery.toSQL(), insertQuery.getParam());
                    }
                    for (Map.Entry<String, List<String>> header : resp.getHeaders().entrySet()) {
                        if (header.getValue() != null && !header.getValue().isEmpty()) {
                            insertQuery = new InsertQuery("tbl_proxy_response_header");
                            insertQuery.addValue("id = uuid()");
                            insertQuery.addValue("proxy_id = :proxy_id", uuid);
                            insertQuery.addValue("h_name = :h_name", header.getKey());
                            insertQuery.addValue("h_value = :h_value", StringUtils.join(header.getValue(), ", "));
                            named.update(insertQuery.toSQL(), insertQuery.getParam());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            String header = req.getHeader("Access-Control-Request-Headers");
            resp.addHeader("Allow", "OPTIONS");
            resp.addHeader("Allow", "GET");
            resp.addHeader("Allow", "POST");
            resp.addHeader("Allow", "PUT");
            resp.addHeader("Allow", "DELETE");
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Access-Control-Allow-Methods", "GET");
            resp.addHeader("Access-Control-Allow-Methods", "POST");
            resp.addHeader("Access-Control-Allow-Methods", "PUT");
            resp.addHeader("Access-Control-Allow-Methods", "DELETE");
            resp.addHeader("Access-Control-Allow-Methods", "OPTIONS");
            resp.addHeader("Access-Control-Allow-Headers", header);
            return;
        } else {
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Access-Control-Allow-Methods", "GET");
            resp.addHeader("Access-Control-Allow-Methods", "POST");
            resp.addHeader("Access-Control-Allow-Methods", "PUT");
            resp.addHeader("Access-Control-Allow-Methods", "DELETE");
            resp.addHeader("Access-Control-Allow-Methods", "OPTIONS");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
