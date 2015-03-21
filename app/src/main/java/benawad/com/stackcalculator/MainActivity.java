package benawad.com.stackcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import benawad.com.stackcalculator.views.CustomColorDialogBuilder;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DIVISION_BY_ZERO_ERROR = "DIVISION BY ZERO ERROR";
    public static final int MAX_DIGITS_ALLOWED = 20;
    public static final String MAX_DIGITS_MESSAGE = "Maximum number of digits (" + MAX_DIGITS_ALLOWED + ") exceeded";
    public static final int ROUNDING_PLACES = 10;
    public static final String OVERFLOW_ERROR = "OVERFLOW";
    public static final String LARGEST_NUMBER = "99999999999999999999";
    public static final String SMALLEST_NUMBER = "-99999999999999999999";
    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;
    TextView mNumView;
    Stack<BigDecimal> mStack;
    boolean mPopReady = true;
    boolean mNewNum;
    BigDecimal mLargestNumber;
    BigDecimal mSmallestNumber;
    AdView mAdView;
    Button _0;
    Button _1;
    Button _2;
    Button _3;
    Button _4;
    Button _5;
    Button _6;
    Button _7;
    Button _8;
    Button _9;
    Button mSquare;
    Button mSquareA;
    Button mDivision;
    Button _x;
    Button mMinus;
    Button mAdd;
    Button mEnter;
    Button mClear;
    Button mDot;
    LinearLayout mRows;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLargestNumber = new BigDecimal(LARGEST_NUMBER);
        mSmallestNumber = new BigDecimal(SMALLEST_NUMBER);

        mSquare = (Button) findViewById(R.id.square);
        mSquareA = (Button) findViewById(R.id.square_a);

        mNumView = (TextView) findViewById(R.id.number_view);

        if (mStack == null) {
            mStack = new Stack<>();
        }

        mSquare.setText(Html.fromHtml("X<sup>2</sup>"));
        mSquareA.setText(Html.fromHtml("X<sup>a</sup>"));

        //load ad
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FA046F508DFEEFBB286DF48CF3A2CC37").addTestDevice("73C3AB00C35F747772E6CF3409EBD546").addTestDevice("BB33E3492537C843C509A66A0E05D3DE").build();
        mAdView.loadAd(adRequest);

//        _0 = (Button) findViewById(R.id.button0);
//        _1 = (Button) findViewById(R.id.button1);
//        _2 = (Button) findViewById(R.id.button2);
//        _3 = (Button) findViewById(R.id.button3);
//        _4 = (Button) findViewById(R.id.button4);
//        _5 = (Button) findViewById(R.id.button5);
//        _6 = (Button) findViewById(R.id.button6);
//        _7 = (Button) findViewById(R.id.button7);
//        _8 = (Button) findViewById(R.id.button8);
//        _9 = (Button) findViewById(R.id.button9);
//        mDivision = (Button) findViewById(R.id.button_divide);
//        _x = (Button) findViewById(R.id.button_mult);
//        mMinus = (Button) findViewById(R.id.button_minus);
//        mAdd = (Button) findViewById(R.id.button_plus);
//        mEnter = (Button) findViewById(R.id.button_enter);
//        mClear = (Button) findViewById(R.id.button_clear);
//        mDot = (Button) findViewById(R.id.button_dot);
//
//        mRows = (LinearLayout) findViewById(R.id.rows);
//
//        _0.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int width = _1.getWidth();
//                if (width > (mRows.getHeight() / 5)) {
//                    width = (mRows.getHeight() / 5);
//                }
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, width);
//                params.weight = 1;
//
//                _1.setLayoutParams(params);
//                _2.setLayoutParams(params);
//                _3.setLayoutParams(params);
//                _4.setLayoutParams(params);
//                _5.setLayoutParams(params);
//                _6.setLayoutParams(params);
//                _7.setLayoutParams(params);
//                _8.setLayoutParams(params);
//                _9.setLayoutParams(params);
//                mClear.setLayoutParams(params);
//                mSquare.setLayoutParams(params);
//                mSquareA.setLayoutParams(params);
//                mAdd.setLayoutParams(params);
//                mMinus.setLayoutParams(params);
//                mDivision.setLayoutParams(params);
//                _x.setLayoutParams(params);
//                mEnter.setLayoutParams(params);
//                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, width);
//                params2.weight = .5f;
//                _0.setLayoutParams(params2);
//                mDot.setLayoutParams(params2);
//
//            }
//        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        if (isFirstRun) {

            String tomatoColor = "#a34b1b";
            CustomColorDialogBuilder customColorDialogBuilder = new CustomColorDialogBuilder(this)
                    .setTitleColor(tomatoColor)
                    .setDividerColor(tomatoColor)
                    .setCustomView(R.layout.checkbox, this);

            LayoutInflater adbInflater = LayoutInflater.from(this);
            View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
            dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
            customColorDialogBuilder.setTitle("Attention");
            customColorDialogBuilder.setMessage(Html.fromHtml("Tap the number at the top to switch it's sign from negative to positive or vice versa."));
            customColorDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String checkBoxResult = "NOT checked";
                    if (dontShowAgain.isChecked())
                        checkBoxResult = "checked";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("skipMessage", checkBoxResult);
                    editor.apply();
                }
            });

            AlertDialog alert = customColorDialogBuilder.create();
            //alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alert.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            String skipMessage = settings.getString("skipMessage", "NOT checked");
            if (!skipMessage.equals("checked")) {
                alert.show();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button okButton = (Button) alert.findViewById(android.R.id.button1);
                okButton.setBackgroundResource(R.drawable.custom_button);
                okButton.setTextColor(Color.parseColor("#FFFFFFFF"));
                okButton.setTextSize(19);
            }
        }

        prefs.edit().putBoolean("isFirstRun", false).apply();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save state to the savedInstanceState
        savedInstanceState.putSerializable("stack", mStack);
        savedInstanceState.putString("numview", mNumView.getText().toString());
        savedInstanceState.putBoolean("newnum", mNewNum);
        savedInstanceState.putBoolean("popready", mPopReady);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state from savedInstanceState
        mStack = (Stack<BigDecimal>) savedInstanceState.getSerializable("stack");
        mNumView.setText(savedInstanceState.getString("numview"));
        mNewNum = savedInstanceState.getBoolean("newnum", mNewNum);
        mPopReady = savedInstanceState.getBoolean("popready", mPopReady);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void numberClick(View view) {
        Button buttonNum = (Button) view;
        String num = buttonNum.getText().toString();
        String current = mNumView.getText().toString();
        mPopReady = true;

        if (current.equals("0") || mNewNum || current.equals(DIVISION_BY_ZERO_ERROR)
                || current.equals(OVERFLOW_ERROR)) {
            if (num.equals(".")) {
                mNumView.setText("0" + num);
            } else {
                mNumView.setText(num);
            }
        } else if (!(current.contains(".") && num.equals("."))) {
            int length = current.length();
            if (current.contains(".")) {
                length--;
            }
            if (current.contains("-")) {
                length--;
            }
            Log.v(TAG, "len=" + length);
            Log.v(TAG, "current=" + current);
            if (length >= MAX_DIGITS_ALLOWED) {
                Toast.makeText(this, MAX_DIGITS_MESSAGE, Toast.LENGTH_SHORT).show();
            } else {
                mNumView.setText(current + num);
            }
        }

        mNewNum = false;

    }

    public void enter(View view) {
        String current = mNumView.getText().toString();
        if (current.equals(DIVISION_BY_ZERO_ERROR) ||
                current.equals(OVERFLOW_ERROR)) {
            //nothing
        } else {
            Log.v(TAG, "StartEnter=" + mStack.toString());
            mNewNum = true;
            mPopReady = false;
            mStack.push(new BigDecimal(current));
            Log.v(TAG, "EndEnter=" + mStack.toString());
        }
    }

    public void plus(View view) {
        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 2) {
            BigDecimal num1 = mStack.pop();
            BigDecimal num2 = mStack.pop();
            BigDecimal newNum;

            try {
                newNum = num2.add(num1);
                mStack.push(newNum);
                mNumView.setText(toSizeString(newNum));
                mNewNum = true;
            } catch (ArithmeticException e) {
                clear(null);
                mNumView.setText(OVERFLOW_ERROR);
            }
        } else {
            mNewNum = true;
        }
    }

    public void minus(View view) {
        Log.v(TAG, "StartMinus=" + mStack.toString());
        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 2) {
            BigDecimal num1 = mStack.pop();
            BigDecimal num2 = mStack.pop();
            BigDecimal newNum;
            try {
                newNum = num2.subtract(num1);
                mStack.push(newNum);
                mNumView.setText(toSizeString(newNum));
                mNewNum = true;
            } catch (ArithmeticException e) {
                clear(null);
                mNumView.setText(OVERFLOW_ERROR);
            }
        } else {
            mNewNum = true;
        }
        Log.v(TAG, "EndMinus=" + mStack.toString());
    }

    public void divide(View view) {
        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 2) {
            BigDecimal num1 = mStack.pop();
            BigDecimal num2 = mStack.pop();
            BigDecimal newNum;
            if (num1.equals(BigDecimal.ZERO)) {
                clear(null);
                mNumView.setText(DIVISION_BY_ZERO_ERROR);
            } else {
                Log.v(TAG, "num2=" + num2.toString());
                Log.v(TAG, "num1=" + num1.toString());
                try {
                    newNum = num2.divide(num1);
                    try {
                        mStack.push(newNum);
                        mNumView.setText(toSizeString(newNum));
                        mNewNum = true;
                    } catch (ArithmeticException z) {
                        clear(null);
                        mNumView.setText(OVERFLOW_ERROR);
                    }
                } catch (ArithmeticException e) {
                    try {
                        newNum = num2.divide(num1, ROUNDING_PLACES, RoundingMode.HALF_UP);
                        mStack.push(newNum);
                        mNumView.setText(toSizeString(newNum));
                        mNewNum = true;
                    } catch (ArithmeticException c) {
                        clear(null);
                        mNumView.setText(OVERFLOW_ERROR);
                    }
                }


            }
        } else {
            mNewNum = true;
        }
    }

    public void multiply(View view) {
        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 2) {
            BigDecimal num1 = mStack.pop();
            BigDecimal num2 = mStack.pop();
            BigDecimal newNum;
            try {
                newNum = num1.multiply(num2);
                mStack.push(newNum);
                mNumView.setText(toSizeString(newNum));
                mNewNum = true;
            } catch (ArithmeticException e) {
                clear(null);
                mNumView.setText(OVERFLOW_ERROR);
            }
        } else {
            mNewNum = true;
        }
    }

    public void clear(View view) {
        mStack.clear();
        mPopReady = true;
        mNewNum = false;
        mNumView.setText("0");
    }

    public void square(View view) {

        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 1) {
            BigDecimal num1 = mStack.pop();
            BigDecimal newNum;
            try {
                newNum = num1.pow(2);
                mStack.push(newNum);
                mNewNum = true;
                mNumView.setText(toSizeString(newNum));
            } catch (ArithmeticException e) {
                clear(null);
                mNumView.setText(OVERFLOW_ERROR);
            }
        } else {
            mNewNum = true;
        }

    }

    public void squareA(View view) {
        if (mPopReady) {
            try {
                mStack.push(new BigDecimal(mNumView.getText().toString()));
                mPopReady = false;
            } catch (Exception ignored) {
            }
        }
        if (mStack.size() >= 2) {
            BigDecimal num1 = mStack.pop();
            BigDecimal num2 = mStack.pop();
            double newNum;
            try {
                newNum = Math.pow(num2.doubleValue(), num1.doubleValue());
                mStack.push(new BigDecimal(newNum));
                mNewNum = true;
                //checking answer is an integer
                if (newNum % 1 == 0) {
                    mNumView.setText(toSizeString((int) newNum));
                } else {
                    mNumView.setText(toSizeString(BigDecimal.valueOf(newNum)));
                }

            } catch (Exception e) {
                clear(null);
                mNumView.setText(OVERFLOW_ERROR);
            }
        } else {
            mNewNum = true;
        }
    }

    public String toSizeString(BigDecimal decimal) {

        if (decimal.compareTo(mLargestNumber) > 0 || decimal.compareTo(mSmallestNumber) < 0) {
            throw new ArithmeticException("Went over max digits allowed (" + MAX_DIGITS_ALLOWED + ")");
        }

        String sDecimal = decimal.stripTrailingZeros().toPlainString();
        int length = sDecimal.length();
        if (sDecimal.contains(".")) {
            length--;
        }
        if (sDecimal.contains("-")) {
            length--;
        }
        if (length > MAX_DIGITS_ALLOWED) {
            int loc = sDecimal.indexOf(".");
            int scale = MAX_DIGITS_ALLOWED - (loc - 1);
            if (scale > 0) {
                sDecimal = decimal.setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            }
        }
        return sDecimal;
    }

    public String toSizeString(int num) {
        String sNum = num + "";
        if (sNum.length() > MAX_DIGITS_ALLOWED) {
            throw new ArithmeticException("Went over max digits allowed (" + MAX_DIGITS_ALLOWED + ")");
        } else {
            return sNum;
        }
    }

    public void toggleSign(View view) {

        String current = mNumView.getText().toString();

        if (!current.equals(DIVISION_BY_ZERO_ERROR) && !current.equals(OVERFLOW_ERROR)) {
            if (mNewNum) {
                BigDecimal num = mStack.pop().negate();
                mStack.push(num);
                try {
                    mNumView.setText(toSizeString(num));
                } catch (ArithmeticException e) {
                    clear(null);
                    mNumView.setText(OVERFLOW_ERROR);
                }
            } else {
                BigDecimal num = new BigDecimal(current);
                try {
                    mNumView.setText(toSizeString(num.negate()));
                } catch (ArithmeticException e) {
                    clear(null);
                    mNumView.setText(OVERFLOW_ERROR);
                }
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        super.onStop();
        if (!isChangingConfigurations()) {
            prefs.edit().putBoolean("isFirstRun", true).apply();
        }
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
