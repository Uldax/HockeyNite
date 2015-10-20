package ca.sils.hockeynitelive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import ca.sils.hockeynitelive.R;
import dataObject.TinyMatch;

/**
 * Created by cbongiorno on 19/10/2015.
 */
public class MatchAdapter extends BaseAdapter{
    private Context mContext;
    int layoutResourceId;

    public HashMap<Integer, TinyMatch> listMatch = null;

    // Constructor
    public MatchAdapter(Context c, int layoutResourceId, HashMap<Integer, TinyMatch> listMatch){
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        this.listMatch = listMatch;
    }

    @Override
    public int getCount() {
        return listMatch.size();
    }

    @Override
    public Object getItem(int i) {
        return listMatch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView name_teamA = (TextView) row.findViewById(R.id.name_teamA);
        TextView name_teamB = (TextView) row.findViewById(R.id.name_teamB);
        TextView timer = (TextView) row.findViewById(R.id.timer);

        name_teamA.setText(listMatch.get(position).getTeamA());
        name_teamB.setText(listMatch.get(position).getTeamB());
        timer.setText(listMatch.get(position).getStringTime());


        return row;
    }

}
