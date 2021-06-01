/**
 * Dieses Interface regelt die Datenbankzugriffe für eine Kategorie und übersetzt diese von einer
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
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category category);

    @Query("select * from Category_table")
    public List<Category> getAllCategory();

    @Query("select * from Category_table where categoryId = :categoryId")
    public Category getCategory(long categoryId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategory(Category category);

    @Query("delete from Category_table where categoryId = :categoryId")
    void removeCategory(long categoryId);
}
