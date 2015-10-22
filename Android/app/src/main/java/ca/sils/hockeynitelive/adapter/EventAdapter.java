package ca.sils.hockeynitelive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.sils.hockeynitelive.R;
import dataObject.Event;
import dataObject.Match;

/**
 * Created by cbongiorno on 19/10/2015.
 */
public class EventAdapter  extends BaseAdapter {
    private Context mContext;
    int layoutResourceId;

    public Match match = null;
    private List<Event> eventList = null;

    // Constructor
    public EventAdapter(Context c, int layoutResourceId, Match match){
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        this.match = match;
        this.eventList = match.getMatchEvent();
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView timer = (TextView) row.findViewById(R.id.timer);
        TextView message = (TextView) row.findViewById(R.id.message);
        ImageView icone = (ImageView) row.findViewById(R.id.icone);

        timer.setText(String.valueOf(eventList.get(position).getTime()));
        message.setText(eventList.get(position).getMessage());
        switch (eventList.get(position).getType()){
            case Event.GOAL:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.goal));
                break;
            case Event.PENALITY:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.penalite));
                break;
            case Event.TIMER:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.timer));
                break;
            case Event.MATCH_END:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.end));
                break;
        }
        return row;
    }

}