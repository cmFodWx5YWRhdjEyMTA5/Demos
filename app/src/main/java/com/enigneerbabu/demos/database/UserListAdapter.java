package com.enigneerbabu.demos.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enigneerbabu.demos.R;


public class UserListAdapter extends CursorAdapter {

    private MyUserDBHelper usersDbHelper;
    Context mContext;

    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mUserPhone;
    private ImageView mUserPhoto;

    public UserListAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.users_listview, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        mUserName = view.findViewById(R.id.textview_User_Name);
        mUserEmail = view.findViewById(R.id.textview_User_Email);
        mUserPhone = view.findViewById(R.id.textview_User_Phone);
        mUserPhoto = view.findViewById(R.id.imageview_Photo);

        int userNameColumnIndex = cursor.getColumnIndex("name");
        int userEmailColumnIndex = cursor.getColumnIndex("email_id");
        int userPhoneColumnIndex = cursor.getColumnIndex("phone");
        int userPhotoColumnIndex = cursor.getColumnIndex("photo");

        String userName = cursor.getString(userNameColumnIndex);
        String userEmail = cursor.getString(userEmailColumnIndex);
        String userPhone = cursor.getString(userPhoneColumnIndex);
        String userPhoto = cursor.getString(userPhotoColumnIndex);

        setValues(userName,userEmail,userPhone,userPhoto);

    }

    public void setValues(String name, String email, String phone, String photo){

        mUserName.setText(name);
        mUserEmail.setText(email);
        mUserPhone.setText(phone);
        //mUserPhoto.setText(name);
    }

}
