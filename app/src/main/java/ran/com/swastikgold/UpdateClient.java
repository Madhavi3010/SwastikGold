package ran.com.swastikgold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UpdateClient extends AppCompatActivity {

    private EditText updateClientName, updatePhoneNo, updateGoldRate, updateQuantityGold, updateTotalBill, updatePendingBill;
    private Button updateClientBtn, deleteClientBtn;
    private Spinner updateClientCategory;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    private String category,uniqueKey;


    private String name, phone, rate, quantity, total, pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        rate = getIntent().getStringExtra("rate");
        quantity = getIntent().getStringExtra("quantity");
        total = getIntent().getStringExtra("total");
        pending = getIntent().getStringExtra("pending");

         uniqueKey = getIntent().getStringExtra("key");
         category = getIntent().getStringExtra("category");

        reference = FirebaseDatabase.getInstance().getReference().child("Client");
        storageReference = FirebaseStorage.getInstance().getReference();


        updateClientName = findViewById(R.id.updateClientName);
        updatePhoneNo = findViewById(R.id.updatePhoneNo);
        updateGoldRate = findViewById(R.id.updateGoldRate);
        updateQuantityGold = findViewById(R.id.updateQuantityGold);
        updateTotalBill = findViewById(R.id.updateTotalBill);
        updatePendingBill = findViewById(R.id.updatePendingBill);
        updateClientBtn = findViewById(R.id.updateClientBtn);
        deleteClientBtn = findViewById(R.id.deleteClientBtn);
        updateClientCategory = findViewById(R.id.updateClientCategory);

        updateClientName.setText(name);
        updatePhoneNo.setText(phone);
        updateGoldRate.setText(rate);
        updateQuantityGold.setText(quantity);
        updateTotalBill.setText(total);
        updatePendingBill.setText(pending);

        updateClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = updateClientName.getText().toString();
                phone = updatePhoneNo.getText().toString();
                rate = updateGoldRate.getText().toString();
                quantity = updateQuantityGold.getText().toString();
                total = updateTotalBill.getText().toString();
                pending = updatePendingBill.getText().toString();

                checkValidation();
            }
        });


        deleteClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateClient.this, "Client Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateClient.this,UploadClient.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateClient.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        if (name.isEmpty()) {
            updateClientName.setError("Empty");
            updateClientName.requestFocus();
        } else if (phone.isEmpty()) {
            updatePhoneNo.setError("Empty");
            updatePhoneNo.requestFocus();
        } else if (rate.isEmpty()) {
            updateGoldRate.setError("Empty");
            updateGoldRate.requestFocus();
        } else if (quantity.isEmpty()) {
            updateQuantityGold.setError("Empty");
            updateQuantityGold.requestFocus();
        } else if (total.isEmpty()) {
            updateTotalBill.setError("Empty");
            updateTotalBill.requestFocus();
        } else if (pending.isEmpty()) {
            updatePendingBill.setError("Empty");
            updatePendingBill.requestFocus();
        } else if (bitmap == null) {
            updateData("");
        }


    }

    private void updateData(String s) {

        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("phone",phone);
        hp.put("rate",rate);
        hp.put("quantity",quantity);
        hp.put("total",total);
        hp.put("pending",pending);



        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateClient.this, "Client Data Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateClient.this,UploadClient.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateClient.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}