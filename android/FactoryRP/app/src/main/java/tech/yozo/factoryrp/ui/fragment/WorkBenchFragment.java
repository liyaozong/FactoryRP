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
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;


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

    private WorkOrderCountVo mTroubleCount;

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
        refreshTroubleCount();
        return view;
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        client.get(getContext(), HttpClient.TROUBLE_COUNT, null, getTroubleCountResponse);
    }

    private void refreshTroubleCount() {
        if(mTroubleCount != null) {
            mTextViewWaitToAudit.setText(String.format("%d", mTroubleCount.getWaitAuditNum()));
            mTextViewWaitToExec.setText(String.format("%d", mTroubleCount.getWaitRepairNum()));
            mTextViewExecating.setText(String.format("%d", mTroubleCount.getRepairingNum()));
            //TODO
            mTextViewWaitToVerify.setText(String.format("%d", mTroubleCount.getAllMyOrderNum()));
        }
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

    private JsonHttpResponseHandler getTroubleCountResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    mTroubleCount = JSON.parseObject(response.getString("data"), WorkOrderCountVo.class);
                    refreshTroubleCount();
                } else {
                    Toast.makeText(getContext(), R.string.failure_save, Toast.LENGTH_SHORT).show();
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
}
