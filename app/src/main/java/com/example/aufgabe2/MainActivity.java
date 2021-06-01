/**
 * Diese App soll eine ToDo-App sein, in welcher man eigene ToDo's erstellen kann.
 * Dazu können eigene Kategorien und eigene Prioritäten erstellt werden. Diese kann man dann beim
 * Erstellen eines ToDo's dem ToDo übergeben und diesem auch ein Ablaufdatum, einen Titel und
 * eine Beschreibung zuweisen.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.aufgabe2.DetailsActivity.EXTRA_TODO_LO;
import static com.example.aufgabe2.DetailsActivity.EXTRA_TODO_ZU;
import static com.example.aufgabe2.SettingsActivity.EXTRA_SETTINGS_GROESSE;

public class MainActivity extends AppCompatActivity {

    /**
     * Hier werden einige Variablen, die im späteren Verlauf benötigt werden, definiert und
     * deklariert.
     */
    public static final String EXTRA_TODO_ID = "com.example.aufgabe2.extra.todo.id";
    public static final String EXTRA_TODO_TITEL = "com.example.aufgabe2.extra.todo.titel";
    public static final String EXTRA_TODO_BESCHR = "com.example.aufgabe2.extra.todo.beschreibung";
    public static final String EXTRA_TODO_DATUM = "com.example.aufgabe2.extra.todo.datum";
    public static final String EXTRA_TODO_PRIORITYID = "com.example.aufgabe2.extra.todo.priorityid";
    private static final int EDITTODO = 5555;
    private static final int ADDTODO = 6666;
    private static final int EDITSETTINGS = 7777;
    private RecyclerView recyclerView;
    private myAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private List<ToDo> input;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Hier wird der Zugriff auf die Toolbar aus dem Hauptbildschirm geholt und sie wird als
         * ActionBar festgelegt.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * Hier werden die gespeicherten Einstellungen geladen.
         */
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean myCheck = sharedPreferences.getBoolean("Settings option",true);

        /**
         * Nun wird die Datenbank geladen, welche die gespeicherten Daten aller ToDo's, Kategorien
         * und Prioritäten speichert.
         */
        database = AppDatabase.getDatabase(getApplicationContext());

        /**
         * Hier wird sich der Zugriff auf den RecyclerView geholt, damit die schon gespeicherten
         * ToDo's geladen und dem RecyclerView, zur Darstellung, übergeben werden können.
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        input = database.toDoDao().getAllToDO();
        mAdapter = new myAdapter(input);
        recyclerView.setAdapter(mAdapter);


        /**
         * Es wird mit einem eigenen Adapter, ein ItemClickListener auf den RecyclerView angewendet,
         * damit man Ereignisse abfangen kann, wenn auf den ein ToDo geklickt wird.
         * Sobald so ein ItemClickEvent eintritt, wird ein Intent mit Daten erstellt und dieses
         * dann an die DetailsActivity zum weiteren benutzten übergeben.
         */
        mAdapter.setOnItemClickListener(toDo -> {
            Intent openDetail = new Intent(this, DetailsActivity.class);
            openDetail.putExtra(EXTRA_TODO_ID, toDo.getId());
            openDetail.putExtra(EXTRA_TODO_TITEL, toDo.getTitel());
            openDetail.putExtra(EXTRA_TODO_BESCHR, toDo.getBeschreibung());
            openDetail.putExtra(EXTRA_TODO_DATUM, toDo.getDatum());
            openDetail.putExtra(EXTRA_TODO_PRIORITYID, toDo.getPriority_id());
            startActivityForResult(openDetail, EDITTODO);
        });
    }

    /**
     * Diese Methode beschreibt, wie das Menu für die Settings aussieht und lädt dieses beim Start
     * der App.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Diese Methode legt fest, was passiert, wenn ein Item aus der Toolbar angeklickt wird.
     * @param item: Item welches angeklickt wurde.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.zuEinstellungen:
                /**
                 * Wenn man zu den Einstellungen möchte, dann wird ein Intent erstellt und die
                 * SettingsActivity wird gestartet.
                 */
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, EDITSETTINGS);
                return true;
            case R.id.zuKategorien:
                /**
                 * Wenn man zu den Kategorien möchte, dann wird ein Intent erstellt und die
                 * CategoryActivity wird gestartet.
                 */
                intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.zuPrioritäten:
                /**
                 * Wenn man zu den Prioritäten möchte, dann wird ein Intent erstellt und die
                 * PriorityActivity wird gestartet.
                 */
                intent = new Intent(this, PriorityActivity.class);
                startActivity(intent);
                return true;
            case R.id.toDo_adden:
                /**
                 * Wenn man ein neues ToDo erstellen möchte, dann wird ein Intent erstellt und die
                 * DetailsActivity wird gestartet.
                 */
                intent = new Intent(this, DetailsActivity.class);
                startActivityForResult(intent, ADDTODO);
                return  true;
            default:
                /**
                 * Falls keines angeklickt wird, dann passiert nichts
                 */
                return true;
        }
    }

    /**
     * In den oberen Methoden werden Activitys gestartet und es wird ein Ergebnis erwartet, wie mit
     * dem Ergebnis umgegangen werden soll wird in dieser Methode festgelegt.
     * @param requestCode: Der Code, der übergeben wurde beim Starten der Activity, um die
     *                   verschiedenen Ereignisse zu unterscheiden.
     * @param resultCode: Der Code, der angibt welches Ergebnis es geworden ist, zum eventuellen
     *                  unterscheiden verschiedener Ergebnisse.
     * @param data: Die Daten die wieder mit zurückgegeben werden, um mit diesen weiterzuarbeiten.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDITTODO && resultCode == RESULT_OK) {
            /**
             * Hier wird beschrieben was passiert, wenn man ein ToDo bearbeitet hat und das Ergebnis
             * als okay gemeldet wird.
             */
            long toDoId = data.getLongExtra(EXTRA_TODO_ID, -1);
            boolean loeschen = data.getBooleanExtra(EXTRA_TODO_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_TODO_ZU, false);


            if (loeschen) {
                /**
                 * Wenn das ToDo gelöscht werden soll, dann wird dieses aus dem RecyclerView
                 * entfernt.
                 */
                for (int i = 0; i < input.size(); i++) {
                    if (toDoId == input.get(i).getId()) {
                        mAdapter.remove(i);
                        break;
                    }
                }
            } else if (!(zurueck) && !(loeschen)) {
                /**
                 * Wenn das ToDO bearbeitet wurde und es wurde speichern gedrückt, dann wird der
                 * RecyclerView bearbeitet, damit auf eine eventuelle Namensanpassung reagiert wird.
                 */
                ToDo toDo = database.toDoDao().getToDo(toDoId);

                for (int i = 0; i < input.size(); i++) {
                    if (toDoId == input.get(i).getId()) {
                        mAdapter.remove(i);
                        mAdapter.add(i, toDo);
                        break;
                    }
                }
            }

        } else if (requestCode == ADDTODO && resultCode == RESULT_OK) {
            /**
             * Hier wird beschrieben, was passiert wenn man ein neues ToDo erstellt.
             */
            long toDoId = data.getLongExtra(EXTRA_TODO_ID, -1);
            boolean loeschen = data.getBooleanExtra(EXTRA_TODO_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_TODO_ZU, false);

            if (!(loeschen) && !(zurueck)) {
                /**
                 * Wenn auf speichern gedrückt wurde, dann wird das ToDo der Datenbank und dem
                 * Recyclerview hinzugefügt.
                 */
                ToDo toDo = database.toDoDao().getToDo(toDoId);
                mAdapter.add(mAdapter.getItemCount(), toDo);
            }
        } else if(requestCode == EDITSETTINGS && resultCode == RESULT_OK) {
            /**
             * Hier wird beschrieben, was passiert, wenn man in die Einstellungen geht.
             */
            String schriftgroesse = data.getStringExtra(EXTRA_SETTINGS_GROESSE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(schriftgroesse));
            mAdapter.notifyDataSetChanged();
        }
    }
}