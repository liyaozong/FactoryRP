package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.PartsDetailActivity;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.nio.charset.Charset;
import java.util.List;


/**
 * 备件列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link PartsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartsListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Long mParam1;
    private String mParam2;

    private ListView mPartsListView;
    private List<SparePartsResp> parts;

    private PartsListAdapter mListAdapter;

    public PartsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartsListFragment newInstance(Long param1, String param2) {
        PartsListFragment fragment = new PartsListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getLong(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parts_list, container, false);
        //TODO
        mPartsListView = (ListView) view.findViewById(R.id.lv_parts_list);
        mPartsListView.setEmptyView(view.findViewById(R.id.textView18));
        mListAdapter = new PartsListAdapter(getContext());
        mPartsListView.setAdapter(mListAdapter);
        mPartsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int count,
                                    long arg3) {
                Intent intent = new Intent(getActivity(), PartsDetailActivity.class);
                intent.putExtra(PartsDetailActivity.PARTS_ID, 181421423);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        //TODO
        SparePartsQueryReq req = new SparePartsQueryReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(100);
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(null, HttpClient.PARTS_LIST, param, requestPartsListResponse);
    }

    private JsonHttpResponseHandler requestPartsListResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    parts = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SparePartsResp.class);
                    mListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.failure_get, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.exception_message, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(getContext(), R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView type;
        TextView stock;
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_parts_list, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_parts_name);
                holder.code = (TextView) convertView.findViewById(R.id.tv_parts_no);
                holder.type = (TextView) convertView.findViewById(R.id.tv_parts_type);
                holder.stock = (TextView) convertView.findViewById(R.id.tv_parts_stock);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            //TODO
            holder.name.setText(parts.get(i).getName());
            holder.code.setText(parts.get(i).getCode());
            holder.type.setText(parts.get(i).getSpecificationsAndodels());
            holder.stock.setText("4个");

            return convertView;
        }
    }
}
