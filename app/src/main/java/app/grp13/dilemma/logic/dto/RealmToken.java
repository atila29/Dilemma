package app.grp13.dilemma.logic.dto;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by champen on 08-01-2016.
 */
public class RealmToken extends RealmObject {
    @Required
    private String token;

    public RealmToken(String token) {
        this.token = token;
    }

    public RealmToken() {
        this.token = "";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
