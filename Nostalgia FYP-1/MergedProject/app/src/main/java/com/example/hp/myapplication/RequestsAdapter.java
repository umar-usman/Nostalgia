package com.example.hp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 12/13/2017.
 */

public class RequestsAdapter extends
        RecyclerView.Adapter<RequestsAdapter.Holderview>{

    private List<Requests> productlist;
    private Context context;

    public RequestsAdapter(List<Requests> productlist, Context context) {
        this.productlist = productlist;
        this.context = context;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.users_single_layout,parent,false);

        return new Holderview(layout);
    }

    @Override    public void onBindViewHolder(Holderview holder, final int position) {

        //holder.v_name.setText(productlist.get(position).getName());
        //holder.v_image.setImageResource(productlist.get(position).getPhoto());
        holder.userNameView.setText(productlist.get(position).getName());
        holder.userStatusView.setText(productlist.get(position).getStatus());
      //  holder.userImageView.setImage(productlist.get(position).getImage();
        Picasso.with(context).load(productlist.get(position).getImage()).placeholder(R.drawable.default_avatar).into(holder.userImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click on " + productlist.get(position).getId(),
                        Toast.LENGTH_LONG).show();
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(context, ProfileActivity.class);
                profileIntent.putExtra("user_id", productlist.get(position).getId());
                context.startActivity(profileIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public void setfilter(List<Requests> listitem)
    {
        productlist=new ArrayList<>();
        productlist.addAll(listitem);
        notifyDataSetChanged();
    }
    class Holderview extends RecyclerView.ViewHolder
    {
        TextView userNameView;
        TextView userStatusView;
        CircleImageView userImageView;
       // ImageView v_image;
        //TextView v_name;

        Holderview(View itemview)
        {
            super(itemview);
            userNameView = (TextView) itemview.findViewById(R.id.user_single_name);
            userStatusView = (TextView) itemview.findViewById(R.id.user_single_status);
            userImageView = (CircleImageView) itemview.findViewById(R.id.user_single_image);
            
            //v_image=(ImageView) itemview.findViewById(R.id.product_image);
            //v_name = (TextView) itemView.findViewById(R.id.product_title);

        }
    }
}
