package com.example.e_shop;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class ProductRepository {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Product product;
    List<Product> productList = new ArrayList<>();
    public MutableLiveData<Product> requestProducts(){
        final MutableLiveData<Product> mutableLiveData = new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    product = new Product(data.child("image").getValue().toString(),
                            data.child("name").getValue().toString(), data.child("price").getValue().toString());
                    mutableLiveData.setValue(product);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error", error.getMessage());
            }});

        return mutableLiveData;
    }
}
