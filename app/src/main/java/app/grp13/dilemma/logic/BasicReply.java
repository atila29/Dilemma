package app.grp13.dilemma.logic;

/**
 * Created by champen on 24-11-2015.
 */
public class BasicReply implements IReply{

    private String reply = new String("");
    private int id = 0;

    @Override
    public String getReply() {
        return this.reply;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public void setReply(String reply) {
        this.reply = reply;
    }
    

}
