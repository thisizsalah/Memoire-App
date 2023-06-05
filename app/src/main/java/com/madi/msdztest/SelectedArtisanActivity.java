package com.madi.msdztest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class SelectedArtisanActivity extends AppCompatActivity {
    private FirebaseFirestore fStore;



    ImageView Imgprofile;
    Button Call;
    TextView Nom, Catégorie, Descripton;
    RecyclerView Images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_artisan);
        Imgprofile = findViewById(R.id.imageViewProfile);
        Nom = findViewById(R.id.textViewName);
        Catégorie = findViewById(R.id.textViewCategorie);
        Descripton = findViewById(R.id.textViewDescription);
        Call = findViewById(R.id.button_appeler);
        Images = findViewById(R.id.ImageListRecyclerView);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String data = extras.getString("artisan_id");
            String telephone = extras.getString("artisan_telephone");
            fStore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = fStore.collection("Artisans").document(data);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    String imageUrl = value.getString("imageProfile");
                    if (imageUrl != null) {
                        Picasso.get().load(imageUrl).into(Imgprofile);
                    }

                    String name = value.getString("Nom");
                    String prenom = value.getString("Prénom");
                    String fullName = name + " " + prenom;

                    Nom.setText(fullName);
                    Catégorie.setText(value.getString("Catégorie"));
                    Descripton.setText(value.getString("Description"));

                    Call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + telephone));

                            startActivity(intent);
                        }

                    });


                }
            });


        }


    }
}