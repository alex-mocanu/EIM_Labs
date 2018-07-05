package ro.pub.cs.systems.eim.lab10.googlemapplaces.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ro.pub.cs.systems.eim.lab10.R;
import ro.pub.cs.systems.eim.lab10.googlemapplaces.model.Place;

public class PlacesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<Place> data;

    private static class PlaceViewHolder {
        private TextView placeInformationTextView;
    }

    public PlacesAdapter(Context context, List<Place> data) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        PlaceViewHolder placeViewHolder;

        Place place = (Place)getItem(position);

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.place, parent, false);
            placeViewHolder = new PlaceViewHolder();
            placeViewHolder.placeInformationTextView = (TextView)view.findViewById(R.id.place_information_text_view);
            view.setTag(placeViewHolder);
        } else {
            view = convertView;
        }

        placeViewHolder = (PlaceViewHolder)view.getTag();
        placeViewHolder.placeInformationTextView.setText(place.toString());

        return view;
    }

}
