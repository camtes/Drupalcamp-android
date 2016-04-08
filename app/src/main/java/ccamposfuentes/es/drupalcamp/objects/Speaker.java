package ccamposfuentes.es.drupalcamp.objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 08/04/16
 * Project: DrupalCamp
 */

public class Speaker {
    public static final String ID = "_id";
    public static final String USERNAME = "username";
    public static final String COMPANY = "company";
    public static final String TWITTER = "twitter";
    public static final String IMAGE = "image";
    public static final String SESSIONS = "session";
    public static final String TYPE = "type";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = USERNAME)
    private String username;

    @DatabaseField(columnName = COMPANY)
    private String company;

    @DatabaseField(columnName = TWITTER)
    private String twitter;

    @DatabaseField(columnName = IMAGE)
    private String image;

    @DatabaseField(columnName = SESSIONS, dataType = DataType.SERIALIZABLE)
    private String[] session;

    @DatabaseField(columnName = TYPE)
    private int type;

    public Speaker() {}

    public Speaker(String name, String company, String twitter, String image, String[] session) {
        this.username = name;
        this.company = company;
        this.twitter = twitter;
        this.image = image;
        this.session = session;
        this.type = 1;
    }

    public Speaker(String username, String company, String twitter, String[] session) {
        this.username = username;
        this.company = company;
        this.twitter = twitter;
        this.session = session;
        this.type = 0;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getSession() {
        return session;
    }

    public void setSession(String[] session) {
        this.session = session;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
