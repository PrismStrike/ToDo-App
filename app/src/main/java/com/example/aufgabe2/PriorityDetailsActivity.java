/**
 * Diese Klasse wird genutzt, um das erstellen oder bearbeiten von Prioritäten zu ermöglichen.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;


import static com.example.aufgabe2.PriorityActivity.EXTRA_PRIO_ID;
import static com.example.aufgabe2.PriorityActivity.EXTRA_PRIO_TITEL;

public class PriorityDetailsActivity extends AppCompatActivity {

    /**
     * Definieren und Deklarieren einiger Variablen, die später benötigt werden.
     */
    public static final String EXTRA_PRIO_LO = "com.example.aufgabe2.extra.prio.lo";
    public static final String EXTRA_PRIO_ZU = "com.example.aufgabe2.extra.prio.zu";
    private String prioTitel;
    private TextInputLayout text;
    private long prioId;
    private boolean loeschen = false;
    private boolean zurueck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_details);

        /**
         * Zugriff auf das Textfeld bekommen.
         */
        text = findViewById(R.id.textInput_Prio);

        /**
         * Laden des übergebenen Intents, um, eine eventuell schon erstellte Prioritäts-ID und
         * den Prioritätsnamen zu bekommen und den Namen in das Textfeld zu schreiben.
         */
       Intent intent = getIntent();
            prioId = intent.getLongExtra(EXTRA_PRIO_ID, -1);
            prioTitel = intent.getStringExtra(EXTRA_PRIO_TITEL);
            text.getEditText().setText(prioTitel);

    }


    public void btnPrioSpeichernPressed(View view) {
        /**
         * Wenn man speichern drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = false;
        zurueck = false;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PRIO_TITEL, text.getEditText().getText().toString());
        intent.putExtra(EXTRA_PRIO_ID, prioId);
        intent.putExtra(EXTRA_PRIO_LO, loeschen);
        intent.putExtra(EXTRA_PRIO_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void btnPrioZurPressed(View view) {
        /**
         * Wenn man zurück drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = false;
        zurueck = true;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PRIO_TITEL, prioTitel);
        intent.putExtra(EXTRA_PRIO_ID, prioId);
        intent.putExtra(EXTRA_PRIO_LO, loeschen);
        intent.putExtra(EXTRA_PRIO_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnPrioLoPressed(View view) {
        /**
         * Wenn man löschen drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = true;
        zurueck = false;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PRIO_TITEL, prioTitel);
        intent.putExtra(EXTRA_PRIO_ID, prioId);
        intent.putExtra(EXTRA_PRIO_LO, loeschen);
        intent.putExtra(EXTRA_PRIO_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }

}