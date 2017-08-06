package com.sanogueralorenzo.brexit.data.local

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type

class SharedPreferencesManager(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun save(key: String, value: String) = prefs.edit().putString(key, value).apply()

    fun load(key: String): String = prefs.getString(key, "")

    fun containsKey(key: String) = prefs.contains(key)

    //Load single objects
    fun <GenericClass> loadObject(classType: Class<GenericClass>): GenericClass? = Gson().fromJson(load(classType.simpleName), classType)

    //Load lists and maps
    fun <GenericClass> loadObject(key: String, type: Type): GenericClass? = Gson().fromJson<GenericClass>(load(key), type)

    //Save single objects
    fun saveObject(`object`: Any) = save(`object`.javaClass.simpleName, Gson().toJson(`object`))

    //Load lists and maps
    fun saveObject(key: String, `object`: Any) = save(key, Gson().toJson(`object`))

    fun delete(key: String) = prefs.edit().remove(key).apply()
}