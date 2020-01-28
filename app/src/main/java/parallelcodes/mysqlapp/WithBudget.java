package parallelcodes.mysqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
public class WithBudget extends AppCompatActivity {
    ArrayList<PlaceDetailsModel> placeDetailsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_budget);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        Bundle bundle = getIntent().getExtras();
        //placeDetailsModelArrayList = (ArrayList<PlaceDetailsModel>) bundle.getSerializable("placeDetailsModelArrayList");
        placeDetailsModelArrayList=this.getIntent().getExtras().getParcelableArrayList("placeDetailsModelArrayList");
        Log.d("testing", String.valueOf(placeDetailsModelArrayList.get(0).getBudget()));
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
       PlaceAdapter adapter = new PlaceAdapter(placeDetailsModelArrayList);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}
