package tech.yozo.factoryrp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditWorkLoadDialog extends Dialog {
    @BindView(R.id.button_starttime)
    Button buttonStarttime;
    @BindView(R.id.button_endtime)
    Button buttonEndtime;
    @BindView(R.id.editText_worktime)
    EditText editTextWorktime;
    private Context mContext;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    private TimePickerDialog mTimePickerDialog;
    private Date hasStart;
    private Date hasEnd;

    public EditWorkLoadDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.work_time_dialog_layout, null);
        setContentView(view);

    }

    @OnClick({R.id.button_starttime, R.id.button_endtime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_starttime:
            case R.id.button_endtime:
                openDateDialog(view.getId());
                break;
        }
    }

    private void openDateDialog(final int id) {
        mTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.TimePickerDialogInterface() {
            @Override
            public void positiveListener() {
                StringBuffer buffer = new StringBuffer();
                buffer.append(mTimePickerDialog.getYear()).append("年")
                        .append(mTimePickerDialog.getMonth()).append("月")
                        .append(mTimePickerDialog.getDay()).append("日 ")
                        .append(mTimePickerDialog.getHour()).append(":")
                        .append(mTimePickerDialog.getMinute());
                switch (id) {
                    case R.id.button_starttime:
                        buttonEndtime.setText(buffer);
                        try {
                            hasEnd = df.parse(buffer.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        calcWorkTime();
                        break;
                    case R.id.button_endtime:
                        buttonEndtime.setText(buffer);
                        try {
                            hasEnd = df.parse(buffer.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        calcWorkTime();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void negativeListener() {

            }
        });
        mTimePickerDialog.showDateAndTimePickerDialog();
    }

    private void calcWorkTime() {
        if(hasStart != null && hasEnd != null) {
            long useTime = (hasEnd.getTime() - hasStart.getTime())/1000;
            editTextWorktime.setText(String.valueOf(useTime));
        }
    }
}
