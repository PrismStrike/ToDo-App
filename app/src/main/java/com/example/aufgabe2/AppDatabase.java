/**
 * Diese Klasse ist eine abstracte Klasse, welche die Datenbank und die einzelnen Tabellen
 * verwaltet.
 *
 * @author: Philipp Obst
 * Quelle: Vorlesungsfolien
 */
package com.example.aufgabe2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {ToDo.class, Category.class, Priority.class, ToDo_Category.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ToDoDao toDoDao();
    public abstract PriorityDao priorityDao();
    public abstract CategoryDao categoryDao();
    public abstract ToDo_CategoryDao toDo_categoryDao();

    /**
     * Methode, um die Datenbank und deren Zugriff zu erhalten.
     * @param context
     * @return
     */
    public static AppDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "toDoDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
