package com.level42.mixit.listeners;

/**
 * Listener sur les tâches async
 */
public interface OnTaskPostExecuteListener<T> {
	
	public abstract void onTaskPostExecuteListener(T result);

	public abstract void onTaskInterruptListener(Exception cancelReason);

	public abstract void onTaskCancelledListener();
	
}
