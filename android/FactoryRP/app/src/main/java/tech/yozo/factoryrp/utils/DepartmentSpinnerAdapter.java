package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.RepairGroup;

import java.util.List;

public class DepartmentSpinnerAdapter extends ArrayAdapter<Department> {
    public DepartmentSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Department department = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView item = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        item.setText(department.getName());
        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        Department department = getItem(position);
        return department.getId();
    }
}
