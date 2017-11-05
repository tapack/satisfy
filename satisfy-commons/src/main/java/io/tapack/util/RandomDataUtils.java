package io.tapack.util;

import static org.apache.commons.lang3.RandomStringUtils.*;

public final class RandomDataUtils {

    private RandomDataUtils() {
    }

    public static String getRandomSsn() {
        return String.format("%s-%s-%s", randomNumeric(3), randomNumeric(2),
                randomNumeric(4));
    }

    public static String getRandomPhone() {
        return String.format("(%s)%s-%s", randomNumeric(3), randomNumeric(3),
                randomNumeric(4));
    }

    public static String getRandomEmail() {
        return String.format("%s@%s.%s", randomAlphanumeric(8).toLowerCase(),
                randomAlphanumeric(6).toLowerCase(), randomAlphabetic(3)
                        .toLowerCase());
    }
}
