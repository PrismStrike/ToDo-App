/**
 * Dieses Interface regelt die Datenbankzugriffe für ein ToDo und übersetzt diese von
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
public interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToDo (ToDo toDo);

    @Query("select * from toDo")
    public List<ToDo> getAllToDO();

    @Query("select * from toDo where id = :toDoId")
    public ToDo getToDo(long toDoId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateToDo (ToDo toDo);

    @Query("delete from toDo where id = :toDoId")
    void removeToDo(long toDoId);


}
