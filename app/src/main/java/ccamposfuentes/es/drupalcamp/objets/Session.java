package ccamposfuentes.es.drupalcamp.objets;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static final String DAY = "day";
    public static final String ROOM = "room";
    public static final String SPEAKER = "speaker";
    public static final String TYPE = "type";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = DATE)
    private String date;

    @DatabaseField(columnName = DAY)
    private String day;

    @DatabaseField(columnName = ROOM)
    private int room;

    @DatabaseField(columnName = SPEAKER)
    private String speaker;

    @DatabaseField(columnName = TYPE)
    private int type;


    public Session() {}

    public Session(String name, String date, int room, String speaker, int type) {
        this.name = name;
        this.date = date;
        this.room = room;
        this.speaker = speaker;
        this.type = type;

        if (date.contains("23"))
            day = "Saturday";
        else if (date.contains("24"))
            day = "Sunday";
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

    public void setDay(String day) {
        this.day = day;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHour() {
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
}
