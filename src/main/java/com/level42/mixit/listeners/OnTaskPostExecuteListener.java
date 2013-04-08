package com.level42.mixit.listeners;

/**
 * Listener sur les tâches asynchrone.
 */
public interface OnTaskPostExecuteListener<T> {

    /**
     * Déclenchement lorsque la tâche est terminée.
     * @param result Résultat de la tâche
     */
    abstract void onTaskPostExecuteListener(T result);

    /**
     * Déclenchement lorsque la tâche est interrompue suite à une erreur.
     * @param cancelReason Exception représentant la raison de l'interuption
     */
    abstract void onTaskInterruptListener(Exception cancelReason);

    /**
     * Déclenchement lorsque la tâche est abandonnée par l'utilisateur.
     */
    abstract void onTaskCancelledListener();

}
