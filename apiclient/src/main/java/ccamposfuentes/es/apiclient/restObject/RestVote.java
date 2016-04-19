package ccamposfuentes.es.apiclient.restObject;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 19/04/16
 * Project: DrupalCamp
 */

public class RestVote {

    private String name;
    private int field_pun;
    private int field_session;
    private int field_votante;

    public RestVote(String name, int field_pun, int field_session, int field_votante) {
        this.name = name;
        this.field_pun = field_pun;
        this.field_session = field_session;
        this.field_votante = field_votante;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getField_pun() {
        return field_pun;
    }

    public void setField_pun(int field_pun) {
        this.field_pun = field_pun;
    }

    public int getField_session() {
        return field_session;
    }

    public void setField_session(int field_session) {
        this.field_session = field_session;
    }

    public int getField_votante() {
        return field_votante;
    }

    public void setField_votante(int field_votante) {
        this.field_votante = field_votante;
    }
}
