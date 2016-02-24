package org.pode.cosmos.exceptions.model;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by patrick on 23.02.16.
 */
@XmlRootElement
public class ExceptionInfo implements Serializable{

    @XmlElement(name = "Class")
    private String exceptionClass;
    @XmlElement(name = "Message")
    private String errorMsg;
    @XmlElement(name = "Info")
    private String errorInfo;

    public ExceptionInfo() {
    }

    public ExceptionInfo(final String clazz, final String msg, final String info){
        this.exceptionClass = clazz;
        this.errorMsg = msg;
        this.errorInfo = info;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
