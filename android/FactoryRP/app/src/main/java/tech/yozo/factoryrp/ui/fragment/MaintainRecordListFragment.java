package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import tech.yozo.factoryrp.R;


/**
 * 保养记录列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link MaintainRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaintainRecordListFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private Long mParam_id;

    private ListView mMaintainRecordListView;

    public MaintainRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaintainRecordListFragment.
     */
    public static MaintainRecordListFragment newInstance(int param1, Long param2) {
        MaintainRecordListFragment fragment = new MaintainRecordListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintain_record_list, container, false);
        mMaintainRecordListView = (ListView) view.findViewById(R.id.lv_maintain_record);
        mMaintainRecordListView.setEmptyView(view.findViewById(R.id.tv_empty_maintain));
        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {

    }
}
