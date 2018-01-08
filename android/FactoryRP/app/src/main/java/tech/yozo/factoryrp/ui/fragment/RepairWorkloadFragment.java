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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.MaintenanceEngineer;
import tech.yozo.factoryrp.ui.EngineerSelectActivity;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkTimeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修工作量页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairWorkloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairWorkloadFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";
    private static final int REQUEST_ADD_MEMBER = 101;

    @BindView(R.id.b_add_workload)
    Button bAddWorkload;
    @BindView(R.id.lv_repair_workload)
    ListView lvRepairWorkload;
    Unbinder unbinder;

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private int mParam_mode;
    private WorkOrderDetailVo mParam_obj;
    private List<WorkTimeVo> modifyWorkTimes;
    private WorkTimesAdapter mListAdapter;

    public RepairWorkloadFragment() {
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
    public static RepairWorkloadFragment newInstance(int param1, WorkOrderDetailVo param2) {
        RepairWorkloadFragment fragment = new RepairWorkloadFragment();
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
            if (mParam_obj.getWorkTimes() == null) {
                modifyWorkTimes = new ArrayList<>();
            } else {
                modifyWorkTimes = mParam_obj.getWorkTimes();
            }
        } else {
            modifyWorkTimes = new ArrayList<>();
        }

        if (mContext instanceof RepairPartsFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) mContext;
            mListener.onModifyWorkTimes(modifyWorkTimes);
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_workload, container, false);
        unbinder = ButterKnife.bind(this, view);

        mListAdapter = new WorkTimesAdapter(getContext());
        lvRepairWorkload.addHeaderView(inflater.inflate(R.layout.item_worktime_head, null));
        lvRepairWorkload.setAdapter(mListAdapter);
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onModifyWorkTimes(List<WorkTimeVo> workTimes);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.b_add_workload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_add_workload:
                startActivityForResult(new Intent(getActivity(), EngineerSelectActivity.class), REQUEST_ADD_MEMBER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_ADD_MEMBER) {
            MaintenanceEngineer param = (MaintenanceEngineer) data.getSerializableExtra(EngineerSelectActivity.ENGINEER);
            boolean exist = false;
            for(WorkTimeVo p : modifyWorkTimes) {
                if(p.getRepairUserId().equals(param.getId())) {
                    exist = true;
                    Toast.makeText(getContext(), R.string.hint_parts_count, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if(!exist) {
                WorkTimeVo obj = new WorkTimeVo();
                obj.setRepairUserId(param.getId());
                obj.setRepairUserName(param.getName());
                obj.setCostHour("1");
                modifyWorkTimes.add(obj);
            }
            mListAdapter.notifyDataSetChanged();
        }
    }

    private static class ViewHolder
    {
        TextView name;
        Spinner number;
        ImageButton delete;
    }

    private class WorkTimesAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private WorkTimesAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (modifyWorkTimes != null) {
                return modifyWorkTimes.size();
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
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_worktime_list, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_engineer_name);
                holder.number = (Spinner) convertView.findViewById(R.id.spinner_worktime);
                holder.delete = (ImageButton) convertView.findViewById(R.id.ib_delete_engineer);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText(modifyWorkTimes.get(i).getRepairUserName());
            holder.number.setSelection(Integer.decode(modifyWorkTimes.get(i).getCostHour()) -1 , false);
            holder.number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    modifyWorkTimes.get(i).setCostHour(String.valueOf(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modifyWorkTimes.remove(i);
                    mListAdapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
