package com.example.beommin;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }

    public static String phoneFormat(String number) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        number = number.replaceAll(" ","");
        return number.replaceAll(regEx, "$1-$2-$3");
    }

    public static boolean validPassword(String pwd) {
        Pattern collectPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher matcher = collectPattern.matcher(pwd);

        if(!matcher.find()){
            logger.info("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상, 20자 이하이어야 합니다.");
            return false;
        }

        Pattern repeatedCharactersPattern = Pattern.compile("(\\w)\\1\\1\\1");
        matcher = repeatedCharactersPattern.matcher(pwd);

        if(matcher.find()) {
            logger.info("비밀번호에 동일한 문자가 4번 이상 반복되면 안됩니다.");
            return false;
        }
        return true;
    }

    public static boolean validEmail(String email) {
        Pattern collectPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher matcher = collectPattern.matcher(email);

        if(!matcher.find()) {
            logger.info("올바른 이메일 형식이 아닙니다.");
            return false;
        }
        else return true;

    }
    public static ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }


}
