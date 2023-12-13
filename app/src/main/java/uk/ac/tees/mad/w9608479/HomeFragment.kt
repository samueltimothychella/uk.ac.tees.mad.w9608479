package uk.ac.tees.mad.w9608479

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
            val btnNearby:Button = view.findViewById<Button>(R.id.nearbyme)
            btnNearby.setOnClickListener {
                (activity as MainActivity).navigate(R.id.nav_nearbyme)
            }

        val btnHelpme = view.findViewById<Button>(R.id.helpme)
        btnHelpme.setOnClickListener {
            (activity as MainActivity).helpme()
        }

        return view
    }
}