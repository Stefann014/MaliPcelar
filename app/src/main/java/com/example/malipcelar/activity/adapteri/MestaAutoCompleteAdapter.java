package com.example.malipcelar.activity.adapteri;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.malipcelar.activity.pomocneKlase.MestaApi;

import java.util.ArrayList;

public class MestaAutoCompleteAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;
    int resource;
    Context context;
    MestaApi mestaApi = new MestaApi();

    public MestaAutoCompleteAdapter(Context context, int resId) {
        super(context, resId);
        this.context = context;
        this.resource = resId;

    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public String getItem(int pos) {
        return results.get(pos);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    results = mestaApi.autoComplete(constraint.toString());

                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;
    }

}
