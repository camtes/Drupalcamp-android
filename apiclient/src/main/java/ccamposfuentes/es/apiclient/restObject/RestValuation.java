package ccamposfuentes.es.apiclient.restObject;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 13/04/16
 * Project: DrupalCamp
 */

public class RestValuation {
    int field_pun;
    String field_session;
    String field_votante;

    public RestValuation() {}

    public RestValuation(int field_pun, String field_session, String field_votante) {
        this.field_pun = field_pun;
        this.field_session = field_session;
        this.field_votante = field_votante;
    }

    public RestValuation(RestValuation body) {
        this.field_pun = body.getField_pun();
        this.field_session = body.getField_session();
        this.field_votante = body.getField_votante();
    }

    public int getField_pun() {
        return field_pun;
    }

    public void setField_pun(int field_pun) {
        this.field_pun = field_pun;
    }

    public String getField_session() {
        return field_session;
    }

    public void setField_session(String field_session) {
        this.field_session = field_session;
    }

    public String getField_votante() {
        return field_votante;
    }

    public void setField_votante(String field_votante) {
        this.field_votante = field_votante;
    }
}
