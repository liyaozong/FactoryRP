package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.RepairDetailActivity;
import tech.yozo.factoryrp.ui.RepairDetailSimpleActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * 维修工单列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairRecordListFragment extends BaseFragment implements HttpClient.OnHttpListener, AbsListView.OnScrollListener {
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";
    private static final int OPERATE_REPAIR = 123;

    private int param_mode;
    private long param_id;

    private SwipeRefreshLayout refreshLayout;
    private ListView mRepairRecordListView;
    private List<WaitAuditWorkOrderVo> troubles;

    private RepairRecordListAdapter mRepairRecordListAdapter;
    private LoadingDialog dialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    private int visibleItem;
    private boolean isUpdateData;
    private View footer;
    private int currentPage = 1;

    public RepairRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairRecordListFragment.
     */
    public static RepairRecordListFragment newInstance(int param1, long param2) {
        RepairRecordListFragment fragment = new RepairRecordListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putLong(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param_mode = getArguments().getInt(ARG_PARAM1);
            param_id = getArguments().getLong(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        footer = inflater.inflate(R.layout.footer_load_layout, null);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_record_list, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(new int[]{R.color.colorHighlight, R.color.colorPrimary});
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                troubles = null;
                requestTroubleList(currentPage++);
            }
        });

        mRepairRecordListView = (ListView) view.findViewById(R.id.lv_repair_record);
        mRepairRecordListView.setEmptyView(view.findViewById(R.id.textView19));

        mRepairRecordListAdapter = new RepairRecordListAdapter(getContext());
        mRepairRecordListView.setAdapter(mRepairRecordListAdapter);
        mRepairRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int count,
                                    long arg3) {
                Intent intent;
                if (param_mode == HttpClient.REQUEST_TROUBLE_WAIT_AUDIT || param_mode == HttpClient.REQUEST_TROUBLE_WAIT_ASSIGN) {
                    intent = new Intent(getActivity(), RepairDetailSimpleActivity.class);
                    intent.putExtra(RepairDetailSimpleActivity.TROUBLE_ID, troubles.get(count).getId());
                    intent.putExtra(RepairDetailSimpleActivity.OPERATE_MODE, param_mode);
                } else {
                    intent = new Intent(getActivity(), RepairDetailActivity.class);
                    intent.putExtra(RepairDetailActivity.TROUBLE_ID, troubles.get(count).getId());
                    intent.putExtra(RepairDetailActivity.OPERATE_MODE, param_mode);
                }
                startActivityForResult(intent, OPERATE_REPAIR);
            }
        });
        mRepairRecordListView.setOnScrollListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {
//        if(troubles == null) {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                .setMessage(R.string.loading_loading);
        dialog = builder.create();
        dialog.show();
        requestTroubleList(currentPage++);
//        } else {
//            mRepairRecordListAdapter.notifyDataSetChanged();
//        }
    }

    private void requestTroubleList(int page) {
        TroubleListReq req = new TroubleListReq();
        req.setCurrentPage(page);
        req.setItemsPerPage(20);

        if (Constant.FOR_DEVICE_ID == param_mode) {
            req.setDeviceId(param_id);
        }
        HttpClient.getInstance().requestRepairList(getContext(), this, param_mode, req);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OPERATE_REPAIR) {
            Long id = data.getLongExtra("id", -1);
            if (id != -1) {
                Iterator<WaitAuditWorkOrderVo> iterator = troubles.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getId().equals(id)) {
                        iterator.remove();
                        mRepairRecordListAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == param_mode) {
            if (list != null && !list.isEmpty() && list.get(0) instanceof WaitAuditWorkOrderVo) {
                if(troubles == null) {
                    troubles = (List<WaitAuditWorkOrderVo>) list;
                } else {
                    troubles.addAll((Collection<? extends WaitAuditWorkOrderVo>) list);
                }
                mRepairRecordListAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
            if (dialog != null) {
                dialog.dismiss();
            }
            if (isUpdateData) {
                mRepairRecordListView.removeFooterView(footer);
                isUpdateData = false;
            }
        }
    }

    @Override
    public void onFailure(int requestType) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (isUpdateData) {
            currentPage--;
            mRepairRecordListView.removeFooterView(footer);
            isUpdateData = false;
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (mRepairRecordListAdapter.getCount() == visibleItem && scrollState == SCROLL_STATE_IDLE) {
            /**
             *scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
             *SCROLL_STATE_IDLE是当屏幕停止滚动时
             *SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The user is scrolling using touch, and their finger is still on the screen）
             *SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user had previously been scrolling using touch and had performed a fling）
             */
            if (!isUpdateData) {
                isUpdateData = true;
                mRepairRecordListView.addFooterView(footer);
                requestTroubleList(currentPage++);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        /**
         * firstVisibleItem 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
         * visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
         * totalItemCount表示ListView的ListItem总数
         * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem
         * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
         */
        visibleItem = firstVisibleItem + visibleItemCount;
    }

    private static class ViewHolder {
        TextView name;
        TextView code;
        TextView time;
        TextView repair_no;
        TextView fault_level;
        TextView repair_worker;
        TextView repair_status;
    }

    private class RepairRecordListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private RepairRecordListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (troubles != null) {
                return troubles.size();
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
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_repair_list, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_repair_device_name);
                holder.code = (TextView) convertView.findViewById(R.id.tv_repair_device_code);
                holder.time = (TextView) convertView.findViewById(R.id.tv_repair_find_time);
                holder.repair_no = (TextView) convertView.findViewById(R.id.tv_repair_no);
                holder.fault_level = (TextView) convertView.findViewById(R.id.tv_repair_fault_level);
                holder.repair_worker = (TextView) convertView.findViewById(R.id.tv_repair_worker);
                holder.repair_status = (TextView) convertView.findViewById(R.id.tv_repair_status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(troubles.get(i).getName());
            holder.code.setText(troubles.get(i).getCode());
            holder.time.setText(sdf.format(troubles.get(i).getHappenTime()));
            holder.repair_no.setText(String.valueOf(troubles.get(i).getOrderNo()));
            holder.fault_level.setText(troubles.get(i).getTroubleLevel());
            holder.repair_worker.setText(troubles.get(i).getRepairUser());
            holder.repair_status.setText(troubles.get(i).getStatus());

            return convertView;
        }
    }
}
