/**
 * Dieses Interface regelt die Datenbankzugriffe für eine Priorität und übersetzt diese von einer
 * Java-Methode in Datenbankbefehle.
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
public interface PriorityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPriority(Priority priority);

    @Query("select * from Priority_table")
    public List<Priority> getAllPrioritys();


    @Query("select * from Priority_table where priorityId = :priorityId")
    public Priority getPriority(long priorityId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePriority(Priority priority);

    @Query("delete from Priority_table where priorityId = :priorityId")
    void removePriority(long priorityId);

}
