package app.grp13.dilemma.logic.util;

/**
 * Created by champen on 27-11-2015.
 * midlertidig fil, lavet til 4. it.
 */
public class MainData {
    private static MainData ourInstance = new MainData();

    public static MainData getInstance() {

        if(ourInstance == null)
            ourInstance = new MainData();

        return ourInstance;
    }

    private MainData() {
    }
}
