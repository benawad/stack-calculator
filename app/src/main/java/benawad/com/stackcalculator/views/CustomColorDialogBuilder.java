package benawad.com.stackcalculator.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import benawad.com.stackcalculator.R;

public class CustomColorDialogBuilder extends AlertDialog.Builder {

    private View mDialogView;


    private TextView mTitle;

    private ImageView mIcon;

    private TextView mMessage;

    private View mDivider;

    public CustomColorDialogBuilder(Context context) {
        super(context);

        mDialogView = View.inflate(context, R.layout.dialog_layout, null);
        setView(mDialogView);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
    }


    public CustomColorDialogBuilder setDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public CustomColorDialogBuilder setTitle(CharSequence text) {
        mTitle.setText(text);
        return this;
    }

    public CustomColorDialogBuilder setTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public CustomColorDialogBuilder setMessage(int textResId) {
        mMessage.setText(textResId);
        return this;
    }

    @Override
    public CustomColorDialogBuilder setMessage(CharSequence text) {
        mMessage.setText(text);
        return this;
    }

    @Override
    public CustomColorDialogBuilder setIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    @Override
    public CustomColorDialogBuilder setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }


    public CustomColorDialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        ((FrameLayout) mDialogView.findViewById(R.id.customPanel)).addView(customView);
        return this;
    }

    @Override
    public AlertDialog show() {
        if (mTitle.getText().equals(""))
            mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        return super.show();
    }

}