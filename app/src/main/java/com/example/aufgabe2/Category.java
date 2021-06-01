/**
 * Diese Klasse legt fest, was eine Kategorie ist, welche Variablen diese besitzt und welchen
 * Primary Key dieses Element hat. Dieses Layout ist für die Verwaltung der Datenbank und die
 * einfachen Zugriffe auf dieses Element in der Datenbank gedacht.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public  long categoryId;
    public String categoryTitel;

    public Category(String categoryTitel) {
        this.categoryTitel = categoryTitel;
    }

    public String getCategoryTitel() {
        return categoryTitel;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
