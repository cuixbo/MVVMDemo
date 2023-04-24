package com.cuixbo.mvvm.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object DataStore {
    // java.lang.IllegalStateException: There are multiple DataStores active for the same file: /data/user/0/com.xiayiye5.jetpackdemo/files/datastore/data_store.preferences_pb. You should either maintain your DataStore as a singleton or confirm that there is no two DataStore's active on the same file (by confirming that the scope is cancelled).

    // 使用Context.扩展属性
    val Context.settingStore: DataStore<Preferences> by preferencesDataStore("settings")
    val Context.movieStore: DataStore<Preferences> by preferencesDataStore("movies")

}