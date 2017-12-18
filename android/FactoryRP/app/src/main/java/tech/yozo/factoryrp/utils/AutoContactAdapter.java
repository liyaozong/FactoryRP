package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import tech.yozo.factoryrp.vo.resp.ContactCompany;

import java.util.ArrayList;
import java.util.List;

public class AutoContactAdapter extends ArrayAdapter<ContactCompany> implements Filterable {
    private Filter mFilter;
    private List<ContactCompany> mData;

    public AutoContactAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactCompany company = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView item = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, null);
        item.setText(company.getName());
        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        ContactCompany company = getItem(position);
        return company.getId();
    }

    @Override
    public Filter getFilter() {
        if(mFilter == null) {
            mFilter = new Filter() {
                FilterResults results = new FilterResults();

                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    ArrayList<String> newData = new ArrayList<>();
                    for(ContactCompany contact : mData) {
                        if(contact.getName().startsWith(charSequence.toString())) {
                            newData.add(contact.getName());
                        }
                    }
                    results.values = newData;
                    results.count = newData.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if(filterResults.count > 0) {
                        notifyDataSetChanged();
                    }
                }

                @Override
                public CharSequence convertResultToString(Object resultValue) {
                    return ((ContactCompany) resultValue).getName();
                }
            };
        }
        return mFilter;
    }
}
