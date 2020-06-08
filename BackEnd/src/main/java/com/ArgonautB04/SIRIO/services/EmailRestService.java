package com.argonautb04.sirio.services;

public interface EmailRestService {
    Boolean sendEmail(String receiver, String subject, String content);
}
