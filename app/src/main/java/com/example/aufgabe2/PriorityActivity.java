/**
 * Diese Klasse ist da, um die Prioritäten zu verwalten.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import static com.example.aufgabe2.PriorityDetailsActivity.EXTRA_PRIO_LO;
import static com.example.aufgabe2.PriorityDetailsActivity.EXTRA_PRIO_ZU;

public class PriorityActivity extends AppCompatActivity {

    /**
     * Definieren und Deklarieren einiger Variablen, die später benötigt werden.
     */
    private static final int ADDPRIO = 1111;
    public static final String EXTRA_PRIO_ID = "com.example.aufgabe2.extra.prio.id";
    public static final String EXTRA_PRIO_TITEL = "com.example.aufgabe2.extra.prio.titel";
    private static final int EDITPRIO = 2222;
    private RecyclerView recyclerView;
    private MyAdapterPriority mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private List<Priority> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);

        /**
         * Holen der Datenbank.
         */
        database = AppDatabase.getDatabase(getApplicationContext());

        /**
         * Den Zugriff auf den RecyclerView holen, um die gespeicherten Prioritäten zu verwalten.
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_Prio);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        input = database.priorityDao().getAllPrioritys();
        mAdapter = new MyAdapterPriority(input);
        recyclerView.setAdapter(mAdapter);

        /**
         * ItemClickListener für den RecyclerView mit eigenem Adapter um die Zugriffe auf die Items
         * zu verwalten.
         */
        mAdapter.setOnItemClickListener(priority -> {
            /**
             * Wenn man eine Priorität bearbeiten will, wird ein Intent erstellt, die nötigen
             * Extras hinzugefügt und die PriorityDetailsActivity gestartet.
             */
            Intent openDetail = new Intent(this, PriorityDetailsActivity.class);
            openDetail.putExtra(EXTRA_PRIO_ID, priority.getPriorityId());
            openDetail.putExtra(EXTRA_PRIO_TITEL, priority.getPriorityTitel());
            startActivityForResult(openDetail, EDITPRIO);
        });
    }


    public void btnPrioHinzuPressed(View view) {
        /**
         * Wenn man eine neue Priorität hinzufügen will, wird ein Intent erstellt und die
         * PriorityDetailsActivity gestartet.
         */
        Intent openDetail = new Intent(this, PriorityDetailsActivity.class);
        startActivityForResult(openDetail, ADDPRIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Hier wird beschrieben, wie mit den Ergebnissen, der oberen Aufrufe umgegangen wird.
         */
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITPRIO && resultCode == RESULT_OK){
            /**
             * Es wird beschrieben, was passiert, wenn eine Priorität erfolgreich bearbeitet wurde.
             */
            String prioTitel = data.getStringExtra(EXTRA_PRIO_TITEL);
            long prioID = data.getLongExtra(EXTRA_PRIO_ID, -1);
            boolean loeschen = data.getBooleanExtra(EXTRA_PRIO_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_PRIO_ZU, false);

            if(loeschen){
                /**
                 * Wenn die Priorität gelöscht werden soll, dann wird sie aus der Datenbank und
                 * dem RecyclerView gelöscht.
                 */
                database.priorityDao().removePriority(prioID);

                for(int i = 0; i < input.size(); i++){
                    if(prioID == input.get(i).getPriorityId()){
                        mAdapter.remove(i);
                        break;
                    }
                }
            }else if(!(zurueck) && !(loeschen)) {
                /**
                 * Wenn die Priorität gespeichert werden soll, dann wird sie der Datenbank und
                 * dem RecyclerView hinzugefügt.
                 */
                Priority priority = new Priority(prioTitel);
                priority.setPriorityId(prioID);
                database.priorityDao().updatePriority(priority);

                for (int i = 0; i < input.size(); i++) {
                    if (prioID == input.get(i).getPriorityId()) {
                        mAdapter.remove(i);
                        mAdapter.add(i, priority);
                        break;
                    }
                }
            }
        }else if(requestCode == ADDPRIO && resultCode == RESULT_OK) {
            /**
             * Es wird beschrieben, was passiert, wenn eine Priorität erfolgreich hinzugefügt wurde.
             */
            String prioTitel = data.getStringExtra(EXTRA_PRIO_TITEL);
            boolean loeschen = data.getBooleanExtra(EXTRA_PRIO_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_PRIO_ZU, false);
            if(!(loeschen) && !(zurueck)){
                /**
                 * Wenn sie gespeichert werden soll, dann wird sie erstellt und der DAtenbank und
                 * dem RecyclerView hinzugefügt.
                 */
                Priority priority = new Priority(prioTitel);
                database.priorityDao().addPriority(priority);
                mAdapter.add(mAdapter.getItemCount(), priority);
            }
        }
    }
}