package top.lingkang.oauth.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import top.lingkang.FinalManager;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.error.OauthClientException;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.client.ClientDetails;
import top.lingkang.oauth.server.client.ClientDetailsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/11
 */
@RestController
public class FinalAuthController {

    @Autowired
    private FinalManager manager;
    @Autowired
    private ClientDetailsManager clientDetailsManager;
    @Autowired
    private OauthCodeGenerate oauthCodeGenerate;


    public Object authorize(String client_id, String response_type, String redirect_uri, String param,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("code".equals(response_type)) {
            // 授权码模式
            ClientDetails clientDetails = clientDetailsManager.getClientDetails(client_id);
            if (clientDetails == null) {
                throw new OauthClientException(OauthConstants.CLIENT_ID_NOT_EXIST);
            }

            response.sendRedirect(redirect_uri + "?param=" + param);
            return null;
        }


        return null;
    }

    public Object confirm_access() {
        return null;
    }

    public Object token() {
        return null;
    }


}
