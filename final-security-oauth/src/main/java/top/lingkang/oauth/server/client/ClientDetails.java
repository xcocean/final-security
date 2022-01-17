package top.lingkang.oauth.server.client;

/**
 * @author lingkang
 * @date 2022/1/17
 */
public class ClientDetails {
    private String clientId;
    private String secret;
    private String authorizedGrantTypes;
    private String[] scopes;
    private String[] resourceId;
    private String redirectUris;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String[] getResourceId() {
        return resourceId;
    }

    public void setResourceId(String[] resourceId) {
        this.resourceId = resourceId;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }
}
