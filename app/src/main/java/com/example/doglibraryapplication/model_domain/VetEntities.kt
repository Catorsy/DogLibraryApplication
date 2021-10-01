package com.example.doglibraryapplication

import com.example.doglibraryapplication.model_domain.Breed

//это модель

data class Dog (
    val name: String,
    val age: Int,
    val weight: Int,
    val breed: Breed, //отдельная сущность для пород, она не мжет быть "любой"
    val photo: String,
    val id: Int? = null //айди обычно сочиняет БД
        )