package ru.borisov.personnelselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.Locale
import kotlin.apply
import kotlin.run
import kotlin.text.format

class PersonListAdapter(
    context: Context,
    personItemList: List<Person>,
) : ArrayAdapter<Person>(
    /* context = */ context,
    /* resource = */ R.layout.person_list_item,
    /* objects = */ personItemList
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: run {
            LayoutInflater.from(context).inflate(R.layout.person_list_item, parent, false)
        }
        val personNameTV = view?.findViewById<TextView>(R.id.personNameTV)
        val personSurnameTV = view?.findViewById<TextView>(R.id.personSurnameTV)
        val personAgeTV = view?.findViewById<TextView>(R.id.personAgeTV)
        val personRoleTV = view?.findViewById<TextView>(R.id.personRoleTV)
        getItem(position)?.apply {
            personNameTV?.text = name
            personSurnameTV?.text = surname
            personAgeTV?.text = String.format(Locale.getDefault(), "%d", age)
            personRoleTV?.text = role
        }
        return view
    }
}