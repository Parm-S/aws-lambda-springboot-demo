package com.spring_boot_assignment.filter;


import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.serverless.proxy.RequestReader;

import javax.servlet.*;
import java.io.IOException;

public class CognitoIdentityFilter implements Filter {
    public static final String COGNITO_IDENTITY_ATTRIBUTE = "com.amazonaws.serverless.cognitoId";

    private static final Logger log = LoggerFactory.getLogger(CognitoIdentityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object apiGwtContext = servletRequest.getAttribute(RequestReader.API_GATEWAY_CONTEXT_PROPERTY);

        if (apiGwtContext == null) {
            log.warn("API GWT context is null");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (!AwsProxyRequestContext.class.isAssignableFrom(apiGwtContext.getClass())) {
            log.warn("API Gateway context object is not of valid type");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        assert apiGwtContext instanceof AwsProxyRequestContext;
        AwsProxyRequestContext ctx = (AwsProxyRequestContext) apiGwtContext;
        if (ctx.getIdentity() == null) {
            log.warn("Identity context is null");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String cognitoIdentityId = ctx.getIdentity().getCognitoIdentityId();
        if (cognitoIdentityId == null || "".equals(cognitoIdentityId.trim())) {
            log.warn("Cognito identity id in request is null");
        }
        servletRequest.setAttribute(COGNITO_IDENTITY_ATTRIBUTE, cognitoIdentityId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
