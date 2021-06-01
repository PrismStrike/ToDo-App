/**
 * Diese Klasse ist da, um die Kategorien zu verwalten.
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
import static com.example.aufgabe2.CategoryDetailsActivity.EXTRA_CAT_LO;
import static com.example.aufgabe2.CategoryDetailsActivity.EXTRA_CAT_ZU;


public class CategoryActivity extends AppCompatActivity {

    /**
     * Definieren und Deklarieren einiger Variablen, die später benötigt werden.
     */
    public static final String EXTRA_CAT_ID = "com.example.aufgabe2.extra.cat.id";
    public static final String EXTRA_CAT_TITEL = "com.example.aufgabe2.extra.cat.titel";
    private static final int EDITCAT = 3333;
    private static final int ADDCAT = 4444;
    private RecyclerView recyclerView;
    private MyAdapterCategory mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private List<Category> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        /**
         * Die Datenbank wird geladen.
         */
        database = AppDatabase.getDatabase(getApplicationContext());

        /**
         * Über einen RecyclerView werden die bereits erstellten Kategorien angezeigt.
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_Kategorien);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        input = database.categoryDao().getAllCategory();
        mAdapter = new MyAdapterCategory(input);
        recyclerView.setAdapter(mAdapter);

        /**
         * Um auf die Auswahl einer Kategorie, über den RecyclerView, zu reagieren wird ein
         * ItemClickListener erstellt, welcher die Zugriffe mit Hilfe eines eigenen Adapters regelt.
         */
        mAdapter.setOnItemClickListener(category -> {
            /**
             * Wenn man eine Kategorie auswählt, dann wird ein Inten erstellt und die
             * CategoryDetailsActivity wird gestartet.
             */
            Intent openDetail = new Intent(this, CategoryDetailsActivity.class);
            openDetail.putExtra(EXTRA_CAT_ID, category.getCategoryId());
            openDetail.putExtra(EXTRA_CAT_TITEL, category.getCategoryTitel());
            startActivityForResult(openDetail, EDITCAT);
        });
    }

    public void btnKatHinzuPressed(View view) {
        /**
         * Wenn man den Button hinzufügen drückt, dann wird ein Inten erstellt und die
         * CategoryDetailsActivity wird gestartet.
         */
        Intent openDetail = new Intent(this, CategoryDetailsActivity.class);
        startActivityForResult(openDetail, ADDCAT);
    }

    /**
     * In dieser Methode wird das Ergebnis ausgewertet, wenn man aus der CategoryDetailsActivity
     * zurückkehrt.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITCAT && resultCode == RESULT_OK){
            /**
             * Wenn man eine Kategorie bearbeitet hat, dann wird das Ergebnis und die Änderung
             * hier gespeichert.
             */
            String catTitel = data.getStringExtra(EXTRA_CAT_TITEL);
            long catId = data.getLongExtra(EXTRA_CAT_ID, -1);
            Category category = new Category(catTitel);
            category.setCategoryId(catId);
            boolean loeschen = data.getBooleanExtra(EXTRA_CAT_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_CAT_ZU, false);

            if(loeschen){
                /**
                 * Falls die Kategorie gelöscht wurde, dann wird diese aus der Datenbank und aus der
                 * Anzeige des RecyclerViews gelöscht.
                 */
                database.categoryDao().removeCategory(catId);

                for(int i = 0; i < input.size(); i++){
                    if(catId == input.get(i).getCategoryId()){
                        mAdapter.remove(i);
                        break;
                    }
                }
            }else if(!(loeschen) && !(zurueck)){
                /**
                 * Wenn die Kategorie bearbeitet wurde, dann wird hier die Datenbank und der
                 * RecyclerView geupdatet.
                 */
                database.categoryDao().updateCategory(category);

                for(int i = 0; i < input.size(); i++){
                    if(catId == input.get(i).getCategoryId()){
                        mAdapter.remove(i);
                        mAdapter.add(i, category);
                        break;
                    }
                }
            }

        }else if(requestCode == ADDCAT && resultCode == RESULT_OK) {
            /**
             * Hier wird geregelt was passiert, wenn man eine Kategorie adden wollte.
             */
            String catTitel = data.getStringExtra(EXTRA_CAT_TITEL);
            boolean loeschen = data.getBooleanExtra(EXTRA_CAT_LO, false);
            boolean zurueck = data.getBooleanExtra(EXTRA_CAT_ZU, false);

            if(!(loeschen) && !(zurueck)) {
                /**
                 * Wenn man auf speichern gedrückt hat, dann wird der Datenbank und dem
                 * RecyclerView die neue Kategorie hinzugefügt.
                 */
                Category category = new Category(catTitel);
                database.categoryDao().addCategory(category);
                mAdapter.add(mAdapter.getItemCount(), category);
            }
        }
    }
}
