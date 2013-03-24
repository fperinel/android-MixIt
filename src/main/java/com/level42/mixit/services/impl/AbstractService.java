package com.level42.mixit.services.impl;

import android.content.Context;

import com.level42.mixit.MixItApplication;

/**
 * Service de base
 */
public class AbstractService {

	private Context context;
	
	public Context getContext() {
		if (context == null) {
			context = MixItApplication.getAppContext();
		}
		return context;
	}
	
	public String getText(Integer resId) {
		return getContext().getText(resId).toString();
	}
	
}
