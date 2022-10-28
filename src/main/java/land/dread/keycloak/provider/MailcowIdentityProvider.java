package land.dread.keycloak.provider;

import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider;
import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.KeycloakSession;

import java.util.Arrays;

public class MailcowIdentityProvider extends AbstractOAuth2IdentityProvider<MailcowIdentityProviderConfig> implements SocialIdentityProvider<MailcowIdentityProviderConfig> {

    private static final Logger logger = Logger.getLogger(MailcowIdentityProvider.class);

    public static final String AUTH_ENDPOINT = "/oauth/authorize";
    public static final String TOKEN_ENDPOINT = "/oauth/token";
    public static final String RESOURCE_PAGE = "/oauth/profile";
    public static final String DEFAULT_SCOPE = "profile";

    public MailcowIdentityProvider(KeycloakSession session, MailcowIdentityProviderConfig config) {
        super(session, config);

        String base = config.getBaseUrl();
        config.setAuthorizationUrl(base + AUTH_ENDPOINT);
        config.setTokenUrl(base + TOKEN_ENDPOINT);
        config.setUserInfoUrl(base + RESOURCE_PAGE);
    }

    @Override
    protected String getDefaultScopes() {
	return DEFAULT_SCOPE;
    }

    @Override
    protected BrokeredIdentityContext doGetFederatedIdentity(String accessToken) {
      try {
          JsonNode profile = SimpleHttp.doGet(getConfig().getUserInfoUrl(), this.session).auth(accessToken).asJson();
          return extractIdentityFromProfile(null, profile);
      } catch (Exception e) {
          throw new IdentityBrokerException("Could not obtain profile from Mailcow server", e);
      }
    }

    @Override
    protected BrokeredIdentityContext extractIdentityFromProfile(EventBuilder event, JsonNode profile) {
        BrokeredIdentityContext user = new BrokeredIdentityContext(getJsonProperty(profile, "id"));
        user.setUsername(getJsonProperty(profile, "username"));
        user.setEmail(getJsonProperty(profile, "email"));
        String []names = getJsonProperty(profile, "displayName").split("\\s+");
        String firstName = names[0];
        String lastName = String.join(" ", Arrays.copyOfRange(names, 1, names.length));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
