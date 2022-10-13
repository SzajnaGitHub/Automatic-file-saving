package com.espressoit.scopedstoragedemo.extensions

fun <R> String.ifNotEmpty(block: (String) -> R): R? = isNotEmpty().ifTrue { block.invoke(this) }
