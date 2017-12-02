package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.DeviceInfo;
import tech.yozo.factoryrp.ui.DeviceDetailActivity;
import tech.yozo.factoryrp.utils.HttpClient;

import java.nio.charset.Charset;
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
public class DeviceListFragment extends BaseFragment {

    private List<DeviceInfo> devices;

    private ListView mListView;
    private DeviceListAdapter mListAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button btn;

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
    // TODO: Rename and change types and number of parameters
    public static DeviceListFragment newInstance(String param1, String param2) {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
                Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
                intent.putExtra(DeviceDetailActivity.DEVICE_OBJECT, devices.get(count));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        JSONObject object = new JSONObject();
        StringEntity param = new StringEntity(object.toString(), Charset.forName("UTF-8"));
        client.setHeaderParam("Content-Type", "application/json");
        client.post(null, HttpClient.DEVICE_LIST, client.getHeaders(), param, "application/json", requestDeviceListResponse);
    }

    @Override
    protected void buildUI() {

    }

    private JsonHttpResponseHandler requestDeviceListResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if(response.has("data")) {
                    devices = JSONArray.parseArray(response.getJSONObject("data").getString("list"), DeviceInfo.class);
                    mListAdapter.notifyDataSetChanged();
                } else {
                    //TODO
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("ERROR", errorResponse.toString());
        }
    };

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                holder.name = (TextView)convertView.findViewById(R.id.tv_device_name);
                holder.code = (TextView)convertView.findViewById(R.id.tv_device_code);
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
