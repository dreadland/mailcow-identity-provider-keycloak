package land.dread.keycloak.provider;

import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

public class MailcowIdentityProviderFactory extends AbstractIdentityProviderFactory<MailcowIdentityProvider> implements SocialIdentityProviderFactory<MailcowIdentityProvider> {

    public static final String PROVIDER_ID = "mailcow";

    @Override
    public String getName() {
        return "Mailcow";
    }

    @Override
    public MailcowIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new MailcowIdentityProvider(session, new MailcowIdentityProviderConfig(model));
    }

    @Override
    public MailcowIdentityProviderConfig createConfig() {
        return new MailcowIdentityProviderConfig();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
