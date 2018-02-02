package osama.me.viewmodelannotations;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler_view);
        ArrayList<AKindOfObject> kindOfObjects = new ArrayList<>();

        kindOfObjects.add(new AKindOfObject("Osama", 4500));
        kindOfObjects.add(new AKindOfObject("Rao", 4500));
        kindOfObjects.add(new AKindOfObject("SomeOtherDude", 4500));


        final MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(kindOfObjects);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}