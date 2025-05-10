package ru.borisov.personnelselection

class DataBasePersons {
    companion object{
        val roleList = mutableListOf<String>(
            "Выберите\n должность",
            "Охранник",
            "Финансист",
            "Программист",
            "Повар",
            "Энергетик",
        )
        val persons = listOf(
            Person(
                name = "Василий",
                surname = "Петров",
                age = 34,
                role = "Энергетик"
            ),
            Person(
                name = "Андрей",
                surname = "Ефимов",
                age = 39,
                role = "Программист"
            ),
            Person(
                name = "Владимир",
                surname = "Афанасьев",
                age = 37,
                role = "Повар"
            ),
            Person(
                name = "Федор",
                surname = "Грачёв",
                age = 50,
                role = "Охранник"
            ),
            Person(
                name = "Петр",
                surname = "Сологуб",
                age = 25,
                role = "Финансист"
            ),
        )
    }
}