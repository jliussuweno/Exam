package com.jliussuweno.exam.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jliussuweno.exam.callback.UserCallback;
import com.jliussuweno.exam.R;
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ToDoViewHolder> {

    private static final int EMPTY = 0;
    List<User> userList = new ArrayList<>();
    UserCallback userCallback = null;
    Context context;

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setUserCallback(UserCallback userCallback) {
        this.userCallback = userCallback;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == EMPTY ? R.layout.recycler_item_empty : R.layout.item_user;
        View tempView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ToDoViewHolder(tempView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        if(getItemViewType(position) != EMPTY) {
            if (userList != null) {
                User current = userList.get(position);
                holder.setViewData(current);
                holder.getParent().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userCallback != null) {
                            userCallback.userPressed(current);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return userList.size() == EMPTY ? 1 : userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return userList.size() == EMPTY ? EMPTY : 2;
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView profilePicture;
        View parent;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            tv_name = itemView.findViewById(R.id.tv_name);
            profilePicture = itemView.findViewById(R.id.imageViewList);

        }

        public void setViewData(User user){
            tv_name.setText(user.getName());
            profilePicture.setImageBitmap(ImageUtils.decodeBase64(user.getImage()));
        }

        public View getParent(){
            return  parent;
        }
    }
}
