package com.example.todolistkotlin

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat

class CustomAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var selectedPosition: Int? = null

    fun setSelectedPosition(position: Int?) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        if (position == selectedPosition) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedItemColor))
        } else {
            view.setBackgroundColor(Color.TRANSPARENT) // Set the default background color
        }
        return view
    }
}
