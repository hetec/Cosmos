package org.pode.cosmos.exceptionHandling.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by patrick on 29.05.16.
 */
@XmlRootElement
public class ConstraintViolationEntry implements Serializable{

    private String fieldName;
    private String wrongValue;
    private String errorMsg;

    public ConstraintViolationEntry(){}

    public ConstraintViolationEntry(final String fieldName,
                                    final String wrongValue,
                                    final String errorMsg) {
        this.fieldName = fieldName;
        this.wrongValue = wrongValue;
        this.errorMsg = errorMsg;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getWrongValue() {
        return wrongValue;
    }

    public void setWrongValue(String wrongValue) {
        this.wrongValue = wrongValue;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
