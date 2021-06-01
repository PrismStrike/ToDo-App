/**
 * Diese Klasse legt fest, was ein ToDo ist, welche Variablen dieses besitzt und welchen
 * Primary Key dieses Element hat. Dieses Layout ist für die Verwaltung der Datenbank und die
 * einfachen Zugriffe auf das Element in der Datenbank gedacht.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToDo {

    @PrimaryKey
    public long id;
    public String titel;
    public String beschreibung;
    public String datum;
    public long priority_id;

    public ToDo(String titel, String beschreibung, String datum, long priority_id) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.datum = datum;
        this.priority_id = priority_id;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public long getId() {
        return id;
    }

    public String getDatum() {
        return datum;
    }

    public long getPriority_id() {
        return priority_id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
