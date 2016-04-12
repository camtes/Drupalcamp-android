package ccamposfuentes.es.apiclient.restObject;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 12/04/16
 * Project: DrupalCamp
 */

public class RestDeviceId {
    private String deviceId;

    public RestDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
