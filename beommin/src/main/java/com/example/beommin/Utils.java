package com.example.beommin;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utils {

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }

    public static String phoneFormat(String number) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        number = number.replaceAll(" ","");
        return number.replaceAll(regEx, "$1-$2-$3");
    }
}
