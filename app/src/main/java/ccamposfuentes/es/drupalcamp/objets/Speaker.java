package ccamposfuentes.es.drupalcamp.objets;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 06/02/16
 * Project: DrupalCamp
 */

public class Speaker {

    String name;
    String company;
    String twitter;
    String image;

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
