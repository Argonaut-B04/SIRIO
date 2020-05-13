package com.ArgonautB04.SIRIO.rest;

/**
 * Kelas ini digunakan sebagai objek utama yang akan dikirim sebagai JsonResponse
 *
 * @param <T> Class bebas masukin apa saja yang bakal jadi konten response
 * @author windawijaya, nathanael
 * @since 2019
 * @version 1.1
 */
public class BaseResponse<T> {
    private int status;
    private String message;
    private T result;

    /**
     * Empty Constructor
     */
    public BaseResponse() {
    }

    /**
     * Constructor to shorthand status, message, and result;
     *
     * @param status  the status
     * @param message the message
     * @param result  the result
     */
    public BaseResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(T result) {
        this.result = result;
    }
}