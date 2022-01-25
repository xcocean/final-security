package top.lingkang.oauth.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.error.OauthClientException;
import top.lingkang.oauth.error.OauthExceptionHandler;
import top.lingkang.oauth.error.OauthTokenException;
import top.lingkang.oauth.server.OauthServerHolder;
import top.lingkang.oauth.server.OauthServerManager;
import top.lingkang.oauth.server.pojo.CheckTokenResponse;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * @date 2022/1/11
 */
public class AuthServerController implements Controller {
    private static final Log log = LogFactory.getLog(AuthServerController.class);
    @Autowired
    private OauthServerManager serverManager;

    @Autowired
    private OauthExceptionHandler exceptionHandler;

    @Autowired
    private OauthServerHolder oauthServerHolder;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        try {
            return handler(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            if (e instanceof OauthTokenException) {
                exceptionHandler.oauthTokenException(e, httpServletRequest, httpServletResponse);
            } else if (e instanceof OauthClientException) {
                exceptionHandler.oauthClientException(e, httpServletRequest, httpServletResponse);
            } else {
                exceptionHandler.oauthOtherException(e, httpServletRequest, httpServletResponse);
            }
        }
        return null;
    }

    private ModelAndView handler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("/oauth/check_token".equals(request.getServletPath())) {
            String token = serverManager.getToken();
            FinalSession session = serverManager.getStorageManager().getSession(token);
            if (session == null) {
                throw new OauthTokenException(OauthConstants.Token_Invalid);
            }
            CheckTokenResponse res = new CheckTokenResponse();
            res.setId(session.getId());
            res.setUser(session.getUser());
            res.setRole(serverManager.getStorageManager().getRoles(token));
            res.setPermission(serverManager.getStorageManager().getPermission(token));
            String json = objectMapper.writeValueAsString(res);
            response.getWriter().print(json);
            response.setStatus(200);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return null;
        }

        if ("/oauth/authorize".equals(request.getServletPath())) {
            // authorize(httpServletRequest, httpServletResponse);
        }

        if ("/oauth/token".equals(request.getServletPath())) {
            Object result = oauthServerHolder.oauthLogin(
                    request.getParameter("id"),
                    request.getParameter("user"),
                    request.getParameterValues("role"),
                    request.getParameterValues("role")
            );
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(result);
            return null;
        }
        printError("404 不存在的请求路径", request, response, 404);
        return null;
    }

    private void printError(String msg, HttpServletRequest request, HttpServletResponse response, int code) {
        log.warn(msg + "  url=" + request.getServletPath());
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = "text/html;charset=UTF-8";
        }
        response.setContentType(contentType);
        response.setStatus(code);
        try {
            if (contentType.toLowerCase().indexOf("json") != -1) {
                response.getWriter().print("{\"code\":" + code + ",\"msg\":\"" + msg + "\"}");
            } else {
                response.getWriter().print(msg);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
