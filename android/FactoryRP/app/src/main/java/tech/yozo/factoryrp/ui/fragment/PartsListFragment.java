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
import tech.yozo.factoryrp.ui.PartsDetailActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.QueryDeviceSpareRelReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.List;


/**
 * 备件列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link PartsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartsListFragment extends BaseFragment implements HttpClient.OnHttpListener, AbsListView.OnScrollListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private Long mParam_id;

    private Context mContext;
    private ListView mPartsListView;
    private List<SparePartsResp> parts;

    private PartsListAdapter mListAdapter;
    private LoadingDialog dialog;

    private OnFragmentInteractionListener mListener;
    private int visibleItem;
    private boolean isUpdateData;
    private View footer;
    private int currentPage = 1;

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
    public static PartsListFragment newInstance(int param1, Long param2) {
        PartsListFragment fragment = new PartsListFragment();
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
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_id = getArguments().getLong(ARG_PARAM2);
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
        footer = inflater.inflate(R.layout.footer_load_layout, null);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parts_list, container, false);
        mPartsListView = (ListView) view.findViewById(R.id.lv_parts_list);
        mPartsListView.setEmptyView(view.findViewById(R.id.textView18));
        mListAdapter = new PartsListAdapter(getContext());
        mPartsListView.setAdapter(mListAdapter);
        mPartsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int count,
                                    long arg3) {
                switch (mParam_mode) {
                    case Constant.FOR_BROWE_MODE:
                        Intent intent = new Intent(getActivity(), PartsDetailActivity.class);
                        intent.putExtra(PartsDetailActivity.PARTS_ID, parts.get(count));
                        startActivity(intent);
                        break;
                    case Constant.FOR_CHOICE_MODE:
                        if(mListener != null) {
                            mListener.onPartSelected(parts.get(count));
                        }
                        break;
                }
            }
        });
        mPartsListView.setOnScrollListener(this);
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
        switch (mParam_mode) {
            case Constant.FOR_BROWE_MODE:
            case Constant.FOR_CHOICE_MODE:
                if(client.getSparePartsList() == null) {
                    LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                            .setMessage(R.string.loading_loading);
                    dialog = builder.create();
                    dialog.show();
                    requestPartsList(currentPage++);
                }
                break;
            case Constant.FOR_DEVICE_ID:
                LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                        .setMessage(R.string.loading_loading);
                dialog = builder.create();
                dialog.show();
                QueryDeviceSpareRelReq req = new QueryDeviceSpareRelReq();
                req.setDeviceId(mParam_id);
                req.setItemsPerPage(100);
                client.requestDeviceOfParts(getContext(), this, req);
                break;
            case Constant.FOR_REPAIR_ID:
                break;
            default:
                break;
        }
    }

    private void requestPartsList(int page) {
        SparePartsQueryReq req = new SparePartsQueryReq();
        req.setCurrentPage(page);
        req.setItemsPerPage(20);
        HttpClient.getInstance().requestPartsList(getContext(), this, req);
    }

    @Override
    protected void buildUI() {
        HttpClient client = HttpClient.getInstance();
        switch (mParam_mode) {
            case Constant.FOR_BROWE_MODE:
            case Constant.FOR_CHOICE_MODE:
                parts = client.getSparePartsList();
                mListAdapter.notifyDataSetChanged();
                break;
            case Constant.FOR_DEVICE_ID:
                break;
            case Constant.FOR_REPAIR_ID:
                break;
            default:
                break;
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == HttpClient.REQUEST_PARTS_LIST || requestType == HttpClient.REQUEST_FIND_PARTS_FOR_DEVICE) {
            parts = (List<SparePartsResp>) list;
            mListAdapter.notifyDataSetChanged();
            if(dialog !=null) {
                dialog.dismiss();
            }
            if(isUpdateData) {
                mPartsListView.removeFooterView(footer);
                isUpdateData = false;
            }
        }
    }

    @Override
    public void onFailure(int requestType) {
        if(requestType == HttpClient.REQUEST_PARTS_LIST || requestType == HttpClient.REQUEST_FIND_PARTS_FOR_DEVICE) {
            if(dialog !=null) {
                dialog.dismiss();
            }
            if(isUpdateData) {
                currentPage--;
                mPartsListView.removeFooterView(footer);
                isUpdateData = false;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (mListAdapter.getCount() == visibleItem && scrollState == SCROLL_STATE_IDLE) {
            /**
             *scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
             *SCROLL_STATE_IDLE是当屏幕停止滚动时
             *SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The user is scrolling using touch, and their finger is still on the screen）
             *SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user had previously been scrolling using touch and had performed a fling）
             */
            switch (mParam_mode) {
                case Constant.FOR_BROWE_MODE:
                case Constant.FOR_CHOICE_MODE:
                    if (!isUpdateData) {
                        isUpdateData = true;
                        mPartsListView.addFooterView(footer);
                        requestPartsList(currentPage++);
                    }
                    break;
                default:
                    break;
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

    public interface OnFragmentInteractionListener {
        void onPartSelected(SparePartsResp parts);
    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView type;
//        TextView stock;
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
//                holder.stock = (TextView) convertView.findViewById(R.id.tv_parts_stock);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText(parts.get(i).getName());
            holder.code.setText(parts.get(i).getCode());
            holder.type.setText(parts.get(i).getSpecificationsAndodels());
//            holder.stock.setText("4个");

            return convertView;
        }
    }
}
