package app.grp13.dilemma;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.CustomArrayAdapter;
import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.ListContainerSerializer;

/**
 * Created by LuxMiz on 1/13/2016.
 */
public class DilemmaListFragment extends Fragment {

    private TextView gravityText;
    private String[] tempGravity, tempQuestion;
    private ListView dilemmaListView;
    private List<IDilemma> myDilemmaList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_dilemma_list, container, false);
   //     dilemmaList = (ListView) getView().findViewById(R.id.list);
//        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
//        toolbar.setTitle("Din liste");
//        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);

        //dilemmaList.setOnItemClickListener();
        dilemmaListView = (ListView) rod.findViewById(R.id.dilemmaFragmentList);
        ListContainerSerializer<IDilemma> containerSerializer = (ListContainerSerializer<IDilemma>)getArguments().getSerializable("dilemmas");
        myDilemmaList = containerSerializer.getList();
        setDilemmaListAdapter(containerSerializer.getList());
        updateList(containerSerializer.getList());

        Log.i(DilemmaListFragment.class.getSimpleName(), "fragment loadet");

        return rod;
    }

    public void updateList(List<IDilemma> dlist){
        String[] tempTitle = new String[dlist.size()];
        final String[] tempGravity = new String[dlist.size()];
        for(int i=0 ; i<dlist.size() ; i++){
            Log.v("TAGSWAG", dlist.get(i).getTitle());
            tempTitle[i] = dlist.get(i).getTitle();
            tempGravity[i] = String.valueOf(dlist.get(i).getgravity());
        }
    }



    public void setDilemmaListAdapter(List<IDilemma> dilemmaArray){
        CustomArrayAdapter adapter = new CustomArrayAdapter(ApplicationState.getAppContext(), dilemmaArray);
        dilemmaListView.setAdapter(adapter);
        dilemmaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openAnswerDilemma = new Intent(getActivity(), AnswerDilemma.class);
                //openAnswerDilemma.putExtra("dilemma", dilemmaBundle(dController.getAllDilemmasArray()[position]));
                Bundle bundle = new Bundle();
                bundle.putSerializable("test", (BasicDilemma) myDilemmaList.get(position));
                openAnswerDilemma.putExtra("dilemma", bundle);
                startActivity(openAnswerDilemma);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
