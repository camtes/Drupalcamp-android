package ccamposfuentes.es.drupalcamp.objets;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

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
    public static final String DATE = "date";
    public static final String ROOM = "room";
    public static final String SPEAKER = "speaker";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = TITLE)
    private String title;

    @DatabaseField(columnName = DATE)
    private Date fechaNacimiento;

    @DatabaseField(columnName = ROOM)
    private int room;

    @DatabaseField(columnName = SPEAKER)
    private String speaker;


    public Session() {}

    public Session(String title, Date fechaNacimiento, int room, String speaker) {
        this.title = title;
        this.fechaNacimiento = fechaNacimiento;
        this.room = room;
        this.speaker = speaker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
