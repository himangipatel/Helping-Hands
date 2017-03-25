package com.helpinghands.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpinghand.R;
import com.helpinghands.OnContactListener;
import com.helpinghands.database.Contacts;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by himangi on 19/03/2017.
 */

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.EmergencyViewHolder>{

    private Context mContext;
    private List<Contacts> mEmergencyContactList;
    private OnContactListener onContactListener;

    public EmergencyContactAdapter(Context mContext, List<Contacts> mEmergencyContactList, OnContactListener onContactListener) {
        this.mContext = mContext;
        this.mEmergencyContactList = mEmergencyContactList;
        this.onContactListener = onContactListener;
    }


    @Override
    public EmergencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emergancy_contact, parent, false);

        return new EmergencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmergencyViewHolder holder, final int position) {

        Contacts contact = mEmergencyContactList.get(position);

        if (contact.getPhoto()!=null && !contact.getPhoto().isEmpty() ){
            Picasso.with(mContext)
                    .load(contact.getPhoto())
                    .error(R.drawable.ic_user)
                    .transform(new CircleTransform())
                    .into(holder.iVUser);
        }else {
            holder.iVUser.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_user));
        }

        holder.tVuserName.setText(contact.getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onContactListener.onContactLongClick(position,mEmergencyContactList.get(position));
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContactListener.onContactClick(position,mEmergencyContactList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEmergencyContactList.size();
    }

    class EmergencyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tVuserName)
        TextView tVuserName;
        @BindView(R.id.iVUser)
        ImageView iVUser;

        EmergencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            tVuserName = (TextView) itemView.findViewById(R.id.tVuserName);
            iVUser = (ImageView) itemView.findViewById(R.id.iVUser);
        }
    }
}
