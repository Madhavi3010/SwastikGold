package ran.com.swastikgold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UploadClient extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView paidDept,pendingDept;
    private LinearLayout paidNoData,pendingNoData;
    private List<ClientData> list1, list2;
    private ClientAdapter adapter;


    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_client);

        paidDept = findViewById(R.id.paidDept);
        pendingDept = findViewById(R.id.pendingDept);

        pendingNoData = findViewById(R.id.pendingNoData);
        paidNoData = findViewById(R.id.paidNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Client");

        paidDept();
        pendingDept();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadClient.this,AddClient.class));
            }
        });

//
    }

    private void paidDept() {
        dbRef = reference.child("PAID");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    paidNoData.setVisibility(View.VISIBLE);
                    paidDept.setVisibility(View.GONE);
                }else {
                    paidNoData.setVisibility(View.GONE);
                    paidDept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ClientData data = snapshot.getValue(ClientData.class);
                        list1.add(data);
                    }
                    paidDept.setHasFixedSize(true);
                    paidDept.setLayoutManager(new LinearLayoutManager(UploadClient.this));
                    adapter = new ClientAdapter(list1,UploadClient.this,"PAID");
                    paidDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UploadClient.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pendingDept() {
        dbRef = reference.child("PENDING");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    pendingNoData.setVisibility(View.VISIBLE);
                    pendingDept.setVisibility(View.GONE);
                }else {
                    pendingNoData.setVisibility(View.GONE);
                    pendingDept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ClientData data = snapshot.getValue(ClientData.class);
                        list2.add(data);
                    }
                    pendingDept.setHasFixedSize(true);
                    pendingDept.setLayoutManager(new LinearLayoutManager(UploadClient.this));
                    adapter = new ClientAdapter(list2,UploadClient.this,"PENDING");
                    pendingDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UploadClient.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}