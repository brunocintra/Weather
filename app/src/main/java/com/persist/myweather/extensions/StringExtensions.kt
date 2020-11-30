package com.persist.myweather.extensions

import com.persist.myweather.model.City

fun String.isTrimEmpty(): Boolean = this.trim().isEmpty()

fun City.nameIsOk(): Boolean = !this.name.isTrimEmpty()