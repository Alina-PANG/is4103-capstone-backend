package capstone.is4103capstone.general;

import org.springframework.beans.factory.annotation.Value;

public class DefaultData {
    public static final String AUTHENTICATION_ERROR_MSG = "Authentication Error!";

    @Value("${website.url}")
    private static final String FRONT_END_URL = null;
}
