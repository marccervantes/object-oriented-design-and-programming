package edu.uoc.pac2;

public class SampleLabelFormatter {
    private static String CODE_PREFIX = "BIO-";
    private static int CODE_LENGTH = 10;
    private static String[] SAMPLE_TYPES = { "BLOOD", "SALIVA", "TISSUE", "DNA" };

    public static boolean isValidSampleCode(String code) {
        if ( code == null || code.length() != CODE_LENGTH || !code.startsWith(CODE_PREFIX)) {
            return false;
        }

        for ( int i = CODE_PREFIX.length(); i < code.length(); i++ ) {
            char character = code.charAt(i);

            if ( character < '0' || character > '9' ) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidSampleType(String type) {

        if (type == null) {
         return false;
        }

        // Check if the type matches
        for (int i = 0; i < SAMPLE_TYPES.length; i++) {
            if (SAMPLE_TYPES[i].equalsIgnoreCase(type)) {
                return true;
            }
        }

        return false;
    }

    public static int calculateCheckDigit(String code) {
        if (!isValidSampleCode(code)) {
            throw new IllegalArgumentException("Invalid sample code.");
        }

        int sum = 0;

        // Multiply each number by its position and add it to the sum
        for (int i = CODE_PREFIX.length(); i < code.length(); i++) {
            sum = sum + (code.charAt(i) - '0') * (i - CODE_PREFIX.length() + 1);
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

        return code + "|" + type.toUpperCase() + "|" + calculateCheckDigit(code);

    }

    public static String buildBatchLabels(String[] codes, String[] types) {
        if (codes == null || types == null || codes.length != types.length) {
            throw new IllegalArgumentException("Invalid batch data.");
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < codes.length; i++) {

            if (!isValidSampleCode(codes[i])) {
                throw new IllegalArgumentException("Invalid batch data.");
            }

            if (!isValidSampleType(types[i])) {
                throw new IllegalArgumentException("Invalid batch data.");
            }

            if (i > 0) {
                result.append("\n");
            }

            String label = buildLabel(codes[i], types[i]);
            result.append(label);
        }

        return result.toString();


    }
}
