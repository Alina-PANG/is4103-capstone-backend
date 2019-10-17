package capstone.is4103capstone.general.session;

import capstone.is4103capstone.entities.Employee;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SessionKeyAuthToken extends AbstractAuthenticationToken {

    private String sessionKey;
    private Employee principle;

    public SessionKeyAuthToken(Employee principle, String sessionKey) {
        super(null);
        this.principle = principle;
        this.sessionKey = sessionKey;
        setAuthenticated(true);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     *
     * @param principle
     * @param sessionKey
     * @param authorities
     */
    public SessionKeyAuthToken(Employee principle, String sessionKey,
                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principle = principle;
        this.sessionKey = sessionKey;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getCredentials() {
        return this.sessionKey;
    }

    public Object getPrincipal() {
        return this.principle;
    }

}
