package com.example.tplistviewsimple

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Liste des éléments
    private val elements = mutableListOf<String>()

    // Adaptateur pour la liste
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Récupérer la ListView
        val listView: ListView = findViewById(R.id.listView)

        // Initialiser l'adaptateur avec la liste des éléments
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, elements)

        // Configurer la ListView avec l'adaptateur
        listView.adapter = adapter

        // Gérer le clic sur un élément de la liste
        listView.setOnItemClickListener { _, _, position, _ ->
            // Implémenter l'action à effectuer lors du clic sur un élément (ex: modification)
            Toast.makeText(this, "Clic sur ${elements[position]}", Toast.LENGTH_SHORT).show()
        }

        // Gérer le clic long sur un élément de la liste
        listView.setOnItemLongClickListener { _, _, position, _ ->
            // Implémenter l'action à effectuer lors du clic long sur un élément (ex: suppression)
            deleteElement(position)
            true
        }

        // Bouton d'ajout
        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            // Implémenter l'action à effectuer lors de l'ajout d'un élément
            val newItem = "Nouvel élément"
            addElement(newItem)
        }
    }

    // Fonction pour ajouter un élément à la liste
    private fun addElement(item: String) {
        elements.add(item)
        adapter.notifyDataSetChanged()
    }

    // Fonction pour supprimer un élément de la liste
    private fun deleteElement(position: Int) {
        elements.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}
