package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import tech.yozo.factoryrp.R;


/**
 * 点检记录列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckRecordListFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private Long mParam_id;

    private ListView mCheckRecordListView;

    public CheckRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckRecordListFragment.
     */
    public static CheckRecordListFragment newInstance(int param1, Long param2) {
        CheckRecordListFragment fragment = new CheckRecordListFragment();
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
        View view = inflater.inflate(R.layout.fragment_check_record_list, container, false);
        mCheckRecordListView = (ListView) view.findViewById(R.id.lv_check_record);
        mCheckRecordListView.setEmptyView(view.findViewById(R.id.textView_empty_check));
        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {

    }
}
