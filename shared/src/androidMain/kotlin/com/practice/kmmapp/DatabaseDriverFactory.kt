package com.practice.kmmapp

import android.content.Context
import com.practice.kmmapp.shared.cache.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
            return AndroidSqliteDriver(AppDatabase.Schema, context, "test.db")
    }
}