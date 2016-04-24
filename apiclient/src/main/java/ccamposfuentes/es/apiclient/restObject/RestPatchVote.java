package ccamposfuentes.es.apiclient.restObject;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 22/04/16
 * Project: DrupalCamp
 */

public class RestPatchVote {

    String name;
    int field_pun;

    public RestPatchVote(String name, int field_pun) {
        this.name = name;
        this.field_pun = field_pun;
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
}
