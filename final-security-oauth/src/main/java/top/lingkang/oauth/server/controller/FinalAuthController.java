package top.lingkang.oauth.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import top.lingkang.FinalManager;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.constants.OauthViewPage;
import top.lingkang.oauth.error.OauthClientException;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.client.ClientDetails;
import top.lingkang.oauth.server.client.ClientDetailsManager;
import top.lingkang.oauth.utils.OauthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/11
 */
public class FinalAuthController implements Controller {
    @Autowired
    private FinalManager manager;
    @Autowired
    private ClientDetailsManager clientDetailsManager;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if ("/oauth/authorize".equals(httpServletRequest.getServletPath())) {
            authorize(httpServletRequest, httpServletResponse);
        }

        httpServletResponse.setStatus(404);
        return null;
    }




    public Object authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String response_type = request.getParameter("response_type");
        String client_id = request.getParameter("client_id");
        String scope = request.getParameter("scope");
        if ("code".equals(response_type)) { // 授权码模式
            ClientDetails clientDetails = clientDetailsManager.getClientDetails(client_id);
            if (clientDetails == null) {
                throw new OauthClientException(OauthConstants.CLIENT_ID_NOT_EXIST);
            }
            if (!OauthUtils.exists(clientDetails.getScopes(), scope)) {
                throw new OauthClientException(OauthConstants.NOT_IN_AUTHORIZE_SCOPE);
            }

            response.sendRedirect("/oauth/login_code?");
//            String code = oauthCodeGenerate.codeGenerate();
//            response.sendRedirect(redirect_uri + "?code=" + code + "&param=" + param);
            return null;
        }


        return null;
    }

    public Object login_code() {
        String loginCodeHtml = OauthViewPage.getLoginCodeHtml();
        return null;
    }

    public Object confirm_access(String username, String password, String loginId) {

        return null;
    }

    public Object token() {
        return null;
    }


}
