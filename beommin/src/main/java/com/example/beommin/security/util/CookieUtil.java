package com.example.beommin.security.util;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;


public class CookieUtil {

  public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();

    if(cookies != null && cookies.length > 0) {
      Cookie findCookie = findCookie(cookies, cookieName);
      if(findCookie != null) return Optional.of(findCookie);
    }
    return Optional.empty();
  }

  public static void addCookie(HttpServletResponse response,
      String cookieName,
      String value,
      int maxAge) {
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
    Cookie[] cookies = request.getCookies();

    if(cookies != null && cookies.length > 0) {
      Cookie findCookie = findCookie(cookies, cookieName);
      if(findCookie != null) {
        findCookie.setValue("");
        findCookie.setPath("/");
        findCookie.setMaxAge(0);
        response.addCookie(findCookie);
      }
    }
  }

  public static String serialize(Object object) {
    return Base64.getUrlEncoder()
        .encodeToString(SerializationUtils.serialize(object));
  }

  public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    return cls.cast(SerializationUtils.deserialize(
        Base64.getUrlDecoder().decode(cookie.getValue())));
  }

  private static Cookie findCookie(Cookie[] cookies, String cookieName) {
    return Arrays.stream(cookies)
        .filter(cookie -> cookie.getName().equals(cookieName))
        .findFirst()
        .orElse(null);
  }

}
