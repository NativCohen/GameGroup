package com.example.gamegroupproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class EventAdapter extends ArrayAdapter {
        private Context context;
        private List<Event> groupsList;

        public EventAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List questions) {
            super(context, resource, textViewResourceId, questions);

            this.context = context;
            this.groupsList = questions;
        }

        @NonNull
        @Override
        public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = ((Activity)context).getLayoutInflater().inflate(R.layout.group_item, parent, false);

            Event q = groupsList.get(i);
            TextView name = v.findViewById(R.id.tvTitle);
            TextView players = v.findViewById(R.id.players);
            TextView date = v.findViewById(R.id.date);
            TextView city = v.findViewById(R.id.city);

            name.setText(q.getEventName());
            players.setText(q.getCurrentMembers()+ "/" +q.getMaxMembers());
            date.setText(q.getDate());
            city.setText(q.getCity());
            return v;
        }
    }

