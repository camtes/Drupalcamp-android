package ccamposfuentes.es.drupalcamp.objets;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 06/02/16
 * Project: DrupalCamp
 */

@DatabaseTable
public class Speaker {

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String COMPANY = "company";
    public static final String TWITTER = "twitter";
    public static final String IMAGE = "image";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = COMPANY)
    private String company;

    @DatabaseField(columnName = TWITTER)
    private String twitter;

    @DatabaseField(columnName = IMAGE)
    private String image;

    public Speaker() {}

    public Speaker(String name, String company, String twitter, String image) {
        this.name = name;
        this.company = company;
        this.twitter = twitter;
        this.image = image;
    }

    public Speaker(String name, String company, String twitter) {
        this.name = name;
        this.company = company;
        this.twitter = twitter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
