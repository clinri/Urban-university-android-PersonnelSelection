package ru.borisov.personnelselection

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var personNameET: EditText
    private lateinit var personSurnameET: EditText
    private lateinit var personAgeET: EditText
    private lateinit var personRoleSpinner: Spinner
    private lateinit var saveBTN: Button
    private lateinit var personItemListLV: ListView
    private lateinit var personViewModel: PersonViewModel
    private val personList = mutableListOf<Person>()
    private val adapterPersonList by lazy { PersonListAdapter(this, personList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]
        val adapterRoleList = object : CustomSpinnerAdapter(this) {}
        personViewModel.filteredPersonList.observe(this) { list ->
            personList.clear()
            personList.addAll(list)
            adapterPersonList.notifyDataSetChanged()
        }
        initVariables()
        setSupportActionBar(toolbar)
        setSaveButtonClickListener()
        setOnItemLongClickListener()
        title = getString(R.string.text_title_toolbar)
        personItemListLV.adapter = adapterPersonList
        personRoleSpinner.adapter = adapterRoleList
        setTextWatchers()
    }

    private fun setTextWatchers() {
        var indexPersonRole = 0
        fun checkFields() {
            val et1 = personNameET.text.toString().trim()
            val et2 = personSurnameET.text.toString().trim()
            val et3 = personAgeET.text.toString().trim()
            saveBTN.isEnabled = et1.isNotEmpty()
                    && et2.isNotEmpty()
                    && et3.isNotEmpty()
                    && indexPersonRole != 0
        }
        saveBTN.isEnabled = false
        val editTexts = listOf(personNameET, personSurnameET, personAgeET)
        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFields()
                }
            })
        }
        personRoleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                indexPersonRole = position
                checkFields()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setOnItemLongClickListener() {
        personItemListLV.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ): Boolean {
                personViewModel.removePerson(position)
                return true
            }
        }
    }

    private fun setSaveButtonClickListener() {
        saveBTN.setOnClickListener {
            val name = personNameET.text.toString()
            val surname = personSurnameET.text.toString()
            val age = personAgeET.text.toString()
            val role = personRoleSpinner.selectedItem.toString()
            if (name.isNotEmpty() || surname.isNotEmpty() || age.isNotEmpty()) {
                personViewModel.addPerson(Person(name, surname, age.toInt(), role))
                personNameET.setText("")
                personSurnameET.setText("")
                personAgeET.setText("")
                personRoleSpinner.setSelection(0)
            }
        }
    }

    private fun initVariables() {
        toolbar = findViewById(R.id.toolbar)
        personNameET = findViewById(R.id.personNameET)
        personSurnameET = findViewById(R.id.personSurnameET)
        personAgeET = findViewById(R.id.personAgeET)
        personRoleSpinner = findViewById(R.id.personRoleSpinner)
        saveBTN = findViewById(R.id.saveBTN)
        personItemListLV = findViewById(R.id.personItemListLV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu?.findItem(R.id.filterRoleSpinner)
        val toolbarSpinner = item?.actionView as Spinner
        val toolbarSpinnerAdapter = object : CustomToolbarSpinnerAdapter(this) {}
        toolbarSpinner.adapter = toolbarSpinnerAdapter
        toolbarSpinner.setSelection(personViewModel.filterRoleIndex)
        toolbarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                personViewModel.changeRoleIndex(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit) finish()
        return super.onOptionsItemSelected(item)
    }
}