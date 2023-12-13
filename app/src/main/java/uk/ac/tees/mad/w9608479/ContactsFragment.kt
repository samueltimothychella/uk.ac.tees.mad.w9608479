package uk.ac.tees.mad.w9608479

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import uk.ac.tees.mad.w9608479.adapter.ContactAdapter
import uk.ac.tees.mad.w9608479.data.ContactDatasource
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [NearByMeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_contacts, container, false)

        val myDataset = ContactDatasource().loadData((activity as MainActivity))

        val recyclerView:RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ContactAdapter((activity as MainActivity), myDataset)


        val btn = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        btn.setOnClickListener {
            (activity as MainActivity).navigate(R.id.nav_add_contact)
        }

        return view
    }
}