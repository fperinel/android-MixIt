package com.level42.mixtit.services.adapters;

import android.widget.TextView;

/**
 * Cache de stockage d'une ligne dans une liste de talk
 * 
 * @author fperinel
 */
public class TalkListViewHolder {
	
	/**
	 * Zone de texte stockant le titre
	 */
	public TextView listTalkTitre;

	/**
	 * Zone de texte stockant la description du commentaire
	 */
	public TextView listTalkShortDescription;
	
	public TalkListViewHolder(TextView listTalkTitre, TextView listTalkShortDescription) {
        this.listTalkTitre = listTalkTitre;
        this.listTalkShortDescription = listTalkShortDescription;
    }
}
