package it.sevenbits.cards.web.domain;

import java.util.Map;

public class JsonResponse {
    private String status = null;
    private Map<String, String> result = null;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Map<String, String> result) {
        this.result = result;
    }
}