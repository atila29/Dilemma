package app.grp13.dilemma;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.CustomArrayAdapter;
import app.grp13.dilemma.logic.dto.IDilemma;

/**
 * Created by LuxMiz on 1/13/2016.
 */
public class DilemmaListFragment extends Fragment {

    TextView gravityText;
    String[] tempGravity, tempQuestion;
    ListView dilemmaList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_dilemma_list, container, false);
   //     dilemmaList = (ListView) getView().findViewById(R.id.list);
//        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
//        toolbar.setTitle("Din liste");
//        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);

        //dilemmaList.setOnItemClickListener();

        return rod;
    }

    public void updateList(IDilemma[] array){
        String[] tempTitle = new String[array.length];
        final String[] tempGravity = new String[array.length];
        for(int i=0 ; i<array.length ; i++){
            tempTitle[i] = array[i].getTitle();
            tempGravity[i] = String.valueOf(array[i].getgravity());
        }
    }

    public void setDilemmaListAdapter(ArrayList<IDilemma> dilemmaArray){
        CustomArrayAdapter adapter = new CustomArrayAdapter(ApplicationState.getAppContext(), dilemmaArray);
        dilemmaList.setAdapter(adapter);
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
