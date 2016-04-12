package ccamposfuentes.es.apiclient.restObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 11/04/16
 * Project: DrupalCamp
 */

public class RestSpeaker {

    private List<RestValue> uid;

    @SerializedName("name")
    private List<RestValue> username;

    @SerializedName("field_name")
    private List<RestValue> name;

    @SerializedName("field_company")
    private List<RestValue> company;

    @SerializedName("user_picture")
    private List<RestTarget> picture;

    public RestSpeaker() {
    }

    public RestSpeaker(List<RestValue> uid, List<RestValue> username, List<RestValue> name,
                       List<RestValue> company, List<RestTarget> picture) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.company = company;
        this.picture = picture;
    }

    public List<RestValue> getUid() {
        return uid;
    }

    public void setUid(List<RestValue> uid) {
        this.uid = uid;
    }

    public List<RestValue> getUsername() {
        return username;
    }

    public void setUsername(List<RestValue> username) {
        this.username = username;
    }

    public List<RestValue> getName() {
        return name;
    }

    public void setName(List<RestValue> name) {
        this.name = name;
    }

    public List<RestValue> getCompany() {
        return company;
    }

    public void setCompany(List<RestValue> company) {
        this.company = company;
    }

    public List<RestTarget> getPicture() {
        return picture;
    }

    public void setPicture(List<RestTarget> picture) {
        this.picture = picture;
    }
}


