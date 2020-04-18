package com.ArgonautB04.SIRIO.services;

public interface EmailRestService {
    Boolean sendEmail(String receiver, String subject, String content);
}
