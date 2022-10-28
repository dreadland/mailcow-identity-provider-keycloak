## Mailcow Identity Provider for Keycloak

This extension adds a new social provider to [Keycloak](https://www.keycloak.org/), which will allow federation to a [Mailcow](https://mailcow.email) instance.

### Building
Run `gradle jar`

This will create the file `build/libs/mailcow-identity-provider-keycloak-0.1.0.jar`

### Installing
Place the jar file in `/opt/keycloak/providers`

### Configuration

1. In the admin console for the realm you want, add Mailcow as an Identity Provider
2. Use the Redirect URI provided and add an OAuth2 client to Mailcow
3. Use the Client ID and Secret generated, along with the base URL to the Mailcow server and complete the configuration in Keycloak

**WARNING** The configuration form on the Keycloak Admin console only shows the required baseURL parameter when the theme is set to keycloak.  Unfortunately, the default theme is keycloak.v2
