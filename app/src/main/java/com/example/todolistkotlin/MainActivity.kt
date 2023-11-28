package com.example.todolistkotlin

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var et_item:EditText
    lateinit var bt_add:Button
    lateinit var bt_delete:Button
    lateinit var bt_update:Button
    lateinit var listView: ListView

    var selectedPosition: Int? = null;

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_item = findViewById(R.id.et_item)
        bt_add = findViewById(R.id.bt_add)
        bt_delete = findViewById(R.id.bt_delete)
        bt_update = findViewById(R.id.bt_update)
        listView = findViewById(R.id.list)
        val customAdapter = CustomAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = customAdapter

        itemList = fileHelper.readData(this)
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        listView.adapter = arrayAdapter

        bt_add.setOnClickListener {
            if (et_item.text.isEmpty()) {
                // Inform the user that the item name cannot be empty
                val emptyNameBuilder = AlertDialog.Builder(this@MainActivity)
                emptyNameBuilder.setTitle("Empty Item Name")
                emptyNameBuilder.setMessage("Please enter a valid item name.")
                emptyNameBuilder.setPositiveButton("OK") { _, _ ->
                    // Do nothing, just close the dialog
                }
                emptyNameBuilder.create().show()
                return@setOnClickListener
            }
            var itemName: String = et_item.text.toString()
            itemList.add(itemName)
            et_item.setText("")
            fileHelper.writeData(this@MainActivity, itemList)
            arrayAdapter.notifyDataSetChanged()

        }

        bt_update.setOnClickListener {
            if (selectedPosition != null) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Update Item")
                builder.setMessage("Do you want to update this item?")

                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    val updatedItemName: String = et_item.text.toString()

                    if (updatedItemName.isNotEmpty()) {
                        itemList[selectedPosition!!] = updatedItemName
                        fileHelper.writeData(this@MainActivity, itemList)
                        arrayAdapter.notifyDataSetChanged()
                        selectedPosition = null
                        et_item.setText("")
                        customAdapter.setSelectedPosition(null)
                    } else {
                        // Inform the user that the item name cannot be empty
                        val emptyNameBuilder = AlertDialog.Builder(this@MainActivity)
                        emptyNameBuilder.setTitle("Empty Item Name")
                        emptyNameBuilder.setMessage("Please enter a valid item name.")
                        emptyNameBuilder.setPositiveButton("OK") { _, _ ->
                            // Do nothing, just close the dialog
                        }
                        emptyNameBuilder.create().show()
                    }
                }

                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                    // Do nothing if "No" is clicked
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            } else {
                // Inform the user to select an item before updating
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("No Item Selected")
                builder.setMessage("Please select an item to update.")
                builder.setPositiveButton("OK") { _, _ ->
                    // Do nothing, just close the dialog
                }
                builder.create().show()
            }
        }


        bt_delete.setOnClickListener {
            if (selectedPosition != null) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Delete Item")
                builder.setMessage("Are you sure you want to delete this item?")

                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    itemList.removeAt(selectedPosition!!)
                    fileHelper.writeData(this@MainActivity, itemList)
                    arrayAdapter.notifyDataSetChanged()
                    selectedPosition = null
                    et_item.setText("")
                }

                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                    // Do nothing if "No" is clicked
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            } else {
                // Inform the user to select an item before deleting
                // You can display a Toast or show another dialog here
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("No Item Selected")
                builder.setMessage("Please select an item to delete.")
                builder.setPositiveButton("OK") { _, _ ->
                    // Do nothing, just close the dialog
                }
                builder.create().show()
            }
        }

        listView.setOnItemClickListener { adapterView, view, position, l ->
            selectedPosition = position
            et_item.setText(itemList[position])
            customAdapter.setSelectedPosition(position)
        }


    }
}