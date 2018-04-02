package tech.yozo.factoryrp.ui;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import tech.yozo.factoryrp.R;

public class DeviceQRCodeActivity extends AppCompatActivity {
    private TextView name;
    private ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_qrcode);
        name = (TextView) findViewById(R.id.textView_name);
        qrcode = (ImageView) findViewById(R.id.iv_qrcode);
        name.setText(getIntent().getStringExtra("name"));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = QRCodeEncoder.syncEncodeQRCode(getIntent().getStringExtra("code"), 300);
                qrcode.setImageBitmap(image);
            }
        });

    }
}
