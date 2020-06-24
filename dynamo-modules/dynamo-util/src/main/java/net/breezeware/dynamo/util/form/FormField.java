package net.breezeware.dynamo.util.form;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents a single form field in a form.
 * 
 * @author Karthik Muthukumaraswamy
 * 
 */
public class FormField implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private String fieldName;

	@Expose
	private String fieldId;

	@Expose
	private String fieldLabel;

	@Expose
	private String fieldType;

	@Expose
	private String fieldValue;

	@Expose
	private List<FormFieldValue> fieldOptions;

	@Expose
	private boolean isRequired;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public List<FormFieldValue> getFieldOptions() {
		return fieldOptions;
	}

	public void setFieldOptions(List<FormFieldValue> fieldOptions) {
		this.fieldOptions = fieldOptions;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String toString() {
		return new Gson().toJson(this);
	}
}