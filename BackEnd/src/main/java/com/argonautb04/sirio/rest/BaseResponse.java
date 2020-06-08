package com.argonautb04.sirio.rest;

/**
 * Kelas ini digunakan sebagai objek utama yang akan dikirim sebagai
 * JsonResponse.
 *
 * @param <T> Class bebas masukin apa saja yang bakal jadi konten response
 * @author windawijaya, nathanael
 * @version 1.1
 * @since 2019
 */
public class BaseResponse<T> {
    /**
     * Response status.
     */
    private int status;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response object.
     */
    private T result;

    /**
     * Empty Constructor.
     */
    public BaseResponse() {
    }

    /**
     * Constructor to shorthand status, message, and result.
     *
     * @param statusCode    the status
     * @param messageToPass the message
     * @param resultObject  the result
     */
    public BaseResponse(final int statusCode, final String messageToPass, final T resultObject) {
        this.status = statusCode;
        this.message = messageToPass;
        this.result = resultObject;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param statusCode the status to set
     */
    public void setStatus(final int statusCode) {
        this.status = statusCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param messageResponse the message to set
     */
    public void setMessage(final String messageResponse) {
        this.message = messageResponse;
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @param resultObject the result to set
     */
    public void setResult(final T resultObject) {
        this.result = resultObject;
    }
}
