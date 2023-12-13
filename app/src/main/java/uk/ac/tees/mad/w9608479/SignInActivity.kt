package uk.ac.tees.mad.w9608479

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        val progress = findViewById<ProgressBar>(R.id.loading)

        val btnRegister = findViewById<Button>(R.id.register)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val btnLogin = findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener {
            val txtEmail = findViewById<EditText>(R.id.email)
            val txtPassword = findViewById<EditText>(R.id.password)
            doLogin(txtEmail.getText().toString(), txtPassword.getText().toString(), progress)
        }

    }
    private fun doLogin(email: String, password: String, progress: ProgressBar) {

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
        progress.visibility = View.VISIBLE
        System.out.println(email+":"+password)
        val auth = FirebaseAuth.getInstance();
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                progress.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Registration successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}