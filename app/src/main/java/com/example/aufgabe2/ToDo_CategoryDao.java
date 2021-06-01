/**
 * Dieses Interface regelt die Datenbankzugriffe für eine ToDo_Kategorie und übersetzt diese von
 * einer Java-Methode in Datenbankbefehle.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDo_CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToDo (ToDo_Category toDo_category);

    @Query("select * from Todo_Category_table")
    public List<ToDo_Category> getAllToDO_Category();

    @Query("select * from todo_category_table where toDoId = :toDoId")
    public List<ToDo_Category> getToDo_Category(long toDoId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateToDo_Category (ToDo_Category toDo_category);

    @Query("delete from todo_category_table where toDoId = :toDoId AND categoryId = :categoryId")
    void removeToDo_Category(long toDoId, long categoryId);

    @Query("delete from todo_category_table where toDoId = :toDoId")
    void removeToDO_Categories(long toDoId);
}
