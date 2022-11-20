package pl.edu.pg.eti.kask.lab.user.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/food-app",
        callerQuery = "select password from users where user_name = ?",
        groupsQuery = "select role from users_roles where user = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class Config {
}
