/**
 * Diese Klasse wird genutzt, um Todo's zu erstellen oder zu bearbeiten.
 *
 * @author: Philipp Obsts (18307)
 */
package com.example.aufgabe2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.example.aufgabe2.MainActivity.EXTRA_TODO_BESCHR;
import static com.example.aufgabe2.MainActivity.EXTRA_TODO_DATUM;
import static com.example.aufgabe2.MainActivity.EXTRA_TODO_ID;
import static com.example.aufgabe2.MainActivity.EXTRA_TODO_PRIORITYID;
import static com.example.aufgabe2.MainActivity.EXTRA_TODO_TITEL;

public class DetailsActivity extends AppCompatActivity {

    /**
     * Definieren und Deklarieren einiger Variablen, die später benötigt werden.
     */
    public static final String EXTRA_TODO_LO = "com.example.aufgabe2.extra.todo.lo";
    public static final String EXTRA_TODO_ZU = "com.example.aufgabe2.extra.todo.zu";
    private AppDatabase database;
    private List<Category> input;
    private List<Priority> priorities;
    private TextInputLayout titelText;
    private TextInputLayout beschreiText;
    private Spinner spinnerPrio;
    private CalendarView calendarView;
    private Spinner spinnerKat;
    private TextView textView;
    private String inhaltKat = "";
    private String datumGeändert = "";
    long toDoId;
    String titel;
    String beschreibung;
    String datum;
    long prioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /**
         * Laden der Datenbank.
         */
        database = AppDatabase.getDatabase(getApplicationContext());

        /**
         * Zugriff auf die Toolbar holen, diese wird genutzt um zu speichern, löschen und
         * zurückzukehren zum vorherigen Bildschirm.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);

        /**
         * Zugriff auf alle Komponenten.
         */
        titelText = findViewById(R.id.textInput_Titel);
        beschreiText = findViewById(R.id.textInput_Beschreibung);
        spinnerPrio = findViewById(R.id.spinner_prio);
        calendarView = findViewById(R.id.calendarViewToDo);
        spinnerKat = findViewById(R.id.spinnerKat);
        textView = findViewById(R.id.tv_Kat_Auswahl);


        /**
         *  Füllen des Spinners für die Prioritäten
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                DetailsActivity.this, R.layout.support_simple_spinner_dropdown_item);
        priorities = database.priorityDao().getAllPrioritys();
        for (Priority prio:priorities) {
            adapter.add(prio.getPriorityTitel());
        }
        spinnerPrio.setAdapter(adapter);

        /**
         * Füllen des Spinners für die Kategorien
         */
        ArrayAdapter<String> katAdapter = new ArrayAdapter<String>(
                DetailsActivity.this, R.layout.support_simple_spinner_dropdown_item);
        List<Category> categories = database.categoryDao().getAllCategory();
        for (Category ca : categories) {
            katAdapter.add(ca.getCategoryTitel());
        }
        spinnerKat.setAdapter(katAdapter);


        /**
         * Zugriff auf den übergebenen Intent holen und die Extras in Variablen schreiben.
         */
        Intent intent = getIntent();
        toDoId = intent.getLongExtra(EXTRA_TODO_ID, -1);
        titel = intent.getStringExtra(EXTRA_TODO_TITEL);
        beschreibung = intent.getStringExtra(EXTRA_TODO_BESCHR);
        datum = intent.getStringExtra(EXTRA_TODO_DATUM);
        prioId = intent.getLongExtra(EXTRA_TODO_PRIORITYID, -1);

        /**
         * Jetzt werden die Komponenten mit Inhalten gefüllt, die als Extras übergeben wurden, falls
         * man ein ToDo bearbeiten will.
         */
        titelText.getEditText().setText(titel);
        beschreiText.getEditText().setText(beschreibung);

        for(int i = 0; i < priorities.size(); i++){
            if(prioId == priorities.get(i).getPriorityId()){
                spinnerPrio.setSelection(i);
                break;
            }
        }

        List<ToDo_Category> toDo_categories = database.toDo_categoryDao().getToDo_Category(toDoId);
        for (ToDo_Category to: toDo_categories) {
            Category cat = database.categoryDao().getCategory(to.getCategoryId());
            inhaltKat += cat.getCategoryTitel() + ",";
        }
        textView.setText(inhaltKat);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                datumGeändert = String.format("%d.%d.%d", dayOfMonth, month, year);
                System.out.println(datumGeändert);
            }
        });

    }

    /**
     * Mit dieser Methode wird das Menu und das Menu-Layout erstellt.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    /**
     * Methode die festlegt was passiert, wenn ein Item aus dem Menu angeklickt wird.
     * @param item: angeklicktes Menu-Item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menuDetailSpeichern:
                /**
                 * Hier wird beschrieben was beim speichern passiert.
                 */
                String titelNeu = titelText.getEditText().getText().toString();
                String beschreibungNeu = beschreiText.getEditText().getText().toString();
                long prioIdNeu;
                for (Priority prio: priorities) {
                    if(prio.getPriorityTitel().equals(spinnerPrio.getSelectedItem().toString())){
                        prioIdNeu = prio.getPriorityId();
                        break;
                    }
                }
                String datumNeu = datumGeändert;
                String katNeu = textView.getText().toString();
                String[] kategorien = katNeu.split(",");

                if(toDoId == -1){
                    /**
                     * Wenn man ein ToDo neu erstellt hat, dann wird dieses in die Datenbank
                     * gespeichert und es wird ein Intent erstellt, die nötigen Variablen, als
                     * Extras, hinzugefügt, das Ergenis auf ok gesetzt und die Activity beendet.
                     */
                    ToDo toDoNeu = new ToDo(titelNeu, beschreibungNeu, datumNeu, prioId);
                    database.toDoDao().addToDo(toDoNeu);
                    long idNeu = toDoNeu.getId();
                    for (String kategorie: kategorien) {
                        for (Category category:input) {
                            if(kategorie.equals(category.getCategoryTitel())){
                                long catIdNeu = category.getCategoryId();
                                ToDo_Category toDo_categoryNeu = new ToDo_Category(idNeu, catIdNeu);
                                database.toDo_categoryDao().addToDo(toDo_categoryNeu);
                            }
                        }
                    }
                    boolean loeschen = false;
                    boolean zurueck = false;
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TODO_ID, idNeu);
                    intent.putExtra(EXTRA_TODO_LO, loeschen);
                    intent.putExtra(EXTRA_TODO_ZU, zurueck);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    /**
                     * Wenn man ein ToDo bearbeitet hat, dann wird dieses in der Datenbank
                     * geupdatet und es wird ein Intent erstellt, die nötigen Variablen, als
                     * Extras, hinzugefügt, das Ergenis auf ok gesetzt und die Activity beendet.
                     */
                    ToDo bearbeitet = new ToDo(titelNeu, beschreibungNeu, datumNeu, prioId);
                    bearbeitet.setId(toDoId);
                    database.toDoDao().updateToDo(bearbeitet);
                    database.toDo_categoryDao().removeToDO_Categories(toDoId);
                    for (String kategorie: kategorien) {
                        for (Category category:input) {
                            if(kategorie.equals(category.getCategoryTitel())){
                                long catIdNeu = category.getCategoryId();
                                ToDo_Category toDo_categoryNeu = new ToDo_Category(toDoId, catIdNeu);
                                database.toDo_categoryDao().addToDo(toDo_categoryNeu);
                            }
                        }
                    }
                    boolean loeschen = false;
                    boolean zurueck = false;
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TODO_ID, toDoId);
                    intent.putExtra(EXTRA_TODO_LO, loeschen);
                    intent.putExtra(EXTRA_TODO_ZU, zurueck);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            case R.id.menuDetailLoeschen:
                /**
                 * Hier wird beschrieben was beim löschen passiert.
                 */
                if(toDoId == -1){
                    /**
                     * Wenn man doch kein neues ToDo erstellen wollte, dann wird es als zurück
                     * gewertet und es wird ein Intent erstellt, die nötigen Variablen, als
                     * Extras, hinzugefügt, das Ergenis auf ok gesetzt und die Activity beendet.
                     */
                    boolean loeschen = false;
                    boolean zurueck = true;
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TODO_ID, toDoId);
                    intent.putExtra(EXTRA_TODO_LO, loeschen);
                    intent.putExtra(EXTRA_TODO_ZU, zurueck);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    /**
                     * Wenn man das ToDo löschen wollte, dann wird es aus der Datenbank gelöscht
                     * und es wird ein Intent erstellt, die nötigen Variablen, als Extras,
                     * hinzugefügt, das Ergenis auf ok gesetzt und die Activity beendet.
                     */
                    database.toDoDao().removeToDo(toDoId);
                    database.toDo_categoryDao().removeToDO_Categories(toDoId);
                    boolean loeschen = true;
                    boolean zurueck = false;
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TODO_ID, toDoId);
                    intent.putExtra(EXTRA_TODO_LO, loeschen);
                    intent.putExtra(EXTRA_TODO_ZU, zurueck);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            case R.id.menuDetailZurueck:
                /**
                 * Wenn man doch kein ToDo erstellen oder  bearbeiten wollte, dann wird
                 * ein Intent erstellt, die nötigen Variablen, als Extras, hinzugefügt,
                 * das Ergenis auf ok gesetzt und die Activity beendet.
                 */
                boolean loeschen = false;
                boolean zurueck = true;
                Intent intent = new Intent();
                intent.putExtra(EXTRA_TODO_ID, toDoId);
                intent.putExtra(EXTRA_TODO_LO, loeschen);
                intent.putExtra(EXTRA_TODO_ZU, zurueck);
                setResult(RESULT_OK, intent);
                finish();
            default:
                return true;
        }
    }

    public void btnDetailKatHinzu(View view){
        /**
         * Wenn man dem ToDo eine Kategorie hinzufügen möchte, dann wird das ausgewählte
         * Spinner-Item in das Textfeld geschrieben.
         */
        String katText = textView.getText().toString();
        String katHinzu = spinnerKat.getSelectedItem().toString();
        katText += katHinzu + ",";
        textView.setText(katText);
    }

    public void btnDetailKatLo(View view){
        /**
         * Wenn man eine Kategorie löschen möchte, dann wird das ausgewählte
         * Spinner-Item aus dem Textfeld gelöscht.
         */
        String katText = textView.getText().toString();
        String katLoeschen = spinnerKat.getSelectedItem().toString();
        String katNeu = katText.replace(katLoeschen + ",","" );
        textView.setText(katNeu);
    }


}