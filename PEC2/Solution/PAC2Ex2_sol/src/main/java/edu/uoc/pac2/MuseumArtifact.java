package edu.uoc.pac2;

import java.time.LocalDate;

/**
 * Represents an artifact preserved by a museum and its suitability for temporary loans.
 *
 * @author UOC
 * @version 1.0
 */
public class MuseumArtifact {

    /** Unique inventory code. */
    private String inventoryCode;
    /** Artifact title or identifying name. */
    private String title;
    /** Artist, author or culture responsible for the artifact. */
    private String creator;
    /** Two-letter country code identifying the artifact's origin. */
    private String originCountry;
    /** Artifact category. */
    private String category;
    /** Current conservation status. */
    private String conservationStatus;
    /** Date on which the museum acquired the artifact. */
    private LocalDate acquisitionDate;
    /** Date of the most recent conservation inspection. */
    private LocalDate lastInspectionDate;
    /** Current appraised value in euros. */
    private double appraisedValue;
    /** Whether the artifact requires fragile handling. */
    private boolean fragile;
    /** Whether the artifact is currently on loan. */
    private boolean onLoan;

    /** Regular expression accepted for inventory codes. */
    private static String INVENTORY_CODE_REGEX = "ART-\\d{4}-\\d{4}";
    /** Categories accepted by the museum catalogue. */
    private static String[] VALID_CATEGORIES = {
            "PAINTING", "SCULPTURE", "MANUSCRIPT", "TEXTILE", "CERAMIC", "OTHER"
    };
    /** Conservation statuses accepted by the museum. */
    private static String[] VALID_STATUSES = {"EXCELLENT", "GOOD", "FAIR", "POOR"};
    /** Minimum value, in euros, for an artifact to be considered high-value. */
    private static double HIGH_VALUE_THRESHOLD = 100_000.0;
    /** Maximum number of months allowed between conservation inspections. */
    private static int INSPECTION_INTERVAL_MONTHS = 12;
    /** Minimum temporary-loan duration. */
    private static int MIN_LOAN_DAYS = 1;
    /** Maximum temporary-loan duration. */
    private static int MAX_LOAN_DAYS = 365;
    /** Fraction of the appraised value charged for each loan day. */
    private static double DAILY_INSURANCE_RATE = 0.0001;
    /** Insurance surcharge applied to fragile artifacts. */
    private static double FRAGILE_SURCHARGE = 0.25;
    /** Insurance surcharge applied to high-value artifacts. */
    private static double HIGH_VALUE_SURCHARGE = 0.15;

    /**
     * Creates an artifact. The values are assigned in parameter order through the setters.
     *
     * @param inventoryCode unique code in {@code ART-YYYY-NNNN} format
     * @param title artifact title
     * @param creator artist, author or culture
     * @param originCountry two-letter country code
     * @param category catalogue category
     * @param conservationStatus conservation status
     * @param acquisitionDate acquisition date
     * @param lastInspectionDate last inspection date
     * @param appraisedValue appraised value in euros
     * @param fragile whether the artifact is fragile
     * @param onLoan whether the artifact is currently on loan
     * @throws IllegalArgumentException if any validated value is invalid
     */
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

    /** @return the inventory code */
    public String getInventoryCode() {
        return inventoryCode;
    }

    /**
     * Sets the inventory code.
     *
     * @param inventoryCode code in {@code ART-YYYY-NNNN} format
     * @throws IllegalArgumentException if the code does not have the required format
     */
    public void setInventoryCode(String inventoryCode) {
        if (inventoryCode == null || !inventoryCode.matches(INVENTORY_CODE_REGEX)) {
            throw new IllegalArgumentException("Inventory code must follow the format ART-YYYY-NNNN.");
        }
        this.inventoryCode = inventoryCode;
    }

    /** @return the artifact title */
    public String getTitle() {
        return title;
    }

    /**
     * Sets and trims the artifact title.
     *
     * @param title artifact title
     * @throws IllegalArgumentException if the title is null or blank
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null, empty or contain only whitespace.");
        }
        this.title = title.trim();
    }

    /** @return the creator */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets and trims the creator.
     *
     * @param creator artist, author or culture
     * @throws IllegalArgumentException if the creator is null or blank
     */
    public void setCreator(String creator) {
        if (creator == null || creator.isBlank()) {
            throw new IllegalArgumentException("Creator cannot be null, empty or contain only whitespace.");
        }
        this.creator = creator.trim();
    }

    /** @return the two-letter origin-country code */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * Sets the origin-country code and stores it in uppercase.
     *
     * @param originCountry two-letter country code
     * @throws IllegalArgumentException if the code does not contain exactly two letters
     */
    public void setOriginCountry(String originCountry) {
        if (originCountry == null || !originCountry.matches("[A-Za-z]{2}")) {
            throw new IllegalArgumentException("Origin country must be a two-letter code.");
        }
        this.originCountry = originCountry.toUpperCase();
    }

    /** @return the catalogue category */
    public String getCategory() {
        return category;
    }

    /**
     * Checks whether a category belongs to the accepted domain.
     *
     * @param category category to check
     * @return {@code true} when the category is accepted
     */
    private boolean isValidCategory(String category) {
        return isValueInArray(category, VALID_CATEGORIES);
    }

    /**
     * Sets the category and stores it in uppercase.
     *
     * @param category catalogue category
     * @throws IllegalArgumentException if the category is not accepted
     */
    public void setCategory(String category) {
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Category must be one of: PAINTING, SCULPTURE, MANUSCRIPT, TEXTILE, CERAMIC, OTHER.");
        }
        this.category = category.toUpperCase();
    }

    /** @return the conservation status */
    public String getConservationStatus() {
        return conservationStatus;
    }

    /**
     * Checks whether a conservation status belongs to the accepted domain.
     *
     * @param conservationStatus status to check
     * @return {@code true} when the status is accepted
     */
    private boolean isValidConservationStatus(String conservationStatus) {
        return isValueInArray(conservationStatus, VALID_STATUSES);
    }

    /**
     * Searches an array of strings without distinguishing letter case.
     *
     * @param value value to find
     * @param values accepted values
     * @return {@code true} when the value appears in the array
     */
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

    /**
     * Sets the conservation status and stores it in uppercase.
     *
     * @param conservationStatus conservation status
     * @throws IllegalArgumentException if the status is not accepted
     */
    public void setConservationStatus(String conservationStatus) {
        if (!isValidConservationStatus(conservationStatus)) {
            throw new IllegalArgumentException("Conservation status must be one of: EXCELLENT, GOOD, FAIR, POOR.");
        }
        this.conservationStatus = conservationStatus.toUpperCase();
    }

    /** @return the acquisition date */
    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets the acquisition date.
     *
     * @param acquisitionDate acquisition date
     * @throws IllegalArgumentException if the date is null, after the last inspection or in the future
     */
    public void setAcquisitionDate(LocalDate acquisitionDate) {
        if (acquisitionDate == null || acquisitionDate.isAfter(LocalDate.now())
                || (lastInspectionDate != null && acquisitionDate.isAfter(lastInspectionDate))) {
            throw new IllegalArgumentException("Acquisition date cannot be null, after the last inspection date or in the future.");
        }
        this.acquisitionDate = acquisitionDate;
    }

    /** @return the date of the most recent inspection */
    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }

    /**
     * Sets the most recent inspection date.
     *
     * @param lastInspectionDate inspection date
     * @throws IllegalArgumentException if the date is null, before acquisition or in the future
     */
    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        if (acquisitionDate == null || lastInspectionDate == null
                || lastInspectionDate.isBefore(acquisitionDate)
                || lastInspectionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Last inspection date cannot be null, before the acquisition date or in the future.");
        }
        this.lastInspectionDate = lastInspectionDate;
    }

    /** @return the appraised value in euros */
    public double getAppraisedValue() {
        return appraisedValue;
    }

    /**
     * Sets the appraised value.
     *
     * @param appraisedValue value in euros
     * @throws IllegalArgumentException if the value is not strictly positive
     */
    public void setAppraisedValue(double appraisedValue) {
        if (appraisedValue <= 0 || Double.isNaN(appraisedValue)) {
            throw new IllegalArgumentException("Appraised value must be greater than 0.");
        }
        this.appraisedValue = appraisedValue;
    }

    /** @return whether the artifact is fragile */
    public boolean isFragile() {
        return fragile;
    }

    /** @param fragile whether the artifact is fragile */
    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    /** @return whether the artifact is currently on loan */
    public boolean isOnLoan() {
        return onLoan;
    }

    /** @param onLoan whether the artifact is currently on loan */
    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    /**
     * Checks a date used to evaluate the artifact.
     *
     * @param referenceDate date to check
     * @return {@code true} when it is not null and not before the last inspection
     */
    private boolean isValidReferenceDate(LocalDate referenceDate) {
        return referenceDate != null && lastInspectionDate != null
                && !referenceDate.isBefore(lastInspectionDate);
    }

    /**
     * Checks a temporary-loan duration.
     *
     * @param loanDays duration in days
     * @return {@code true} when the duration is within the accepted range
     */
    private boolean isValidLoanDays(int loanDays) {
        return loanDays >= MIN_LOAN_DAYS && loanDays <= MAX_LOAN_DAYS;
    }

    /**
     * Determines whether another conservation inspection is due.
     *
     * @param referenceDate date on which the artifact is evaluated
     * @return {@code true} when at least twelve months have elapsed
     * @throws Exception if the reference date is invalid
     */
    public boolean needsInspection(LocalDate referenceDate) throws Exception {
        validateReferenceDate(referenceDate);
        return !referenceDate.isBefore(lastInspectionDate.plusMonths(INSPECTION_INTERVAL_MONTHS));
    }

    /** @return whether the appraised value reaches the high-value threshold */
    public boolean isHighValue() {
        return appraisedValue >= HIGH_VALUE_THRESHOLD;
    }

    /**
     * Determines whether fragile handling combines with a conservation risk.
     *
     * @param referenceDate date on which the artifact is evaluated
     * @return {@code true} when the artifact is high-risk
     * @throws Exception if the reference date is invalid
     */
    public boolean isHighRisk(LocalDate referenceDate) throws Exception {
        validateReferenceDate(referenceDate);
        boolean vulnerableStatus = "FAIR".equals(conservationStatus) || "POOR".equals(conservationStatus);
        return fragile && (vulnerableStatus || needsInspection(referenceDate));
    }

    /**
     * Determines whether the artifact currently meets the museum's loan conditions.
     *
     * @param referenceDate date on which the artifact is evaluated
     * @return {@code true} when the artifact can be loaned
     * @throws Exception if the reference date is invalid
     */
    public boolean canBeLoaned(LocalDate referenceDate) throws Exception {
        validateReferenceDate(referenceDate);
        boolean suitableStatus = "EXCELLENT".equals(conservationStatus) || "GOOD".equals(conservationStatus);
        return !onLoan && suitableStatus && !needsInspection(referenceDate);
    }

    /**
     * Calculates the insurance cost for a temporary loan.
     *
     * @param loanDays temporary-loan duration
     * @return insurance cost in euros
     * @throws Exception if the duration is outside the accepted range
     */
    public double calculateInsuranceCost(int loanDays) throws Exception {
        validateLoanDays(loanDays);
        double cost = appraisedValue * DAILY_INSURANCE_RATE * loanDays;
        if (fragile) {
            cost *= 1 + FRAGILE_SURCHARGE;
        }
        if (isHighValue()) {
            cost *= 1 + HIGH_VALUE_SURCHARGE;
        }
        return cost;
    }

    /**
     * Determines whether this artifact satisfies one loan request.
     *
     * @param requestedCategory requested artifact category
     * @param maxInsuredValue maximum value covered by the requester
     * @param loanDays requested duration
     * @param referenceDate date on which the request is evaluated
     * @return {@code true} when all request and artifact conditions are met
     * @throws Exception if any request value is invalid
     */
    public boolean isEligibleForLoan(String requestedCategory, double maxInsuredValue,
                                     int loanDays, LocalDate referenceDate) throws Exception {
        if (!isValidCategory(requestedCategory) || maxInsuredValue <= 0
                || Double.isNaN(maxInsuredValue) || !isValidLoanDays(loanDays)) {
            throw new Exception("Invalid loan request data.");
        }
        validateReferenceDate(referenceDate);
        return category.equalsIgnoreCase(requestedCategory)
                && appraisedValue <= maxInsuredValue
                && canBeLoaned(referenceDate);
    }

    /**
     * Checks all parallel arrays used to describe loan requests.
     *
     * @param requestedCategories requested categories
     * @param maxInsuredValues maximum covered values
     * @param loanDays requested durations
     * @param referenceDate evaluation date
     * @return {@code true} when the arrays and all their elements are valid
     */
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
                    || Double.isNaN(maxInsuredValues[i]) || !isValidLoanDays(loanDays[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Counts the loan requests for which this artifact is eligible.
     *
     * @param requestedCategories requested categories
     * @param maxInsuredValues maximum covered values
     * @param loanDays requested durations
     * @param referenceDate evaluation date
     * @return number of eligible requests
     * @throws IllegalArgumentException if the arrays or any element are invalid
     * @throws Exception if eligibility evaluation fails
     */
    public int countEligibleLoanRequests(String[] requestedCategories, double[] maxInsuredValues,
                                         int[] loanDays, LocalDate referenceDate) throws Exception {
        validateLoanRequests(requestedCategories, maxInsuredValues, loanDays, referenceDate);
        int eligibleRequests = 0;
        for (int i = 0; i < requestedCategories.length; i++) {
            if (isEligibleForLoan(requestedCategories[i], maxInsuredValues[i], loanDays[i], referenceDate)) {
                eligibleRequests++;
            }
        }
        return eligibleRequests;
    }

    /**
     * Calculates the percentage of loan requests for which this artifact is eligible.
     *
     * @param requestedCategories requested categories
     * @param maxInsuredValues maximum covered values
     * @param loanDays requested durations
     * @param referenceDate evaluation date
     * @return eligible-request percentage, or {@code 0.0} for empty arrays
     * @throws IllegalArgumentException if the arrays or any element are invalid
     * @throws Exception if eligibility evaluation fails
     */
    public double calculateEligibleLoanRequestPercentage(String[] requestedCategories,
                                                         double[] maxInsuredValues, int[] loanDays,
                                                         LocalDate referenceDate) throws Exception {
        validateLoanRequests(requestedCategories, maxInsuredValues, loanDays, referenceDate);
        if (requestedCategories.length == 0) {
            return 0.0;
        }
        return (double) countEligibleLoanRequests(requestedCategories, maxInsuredValues, loanDays,
                referenceDate) / requestedCategories.length * 100.0;
    }

    /**
     * Rejects an invalid reference date.
     *
     * @param referenceDate date to validate
     * @throws Exception if the date is invalid
     */
    private void validateReferenceDate(LocalDate referenceDate) throws Exception {
        if (!isValidReferenceDate(referenceDate)) {
            throw new Exception("Reference date cannot be null or before the last inspection date.");
        }
    }

    /**
     * Rejects an invalid loan duration.
     *
     * @param loanDays duration to validate
     * @throws Exception if the duration is invalid
     */
    private void validateLoanDays(int loanDays) throws Exception {
        if (!isValidLoanDays(loanDays)) {
            throw new Exception("Loan days must be between 1 and 365.");
        }
    }

    /**
     * Rejects invalid parallel request arrays.
     *
     * @param requestedCategories requested categories
     * @param maxInsuredValues maximum covered values
     * @param loanDays requested durations
     * @param referenceDate evaluation date
     * @throws IllegalArgumentException if the complete request data is invalid
     */
    private void validateLoanRequests(String[] requestedCategories, double[] maxInsuredValues,
                                      int[] loanDays, LocalDate referenceDate) {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            throw new IllegalArgumentException("Invalid input data for loan request calculation.");
        }
    }
}
