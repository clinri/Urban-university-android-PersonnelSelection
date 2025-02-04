package ru.borisov.personnelselection

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

open class CustomSpinnerAdapter(
    private val context: Context,
    list: List<String> = DataBasePersons.roleList,
) : ArrayAdapter<String>(
    context, android.R.layout.simple_spinner_item, list
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent).customizeItemView(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        } else convertView
        return super.getDropDownView(position, view, parent).customizeItemView(position)
    }

    fun View.customizeItemView(position: Int): View {
        val textView = this.findViewById<TextView>(android.R.id.text1)
        if (position == 0) {
            // Disable the first item
            textView.setTextColor(Color.GRAY)
            isEnabled = false
        } else {
            // Disable the first item
            textView.setTextColor(Color.BLACK)
            isEnabled = true
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        return this
    }
}