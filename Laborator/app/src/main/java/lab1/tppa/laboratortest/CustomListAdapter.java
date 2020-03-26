package lab1.tppa.laboratortest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> products;

    public CustomListAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    public int getCount() {
        return this.products.size();
    }

    public Object getItem(int position) {
        return this.products.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        Product currentItem = (Product) getItem(position);

        TextView textViewProductName = (TextView)
                convertView.findViewById(R.id.text_view_product_name);

        textViewProductName.setText(currentItem.getName());

        return convertView;
    }
}
