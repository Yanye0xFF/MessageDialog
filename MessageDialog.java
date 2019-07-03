
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MessageDialog extends Dialog implements View.OnClickListener {

    private static final double WIDTH_SCALE = 0.9D;

    private TextView tvTitle,tvMessage,tvSave,tvCancel;

    private DialogButtonClickListener listener;

    public MessageDialog(@NonNull Context context) {
        this(context, 0, null);
    }

    private MessageDialog(Context context, int theme, View contentView) {
        super(context, theme == 0 ? R.style.MessageDialogStyle : theme);

        DisplayMetrics dm = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(contentView == null) {
            contentView = View.inflate(context, R.layout.view_message_dialog, null);
        }
        setContentView(contentView);
        initView(context, contentView);
    }

    private void initView(Context context, View parent) {
        ConstraintLayout layout = parent.findViewById(R.id.parent_layout);
        tvTitle = parent.findViewById(R.id.tv_dialog_title);
        tvMessage = parent.findViewById(R.id.tv_dialog_message);
        tvSave = parent.findViewById(R.id.tv_save);
        tvCancel = parent.findViewById(R.id.tv_cancel);

        DisplayMetrics dm = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        layout.setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels * WIDTH_SCALE),
                FrameLayout.LayoutParams.WRAP_CONTENT));

        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    public void setDialogTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDialogMessage(String message) {
        tvMessage.setText(message);
    }

    public void setMessageTextColor(String color) {
        tvMessage.setTextColor(Color.parseColor(color));
    }

    public void setButtonName(String leftName,String RightName) {
        tvSave.setText(leftName);
        tvCancel.setText(RightName);
    }

    public void setButtonColor(int leftColor,int rightColor) {
        tvSave.setTextColor(leftColor);
        tvCancel.setTextColor(rightColor);
    }

    public void setDialogButtonClickListener(DialogButtonClickListener listener) {
        this.listener = listener;
    }

    public interface DialogButtonClickListener {
        void onLeftButtonClicked();
        void onRightButtonClicked();
    }

    @Override
    public void onClick(View view) {
        if(listener == null) {
            dismiss();
            return;
        }
        if(view.getId() == R.id.tv_save) {
            dismiss();
            listener.onLeftButtonClicked();
        }else if(view.getId() == R.id.tv_cancel) {
            dismiss();
            listener.onRightButtonClicked();
        }
    }
}
