package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.DeviceDetailActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.util.List;


/**
 * 设备列表页
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceListFragment extends BaseFragment implements HttpClient.OnHttpListener {

    private Context mContext;
    private ListView mListView;
    private DeviceListAdapter mListAdapter;
    private List<SimpleDeviceInfoResp> devices;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private String mParam_id;

    private LoadingDialog dialog;
    private OnFragmentInteractionListener mListener;

    public DeviceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceListFragment.
     */
    public static DeviceListFragment newInstance(int param1, String param2) {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_id = getArguments().getString(ARG_PARAM2);
        }

        if(mParam_mode == Constant.FOR_CHOICE_MODE) {
            if (mContext instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) mContext;
            } else {
                throw new RuntimeException(mContext.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setEmptyView(view.findViewById(R.id.textView22));
        mListAdapter = new DeviceListAdapter(getActivity());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int count,
                                    long arg3) {
                switch (mParam_mode) {
                    case Constant.FOR_BROWE_MODE:
                        Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
                        intent.putExtra(DeviceDetailActivity.DEVICE_ID, devices.get(count).getId());
                        startActivity(intent);
                        break;
                    case Constant.FOR_CHOICE_MODE:
                        if(mListener != null) {
                            mListener.onDeviceSelected(devices.get(count));
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
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
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        devices = client.getSimpleDeviceInfoList();
        if(devices == null) {
            LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                    .setMessage(R.string.loading_loading);
            dialog = builder.create();
            dialog.show();
            client.requestDeviceList(getContext(), this);
        }
    }

    @Override
    protected void buildUI() {
        HttpClient client = HttpClient.getInstance();
        devices = client.getSimpleDeviceInfoList();
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == HttpClient.REQUEST_DEVICE_LIST) {
            HttpClient client = HttpClient.getInstance();
            devices = client.getSimpleDeviceInfoList();
            mListAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    @Override
    public void onFailure(int requestType) {
        if(requestType == HttpClient.REQUEST_DEVICE_LIST) {
            dialog.dismiss();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDeviceSelected(SimpleDeviceInfoResp device);
    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView type;
        TextView dept;
        TextView place;
    }

    private class DeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private DeviceListAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(devices != null) {
                return devices.size();
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_device_list, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_device_name);
                holder.code = (TextView) convertView.findViewById(R.id.tv_device_code);
                holder.type = (TextView) convertView.findViewById(R.id.tv_device_type);
                holder.dept = (TextView) convertView.findViewById(R.id.tv_device_dept);
                holder.place = (TextView) convertView.findViewById(R.id.tv_device_place);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText(devices.get(i).getName());
            holder.code.setText(devices.get(i).getCode());
            holder.type.setText(String.valueOf(devices.get(i).getSpecification()));
            holder.dept.setText(String.valueOf(devices.get(i).getUseDept()));
            holder.place.setText(devices.get(i).getInstallationAddress());

            return convertView;
        }
    }
}
