package commands.statistic_handling;

public class UserDetails implements StatisticInterface {
    @Override
    public String title() {
        return "User Details";
    }

    @Override
    public String render() {
        return "LOL";
    }
}
