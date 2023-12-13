package uk.ac.tees.mad.w9608479.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.tees.mad.w9608479.MainActivity
import uk.ac.tees.mad.w9608479.R
import uk.ac.tees.mad.w9608479.data.ContactDatasource
import uk.ac.tees.mad.w9608479.modal.Help

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */

class ContactAdapter(
    private var context: MainActivity,
    private var dataset: HashMap<String, String>
): RecyclerView.Adapter<ContactAdapter.ItemViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
   inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.findViewById(R.id.item_title)
        val textValue: TextView = view.findViewById(R.id.item_value)
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)

        init{
            deleteButton.setOnClickListener{
                if (it.getId() == deleteButton.getId()) {
                    var key:String = textValue.getText().toString()
                    ContactDatasource().removeContact(context, key)
                    dataset = ContactDatasource().loadData(context)
                    notifyDataSetChanged()
                }
            }
        }

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_action, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var keys:MutableSet<String> = dataset.keys
        val key = keys.elementAt(position)
        val value = dataset.get(keys.elementAt(position))
        holder.textTitle.text = value
        holder.textValue.text = key
        holder.imageView.setImageResource(R.drawable.ic_menu_person)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}