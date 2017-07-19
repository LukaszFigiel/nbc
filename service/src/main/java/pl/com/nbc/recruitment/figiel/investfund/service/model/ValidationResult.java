package pl.com.nbc.recruitment.figiel.investfund.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class ValidationResult implements Serializable{

    private static final long serialVersionUID = 5883198960365245840L;

    private boolean isValid;

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        if(messages == null) {
            messages = new ArrayList<String>();
        }
        return messages;
    }

    ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.messages.add(message);
    }

    ValidationResult(boolean isValid) {
        this.isValid = isValid;
    }

    public static ValidationResult createValid() {
        return new ValidationResult(true);
    }

    public static ValidationResult createInValid(String message) {
        return new ValidationResult(false, message);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
