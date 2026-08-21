
package com.mycompany.churchnotificationsystem.validation;

public class ValidationResult {
    
    private final boolean valid;
    private final String errorField;
    private final String errorMessage;

    public ValidationResult(boolean valid, String errorField, String errorMessage) {
        this.valid = valid;
        this.errorField = errorField;
        this.errorMessage = errorMessage;
    }
    
    public static ValidationResult success() {
        return new ValidationResult(true, null, null);
    }
    
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
