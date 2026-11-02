package edu.uoc.pac2;

import java.time.LocalDate;

public class MuseumArtifact {
    // Constants
    private static String INVENTORY_CODE_REGEX = "ART-\\d{4}-\\d{4}";
    private static String[] VALID_CATEGORIES = {"PAINTING", "SCULPTURE", "MANUSCRIPT", "TEXTILE", "CERAMIC", "OTHER"};
    private static String[] VALID_STATUSES = {"EXCELLENT", "GOOD", "FAIR", "POOR"};
    private static double HIGH_VALUE_THRESHOLD = 100000.00;
    private static int INSPECTION_INTERVAL_MONTHS = 12;
    private static int MIN_LOAN_DAYS = 1;
    private static int MAX_LOAN_DAYS = 365;
    private static double DAILY_INSURANCE_RATE = 0.0001;
    private static double FRAGILE_SURCHARGE = 1.25;
    private static double HIGH_VALUE_SURCHARGE = 1.15;

    // Instance attributes
    private String inventoryCode;
    private String title;
    private String creator;
    private String originCountry;
    private String category;
    private String conservationStatus;
    private LocalDate acquisitionDate;
    private LocalDate lastInspectionDate;
    private double appraisedValue;
    private boolean fragile;
    private boolean onLoan;

    // Constructor initializing all instance attributes.

    public MuseumArtifact( String inventoryCode, String title, String creator, String originCountry, String category, String conservationStatus, LocalDate acquisitionDate, LocalDate lastInspectionDate, double appraisedValue, boolean fragile, boolean onLoan ) {
        setTitle(title);
        setInventoryCode(inventoryCode);
        setCreator(creator);
        setOriginCountry(originCountry);
        setCategory(category);
        setConservationStatus(conservationStatus);
        setAcquisitionDate(acquisitionDate);
        setLastInspectionDate(lastInspectionDate);
        setAppraisedValue(appraisedValue);
        setFragile(fragile);
        setOnLoan(onLoan);
    }

    // Getters and Setters

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        if (inventoryCode == null || !inventoryCode.matches(INVENTORY_CODE_REGEX)) {
            System.out.println("[ERROR] Inventory code must follow the format ART-YYYY-NNNN.");
            return;
        }

        this.inventoryCode = inventoryCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("[ERROR] Title cannot be null, empty or contain only whitespace.");
            return;
        }

        this.title = title.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        if (creator == null || creator.trim().isEmpty()) {
            System.out.println("[ERROR] Creator cannot be null, empty or contain only whitespace.");
            return;
        }

        this.creator = creator.trim();
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        if (originCountry == null || !originCountry.matches("[A-Za-z]{2}")) {
            System.out.println("[ERROR] Origin country must be a two-letter code.");
            return;
        }

        this.originCountry = originCountry.toUpperCase();
    }

    public String getCategory() {
        return category;
    }

    private boolean isValidCategory(String category) {
        if (category == null) {
            return false;
        }

        // Check if the category matches with a valid category
        for (int i = 0; i < VALID_CATEGORIES.length; i++) {
            if (VALID_CATEGORIES[i].equalsIgnoreCase(category)) {
                return true;
            }
        }

        return false;
    }

    public void setCategory(String category) {
        if (!isValidCategory(category)) {
            System.out.println("[ERROR] Category must be one of: PAINTING, SCULPTURE, MANUSCRIPT, TEXTILE, CERAMIC, OTHER.");

            return;
        }

        this.category = category.toUpperCase();
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    private boolean isValidConservationStatus(String conservationStatus) {
        if (conservationStatus == null) {
            return false;
        }

        // Check if the conservation status matches a valid status
        for (int i = 0; i < VALID_STATUSES.length; i++) {
            if (VALID_STATUSES[i].equalsIgnoreCase(conservationStatus)) {
                return true;
            }
        }

        return false;
    }

    private boolean isValueInArray(String value, String[] values) {
        if (value == null) {
            return false;
        }

        // Check if the value matches an array element
        for (int i = 0; i < values.length; i++) {
            if (value.equalsIgnoreCase(values[i])) {
                return true;
            }
        }

        return false;
    }

    public void setConservationStatus(String conservationStatus) {
        if (!isValidConservationStatus(conservationStatus)) {
            System.out.println("[ERROR] Conservation status must be one of: EXCELLENT, GOOD, FAIR, POOR.");
            return;
        }

        this.conservationStatus = conservationStatus.toUpperCase();
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        if (acquisitionDate == null || acquisitionDate.isAfter(LocalDate.now()) || (lastInspectionDate != null && acquisitionDate.isAfter(lastInspectionDate))) {            System.out.println("[ERROR] Acquisition date cannot be null, after the last inspection date or in the future.");
            return;
        }

        this.acquisitionDate = acquisitionDate;
    }

    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        if (lastInspectionDate == null || acquisitionDate == null || lastInspectionDate.isBefore(acquisitionDate) || lastInspectionDate.isAfter(LocalDate.now())) {
            System.out.println("[ERROR] Last inspection date cannot be null, before the acquisition date or in the future.");
            return;
        }

        this.lastInspectionDate = lastInspectionDate;
    }

    public double getAppraisedValue() {
        return appraisedValue;
    }

    public void setAppraisedValue(double appraisedValue) {
        if (!(appraisedValue > 0)) {
            System.out.println("[ERROR] Appraised value must be greater than 0.");

            return;
        }

        this.appraisedValue = appraisedValue;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    // Validation methods

    private boolean isValidReferenceDate(LocalDate referenceDate) {
        if (referenceDate == null || lastInspectionDate == null || referenceDate.isBefore(lastInspectionDate)) {
            return false;
        }

        return true;
    }

    private boolean isValidLoanDays(int loanDays) {
        if (loanDays < MIN_LOAN_DAYS || loanDays > MAX_LOAN_DAYS) {
            return false;
        }

        return true;
    }

    public boolean needsInspection(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");

            return false;
        }

        LocalDate nextInspectionDate = lastInspectionDate.plusMonths(INSPECTION_INTERVAL_MONTHS);

        if (referenceDate.isBefore(nextInspectionDate)) {
            return false;
        }

        return true;
    }

    public boolean isHighValue() {
        if (appraisedValue >= HIGH_VALUE_THRESHOLD) {
            return true;
        }

        return false;
    }

    public boolean isHighRisk(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");

            return false;
        }

        if (!fragile) {
            return false;
        }

        if ("FAIR".equals(conservationStatus) || "POOR".equals(conservationStatus) || needsInspection(referenceDate)) {
            return true;
        }

        return false;
    }

    public boolean canBeLoaned(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");

            return false;
        }

        if (onLoan || needsInspection(referenceDate) || !"EXCELLENT".equals(conservationStatus) && !"GOOD".equals(conservationStatus)) {
            return false;
        }

        return true;
    }

    public double calculateInsuranceCost(int loanDays) {
        if (!isValidLoanDays(loanDays)) {
            System.out.println("[ERROR] Loan days must be between 1 and 365.");

            return 0.0;
        }

        double cost = appraisedValue * DAILY_INSURANCE_RATE * loanDays;

        if (fragile) {
            cost *= FRAGILE_SURCHARGE;
        }

        if (isHighValue()) {
            cost *= HIGH_VALUE_SURCHARGE;
        }

        return cost;
    }

    public boolean isEligibleForLoan(String requestedCategory, double maxInsuredValue, int loanDays, LocalDate referenceDate) {
        if (!isValidCategory(requestedCategory) || !(maxInsuredValue > 0) || !isValidLoanDays(loanDays)) {
            System.out.println("[ERROR] Invalid loan request data.");

            return false;
        }

        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");

            return false;
        }

        if (!requestedCategory.equalsIgnoreCase(category) || appraisedValue > maxInsuredValue || !canBeLoaned(referenceDate)) {
            return false;
        }

        return true;
    }

    private boolean areLoanRequestsValid(String[] requestedCategories, double[] maxInsuredValues, int[] loanDays, LocalDate referenceDate) {
        if (requestedCategories == null || maxInsuredValues == null || loanDays == null || requestedCategories.length != maxInsuredValues.length || requestedCategories.length != loanDays.length || !isValidReferenceDate(referenceDate)) {
            return false;
        }

        // Check if the category, insured value, or loan days are invalid
        for (int i = 0; i < requestedCategories.length; i++) {
            if (!isValidCategory(requestedCategories[i]) || !(maxInsuredValues[i] > 0) || !isValidLoanDays(loanDays[i])) {
                return false;
            }
        }

        return true;
    }

    public int countEligibleLoanRequests(String[] requestedCategories, double[] maxInsuredValues, int[] loanDays, LocalDate referenceDate) {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            System.out.println("[ERROR] Invalid input data for loan request calculation.");

            return 0;
        }

        int eligibleRequests = 0;

        // Check if the loan request is eligible
        for (int i = 0; i < requestedCategories.length; i++) {
            if (isEligibleForLoan(requestedCategories[i], maxInsuredValues[i], loanDays[i], referenceDate)) {
                eligibleRequests ++;
            }
        }

        return eligibleRequests;
    }

    public double calculateEligibleLoanRequestPercentage(String[] requestedCategories, double[] maxInsuredValues, int[] loanDays, LocalDate referenceDate) {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            System.out.println("[ERROR] Invalid input data for loan request calculation.");

            return 0.0;
        }

        if (requestedCategories.length == 0) {
            return 0.0;
        }

        int eligibleRequests = countEligibleLoanRequests(requestedCategories, maxInsuredValues, loanDays, referenceDate);

        return eligibleRequests * 100.0 / requestedCategories.length;
    }
}
