package com.roland.urlblocker.adapter;

import static com.roland.urlblocker.helpers.DateTypeConverter.LongtoDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roland.urlblocker.R;
import com.roland.urlblocker.models.UrlModel;

import java.util.ArrayList;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {
    private ArrayList<UrlModel> _urlModelList;
    private Context _context;
    private int _position;
    private UrlModel _url;

    public UrlAdapter(ArrayList<UrlModel> urlModelArrayList, Context context){
        this._urlModelList = urlModelArrayList;
        this._context = context;
    }

    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item, parent, false);
        return new UrlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
        UrlModel model = _urlModelList.get(position);
        holder.urlName.setText(model.getName());
        holder.urlDate.setText(String.valueOf(LongtoDate(model.getDate())));
    }

    @Override
    public int getItemCount() {
        return _urlModelList.size();
    }

    public UrlModel getSelectedItem(){
        return _url;
    }

    private void setSelectedItem(UrlModel model){
        _url = model;
    }

    public class UrlViewHolder extends RecyclerView.ViewHolder{
        private TextView urlName, urlDate;
        public UrlViewHolder(@NonNull View itemView) {
            super(itemView);
            urlDate = itemView.findViewById(R.id.date);
            urlName = itemView.findViewById(R.id.urlName);
        }
    }
}
