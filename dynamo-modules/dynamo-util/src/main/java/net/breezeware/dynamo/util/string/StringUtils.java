package net.breezeware.dynamo.util.string;

public class StringUtils {
    public static String unescapeString(String value) {
        String returnVal = value;

        returnVal = returnVal.replace("\"[", "[");
        returnVal = returnVal.replace("\\\"", "\"");
        returnVal = returnVal.replace("]\"", "]");

        return returnVal;
    }

    public static String escapeString(String value) {
        String returnVal = value;

        returnVal = returnVal.replace("[", "\\[");
        returnVal = returnVal.replace("\"", "\\\"");
        returnVal = returnVal.replace("]", "\\]");

        return returnVal;
    }

    public static String removeNewLine(String value) {
        return value.replaceAll("\\r|\\n", " ");
    }
}