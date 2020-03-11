package com.ArgonautB04.SIRIO.rest;

/**
 * Kelas ini digunakan sebagai objek utama yang akan dikirim sebagai JsonResponse
 *
 * @param <T> Class bebas masukin apa saja yang bakal jadi konten response
 * @author windawijaya
 * @since 2019
 */
public class BaseResponse<T> {
    private int status;
    private String message;
    private T result;

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param result the result to set
     */
    public void setResult(T result) {
        this.result = result;
    }
}