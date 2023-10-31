package cz.cvut.fel.autoserviceIS.security;

public class SecurityConstants {
    private SecurityConstants() {
        throw new AssertionError();
    }

    public static final String SESSION_COOKIE_NAME = "BP_JSESSIONID",
            REMEMBER_ME_COOKIE_NAME = "remember-me",
            USERNAME_PARAM = "username",
            PASSWORD_PARAM = "password",
            SECURITY_CHECK_URI = "/login",
            LOGOUT_URI = "/api/v1/logout",
            COOKIE_URI = "/";

    public static final int SESSION_TIMEOUT = 30 * 60;
}
