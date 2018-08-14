package com.example.aqil.projectkamus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aqil.projectkamus.DetailActivity;
import com.example.aqil.projectkamus.Model.KamusItem;
import com.example.aqil.projectkamus.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.holder> {
    public KamusAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    public void setKamusItems(ArrayList<KamusItem> kamusItems) {
        this.kamusItems = kamusItems;
    }

    private ArrayList<KamusItem> kamusItems = new ArrayList();
public static final String KAMUS_ITEM ="kamus item";

    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusAdapter.holder holder, final int position) {
        holder.itemTitle.setText(kamusItems.get(position).getTitle());
        holder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("kamus item", kamusItems.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamusItems.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView itemTitle;


        public holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
