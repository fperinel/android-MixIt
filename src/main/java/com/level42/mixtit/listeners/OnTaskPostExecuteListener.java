package com.level42.mixtit.listeners;

/**
 * Listener sur les tâches async
 */
public interface OnTaskPostExecuteListener<T> {
	
	public abstract void onTaskPostExecuteListener(T result);
	
}
