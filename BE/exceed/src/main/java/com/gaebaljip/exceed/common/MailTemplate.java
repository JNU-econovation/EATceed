package com.gaebaljip.exceed.common;

public class MailTemplate {
    public static final String SIGN_UP_TEMPLATE = "signup";
    public static final String UPDATE_PASSWORD_TEMPLATE = "updatePassword";

    public static final String SIGN_UP_TITLE = "Eatceed 회원가입 인증 메일";
    public static final String UPDATE_PASSWORD_TITLE = "Eatceed 비밀번호 변경 메일";
    public static final String SIGN_UP_MAIL_CONTEXT = "signupLink";
    public static final String UPDATE_PASSWORD_MAIL_CONTEXT = "updatePasswordLink";
    public static final String SIGN_UP_EMAIL = "email";
    public static final String UPDATE_PASSWORD_EMAIL = "email";
    public static final String REPLY_TO_SIGN_UP_MAIL_URL = "/v1/signUp-redirect";
    public static final String REPLY_TO_UPDATE_PASSWORD_MAIL_URL = "/v1/updatePassword-redirect";
}
