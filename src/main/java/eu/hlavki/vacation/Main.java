package eu.hlavki.vacation;

public class Main {

    public static void main(String[] args) throws Exception {
        String calFile = "/data/home/hlavki/Dovolenky_xit.camp_ginpjmurvhk9s4akcb0k660juo@group.calendar.google.com.ics";
        Vacations vacations = Vacations.parse(calFile, "hlk");


        long days = vacations.stream().filter(d -> d.getYear() == 2018).count();
        System.out.println("Pocet dni v roku 2018: " + days);

        long daysAll = vacations.stream().count();
        System.out.println("Pocet dni celkovo: " + daysAll);
    }
}
