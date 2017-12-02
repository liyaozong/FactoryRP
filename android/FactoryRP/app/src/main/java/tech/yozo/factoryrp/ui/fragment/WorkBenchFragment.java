package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import tech.yozo.factoryrp.R;


/**
 * 用户主页
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkBenchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkBenchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkBenchFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView mTextViewWaitToAudit;
    private TextView mTextViewWaitToExec;
    private TextView mTextViewExecating;
    private TextView mTextViewWaitToVerify;

    private int mWaitToAudit;
    private int mWaitToExec;
    private int mExecating;
    private int mWaitToVerify;

    private ListView mMainTainTaskList;
    private ListView mCheckTaskList;


    public WorkBenchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkBenchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkBenchFragment newInstance(String param1, String param2) {
        WorkBenchFragment fragment = new WorkBenchFragment();
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
        View view = inflater.inflate(R.layout.fragment_workbench, container, false);
        mTextViewWaitToAudit = (TextView) view.findViewById(R.id.textView_waitto_audit);
        mTextViewWaitToExec = (TextView) view.findViewById(R.id.textView_waitto_exec);
        mTextViewExecating = (TextView) view.findViewById(R.id.textView_executing);
        mTextViewWaitToVerify = (TextView) view.findViewById(R.id.textView_waitto_verify);

        mMainTainTaskList = (ListView) view.findViewById(R.id.listview_maintain_task);
        mMainTainTaskList.setEmptyView(view.findViewById(R.id.noNewMaintainTask));
        mCheckTaskList = (ListView) view.findViewById(R.id.listview_check_task);
        mCheckTaskList.setEmptyView(view.findViewById(R.id.noNewCheckTask));
        return view;
    }

    @Override
    protected void loadData() {
        mWaitToAudit = 1;
        mWaitToExec = 2;
        mExecating = 5;
        mWaitToVerify = 3;
    }

    @Override
    protected void buildUI() {
        mTextViewWaitToAudit.setText(String.format("%d", mWaitToAudit));
        mTextViewWaitToExec.setText(String.format("%d", mWaitToExec));
        mTextViewExecating.setText(String.format("%d", mExecating));
        mTextViewWaitToVerify.setText(String.format("%d", mWaitToVerify));
    }

// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onWorkBenchFragmentInteraction(uri);
//        }
    }

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
        void onWorkBenchFragmentInteraction(Uri uri);
    }
}
