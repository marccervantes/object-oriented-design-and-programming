package edu.uoc.pac2;

import java.util.Locale;

public class SampleLabelFormatter {

    public static boolean isValidSampleCode(String code) {
        if (code == null || code.length() != 10 || !code.startsWith("BIO-")) {
            return false;
        }
        for (int i = 4; i < code.length(); i++) {
            char digit = code.charAt(i);
            if (digit < '0' || digit > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidSampleType(String type) {
        return type != null && (type.equalsIgnoreCase("BLOOD")
                || type.equalsIgnoreCase("SALIVA")
                || type.equalsIgnoreCase("TISSUE")
                || type.equalsIgnoreCase("DNA"));
    }

    public static int calculateCheckDigit(String code) {
        if (!isValidSampleCode(code)) {
            throw new IllegalArgumentException("Invalid sample code.");
        }
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += (i + 1) * (code.charAt(4 + i) - '0');
        }
        return sum % 10;
    }

    public static String buildLabel(String code, String type) {
        if (!isValidSampleCode(code)) {
            throw new IllegalArgumentException("Invalid sample code.");
        }
        if (!isValidSampleType(type)) {
            throw new IllegalArgumentException("Invalid sample type.");
        }
        StringBuilder label = new StringBuilder();
        label.append(code).append('|').append(type.toUpperCase(Locale.ROOT));
        label.append('|').append(calculateCheckDigit(code));
        return label.toString();
    }

    public static String buildBatchLabels(String[] codes, String[] types) {
        if (codes == null || types == null || codes.length != types.length) {
            throw new IllegalArgumentException("Invalid batch data.");
        }
        for (int i = 0; i < codes.length; i++) {
            if (!isValidSampleCode(codes[i]) || !isValidSampleType(types[i])) {
                throw new IllegalArgumentException("Invalid batch data.");
            }
        }
        StringBuffer labels = new StringBuffer();
        for (int i = 0; i < codes.length; i++) {
            if (i > 0) {
                labels.append('\n');
            }
            labels.append(buildLabel(codes[i], types[i]));
        }
        return labels.toString();
    }
}
