package capstone.is4103capstone.general.session;

import capstone.is4103capstone.general.model.GeneralRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {

            // custom error response class used across my project
            GeneralRes errorResponse = new GeneralRes();
            errorResponse.setHasError(true);
            errorResponse.setMessage(e.getMessage());

            Integer httpStatusCode;

            if (e.getMessage().contains("MISSING_AUTHENTICATION_HEADER")) {
                httpStatusCode = 401;
            } else if (e.getMessage().contains("INVALID_SESSION_KEY")) {
                httpStatusCode = 401;
            } else {
                httpStatusCode = 500;
            }
            response.setContentType("application/json");
            response.setStatus(httpStatusCode);
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
