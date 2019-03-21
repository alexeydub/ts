package com.etrusted.interview.demo.service;

/**
 * Parsing utils.
 */
public class ParameterUtils {

  /**
   * Parse string to int.
   *
   * @param str        string to parse
   * @param defaultVal default value for non-numeric str
   * @return parsed string or default value
   */
  public static Integer parseIntDefault(String str, Integer defaultVal) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException ignore) {
      // return default
    }
    return defaultVal;
  }

  /**
   * Parse string to long.
   *
   * @param str        string to parse
   * @param defaultVal default value for non-numeric str
   * @return parsed string or default value
   */
  public static Long parseLongDefault(String str, Long defaultVal) {
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException ignore) {
      // return default
    }
    return defaultVal;
  }

}
