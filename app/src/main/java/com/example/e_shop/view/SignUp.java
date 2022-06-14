package com.example.e_shop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_shop.R;
import com.example.e_shop.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUp extends Fragment {
    private TextInputEditText signUpEmail;
    private Button signUpButton;
    private TextInputEditText signupPassword;
    private FirebaseAuth firebaseAuth;
    private String newUserEmail, newUserPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUpEmail = view.findViewById(R.id.sign_up_emailEdittext);
        signupPassword = view.findViewById(R.id.sign_up_password_edittext);
        signUpButton = view.findViewById(R.id.signup_button);

        firebaseAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUserEmail = signUpEmail.getText().toString();
                newUserPassword = signupPassword.getText().toString();
                if (TextUtils.isEmpty(newUserEmail)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newUserPassword)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newUserPassword.length() < 6) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(newUserEmail, newUserPassword)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Welcome to e-Shop!", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    updateUI();
                                } else {
                                    Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Authentication Failed Check credentials again",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }

    private void updateUI() {
        Fragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        loginFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().remove(new SignUp())
                .replace(R.id.fragment_container_view, loginFragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}