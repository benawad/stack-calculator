package benawad.com.stackcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Created by Ben on 3/21/2015.
 */
public class StackAdapter extends BaseAdapter {

    public static final String TAG = StackAdapter.class.getSimpleName();

    private Context mContext;
    private Stack<BigDecimal> mStack;

    public StackAdapter(Context context, Stack<BigDecimal> stack) {
        mContext = context;
        mStack = stack;
    }

    @Override
    public int getCount() {
        return mStack.size();
    }

    @Override
    public Object getItem(int position) {
        return mStack.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.stack_item, null);
            holder = new ViewHolder();
            holder.num = (TextView) convertView.findViewById(R.id.stack_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.num.setText(MainActivity.toSizeString(mStack.get(position)));

        return convertView;
    }

    public static class ViewHolder {
        TextView num;
    }

}
