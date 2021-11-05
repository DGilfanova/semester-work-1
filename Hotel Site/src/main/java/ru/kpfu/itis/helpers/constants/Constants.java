package ru.kpfu.itis.helpers.constants;

public class Constants {

    public final static String AUTH_USER_COOKIE_NAME = "auth_user";
    public final static Integer COOKIE_MAX_AGE = 60 * 60 * 24 * 365;
    public final static Integer COOKIE_DELETE_AGE = 0;

    public final static String USER_SESSION_ATTRIBUTE_NAME = "user";
    public final static String UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME = "uuid";
    public final static String BOOKING_ATTRIBUTE_NAME = "booking";
    public final static String ERROR_ATTRIBUTE_NAME = "error";
    public final static String EDIT_PROFILE_ATTRIBUTE_NAME = "editProfile";
    public final static String AUTH_ATTRIBUTE_NAME = "auth";

    public final static String DEFAULT_USER_ROLE = "user";

    public final static String BOOKING_DATE_FORMAT = "yyyy-MM-dd";
    public final static String ORDER_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public final static Integer DAY_IN_MS = 1000 * 60 * 60 * 24;

    public final static String MAIL_MESSAGE_THEME = "Hotel Administration";
    public final static String RECEPTION_MAIL = "fanoval.frez77@gmail.com";

    public final static String NAME_REGEX = "^[a-zA-Z ]{1,30}$";
    public final static String MOBILE_NUMBER_REGEX = "^(\\+[0-9]{3,15})|([0-9]{4,16})$";
    public final static String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public final static String PASSWORD_REGEX = "^[A-Za-z0-9]{8,20}$";
    public final static String PASSPORT_NUMBER_REGEX = "^[A-Za-z0-9]{6,20}$";

    public final static String MAIL_PROP_USER = "mail.username";
    public final static String MAIL_PROP_PASSWORD = "mail.password";

    public final static String BOOKING_USER_ERROR = "bookingUserError";
    public final static String CHOOSE_ORDER_ERROR = "chooseOrderError";
    public final static String CHOOSE_BOOKING_ERROR = "chooseBookingError";
    public final static String ROLE_ERROR = "roleError";
    public final static String AUTH_ERROR = "authError";

    public final static String GUEST_ROLE = "guest";
}
