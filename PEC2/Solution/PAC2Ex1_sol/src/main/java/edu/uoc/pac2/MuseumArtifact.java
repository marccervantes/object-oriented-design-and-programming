package edu.uoc.pac2;

import java.time.LocalDate;

public class MuseumArtifact {

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

    private static String INVENTORY_CODE_REGEX = "ART-\\d{4}-\\d{4}";
    private static String[] VALID_CATEGORIES = {
            "PAINTING", "SCULPTURE", "MANUSCRIPT", "TEXTILE", "CERAMIC", "OTHER"
    };
    private static String[] VALID_STATUSES = {"EXCELLENT", "GOOD", "FAIR", "POOR"};
    private static double HIGH_VALUE_THRESHOLD = 100_000.0;
    private static int INSPECTION_INTERVAL_MONTHS = 12;
    private static int MIN_LOAN_DAYS = 1;
    private static int MAX_LOAN_DAYS = 365;
    private static double DAILY_INSURANCE_RATE = 0.0001;
    private static double FRAGILE_SURCHARGE = 0.25;
    private static double HIGH_VALUE_SURCHARGE = 0.15;

    public MuseumArtifact(String inventoryCode, String title, String creator, String originCountry,
                          String category, String conservationStatus, LocalDate acquisitionDate,
                          LocalDate lastInspectionDate, double appraisedValue, boolean fragile,
                          boolean onLoan) {
        setInventoryCode(inventoryCode);
        setTitle(title);
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

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        if (inventoryCode != null && inventoryCode.matches(INVENTORY_CODE_REGEX)) {
            this.inventoryCode = inventoryCode;
        } else {
            System.out.println("[ERROR] Inventory code must follow the format ART-YYYY-NNNN.");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title.trim();
        } else {
            System.out.println("[ERROR] Title cannot be null, empty or contain only whitespace.");
        }
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        if (creator != null && !creator.isBlank()) {
            this.creator = creator.trim();
        } else {
            System.out.println("[ERROR] Creator cannot be null, empty or contain only whitespace.");
        }
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        if (originCountry != null && originCountry.matches("[A-Za-z]{2}")) {
            this.originCountry = originCountry.toUpperCase();
        } else {
            System.out.println("[ERROR] Origin country must be a two-letter code.");
        }
    }

    public String getCategory() {
        return category;
    }

    private boolean isValidCategory(String category) {
        return isValueInArray(category, VALID_CATEGORIES);
    }

    public void setCategory(String category) {
        if (isValidCategory(category)) {
            this.category = category.toUpperCase();
        } else {
            System.out.println("[ERROR] Category must be one of: PAINTING, SCULPTURE, MANUSCRIPT, TEXTILE, CERAMIC, OTHER.");
        }
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    private boolean isValidConservationStatus(String conservationStatus) {
        return isValueInArray(conservationStatus, VALID_STATUSES);
    }

    private boolean isValueInArray(String value, String[] values) {
        if (value != null) {
            for (String validValue : values) {
                if (validValue.equalsIgnoreCase(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setConservationStatus(String conservationStatus) {
        if (isValidConservationStatus(conservationStatus)) {
            this.conservationStatus = conservationStatus.toUpperCase();
        } else {
            System.out.println("[ERROR] Conservation status must be one of: EXCELLENT, GOOD, FAIR, POOR.");
        }
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        if (acquisitionDate != null && !acquisitionDate.isAfter(LocalDate.now())
                && (lastInspectionDate == null || !acquisitionDate.isAfter(lastInspectionDate))) {
            this.acquisitionDate = acquisitionDate;
        } else {
            System.out.println("[ERROR] Acquisition date cannot be null, after the last inspection date or in the future.");
        }
    }

    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        if (acquisitionDate != null && lastInspectionDate != null
                && !lastInspectionDate.isBefore(acquisitionDate)
                && !lastInspectionDate.isAfter(LocalDate.now())) {
            this.lastInspectionDate = lastInspectionDate;
        } else {
            System.out.println("[ERROR] Last inspection date cannot be null, before the acquisition date or in the future.");
        }
    }

    public double getAppraisedValue() {
        return appraisedValue;
    }

    public void setAppraisedValue(double appraisedValue) {
        if (appraisedValue > 0) {
            this.appraisedValue = appraisedValue;
        } else {
            System.out.println("[ERROR] Appraised value must be greater than 0.");
        }
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

    private boolean isValidReferenceDate(LocalDate referenceDate) {
        return referenceDate != null && lastInspectionDate != null
                && !referenceDate.isBefore(lastInspectionDate);
    }

    private boolean isValidLoanDays(int loanDays) {
        return loanDays >= MIN_LOAN_DAYS && loanDays <= MAX_LOAN_DAYS;
    }

    public boolean needsInspection(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");
            return false;
        }
        return !referenceDate.isBefore(lastInspectionDate.plusMonths(INSPECTION_INTERVAL_MONTHS));
    }

    public boolean isHighValue() {
        return appraisedValue >= HIGH_VALUE_THRESHOLD;
    }

    public boolean isHighRisk(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");
            return false;
        }
        boolean vulnerableStatus = "FAIR".equals(conservationStatus) || "POOR".equals(conservationStatus);
        return fragile && (vulnerableStatus || needsInspection(referenceDate));
    }

    public boolean canBeLoaned(LocalDate referenceDate) {
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");
            return false;
        }
        boolean suitableStatus = "EXCELLENT".equals(conservationStatus) || "GOOD".equals(conservationStatus);
        return !onLoan && suitableStatus && !needsInspection(referenceDate);
    }

    public double calculateInsuranceCost(int loanDays) {
        if (!isValidLoanDays(loanDays)) {
            System.out.println("[ERROR] Loan days must be between 1 and 365.");
            return 0.0;
        }

        double cost = appraisedValue * DAILY_INSURANCE_RATE * loanDays;
        if (fragile) {
            cost *= 1 + FRAGILE_SURCHARGE;
        }
        if (isHighValue()) {
            cost *= 1 + HIGH_VALUE_SURCHARGE;
        }
        return cost;
    }

    public boolean isEligibleForLoan(String requestedCategory, double maxInsuredValue,
                                     int loanDays, LocalDate referenceDate) {
        if (!isValidCategory(requestedCategory) || maxInsuredValue <= 0
                || Double.isNaN(maxInsuredValue) || !isValidLoanDays(loanDays)) {
            System.out.println("[ERROR] Invalid loan request data.");
            return false;
        }
        if (!isValidReferenceDate(referenceDate)) {
            System.out.println("[ERROR] Reference date cannot be null or before the last inspection date.");
            return false;
        }
        return category.equalsIgnoreCase(requestedCategory)
                && appraisedValue <= maxInsuredValue
                && canBeLoaned(referenceDate);
    }

    private boolean areLoanRequestsValid(String[] requestedCategories, double[] maxInsuredValues,
                                         int[] loanDays, LocalDate referenceDate) {
        if (requestedCategories == null || maxInsuredValues == null || loanDays == null
                || requestedCategories.length != maxInsuredValues.length
                || requestedCategories.length != loanDays.length
                || !isValidReferenceDate(referenceDate)) {
            return false;
        }

        for (int i = 0; i < requestedCategories.length; i++) {
            if (!isValidCategory(requestedCategories[i]) || maxInsuredValues[i] <= 0
                    || Double.isNaN(maxInsuredValues[i])
                    || !isValidLoanDays(loanDays[i])) {
                return false;
            }
        }
        return true;
    }

    public int countEligibleLoanRequests(String[] requestedCategories, double[] maxInsuredValues,
                                         int[] loanDays, LocalDate referenceDate) {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            System.out.println("[ERROR] Invalid input data for loan request calculation.");
            return 0;
        }

        int eligibleRequests = 0;
        for (int i = 0; i < requestedCategories.length; i++) {
            if (isEligibleForLoan(requestedCategories[i], maxInsuredValues[i], loanDays[i], referenceDate)) {
                eligibleRequests++;
            }
        }
        return eligibleRequests;
    }

    public double calculateEligibleLoanRequestPercentage(String[] requestedCategories,
                                                         double[] maxInsuredValues, int[] loanDays,
                                                         LocalDate referenceDate) {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            System.out.println("[ERROR] Invalid input data for loan request calculation.");
            return 0.0;
        }
        if (requestedCategories.length == 0) {
            return 0.0;
        }
        return (double) countEligibleLoanRequests(requestedCategories, maxInsuredValues, loanDays,
                referenceDate) / requestedCategories.length * 100.0;
    }
}
