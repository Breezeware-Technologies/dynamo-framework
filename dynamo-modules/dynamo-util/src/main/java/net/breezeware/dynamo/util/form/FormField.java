package net.breezeware.dynamo.util.form;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a single form field in a form.
 * @author Karthik Muthukumaraswamy
 */
public class FormField implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    @Getter
    @Setter
    private String fieldName;

    @Expose
    @Getter
    @Setter
    private String fieldId;

    @Expose
    @Getter
    @Setter
    private String fieldLabel;

    @Expose
    @Getter
    @Setter
    private String fieldType;

    @Expose
    @Getter
    @Setter
    private String fieldValue;

    @Expose
    @Getter
    @Setter
    private List<FormFieldValue> fieldOptions;

    @Expose
    @Getter
    @Setter
    private boolean isRequired;

    public String toString() {
        return new Gson().toJson(this);
    }
}