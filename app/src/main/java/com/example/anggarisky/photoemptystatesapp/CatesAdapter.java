package com.example.anggarisky.photoemptystatesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CatesAdapter extends RecyclerView.Adapter<CatesAdapter.CatesViewHolder> {

    Context context;
    List<Cates> catesList;

    public CatesAdapter(Context context, List<Cates> catesList){
        this.context = context;
        this.catesList = catesList;
    }

    @NonNull
    @Override
    public CatesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_menu, null);
        return new CatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatesViewHolder catesViewHolder, int i) {

        Cates cates = catesList.get(i);

        catesViewHolder.icontitle.setText(cates.getIcontitle());
        catesViewHolder.iconimg.setImageDrawable(context.getResources().
                getDrawable(cates.getIconimg()));

    }

    @Override
    public int getItemCount() {
        return catesList.size();
    }

    class CatesViewHolder extends RecyclerView.ViewHolder {

        TextView icontitle;
        ImageView iconimg;

        public CatesViewHolder(@NonNull View itemView) {
            super(itemView);

            iconimg = itemView.findViewById(R.id.iconimg);
            icontitle = itemView.findViewById(R.id.icontitle);

        }
    }


}
