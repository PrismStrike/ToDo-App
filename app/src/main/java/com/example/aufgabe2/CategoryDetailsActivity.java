/**
 * Diese Klasse wird genutzt, um das erstellen oder bearbeiten von Kategorien zu ermöglichen.
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.aufgabe2.CategoryActivity.EXTRA_CAT_ID;
import static com.example.aufgabe2.CategoryActivity.EXTRA_CAT_TITEL;

public class CategoryDetailsActivity extends AppCompatActivity {

    /**
     * Definieren und Deklarieren einiger Variablen, die später benötigt werden.
     */
    public static final String EXTRA_CAT_LO = "com.example.aufgabe2.extra.cat.lo";
    public static final String EXTRA_CAT_ZU = "com.example.aufgabe2.extra.cat.zu";
    private String catTitle;
    private TextInputLayout text;
    private long catId;
    private boolean loeschen = false;
    private boolean zurueck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        /**
         * Zugriff auf das TextInputLayout holen, für den Zugriff auf das Textfeld.
         */
        text = findViewById(R.id.textInput_kategorietitel);

        /**
         * Das übergebene Intent wird in einer lokalen Variable geholt, damit man daraus die
         * übergbene ID und den Titel der Kategorie bekommt, falls eine Kategorie bearbeitet werden
         * soll.
         */
        Intent intent = getIntent();
        catId = intent.getLongExtra(EXTRA_CAT_ID, -1);
        catTitle = intent.getStringExtra(EXTRA_CAT_TITEL);
        text.getEditText().setText(catTitle);

    }

    public void btnKatSpeichernPressed(View view){
        /**
         * Wenn man speichern drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = false;
        zurueck = false;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CAT_TITEL, text.getEditText().getText().toString());
        intent.putExtra(EXTRA_CAT_ID, catId);
        intent.putExtra(EXTRA_CAT_LO, loeschen);
        intent.putExtra(EXTRA_CAT_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnCatZurPressed(View view){
        /**
         * Wenn man zuück drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = false;
        zurueck = true;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CAT_TITEL, catTitle);
        intent.putExtra(EXTRA_CAT_ID, catId);
        intent.putExtra(EXTRA_CAT_LO, loeschen);
        intent.putExtra(EXTRA_CAT_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnCatLoPressed(View view){
        /**
         * Wenn man löschen drückt, dann wird ein neues Intent erstellt, die notwendigen
         * Variablen als Extras hinzugefügt, das Ergebnis auf ok gesetzt und die Activity beendet.
         */
        loeschen = true;
        zurueck = false;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CAT_TITEL, catTitle);
        intent.putExtra(EXTRA_CAT_ID, catId);
        intent.putExtra(EXTRA_CAT_LO, loeschen);
        intent.putExtra(EXTRA_CAT_ZU, zurueck);
        setResult(RESULT_OK, intent);
        finish();
    }

}