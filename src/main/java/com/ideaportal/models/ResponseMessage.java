package com.ideaportal.models;

public class ResponseMessage<T>
{
    private int status;
    private String statusText;
    private T result;
    private String token;
    private int totalElements;
    private Object object;
    
    public ResponseMessage(final int status, final String statusText, final T result) {
        this.status = status;
        this.statusText = statusText;
        this.result = result;
    }
    
    public ResponseMessage(final int status, final String statusText) {
        this.status = status;
        this.statusText = statusText;
    }
    
    public ResponseMessage(final int status, final String statusText, final T result, final String token) {
        this.status = status;
        this.statusText = statusText;
        this.result = result;
        this.token = token;
    }
    
    public ResponseMessage(final int status, final String statusText, final T result, final String token, final Object object) {
        this.status = status;
        this.statusText = statusText;
        this.result = result;
        this.token = token;
        this.object = object;
    }
    
    public Object getObject() {
        return this.object;
    }
    
    public void setObject(final Object object) {
        this.object = object;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
    
    public ResponseMessage() {
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public String getStatusText() {
        return this.statusText;
    }
    
    public void setStatusText(final String statusText) {
        this.statusText = statusText;
    }
    
    public T getResult() {
        return this.result;
    }
    
    public void setResult(final T result) {
        this.result = result;
    }
    
    public int getTotalElements() {
        return this.totalElements;
    }
    
    public void setTotalElements(final int totalElements) {
        this.totalElements = totalElements;
    }
    
    @Override
    public String toString() {
        return "ResponseMessage [status=" + this.status + ", statusText=" + this.statusText + ", result=" + this.result + "]";
    }
}