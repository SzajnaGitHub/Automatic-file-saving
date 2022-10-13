package com.espressoit.scopedstoragedemo.extensions

inline fun <R> R?.orDefault(block: () -> R): R = this ?: block.invoke()

