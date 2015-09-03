package it.sevenbits.cards.web.domain.forms;

public class FeedbackForm {
    private String email;
    private String title;
    private String describe;

    public String getEmail() {
        return email;
    }

    public String getDescribe() {
        return describe;
    }

    public String getTitle() {
        return title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("FeedbackForm[title=%s, email=%s, describe=%s]", title, email, describe);
    }
}

