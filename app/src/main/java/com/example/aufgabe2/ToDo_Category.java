/**
 * Diese Klasse legt fest, was eine ToDo-Kategorie ist, welche Variablen dieses Element besitzt und
 * welchen Primary Key es hat. Dieses Layout ist für die Verwaltung der Datenbank und die
 * einfachen Zugriffe auf dieses Element in der Datenbank gedacht.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todo_Category_table")
public class ToDo_Category {

        @PrimaryKey
        public long toDoId;
        public long categoryId;


        public ToDo_Category(long toDoId, long categoryId) {
                this.toDoId = toDoId;
                this.categoryId = categoryId;
        }

        public long getToDoId() {
                return toDoId;
        }

        public long getCategoryId() {
                return categoryId;
        }
}
