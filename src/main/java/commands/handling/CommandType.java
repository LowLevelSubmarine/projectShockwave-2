package commands.handling;

public enum CommandType {

    ADMINISTRATION("Administrierung"),
    MUSIC("Musik"),
    INFORMATION("Informationen"),
    SETTINGS("Einstellungen");

    private String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
