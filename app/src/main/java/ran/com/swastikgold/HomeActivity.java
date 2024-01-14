package ran.com.swastikgold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView addClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addClient = findViewById(R.id.addClient);

        addClient.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addClient:
                Intent intent = new Intent(HomeActivity.this,UploadClient.class);
                startActivity(intent);
                break;
        }

    }
}