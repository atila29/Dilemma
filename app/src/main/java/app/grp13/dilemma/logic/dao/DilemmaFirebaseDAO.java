package app.grp13.dilemma.logic.dao;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.MainActivity;
import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.builder.DilemmaFactory;
import app.grp13.dilemma.logic.dto.BasicAnswer;
import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.BasicReply;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 02-01-2016.
 */
public class DilemmaFirebaseDAO implements IDilemmaDAO {

    private Firebase firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
    private List<IDilemma> dilemmas;


    private boolean connection;


    public DilemmaFirebaseDAO() {
        dilemmas = new ArrayList<>();

        Firebase dilemmaref = firebase.child("dilemmas");
        dilemmaref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Firebase con = new Firebase("https://dtu-dilemma.firebaseio.com/.info/connected");
                con.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot connectionSnapShot) {
                        connection = connectionSnapShot.getValue(Boolean.class);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    System.out.println(d.child("title").getValue());
                    String title = (String) d.child("title").getValue();
                    //boolean active = (boolean)d.child("active").getValue();
                    String description = (String) d.child("description").getValue();
                    int gravity = Integer.valueOf(d.child("gravity").getValue().toString());
                    int id = Integer.valueOf(d.child("id").getValue().toString());

                    List<IAnswer> possibleAnswers = new ArrayList<>();
                    for (DataSnapshot l : d.child("possibleAnswers").getChildren()) {
                        String answer = (String) l.child("answer").getValue();
                        possibleAnswers.add(new BasicAnswer(answer));
                    }

                    // laver selve dilemma'et
                    IDilemma dilemma = new DilemmaFactory().createBasicDilemma(id, title, description, gravity, possibleAnswers);

                    // test
                    Log.v("SHIT", dilemma.getTitle() + " her,");

                    for (DataSnapshot l : d.child("replys").getChildren()) {
                        int replyID = Integer.valueOf(l.child("id").getValue().toString());
                        String reply = (String) l.child("reply").getValue();
                        IReply temp = new BasicReply();
                        temp.setID(replyID);
                        temp.setReply(reply);
                        dilemma.addReply(temp);
                    }

                    dilemmas.add(dilemma);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                dilemmas = null;
            }
        });
    }

    @Override
    public void saveDilemma(IDilemma dilemma) throws DAOException {
        Firebase ref = firebase.child("dilemmas").child(String.valueOf(dilemma.getID()));
        ref.setValue(dilemma);
    }

    @Override
    public List<IDilemma> getDilemmas() throws DAOException {
        if(dilemmas == null)
            throw new DAOException("Firebase exception");
        return dilemmas;
    }

    @Override
    public void deleteDilemma(IDilemma dilemma) throws DAOException {

    }

    public boolean isNetworkAvalible(){
        ConnectivityManager cm = (ConnectivityManager) ApplicationState.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni !=null && ni.isConnected();
    }

    public boolean isConnected() {
        return connection;
    }

    // bedre løsning havde været at lave query til firebase for at hente specific data derfra.
    @Override
    public List<IDilemma> getSpecificDilemmas(List<Integer> index) throws DAOException {
        List<IDilemma> list = new ArrayList<>();
        for(IDilemma d : getDilemmas()){
            if(index.contains(Integer.valueOf(d.getID())))
                list.add(d);
        }
        return list;
    }


}
