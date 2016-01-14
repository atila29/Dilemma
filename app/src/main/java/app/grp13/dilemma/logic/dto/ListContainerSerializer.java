package app.grp13.dilemma.logic.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by champen on 14-01-2016.
 */
public class ListContainerSerializer <E> implements Serializable{
    private List<E> list;

    public ListContainerSerializer(List<E> list){
        this.list = list;
    }

    public List<E> getList() {
        return list;
    }
}
