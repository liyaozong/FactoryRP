package tech.yozo.factoryrp.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.PartsSelectActivity;
import tech.yozo.factoryrp.vo.resp.device.trouble.UsedSparePartsVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修所需的备件页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairPartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairPartsFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";

    private static final int REQUEST_ADD_PARTS = 100;
    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private int mParam_mode;
    private WorkOrderDetailVo mParam_obj;

    private ListView mRepairPartsView;
    private PartsListAdapter mListAdapter;
    private Button mAddParts;

    private List<UsedSparePartsVo> parts;

    public RepairPartsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairInfoFragment.
     */
    public static RepairPartsFragment newInstance(int param1, WorkOrderDetailVo param2) {
        RepairPartsFragment fragment = new RepairPartsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_obj = (WorkOrderDetailVo) getArguments().getSerializable(ARG_PARAM2);
        }

        if(mParam_obj != null) {
            if (mParam_obj.getReplaceSpares() == null) {
                parts = new ArrayList<>();
            } else {
                parts = mParam_obj.getReplaceSpares();
            }
        } else {
            parts = new ArrayList<>();
        }

        if (mContext instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) mContext;
            mListener.onModifyParts(parts);
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_parts, container, false);
        mAddParts = (Button) view.findViewById(R.id.b_add_part);
        mAddParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), PartsSelectActivity.class), REQUEST_ADD_PARTS);
            }
        });

        mRepairPartsView = (ListView) view.findViewById(R.id.lv_repair_parts);
        mListAdapter = new RepairPartsFragment.PartsListAdapter(getContext());
        mRepairPartsView.setAdapter(mListAdapter);
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onModifyParts(List<UsedSparePartsVo> parts);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_ADD_PARTS) {
            SparePartsResp param = (SparePartsResp) data.getSerializableExtra(PartsSelectActivity.PARTS_OBJECT);
            boolean exist = false;
            for(UsedSparePartsVo p : parts) {
                //TODO Id is better
                if(p.getCode().equals(param.getCode())) {
                    if(p.getAmount() >= 10) {
                        Toast.makeText(getContext(), R.string.hint_parts_count, Toast.LENGTH_SHORT).show();
                    } else {
                        p.setAmount(p.getAmount() + 1);
                    }
                    exist = true;
                    break;
                }
            }
            if(!exist) {
                UsedSparePartsVo obj = new UsedSparePartsVo();
                obj.setCode(param.getCode());
                obj.setName(param.getName());
                obj.setSpecificationsAndodels(param.getSpecificationsAndodels());
                obj.setAmount(1);
                parts.add(obj);
            }
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {

    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView type;
        Spinner number;
        ImageButton delete;
    }

    private class PartsListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private PartsListAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (parts != null) {
                return parts.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            RepairPartsFragment.ViewHolder holder;
            if(convertView == null) {
                holder = new RepairPartsFragment.ViewHolder();
                convertView = mInflater.inflate(R.layout.item_repair_parts, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_parts_name);
                holder.code = (TextView) convertView.findViewById(R.id.tv_parts_no);
                holder.type = (TextView) convertView.findViewById(R.id.tv_parts_type);
                holder.number = (Spinner) convertView.findViewById(R.id.tv_parts_num);
                holder.delete = (ImageButton) convertView.findViewById(R.id.ib_delete_part);
                convertView.setTag(holder);
            }
            else {
                holder = (RepairPartsFragment.ViewHolder)convertView.getTag();
            }
            holder.name.setText(parts.get(i).getName());
            holder.code.setText(parts.get(i).getCode());
            holder.type.setText(parts.get(i).getSpecificationsAndodels());
            holder.number.setSelection(parts.get(i).getAmount() - 1, false);
            holder.number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    parts.get(i).setAmount(position + 1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parts.remove(i);
                    mListAdapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
