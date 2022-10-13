package com.espressoit.scopedstoragedemo.extensions

fun Boolean?.orFalse() = this ?: false

fun <R> Boolean.ifTrue(block: () -> R): R? = if (this) block.invoke() else null

