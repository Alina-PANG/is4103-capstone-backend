package capstone.is4103capstone.general.session;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.UserAuthenticationService;
import capstone.is4103capstone.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class SessionKeyAuthenticationFilter extends OncePerRequestFilter {

    private static String AUTHORIZATION_HEADER_NAME = "X-Authorization";

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/api/ua/**", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get X-Authorization from header
        // null check
        if (Objects.isNull(request.getHeader(AUTHORIZATION_HEADER_NAME))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "MISSING_AUTHENTICATION_HEADER - The authentication header is missing. Please check the HTTP request and try again.");
        } else {
            String xAuth = request.getHeader(AUTHORIZATION_HEADER_NAME);
            System.err.println("SESSION KEY: " + xAuth);

            // validate the value in xAuth
            try {
                Employee employee;
                // manual override for development
                if (xAuth.equalsIgnoreCase("admin")) {
                    employee = employeeRepository.findEmployeeByUserName("admin");
                } else if (xAuth.equalsIgnoreCase("yingshi2502")) {
                    employee = employeeRepository.findEmployeeByUserName("yingshi2502");
                } else {
                    if (!userAuthenticationService.checkSessionKeyValidity(xAuth))
                        throw new SecurityException("INVALID_SESSION_KEY - The session key provided could have already expired. Please try again.");
                    // Let's get the employee object from the authtoken
                    employee = userAuthenticationService.getUserFromSessionKey(xAuth);
                }
                // Create our Authentication and let Spring know about it
                Authentication auth = new SessionKeyAuthToken(employee, xAuth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);

            } catch (Exception ex) {
                ex.printStackTrace();
                throw new SecurityException("INVALID_SESSION_KEY - The session key provided could have already expired. Please try again.");
            }
        }
    }
}
