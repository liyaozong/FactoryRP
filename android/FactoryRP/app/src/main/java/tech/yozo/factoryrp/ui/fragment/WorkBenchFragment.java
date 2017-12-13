package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.DeviceAddActivity;
import tech.yozo.factoryrp.ui.RepairRecordListActivity;
import tech.yozo.factoryrp.ui.ReportFaultActivity;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;


/**
 * 用户主页
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkBenchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkBenchFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.button_ask_repair)
    Button buttonAskRepair;
    @BindView(R.id.button_add_repair)
    Button buttonAddRepair;
    @BindView(R.id.button_ask_parts)
    Button buttonAskParts;
    @BindView(R.id.button_add_device)
    Button buttonAddDevice;
    @BindView(R.id.textView_waitto_audit)
    TextView textViewWaittoAudit;
    @BindView(R.id.textView_waitto_exec)
    TextView textViewWaittoExec;
    @BindView(R.id.textView_executing)
    TextView textViewExecuting;
    @BindView(R.id.textView_waitto_verify)
    TextView textViewWaittoVerify;
    @BindView(R.id.listview_maintain_task)
    ListView listviewMaintainTask;
    @BindView(R.id.listview_check_task)
    ListView listviewCheckTask;
    @BindView(R.id.noNewMaintainTask)
    TextView noNewMaintainTask;
    @BindView(R.id.noNewCheckTask)
    TextView noNewCheckTask;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WorkOrderCountVo mTroubleCount;


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
        unbinder = ButterKnife.bind(this, view);
        listviewMaintainTask.setEmptyView(noNewMaintainTask);
        listviewCheckTask.setEmptyView(noNewCheckTask);
        refreshTroubleCount();
        return view;
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        client.get(getContext(), HttpClient.TROUBLE_COUNT, null, getTroubleCountResponse);
    }

    private void refreshTroubleCount() {
        if (mTroubleCount != null) {
            textViewWaittoAudit.setText(String.valueOf(mTroubleCount.getWaitAuditNum()));
            textViewWaittoExec.setText(String.valueOf(mTroubleCount.getWaitRepairNum()));
            textViewExecuting.setText(String.valueOf(mTroubleCount.getRepairingNum()));
            textViewWaittoVerify.setText(String.valueOf(mTroubleCount.getWaitValidateNum()));
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.button_ask_repair, R.id.button_add_repair, R.id.button_ask_parts, R.id.button_add_device, R.id.textView_waitto_audit, R.id.textView_waitto_exec, R.id.textView_executing, R.id.textView_waitto_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ask_repair: {
                Intent intent = new Intent(getContext(), ReportFaultActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_repair:
                //TODO
                break;
            case R.id.button_ask_parts:
                break;
            case R.id.button_add_device: {
                Intent intent = new Intent(getContext(), DeviceAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_audit:
            case R.id.textView_waitto_exec:
            case R.id.textView_executing:
            case R.id.textView_waitto_verify: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, view.getId());
                startActivity(intent);
                break;
            }
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
