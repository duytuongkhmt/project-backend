package project.common;

import java.time.format.DateTimeFormatter;

public class Constant {

    public static final String UPLOAD_DIR = "D:/Hangout/Graduation/project-frontend/image";

    public static final class PAGE {
        public static int DEFAULT = 0;
        public static int LIMIT = 20;
    }

    public static final class FORMAT_DATE {
        public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
        public static final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }


    public static final class COLUMN {
        public static final String ARTIST_ID = "artistId";
        public static final String USER_ID = "userId";
        public static final String PROFILE = "profile";
        public static final String BOOKER_ID = "bookerId";
        public static final String FULL_NAME = "fullName";
        public static final String PAYMENT_STATUS = "paymentStatus";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AM = "am";
        public static final String PRICE = "price";
        public static final String DATE = "date";
        public static final String START_AT = "startAt";
        public static final String FROM = "from";
        public static final String TO = "to";
        public static final String PAYMENT_DATE = "paymentDate";

        public static final String ROLE = "role";
        public static final String IS_EMAIL_VERIFIED = "isEmailVerified";

        public static final String STATUS = "status";
        public static final String PAYMENT_PERCENT = "paymentPercent";
        public static final String RATE = "rate";
        public static final String GENRE = "genre";
        public static final String IS_DELETE = "isDelete";
    }
}
