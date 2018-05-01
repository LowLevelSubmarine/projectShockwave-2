package database.config;

public class BotUser {
    private String name = "The name of the token";
    private String token = "The token";

    public String getName() {
        return this.name;
    }
    public String getToken() {
        return this.token;
    }

    public String isValid() {
        if (this.name.isEmpty()) {
            return "The field \"name\" is empty";
        }
        if (this.token.isEmpty()) {
            return "The field \"token\" is empty";
        }
        return null;
    }
}
