package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import tech.yozo.factoryrp.entity.RepairGroup;

import java.util.List;

public class GroupSpinnerAdapter extends ArrayAdapter<RepairGroup> {
    public GroupSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepairGroup paramDicEnumResp = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView item = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        item.setText(paramDicEnumResp.getName());
        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        RepairGroup paramDicEnumResp = getItem(position);
        return paramDicEnumResp.getId();
    }
}
