package com.level42.mixit.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.level42.mixit.R;

/**
 * Adapter pour les sélecteurs des filtres planning
 */
public class FilterAdapter extends BaseAdapter {

    /**
     * Liste des talks.
     */
    private List<String> items = new ArrayList<String>();

    /**
     * Contexte de l'activité appelante.
     */
    private Context context;
    
    /**
     * Item sélectionné
     */
    private String selected;
    
    /**
     * Layout a utiliser
     */
    private int layout;

    /**
     * Constructeur.
     * @param context Contexte de l'activité
     */
    public FilterAdapter(Context context, List<String> items, String selected, int layout) {
        this.context = context;
        this.items = items;
        this.selected = selected;
        this.layout = layout;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return (items != null ? items.size() : 0);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public String getItem(int arg0) {
        return items.get(arg0);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int arg0) {
        return -1;
    }

    /**
     * Change la selection
     * @param selected
     */
    public void setSelected(String selected) {
       this.selected = selected;
       notifyDataSetChanged();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtLabel;
        
        if (convertView == null) {
            convertView = View.inflate(context, this.layout, null);

            txtLabel = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(R.id.tv, txtLabel);
        } else {
            txtLabel = (TextView) convertView.getTag(R.id.tv);
        }

        String item = getItem(position);
        txtLabel.setText(item);
        
        if(item.equals(selected)) {
            txtLabel.setBackgroundColor(context.getResources().getColor(R.color.mixitBlue));
        } else {
            txtLabel.setBackgroundColor(context.getResources().getColor(R.color.filterBlue));  
        }
        
        return convertView;
    }

}
