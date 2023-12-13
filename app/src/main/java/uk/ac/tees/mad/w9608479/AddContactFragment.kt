package uk.ac.tees.mad.w9608479

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import uk.ac.tees.mad.w9608479.data.ContactDatasource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_contact, container, false)
        val btnRegister = view.findViewById<Button>(R.id.save)
        btnRegister.setOnClickListener {
            val txtName = view.findViewById<EditText>(R.id.name)
            val txtPhone = view.findViewById<EditText>(R.id.phone)
            val progress = view.findViewById<ProgressBar>(R.id.loading)
            progress.visibility = View.VISIBLE
            saveRegistration((activity as MainActivity), txtName.getText().toString(),txtPhone.getText().toString());
            progress.visibility = View.GONE
        }


        return view
    }

    private fun saveRegistration(context: MainActivity,name: String, phone: String) {

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(
                context,
                "Please enter your name",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(
                context,
                "Please enter phone no.",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        var list:HashMap<String, String> = ContactDatasource().loadData(context)
        if (list.containsKey(phone)) {
            Toast.makeText(
                context,
                "Phone no. already exists",
                Toast.LENGTH_LONG
            )
                .show();
            return;
        }
        ContactDatasource().addContact(context, phone, name);
        context.navigate(R.id.nav_contacts)
    }
}