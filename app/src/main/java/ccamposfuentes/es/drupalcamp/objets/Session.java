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
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String ROOM = "room";
    public static final String SPEAKER = "speaker";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = DATE)
    private String date;

    @DatabaseField(columnName = ROOM)
    private int room;

    @DatabaseField(foreign=true, columnName = SPEAKER)
    private Speaker speaker;


    public Session() {}

    public Session(String name, String date, int room, Speaker speaker) {
        this.name = name;
        this.date = date;
        this.room = room;
        this.speaker = speaker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String fechaNacimiento) {
        this.date = fechaNacimiento;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
