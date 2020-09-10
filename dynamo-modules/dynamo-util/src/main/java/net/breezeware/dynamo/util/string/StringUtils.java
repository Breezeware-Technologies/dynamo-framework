package net.breezeware.dynamo.util.string;

public class StringUtils {

    /**
     * Unescape the special characters in a string.
     * @param value the value to be unescaped
     * @return the unescaped string
     */
    public static String unescapeString(String value) {
        String returnVal = value;

        returnVal = returnVal.replace("\"[", "[");
        returnVal = returnVal.replace("\\\"", "\"");
        returnVal = returnVal.replace("]\"", "]");

        return returnVal;
    }

    /**
     * Escape the special characters in a string.
     * @param value the value to be escaped
     * @return the escaped string
     */
    public static String escapeString(String value) {
        String returnVal = value;

        returnVal = returnVal.replace("[", "\\[");
        returnVal = returnVal.replace("\"", "\\\"");
        returnVal = returnVal.replace("]", "\\]");

        return returnVal;
    }

    /**
     * Remove the new line characters in a string.
     * @param value the string to be converted
     * @return the string with new line characters removed
     */
    public static String removeNewLine(String value) {
        return value.replaceAll("\\r|\\n", " ");
    }
}