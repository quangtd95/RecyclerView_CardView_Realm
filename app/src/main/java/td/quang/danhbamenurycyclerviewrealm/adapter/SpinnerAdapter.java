package td.quang.danhbamenurycyclerviewrealm.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import td.quang.danhbamenurycyclerviewrealm.R;

/**
 * Created by Quang_TD on 11/10/2016.
 */
public class SpinnerAdapter extends ArrayAdapter {
    private String[] s;
    private Context context;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context, String[] s) {
        super(context, 0, s);
        this.context = context;
        this.s = s;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.spinner_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageAvatar);
        Picasso.with(context).load(s[position]).into(imageView);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.spinner_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageAvatar);
        Picasso.with(context).load(s[position]).into(imageView);
        return view;

    }
}
