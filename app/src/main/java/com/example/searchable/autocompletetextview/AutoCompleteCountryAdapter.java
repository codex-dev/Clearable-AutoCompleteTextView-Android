package com.example.searchable.autocompletetextview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Ravindu Fernando on 2019-12-12 at 10:04 AM
 * Ref - https://stackoverflow.com/a/19951763
 */

public class AutoCompleteCountryAdapter extends ArrayAdapter<Country> {
    private ArrayList<Country> items;
    private ArrayList<Country> itemsAll;
    private ArrayList<Country> suggestions;
    private int viewResourceId;

    @SuppressWarnings("unchecked")
    public AutoCompleteCountryAdapter(Context context, int viewResourceId,
                                ArrayList<Country> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Country>) items.clone();
        this.suggestions = new ArrayList<>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Country item = items.get(position);
        if (item != null) {
            TextView itemLabel =  v.findViewById(R.id.text);
            if (itemLabel != null) {
                itemLabel.setText(item.getCountryName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return ((Country) (resultValue)).getCountryName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Country item : itemsAll) {
                    if (item.getCountryName().toLowerCase()
                            .contains(constraint.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<Country> filteredList = (ArrayList<Country>) results.values;
            if (results != null && results.count > 0) {
//                clear();       //Possible ConcurrentModificationException imminent for a list with large size.
//                for (Country c : filteredList) {
//                    add(c);
//                }
                items.clear();
                items.addAll(filteredList);
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

}
