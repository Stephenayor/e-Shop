package com.example.e_shop.data;

import android.util.Log;
import android.widget.Toast;

import com.example.e_shop.data.model.LoggedInUser;
import com.example.e_shop.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String SIGN_IN = "Signed In";
    private FirebaseAuth firebaseAuth;
    private LoginFragment loginFragment;

    public Result<LoggedInUser> login(String username, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            //handle loggedInUser authentication
            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(SIGN_IN, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(SIGN_IN, "signInWithEmail:failure", task.getException());

                            }
                        }
                    });
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),username
                            );
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseAuth.getInstance().signOut();
    }
    private void updateUI(){

    }

}