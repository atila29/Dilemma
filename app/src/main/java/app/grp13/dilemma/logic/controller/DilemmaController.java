package app.grp13.dilemma.logic.controller;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.grp13.dilemma.logic.builder.DilemmaFactory;
import app.grp13.dilemma.logic.builder.ReplyBuilder;
import app.grp13.dilemma.logic.dao.DilemmaFirebaseDAO;
import app.grp13.dilemma.logic.dao.IDilemmaDAO;
import app.grp13.dilemma.logic.dto.BasicAnswer;
import app.grp13.dilemma.logic.dto.BasicReply;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DAOException;
import app.grp13.dilemma.logic.exceptions.DilemmaException;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class DilemmaController implements Serializable{

    private ReplyBuilder replyBuilder;
    private DilemmaFactory dilemmaFactory;
    private final String FILENAME = "dilemmas.bin";
    private IDilemmaDAO dilemmaDAO;

    private Map<Integer,IDilemma> dilemmaMap;
    //private Map<IAnswer,IReply> answerReplyMap;

    public DilemmaController() {

        Log.v("SHIT", "bliver kørt 1");

        dilemmaMap = new HashMap<Integer, IDilemma>();

        Map<IAnswer, IReply> a2r = new HashMap<>();
        a2r.put(new BasicAnswer(""), new BasicReply());
        replyBuilder = new ReplyBuilder(a2r);

        dilemmaFactory = new DilemmaFactory();
        dilemmaDAO = new DilemmaFirebaseDAO();

    }



    public void addDilemma(IDilemma dilemma) {

        this.dilemmaMap.put(dilemma.getID(), dilemma);
    }

    public DilemmaController(List<IDilemma> dilemmas) {
        this();
        Log.v("SHIT", "bliver kørt 3");
        for(IDilemma d : dilemmas) {
            Log.v("SHIT", "bliver kørt 2");
            this.addDilemma(d);
        }
    }

    // ikke en synderlig fleksibel måde at lave dilemmaer, men virker (nok!)
    public int createDilemma(String title, String description, int gravity, String ... answerOptions) {

        // id skal være unikt, skal derfor laves om.
        Integer key = 0;
        do {
            key = (int)(Math.random()*(2^32));
        } while(dilemmaMap.containsKey(key));

        List<IAnswer> tempAnswerOptions = new ArrayList<>();
        for(String s : answerOptions) {
            tempAnswerOptions.add(new BasicAnswer(s));
        }

        this.dilemmaMap.put(key, dilemmaFactory.createBasicDilemma(key, title, description, gravity, tempAnswerOptions));
        return key;
    }


    public List<IDilemma> getAllDilemmas(){
        List<IDilemma> temp = new ArrayList<>();
        temp.addAll(dilemmaMap.values());
        return temp;
    }

    public IDilemma[] getAllDilemmasArray() {
        //  return dilemmaMap.values().toArray();
        int i = 0;
        IDilemma[] temp = new IDilemma[dilemmaMap.values().size()];
        for(IDilemma d : dilemmaMap.values()) {
            temp[i++] = d;
        }
        return temp;
    }

    public void deleteDilemma(int id) throws DilemmaException {
        if(!dilemmaMap.containsKey(id))
            throw new DilemmaException("Dilemma not found");

        dilemmaMap.remove(id);
    }

    public IDilemma getDilemma(int id) throws DilemmaException {
        if(!dilemmaMap.containsKey(id))
            throw new DilemmaException("Dilemma not found");
        return dilemmaMap.get(id);
    }

    public void answerDilemma(int id, int answerIndex) throws IllegalAccessException, DilemmaException, InstantiationException {
        dilemmaMap.get(id).addReply(replyBuilder.createReply(dilemmaMap.get(id).getPossibleAnswers().get(answerIndex), id));
    }

    public int getDilemmaKey(IDilemma dilemma) throws DilemmaException {
        boolean fejl = true;
        for(IDilemma i : dilemmaMap.values()) {
            Log.v("TE2", String.valueOf(dilemma.getID()));
            Log.v("TE2", String.valueOf(i.getID()));
            if(i.getID() == dilemma.getID()){
                Log.v("TE2", "bliver kørt");
                return i.getID();
            }
        }
        throw new DilemmaException("ID NOT FOUND");
    }


    public void saveDilemmasToDevice(Context ctx) throws IOException {

        DilemmaStorage storage = new DilemmaStorage();
        storage.setList(this.getAllDilemmas());

        File file = new File(ctx.getFilesDir() + FILENAME);
        if(!file.exists())
            file.createNewFile();

        FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(storage);
        fos.flush();
        objectOutputStream.flush();
        fos.close();
    }

    public void loadDilemmasFromDevice(Context ctx) throws IOException, ClassNotFoundException {

        FileInputStream inputStream = ctx.openFileInput(FILENAME);

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        DilemmaStorage temp = (DilemmaStorage)objectInputStream.readObject();

        inputStream.close();

        this.dilemmaMap = new HashMap<>();

        for(IDilemma a : temp.getList()) {
            this.dilemmaMap.put(a.getID(), a);
        }
    }

    public IDilemmaDAO getDilemmaDAO() {
        return dilemmaDAO;
    }

    private class DilemmaStorage implements Serializable {
        private List<IDilemma> list = new ArrayList<>();

        public List<IDilemma> getList() {
            return list;
        }

        public void setList(List<IDilemma> list) {
            this.list = list;
        }
    }

}


