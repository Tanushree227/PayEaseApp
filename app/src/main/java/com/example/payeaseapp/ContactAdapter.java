package com.example.payeaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter implements Filterable {
    private List<Contacts> contactList;
    private List<Contacts> filteredContactList;
    private Context context;

    public ContactAdapter(Context context, List<Contacts> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.filteredContactList = new ArrayList<>(contactList);
    }

    @Override
    public int getCount() {
        return filteredContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.contact_list_view, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView phoneNumberTextView = convertView.findViewById(R.id.phoneNumberTextView);

        Contacts contact = filteredContactList.get(position);
        nameTextView.setText(contact.getName());
        phoneNumberTextView.setText(contact.getPhoneNumber());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                if (filterString.isEmpty()) {
                    results.values = contactList;
                } else {
                    List<Contacts> filteredList = new ArrayList<>();
                    for (Contacts contact : contactList) {
                        if (contact.getName().toLowerCase().contains(filterString)) {
                            filteredList.add(contact);
                        }
                    }
                    results.values = filteredList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredContactList = (List<Contacts>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
