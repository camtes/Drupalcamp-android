package ccamposfuentes.es.drupalcamp.objects;

import android.util.Log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/02/16
 * Project: DrupalCamp
 */

@DatabaseTable
public class Session {

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String TEXT = "body";
    public static final String DATE = "date";
    public static final String DIFFICULTY = "difficulty";
    public static final String LANGUAGE = "language";
    public static final String DAY = "day";
    public static final String ROOM = "room";
    public static final String SPEAKER = "speakers";
    public static final String TYPE = "type";
    public static final String VOTOS = "votos";
    public static final String VOTANTES = "votantes";

    @DatabaseField(id = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = TITLE)
    private String title;

    @DatabaseField(columnName = TEXT)
    private String text;

    @DatabaseField(columnName = DATE)
    private String date;

    @DatabaseField(columnName = DIFFICULTY)
    private String difficulty;

    @DatabaseField(columnName = LANGUAGE)
    private String language;

    @DatabaseField(columnName = DAY)
    private String day;

    @DatabaseField(columnName = ROOM)
    private int room;

    @DatabaseField(columnName = SPEAKER, dataType = DataType.SERIALIZABLE)
    private String[] speakers;

    @DatabaseField(columnName = TYPE)
    private int type;

    @DatabaseField(columnName = VOTOS)
    private String votos;

    @DatabaseField(columnName = VOTANTES)
    private String votantes;


    public Session() {}

    public Session(String id, String title, String text, String difficulty, String language, String date, int room, String[] speakers, int type) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.difficulty = difficulty;
        this.language = language;
        this.date = date;
        this.room = room;
        this.speakers = speakers;
        this.type = type;

        if (date.contains("23"))
            day = "Saturday";
        else if (date.contains("24"))
            day = "Sunday";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String fechaNacimiento) {
        this.date = fechaNacimiento;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String[] getSpeaker() {
        return speakers;
    }

    public void setSpeaker(String[] speakers) {
        this.speakers = speakers;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHour() {
        String hour = null;
        Date newDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatHour = new SimpleDateFormat("hh:mm");

        try {
            newDate = formatter.parse(date);
            hour = formatHour.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Session", e.toString());
        }

        return hour;
    }

    public String getDay() {
        String hour = null;
        Date newDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");

        try {
            newDate = formatter.parse(date);
            hour = formatHour.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String[] speakers) {
        this.speakers = speakers;
    }

    public String getVotos() {
        return votos;
    }

    public void setVotos(String votos) {
        this.votos = votos;
    }

    public String getVotantes() {
        return votantes;
    }

    public void setVotantes(String votantes) {
        this.votantes = votantes;
    }
}