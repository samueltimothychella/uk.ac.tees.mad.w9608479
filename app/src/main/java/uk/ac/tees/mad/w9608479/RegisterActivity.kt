package uk.ac.tees.mad.w9608479

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val progress = findViewById<ProgressBar>(R.id.loading)
        val btnLogin = findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        val btnRegister = findViewById<Button>(R.id.register)
        btnRegister.setOnClickListener {
            val txtName = findViewById<EditText>(R.id.name)
            val txtEmail = findViewById<EditText>(R.id.email)
            val txtPassword = findViewById<EditText>(R.id.password)
            val txtConfirmPassword = findViewById<EditText>(R.id.confirm_password)
            saveRegistration(txtName.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString(), txtConfirmPassword.getText().toString(), progress)
        }

    }

    private fun saveRegistration(name: String, email: String, password: String, confirmPassword: String, progress: ProgressBar) {

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(
                getApplicationContext(),
                "Please enter your name",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                getApplicationContext(),
                "Please enter valid email",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        if (password.length < 8) {
            Toast.makeText(
                getApplicationContext(),
                "Password should be minimum 8 characters",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(
                getApplicationContext(),
                "Confirm password is not matching",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        progress.visibility = View.VISIBLE
        val auth = FirebaseAuth.getInstance();
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    var user:FirebaseUser? = task.getResult().getUser();
                    val profileUpdates: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                    user?.updateProfile(profileUpdates);

                    progress.visibility = View.GONE

                    Toast.makeText(
                        baseContext,
                        "Registration successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val intent = Intent(baseContext, SignInActivity::class.java)
                    startActivity(intent)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    progress.visibility = View.GONE
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}