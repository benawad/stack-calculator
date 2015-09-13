package benawad.com.stackcalculator.customViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import benawad.com.stackcalculator.MainActivity;
import benawad.com.stackcalculator.R;

/**
 * Created by benawad on 9/13/15.
 */
public class MyDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button ok;
    public CheckBox checkBox;

    public MyDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkbox);
        checkBox = (CheckBox)findViewById(R.id.skip);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(checkBox.isChecked()){
            String checkBoxResult = "checked";
            SharedPreferences settings = c.getSharedPreferences(MainActivity.PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("skipMessage", checkBoxResult);
            editor.apply();
        }
        switch (v.getId()) {
            case R.id.ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}
