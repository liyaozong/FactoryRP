package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.AboutUsActivity;
import tech.yozo.factoryrp.ui.LoginActivity;
import tech.yozo.factoryrp.utils.HttpClient;


/**
 * 我的帐号设置页
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";
    @BindView(R.id.button_logout)
    Button buttonLogout;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.ll_my_repair)
    LinearLayout llMyRepair;
    @BindView(R.id.ll_my_report)
    LinearLayout llMyReport;
    @BindView(R.id.ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.ll_check_update)
    LinearLayout llCheckUpdate;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    Unbinder unbinder;


    private int mParam_mode;
    private String mParam_id;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    public static PersonFragment newInstance(int param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loadData();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {
        HttpClient client = HttpClient.getInstance();
        tvPersonName.setText(client.getAuthUser().getUserName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.button_logout, R.id.ll_my_repair, R.id.ll_my_report, R.id.ll_change_password, R.id.ll_check_update, R.id.ll_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_logout: {
                HttpClient client = HttpClient.getInstance();
                client.setAuthUser(null);

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            }
            case R.id.ll_my_repair:
                break;
            case R.id.ll_my_report:
                break;
            case R.id.ll_change_password:
                break;
            case R.id.ll_check_update:
                break;
            case R.id.ll_about_us: {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
