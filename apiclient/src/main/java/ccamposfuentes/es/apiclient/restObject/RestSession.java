package ccamposfuentes.es.apiclient.restObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/04/16
 * Project: DrupalCamp
 */

public class RestSession {
    List<RestValue> nid;
    List<RestValue> title;
    @SerializedName("body")
    List<RestValue> text;
    @SerializedName("field_date")
    List<RestValue> date;
    @SerializedName("field_difficulty")
    List<RestTarget> difficulty;
    @SerializedName("field_language")
    List<RestValue> language;
    @SerializedName("field_speakers")
    List<RestTarget> speakers;
//    @SerializedName("field_votantes")
//    List<RestVotantes> votantes;
//    @SerializedName("field_votos")
//    List<RestVotos> votos;


    public RestSession() {}

    public RestSession(List<RestValue> nid, List<RestValue> title, List<RestValue> text, List<RestValue> date, List<RestTarget> difficulty, List<RestValue> language, List<RestTarget> speakers) {
        this.nid = nid;
        this.title = title;
        this.text = text;
        this.date = date;
        this.difficulty = difficulty;
        this.language = language;
        this.speakers = speakers;
    }

    public List<RestValue> getNid() {
        return nid;
    }

    public void setNid(List<RestValue> uid) {
        this.nid = uid;
    }

    public List<RestValue> getTitle() {
        return title;
    }

    public void setTitle(List<RestValue> title) {
        this.title = title;
    }

    public List<RestValue> getText() {
        return text;
    }

    public void setText(List<RestValue> text) {
        this.text = text;
    }

    public List<RestValue> getDate() {
        return date;
    }

    public void setDate(List<RestValue> date) {
        this.date = date;
    }

    public List<RestTarget> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(List<RestTarget> difficulty) {
        this.difficulty = difficulty;
    }

    public List<RestValue> getLanguage() {
        return language;
    }

    public void setLanguage(List<RestValue> language) {
        this.language = language;
    }

    public List<RestTarget> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<RestTarget> speakers) {
        this.speakers = speakers;
    }
}
