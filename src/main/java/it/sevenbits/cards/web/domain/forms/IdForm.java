package it.sevenbits.cards.web.domain.forms;

public class IdForm {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("id=%d]",
                id);
    }

}




