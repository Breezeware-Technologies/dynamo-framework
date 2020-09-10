package net.breezeware.dynamo.util.form;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents a possible complex form field value.
 * @author Karthik Muthukumaraswamy
 */
public class FormFieldValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    private String label;

    @Expose
    private String name;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}