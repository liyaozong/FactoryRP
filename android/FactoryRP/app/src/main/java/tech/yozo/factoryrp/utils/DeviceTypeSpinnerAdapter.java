package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.DeviceType;

import java.util.List;

public class DeviceTypeSpinnerAdapter extends ArrayAdapter<DeviceType> {
    public DeviceTypeSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceType deviceType = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView item = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        item.setText(deviceType.getName());
        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        DeviceType deviceType = getItem(position);
        return deviceType.getId();
    }
}
