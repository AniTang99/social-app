package com.anitang99.socialapp.Messaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anitang99.socialapp.R;
import com.anitang99.socialapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageListAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_MESSAGE_SENT = 1;
    private final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private MessageListActivity mContext;
    private List<Message> mMessageList;
    private String username ;

    public MessageListAdapter(Context context) {
        mContext = (MessageListActivity) context;
        mMessageList = new ArrayList<>();
        username = mContext.getUsername();
    }

    public void setMessageList(List<Message> messages) {
        mMessageList = messages;
        notifyDataSetChanged();
    }

    public void addFirst(Message message) {
        mMessageList.add(0, message);
        notifyDataSetChanged();
    }

    public void addLast(Message message) {
        mMessageList.add(message);
        notifyDataSetChanged();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        UserMessage message = (UserMessage) mMessageList.get(position);

        if (message.getSender().getUsername().equals(this.username)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserMessage message = (UserMessage) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return this.mMessageList.size();
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
//        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
//            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatTime(message.getCreatedAt()));
            nameText.setText(message.getSender().getUsername());

            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatTime(message.getCreatedAt()));
        }
    }
}
