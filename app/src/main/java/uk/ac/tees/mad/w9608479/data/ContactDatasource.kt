package uk.ac.tees.mad.w9608479.data

import android.content.Context
import uk.ac.tees.mad.w9608479.R
import uk.ac.tees.mad.w9608479.modal.Help
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ContactDatasource {
    fun loadData(baseContext:Context): HashMap<String, String> {

            val sharedPref = baseContext.getSharedPreferences(
                "CONTACTS", Context.MODE_PRIVATE)

        val gson = Gson()
        var json = sharedPref.getString("contact_list", null)

        var list : HashMap<String, String>
                = HashMap<String, String> ()

        if (json != null) {
            val type: Type = object : TypeToken<HashMap<String?, String?>?>() {}.getType()
            list = gson.fromJson<HashMap<String, String>>(json, type)
        }

        return list

    }

    public fun addContact(baseContext:Context, key:String, value: String) {
        val sharedPref = baseContext.getSharedPreferences(
            "CONTACTS", Context.MODE_PRIVATE)

        var list : HashMap<String, String>
                = ContactDatasource().loadData(baseContext)

        list.put(key, value)
        val gson = Gson()
        val json:String = gson.toJson(list)

        with(sharedPref.edit()){
            putString("contact_list", json)
            apply()
        }
    }
    public fun removeContact(baseContext:Context, key:String) {
        val sharedPref = baseContext.getSharedPreferences(
            "CONTACTS", Context.MODE_PRIVATE)

        var list : HashMap<String, String>
                = ContactDatasource().loadData(baseContext)

        list.remove(key)
        val gson = Gson()
        val json:String = gson.toJson(list)

        with(sharedPref.edit()){
            putString("contact_list", json)
            apply()
        }
    }
}