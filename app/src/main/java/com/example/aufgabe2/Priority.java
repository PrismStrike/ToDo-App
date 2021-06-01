/**
 * Diese Klasse legt fest, was eine Priorität ist, welche Variablen diese besitzt und welchen
 * Primary Key dieses Element hat. Dieses Layout ist für die Verwaltung der Datenbank und die
 * einfachen Zugriffe auf dieses Element in der Datenbank gedacht.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Priority_table")
public class Priority {

    @PrimaryKey(autoGenerate = true)
    public  long priorityId;
    public String priorityTitel;

    public Priority(String priorityTitel) {
        this.priorityTitel = priorityTitel;
    }

    public String getPriorityTitel() {
        return priorityTitel;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }

    public long getPriorityId() {
        return priorityId;
    }
}
