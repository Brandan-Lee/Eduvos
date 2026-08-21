
package com.mycompany.churchnotificationsystem.validation;

//Helper utility class to provide a result for the validation sequences
public class ValidationResult {
    
    private final boolean valid;
    private final String errorField;
    private final String errorMessage;
    
    //Consttructor
    public ValidationResult(boolean valid, String errorField, String errorMessage) {
        this.valid = valid;
        this.errorField = errorField;
        this.errorMessage = errorMessage;
    }
    
    //The validation returned a success reusult
    public static ValidationResult success() {
        return new ValidationResult(true, null, null);
    }
    
    //Validation result failed
    public static ValidationResult fail(String field, String message) {
        return new ValidationResult(false, field, message);
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public String getErrorField() {
        return errorField;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
}
