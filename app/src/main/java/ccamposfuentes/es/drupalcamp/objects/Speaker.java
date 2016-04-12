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
    public static final String NAME = "name";
    public static final String COMPANY = "company";
    public static final String IMAGE = "image";
    public static final String TYPE = "type";

    @DatabaseField(id = true, columnName = ID)
    private String id;

    @DatabaseField(columnName = USERNAME)
    private String username;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = COMPANY)
    private String company;

    @DatabaseField(columnName = IMAGE)
    private String image;

    @DatabaseField(columnName = TYPE)
    private int type;

    public Speaker() {}

    public Speaker(String id, String username, String name, String company, String image) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.company = company;
        this.image = image;
        this.type = 1;
    }

    public Speaker(String id, String username, String name, String company) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.company = company;
        this.type = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
