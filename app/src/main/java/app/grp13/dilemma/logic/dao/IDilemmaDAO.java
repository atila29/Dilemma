package app.grp13.dilemma.logic.dao;

import java.util.List;

import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 02-01-2016.
 */
public interface IDilemmaDAO {

    void saveDilemma(IDilemma dilemma) throws DAOException;
    List<IDilemma> getDilemmas()throws DAOException;
    void deleteDilemma(IDilemma dilemma) throws  DAOException;
    boolean isLoading();

    boolean isConnected();


}
