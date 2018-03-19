package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.UploadImageResp;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadImageFragment extends Fragment implements HttpClient.OnHttpListener {
    private static final int REQUEST_CODE_CAMERA = 0x902;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ib_capture)
    ImageButton ibCapture;
    @BindView(R.id.ll_images)
    LinearLayout llImages;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private int mImageCount;
    private LoadingDialog dialog;
    private View currentUploadingView;
    private OnFragmentInteractionListener mListener;

    public UploadImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadImageFragment.
     */
    public static UploadImageFragment newInstance(String param1, String param2) {
        UploadImageFragment fragment = new UploadImageFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    @OnClick(R.id.ib_capture)
    public void onViewClicked() {
        if(mImageCount >=6) {
            Toast.makeText(getContext(), R.string.hint_image_count, Toast.LENGTH_SHORT).show();
            return;
        }
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.CAMERA}, Constant.REQUEST_CAMERA_PERMISSION);
        }else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA) {
            if (data != null && data.hasExtra("data")) {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                final Bitmap thumbnail = data.getParcelableExtra("data");
                final View contentView = mInflater.inflate(R.layout.image_wait_upload_layout, null);
                ImageView image = (ImageView) contentView.findViewById(R.id.iv_wait_upload);
                image.setImageBitmap(thumbnail);
                ImageView del = (ImageView) contentView.findViewById(R.id.iv_image_del);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        llImages.removeView(contentView);
                        mImageCount--;
                    }
                });
                ImageView upload = (ImageView) contentView.findViewById(R.id.iv_image_upload);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadImage(contentView, thumbnail);
                    }
                });
                llImages.addView(contentView);
                mImageCount++;
            }
        }
    }

    private void uploadImage(View view, Bitmap image) {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                .setMessage(R.string.loading_save);
        dialog = builder.create();
        dialog.show();
        currentUploadingView = view;

        HttpClient.getInstance().requestUploadFile(getContext(), this, "inspect", image);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_UPLOAD_FILE:
                UploadImageResp uploadImageResp = (UploadImageResp) obj;
                if (mListener != null) {
                    mListener.onImageUploadSuccess(uploadImageResp.getKey());
                }

                if(currentUploadingView != null) {
                    ImageView delete = (ImageView) currentUploadingView.findViewById(R.id.iv_image_del);
                    delete.setVisibility(View.GONE);
                    ImageView upload = (ImageView) currentUploadingView.findViewById(R.id.iv_image_upload);
                    upload.setEnabled(false);
                    upload.setImageResource(R.drawable.ic_file_upload_done);
                }
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                Toast.makeText(getContext(), R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
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
        void onImageUploadSuccess(String uri);
    }
}
