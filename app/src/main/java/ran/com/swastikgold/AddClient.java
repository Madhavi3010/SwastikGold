package ran.com.swastikgold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddClient extends AppCompatActivity {

    private EditText clinetName, phoneNo, goldRate, quantityGold, totalBill, pendingBill;
    private Spinner clientCategory;
    private Button addClientBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name, phone, rate, quantity, total, pending;
    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        clinetName = findViewById(R.id.clinetName);
        phoneNo = findViewById(R.id.phoneNo);
        goldRate = findViewById(R.id.goldRate);
        quantityGold = findViewById(R.id.quantityGold);
        totalBill = findViewById(R.id.totalBill);
        pendingBill = findViewById(R.id.pendingBill);
        clientCategory = findViewById(R.id.clientCategory);
        addClientBtn = findViewById(R.id.addClientBtn);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Client");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category", "PAID", "PENDING"};
        clientCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        clientCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = clientCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();

            }
        });


    }

    private void checkValidation() {
        name = clinetName.getText().toString();
        phone = phoneNo.getText().toString();
        rate = goldRate.getText().toString();
        quantity = quantityGold.getText().toString();
        total = totalBill.getText().toString();
        pending = pendingBill.getText().toString();

        if (name.isEmpty()) {
            clinetName.setError("Empty");
            clinetName.requestFocus();
        } else if (phone.isEmpty()) {
            phoneNo.setError("Empty");
            phoneNo.requestFocus();
        } else if (rate.isEmpty()) {
            goldRate.setError("Empty");
            goldRate.requestFocus();
        } else if (quantity.isEmpty()) {
            quantityGold.setError("Empty");
            quantityGold.requestFocus();
        } else if (total.isEmpty()) {
            totalBill.setError("Empty");
            totalBill.requestFocus();
        } else if (pending.isEmpty()) {
            pendingBill.setError("Empty");
            pendingBill.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "Please select Client Category", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            uploadData();

        }
    }

    private void uploadData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        ClientData clientData = new ClientData(name, phone,rate,quantity,total,pending,uniqueKey);

        dbRef.child(uniqueKey).setValue(clientData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddClient.this, "Client Details Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddClient.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}