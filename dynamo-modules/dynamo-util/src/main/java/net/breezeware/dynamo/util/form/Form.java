package net.breezeware.dynamo.util.form;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents a single Form.
 * 
 * @author Karthik Muthukumaraswamy
 * 
 */
public class Form implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Expose
    private List<FormField> formFields;

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}