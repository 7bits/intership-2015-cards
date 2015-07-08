package it.sevenbits.cards.web.service;

public class ServiceException extends Exception {
    public ServiceException(String s, Exception e) {
        super(s, e);
    }
}