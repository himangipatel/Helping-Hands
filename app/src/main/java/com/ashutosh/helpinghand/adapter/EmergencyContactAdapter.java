package com.ashutosh.helpinghand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashutosh.helpinghand.R;
import com.ashutosh.helpinghand.database.Contacts;
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

    public EmergencyContactAdapter(Context mContext, List<Contacts> mEmergencyContactList) {
        this.mContext = mContext;
        this.mEmergencyContactList = mEmergencyContactList;
    }


    @Override
    public EmergencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emergancy_contact, parent, false);

        return new EmergencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmergencyViewHolder holder, int position) {

        Contacts contact = mEmergencyContactList.get(position);

        if (contact.getPhoto()!=null && !contact.getPhoto().isEmpty() ){
            Picasso.with(mContext)
                    .load(contact.getPhoto())
                    .error(R.drawable.ic_user)
                    .transform(new CircleTransform())
                    .into(holder.iVUser);
        }

        holder.tVuserName.setText(contact.getName());
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
