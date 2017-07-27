package com.example.jinphy.puzzlegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinphy on 2017/7/16.
 */

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private List<Integer> datas;
    private LayoutInflater inflater;
    private int resourceId;

    public ImageAdapter(Context context, ArrayList<Integer> datas,int resourceId) {
        this.context = context;
        this.datas = datas;
        inflater.from(context);
        this.resourceId = resourceId;
    }

    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, viewGroup);
            holder = new ViewHolder();
            holder.iamge = convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }
//        holder.iamge.setImageBitmap();
        return null;
    }

    class ViewHolder{
        ImageView iamge;
    }
}
