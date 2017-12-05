package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.CaptureActivity;

public class ReportFaultActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 0x900;
    private static final int REQUEST_CODE_DEVICE = 0x901;
    private static final int REQUEST_CODE_CAMERA = 0x902;

    private ImageView mScanDevice;
    private LinearLayout mImagesView;
    private int mImageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fault);
        mImagesView = (LinearLayout) findViewById(R.id.ll_images);
        mScanDevice = (ImageView) findViewById(R.id.iv_select_device);
    }

    public void onScanDevice(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public void onSelectDivice(View view) {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_DEVICE);
    }

    public void onCaptureImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (mImageCount <= 3) {
                        if (data != null && data.hasExtra("data")) {
                            mImageCount++;
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            ImageView view = new ImageView(this);
                            view.setImageBitmap(thumbnail);
                            view.setPadding(6, 2, 6, 2);
                            mImagesView.addView(view);
                        }
                    } else {
                        Toast.makeText(this, R.string.image_count_hint, Toast.LENGTH_SHORT).show();
                    }
                case REQUEST_CODE_SCAN:
                    if (data != null) {
                        //TODO
                    }
                case REQUEST_CODE_DEVICE:
                    if (data != null) {
                        //TODO
                    }
                default:
                    break;
            }
        }
    }
}
