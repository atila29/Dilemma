package app.grp13.dilemma.logic.dao;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 02-01-2016.
 */
public class DilemmaFirebaseDAO implements IDilemmaDAO {

    private Firebase firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
    private Firebase dilemmaref = firebase.child("dilemmas");
    private List<IDilemma> dilemmas;



    @Override
    public void saveDilemma(IDilemma dilemma) throws DAOException {
        Firebase ref = firebase.child("dilemmas").child(String.valueOf(dilemma.getID()));
        ref.setValue(dilemma);
    }

    @Override
    public List<IDilemma> getDilemmas() throws DAOException {
        if(dilemmas == null)
            throw new DAOException("fucking firebase error or some other shit happened, i really don't know.");
        return dilemmas;
    }

    @Override
    public void deleteDilemma(IDilemma dilemma) throws DAOException {

    }
}
