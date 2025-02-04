package ru.borisov.personnelselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonViewModel : ViewModel() {
    private var _personList = mutableListOf<Person>().also {
        it.addAll(DataBasePersons.persons)
    }
    private var _filterRoleIndex = 0

    val filterRoleIndex: Int
        get() = _filterRoleIndex

    private val _filteredPersonList = MutableLiveData<List<Person>>()

    val filteredPersonList: LiveData<List<Person>>
        get() = _filteredPersonList

    private fun updateFilteredPersonList() {
        _filteredPersonList.value = _personList.filter {
            if (filterRoleIndex == 0) {
                true
            } else {
                it.role == DataBasePersons.roleList[filterRoleIndex]
            }
        }
    }

    fun addPerson(person: Person) {
        _personList.add(person)
        updateFilteredPersonList()
    }

    fun removePerson(index: Int) {
        _personList.removeAt(index)
        updateFilteredPersonList()
    }

    fun changeRoleIndex(index: Int) {
        _filterRoleIndex = index
        updateFilteredPersonList()
    }
}