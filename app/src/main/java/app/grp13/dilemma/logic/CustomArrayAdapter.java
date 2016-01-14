package app.grp13.dilemma.logic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.DilemmaRow;
import app.grp13.dilemma.R;
import app.grp13.dilemma.logic.dto.IDilemma;

/**
 * Created by LuxMiz on 1/13/2016.
 */
public class CustomArrayAdapter extends BaseAdapter {

    Context context;
    List<DilemmaRow> rowItem;
    TextView dilemmaQuestionInText;
    TextView gravityText;
    String[] tempGravity = {"1","2","3","4","5"};

    public CustomArrayAdapter(Context context, List<IDilemma> rowItem) {
        this.context = context;
        this.rowItem = new ArrayList<>();

        for (int i = 0; i < rowItem.size(); i++){
            this.rowItem.add(new DilemmaRow(rowItem.get(i).getgravity(), rowItem.get(i).getTitle(), rowItem.get(i).getID()));
        }
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_dilemma_row, null);
        }

        gravityText = (TextView) convertView.findViewById(R.id.gravityText);
        dilemmaQuestionInText = (TextView) convertView.findViewById(R.id.questionText);
        DilemmaRow row_pos = rowItem.get(position);
        gravityText.setText(String.valueOf(row_pos.getGravity()));
        dilemmaQuestionInText.setText(row_pos.getQuestion());

        if (row_pos.getGravity() == 1) {
            gravityText.setBackgroundResource(R.drawable.gravity1_container);
        } else if (row_pos.getGravity() == 2) {
            gravityText.setBackgroundResource(R.drawable.gravity2_container);
        } else if (row_pos.getGravity() == 3) {
            gravityText.setBackgroundResource(R.drawable.gravity3_container);
        } else if (row_pos.getGravity() == 4) {
            gravityText.setBackgroundResource(R.drawable.gravity4_container);
        } else {
            gravityText.setBackgroundResource(R.drawable.gravity5_container);
        }

        return convertView;

    }
}
