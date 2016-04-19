package ccamposfuentes.es.apiclient.restObject;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 12/04/16
 * Project: DrupalCamp
 */

public class RestRegister {

    private String oauth_token;
    private String uid;

    public RestRegister() {}

    public RestRegister(String oauth_token, String uid) {
        this.oauth_token = oauth_token;
        this.uid = uid;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
