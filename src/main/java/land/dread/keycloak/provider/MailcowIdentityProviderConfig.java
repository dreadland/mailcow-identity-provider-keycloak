package land.dread.keycloak.provider;

import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class MailcowIdentityProviderConfig extends OAuth2IdentityProviderConfig {

    public MailcowIdentityProviderConfig(IdentityProviderModel identityProviderModel) {
        super(identityProviderModel);
    }

    public MailcowIdentityProviderConfig() {
    }

    public String getBaseUrl() {
        return getConfig().get("baseUrl");
    }

    public void setBaseUrl(String base) {
        getConfig().put("baseUrl", base);
    }
}
