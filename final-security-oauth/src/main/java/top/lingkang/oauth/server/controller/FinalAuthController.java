package top.lingkang.oauth.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.error.OauthClientException;
import top.lingkang.oauth.error.OauthExceptionHandler;
import top.lingkang.oauth.error.OauthTokenException;
import top.lingkang.oauth.server.OauthServerManager;
import top.lingkang.oauth.server.pojo.CheckTokenResponse;
import top.lingkang.oauth.server.pojo.OauthToken;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/11
 */
public class FinalAuthController implements Controller {
    @Autowired
    private OauthServerManager serverManager;

    @Autowired
    private OauthExceptionHandler exceptionHandler;

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

    private ModelAndView handler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if ("/oauth/check_token".equals(httpServletRequest.getServletPath())) {
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
            httpServletResponse.getWriter().print(json);
            httpServletResponse.setStatus(200);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return null;
        }

        if ("/oauth/authorize".equals(httpServletRequest.getServletPath())) {
            // authorize(httpServletRequest, httpServletResponse);
        }

        httpServletResponse.setStatus(404);
        return null;
    }


}
