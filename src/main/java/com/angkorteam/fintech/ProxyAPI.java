package com.angkorteam.fintech;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (request.getContentType() != null && !"".equals(request.getContentType())) {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> resp = Unirest.post(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", request.getContentType()).body(bytes).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                } else {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> resp = Unirest.post(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).body(bytes).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                }
            } else if (request.getMethod().equalsIgnoreCase("PUT")) {
                if (request.getContentType() != null && !"".equals(request.getContentType())) {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> resp = Unirest.put(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", request.getContentType()).body(bytes).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                } else {
                    byte[] bytes = IOUtils.toByteArray(request.getInputStream());
                    HttpResponse<InputStream> resp = Unirest.put(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).body(bytes).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                }
            } else if (request.getMethod().equalsIgnoreCase("DELETE")) {
                if (request.getContentType() != null && !"".equals(request.getContentType())) {
                    HttpResponse<InputStream> resp = Unirest.delete(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", request.getContentType()).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                } else {
                    HttpResponse<InputStream> resp = Unirest.delete(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                }
            } else if (request.getMethod().equalsIgnoreCase("GET")) {
                if (request.getContentType() != null && !"".equals(request.getContentType())) {
                    HttpResponse<InputStream> resp = Unirest.get(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).header("Content-Type", request.getContentType()).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                } else {
                    HttpResponse<InputStream> resp = Unirest.get(mifosUrl).header("Accept", request.getHeader("Accept")).header("Authorization", authorization).header("Fineract-Platform-TenantId", identifier).asBinary();
                    IOUtils.copy(resp.getBody(), response.getOutputStream());
                    response.setContentType(resp.getHeaders().getFirst("Content-Type"));
                    response.setStatus(resp.getStatus());
                }

            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
            String method = req.getHeader("Access-Control-Request-Method");
            String header = req.getHeader("Access-Control-Request-Headers");
            resp.addHeader("Allow", "OPTIONS");
            resp.addHeader("Allow", method);
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
