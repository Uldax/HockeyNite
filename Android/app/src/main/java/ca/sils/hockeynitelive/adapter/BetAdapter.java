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
import dataObject.BetRespond;

/**
 * Created by cbongiorno on 22/10/2015.
 */
public class BetAdapter extends BaseAdapter {
    private Context mContext;
    int layoutResourceId;

    private List<BetRespond> betList = null;

    // Constructor
    public BetAdapter(Context c, int layoutResourceId, List<BetRespond> betList){
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        this.betList = betList;
    }

    @Override
    public int getCount() {
        return betList.size();
    }

    @Override
    public Object getItem(int i) {
        return betList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView message = (TextView) row.findViewById(R.id.message);
        ImageView icone = (ImageView) row.findViewById(R.id.icone);

        date.setText(betList.get(position).getBetID());
        message.setText(betList.get(position).getBetAmount() + " on " + betList.get(position).getMatchID());
        switch (betList.get(position).getMatchID()){
            case 0:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.button_red));
                break;
            case 1:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.button_green));
                break;
            default:
                icone.setImageDrawable(mContext.getResources().getDrawable(R.drawable.button_yellow));
                break;
        }
        return row;
    }
}
