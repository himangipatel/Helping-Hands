package com.helpinghands;

import com.helpinghands.database.Contacts;

/**
 * Created by himangi on 25/03/2017.
 */

public interface OnContactListener {
    void onContactLongClick(int position, Contacts contacts);
    void onContactClick(int position , Contacts contacts);
}
