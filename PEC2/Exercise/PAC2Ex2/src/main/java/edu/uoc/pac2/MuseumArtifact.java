package edu.uoc.pac2;

import java.time.LocalDate;

/**
 * MuseumArtifact represents an artefact held by a museum. The museum must
 * store its basic details, monitor its condition and determine whether it can
 * be made available for temporary loans requested by other institutions.
 *
 * @author Marc Cervantes Garcia
 * @version 1.0
 */

public class MuseumArtifact {
    /**
     * Regular expression defining the valid inventory code: ART-YYYY-NNNN
     */
    private static String INVENTORY_CODE_REGEX = "ART-\\d{4}-\\d{4}";

    /**
     * Array of valid categories
     */
    private static String[] VALID_CATEGORIES = {"PAINTING", "SCULPTURE", "MANUSCRIPT", "TEXTILE", "CERAMIC", "OTHER"};

    /**
     * Array of valid status
     */
    private static String[] VALID_STATUSES = {"EXCELLENT", "GOOD", "FAIR", "POOR"};

    /**
     * Threshold monetary value
     */
    private static double HIGH_VALUE_THRESHOLD = 100000.00;

    /**
     * Time interval in months required
     */
    private static int INSPECTION_INTERVAL_MONTHS = 12;

    /**
     * Min allowed loan duration days
     */
    private static int MIN_LOAN_DAYS = 1;

    /**
     * Max allowed loan duration days
     */
    private static int MAX_LOAN_DAYS = 365;

    /**
     * Base daily insurance rate
     */
    private static double DAILY_INSURANCE_RATE = 0.0001;

    /**
     * Surcharge multiplier applied to the insurance cost if the artifact is fragile
     */
    private static double FRAGILE_SURCHARGE = 1.25;

    /**
     * Surcharge multiplier applied to the insurance cost if the artifact is high value
     */
    private static double HIGH_VALUE_SURCHARGE = 1.15;

    //

    /**
     * Inventory identifier code
     */
    private String inventoryCode;

    /**
     * Title of the artifact
     */
    private String title;

    /**
     * Creator of the artifact
     */
    private String creator;

    /**
     * Two-letter country code
     */
    private String originCountry;

    /**
     * Category of the artifact
     */
    private String category;

    /**
     * Conservation status
     */
    private String conservationStatus;

    /**
     * Date when the artifact was acquired by the museum
     */
    private LocalDate acquisitionDate;

    /**
     * Date of the most recent conservation inspection
     */
    private LocalDate lastInspectionDate;

    /**
     * Appraised monetary value
     */
    private double appraisedValue;

    /**
     * Indicates if the artifact is fragile
     */
    private boolean fragile;

    /**
     * Indicates if the artifact is currently on loan
     */
    private boolean onLoan;

    //

    /**
     * Constructs a new {@code MuseumArtifact} with the specified initial parameters
     *
     * @param inventoryCode      the inventory code following format ART-YYYY-NNNN
     * @param title              the title of the artifact
     * @param creator            the creator of the artifact
     * @param originCountry      the two-letter country code of origin
     * @param category           the category of the artifact
     * @param conservationStatus the conservation status
     * @param acquisitionDate    the date of acquisition
     * @param lastInspectionDate the date of the last inspection
     * @param appraisedValue     the appraised monetary value
     * @param fragile            {@code true} if the artifact is fragile, {@code false} otherwise
     * @param onLoan             {@code true} if the artifact is currently on loan, {@code false} otherwise
     */

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

    /**
     * Gets the inventory code of the artifact
     *
     * @return the inventory code
     */
    public String getInventoryCode() {
        return inventoryCode;
    }

    /**
     * Sets the inventory code of the artifact
     *
     * @param inventoryCode the inventory code to set
     * @throws IllegalArgumentException if the code is null or does not match the ART-YYYY-NNNN
     */
    public void setInventoryCode(String inventoryCode) {
        if (inventoryCode == null || !inventoryCode.matches(INVENTORY_CODE_REGEX)) {
            throw new IllegalArgumentException("Inventory code must follow the format ART-YYYY-NNNN.");
        }

        this.inventoryCode = inventoryCode;
    }

    /**
     * Gets the title of the artifact
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the artifact after trimming and validating
     *
     * @param title the title to set
     * @throws IllegalArgumentException if title is null, empty, or whitespace
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null, empty or contain only whitespace.");
        }

        this.title = title.trim();
    }

    /**
     * Gets the creator of the artifact
     *
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the artifact after trimming and validating it is non-empty
     *
     * @param creator the creator to set
     * @throws IllegalArgumentException if creator is null, empty, or whitespace only
     */
    public void setCreator(String creator) {
        if (creator == null || creator.trim().isEmpty()) {
            throw new IllegalArgumentException("Creator cannot be null, empty or contain only whitespace.");
        }

        this.creator = creator.trim();
    }

    /**
     * Gets the origin country code of the artifact
     *
     * @return the uppercase two-letter country code
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * Sets the origin country code after validating it is a two-letter string.
     *
     * @param originCountry the two-letter origin country code
     * @throws IllegalArgumentException if originCountry is null or not a two-letter code
     */
    public void setOriginCountry(String originCountry) {
        if (originCountry == null || !originCountry.matches("[A-Za-z]{2}")) {
            throw new IllegalArgumentException("Origin country must be a two-letter code.");
        }

        this.originCountry = originCountry.toUpperCase();
    }

    /**
     * Gets the category of the artifact
     *
     * @return the category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Validates the category string if is part of the allowed categories
     *
     * @param category the category string to check
     * @return {@code true} if valid, {@code false} otherwise
     */
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

    /**
     * Sets the category of the artifact after validating against allowed categories
     *
     * @param category the category to set
     * @throws IllegalArgumentException if category is not valid
     */
    public void setCategory(String category) {
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Category must be one of: PAINTING, SCULPTURE, MANUSCRIPT, TEXTILE, CERAMIC, OTHER.");
        }

        this.category = category.toUpperCase();
    }

    /**
     * Gets the conservation status of the artifact
     *
     * @return the conservation status
     */
    public String getConservationStatus() {
        return conservationStatus;
    }

    /**
     * Validates the conservation status string if is part of the allowed statuses
     *
     * @param conservationStatus the status string to check
     * @return {@code true} if valid, {@code false} otherwise
     */
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

    /**
     * Checks if a string value exists in an array
     *
     * @param value  the string value to search
     * @param values the array of valid values
     * @return {@code true} if value is in array, {@code false} otherwise
     */
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

    /**
     * Sets the conservation status of the artifact
     *
     * @param conservationStatus the status to set
     * @throws IllegalArgumentException if conservationStatus is not valid
     */
    public void setConservationStatus(String conservationStatus) {
        if (!isValidConservationStatus(conservationStatus)) {
            throw new IllegalArgumentException("Conservation status must be one of: EXCELLENT, GOOD, FAIR, POOR.");
        }

        this.conservationStatus = conservationStatus.toUpperCase();
    }

    /**
     * Gets the acquisition date of the artifact
     *
     * @return the acquisition date
     */
    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets the acquisition date of the artifact
     *
     * @param acquisitionDate the acquisition date to set
     * @throws IllegalArgumentException if acquisitionDate is null, in the future, or after lastInspectionDate
     */
    public void setAcquisitionDate(LocalDate acquisitionDate) {
        if (acquisitionDate == null || acquisitionDate.isAfter(LocalDate.now()) || (lastInspectionDate != null && acquisitionDate.isAfter(lastInspectionDate))) {
            throw new IllegalArgumentException("Acquisition date cannot be null, after the last inspection date or in the future.");
        }

        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Gets the last inspection date of the artifact
     *
     * @return the last inspection date
     */
    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }

    /**
     * Sets the last inspection date of the artifact
     *
     * @param lastInspectionDate the last inspection date to set
     * @throws IllegalArgumentException if lastInspectionDate is null or before acquisitionDate
     */
    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        if (lastInspectionDate == null || acquisitionDate == null || lastInspectionDate.isBefore(acquisitionDate) || lastInspectionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Last inspection date cannot be null, before the acquisition date or in the future.");
        }

        this.lastInspectionDate = lastInspectionDate;
    }

    /**
     * Gets the appraised value of the artifact
     *
     * @return the appraised value
     */
    public double getAppraisedValue() {
        return appraisedValue;
    }

    /**
     * Sets the appraised monetary value of the artifact
     *
     * @param appraisedValue the value to set
     * @throws IllegalArgumentException if appraisedValue is not strictly greater than 0
     */
    public void setAppraisedValue(double appraisedValue) {
        if (!(appraisedValue > 0)) {
            throw new IllegalArgumentException("Appraised value must be greater than 0.");
        }

        this.appraisedValue = appraisedValue;
    }

    /**
     * Checks whether the artifact is fragile
     *
     * @return {@code true} if fragile, {@code false} otherwise
     */
    public boolean isFragile() {
        return fragile;
    }

    /**
     * Sets the fragility status of the artifact
     *
     * @param fragile {@code true} if fragile, {@code false} otherwise
     */
    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    /**
     * Checks whether the artifact is currently on loan
     *
     * @return {@code true} if on loan, {@code false} otherwise
     */
    public boolean isOnLoan() {
        return onLoan;
    }

    /**
     * Sets the loan status of the artifact
     *
     * @param onLoan {@code true} if on loan, {@code false} otherwise
     */
    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    //

    /**
     * Checks if the reference date is valid. The date cannot be null or before the last inspection date
     *
     * @param referenceDate The date to validate
     * @return true if the date is valid, false otherwise
     */
    private boolean isValidReferenceDate(LocalDate referenceDate) {
        if (referenceDate == null || lastInspectionDate == null || referenceDate.isBefore(lastInspectionDate)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the number of loan days is valid
     *
     * @param loanDays The number of loan days to validate
     * @return true if the number of days is valid, false otherwise
     */
    private boolean isValidLoanDays(int loanDays) {
        if (loanDays < MIN_LOAN_DAYS || loanDays > MAX_LOAN_DAYS) {
            return false;
        }

        return true;
    }

    /**
     * Determines the artifact requires a routine inspection
     *
     * @param referenceDate the date against which inspection status is evaluated
     * @return {@code true} if 12 or more months have passed since last inspection, {@code false} otherwise
     * @throws Exception if referenceDate is null or before lastInspectionDate
     */
    public boolean needsInspection(LocalDate referenceDate) throws Exception {
        if (!isValidReferenceDate(referenceDate)) {
            throw new Exception("Reference date cannot be null or before the last inspection date.");
        }

        LocalDate nextInspectionDate = lastInspectionDate.plusMonths(INSPECTION_INTERVAL_MONTHS);

        if (referenceDate.isBefore(nextInspectionDate)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the artifact is considered high value
     *
     * @return {@code true} if high value, {@code false} otherwise
     */
    public boolean isHighValue() {
        if (appraisedValue >= HIGH_VALUE_THRESHOLD) {
            return true;
        }

        return false;
    }

    /**
     * Evaluates if the artifact is at high risk conservation status, and inspection status
     *
     * @param referenceDate the reference date for inspection check
     * @return {@code true} if high risk, {@code false} otherwise
     * @throws Exception if referenceDate is null or before lastInspectionDate
     */
    public boolean isHighRisk(LocalDate referenceDate) throws Exception {
        if (!isValidReferenceDate(referenceDate)) {
            throw new Exception("Reference date cannot be null or before the last inspection date.");
        }

        if (!fragile) {
            return false;
        }

        if ("FAIR".equals(conservationStatus) || "POOR".equals(conservationStatus) || needsInspection(referenceDate)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the artifact can currently be loaned out
     *
     * @param referenceDate the reference date
     * @return {@code true} if not on loan, status is EXCELLENT/GOOD, and no inspection needed, {@code false} otherwise
     * @throws Exception if referenceDate is null or before lastInspectionDate
     */
    public boolean canBeLoaned(LocalDate referenceDate) throws Exception {
        if (!isValidReferenceDate(referenceDate)) {
            throw new Exception("Reference date cannot be null or before the last inspection date.");
        }

        if (onLoan || needsInspection(referenceDate) || !"EXCELLENT".equals(conservationStatus) && !"GOOD".equals(conservationStatus)) {
            return false;
        }

        return true;
    }

    /**
     * Calculates the insurance cost for a given loan duration in days
     *
     * @param loanDays duration of the loan in days
     * @return the calculated total insurance cost
     * @throws Exception if loanDays is invalid
     */
    public double calculateInsuranceCost(int loanDays) throws Exception {
        if (!isValidLoanDays(loanDays)) {
            throw new Exception("Loan days must be between 1 and 365.");
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

    /**
     * Evaluates the artifact is eligible for a specific loan request
     *
     * @param requestedCategory the requested category
     * @param maxInsuredValue    the maximum insured value covered by requester
     * @param loanDays           duration of loan in days
     * @param referenceDate      the reference date
     * @return {@code true} if eligible, {@code false} otherwise
     * @throws Exception if input loan data or reference date is invalid
     */
    public boolean isEligibleForLoan(String requestedCategory, double maxInsuredValue, int loanDays, LocalDate referenceDate) throws Exception {
        if (!isValidCategory(requestedCategory) || !(maxInsuredValue > 0) || !isValidLoanDays(loanDays)) {
            throw new Exception("Invalid loan request data.");
        }

        if (!isValidReferenceDate(referenceDate)) {
            throw new Exception("Reference date cannot be null or before the last inspection date.");
        }

        if (!requestedCategory.equalsIgnoreCase(category) || appraisedValue > maxInsuredValue || !canBeLoaned(referenceDate)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if all loan requests are valid
     *
     * @param requestedCategories The categories requested for the loan
     * @param maxInsuredValues The maximum insured values for each loan
     * @param loanDays The number of loan days for each request
     * @param referenceDate The reference date to validate
     * @return true if all loan requests are valid, false otherwise
     */
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

    /**
     * Counts how many loan requests in a batch are eligible
     *
     * @param requestedCategories array of requested categories
     * @param maxInsuredValues    array of maximum insured values
     * @param loanDays            array of loan durations in days
     * @param referenceDate       the reference date
     * @return number of eligible loan requests
     * @throws IllegalArgumentException if input arrays or reference date are invalid
     * @throws Exception if an error occurs while checking loan
     */
    public int countEligibleLoanRequests(String[] requestedCategories, double[] maxInsuredValues, int[] loanDays, LocalDate referenceDate) throws Exception {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            throw new IllegalArgumentException("Invalid input data for loan request calculation.");
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

    /**
     * Calculates the percentage of loan requests in a batch that are eligible
     *
     * @param requestedCategories array of requested categories
     * @param maxInsuredValues    array of maximum insured values
     * @param loanDays            array of loan durations in days
     * @param referenceDate       the reference date
     * @return percentage of eligible loan requests
     * @throws IllegalArgumentException if input arrays or reference date are invalid
     * @throws Exception if an error occurs while checking loan
     */
    public double calculateEligibleLoanRequestPercentage(String[] requestedCategories, double[] maxInsuredValues, int[] loanDays, LocalDate referenceDate) throws Exception {
        if (!areLoanRequestsValid(requestedCategories, maxInsuredValues, loanDays, referenceDate)) {
            throw new IllegalArgumentException("Invalid input data for loan request calculation.");
        }

        if (requestedCategories.length == 0) {
            return 0.0;
        }

        int eligibleRequests = countEligibleLoanRequests(requestedCategories, maxInsuredValues, loanDays, referenceDate);

        return eligibleRequests * 100.0 / requestedCategories.length;
    }
}
