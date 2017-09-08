package com.sanogueralorenzo.brexit.data.local

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class Local
@Inject constructor
(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveString(key: String, value: String) = prefs.edit().putString(key, value).apply()

    fun loadString(key: String): String = prefs.getString(key, "")

    fun <GenericClass> load(classType: Class<GenericClass>): GenericClass? = Gson().fromJson(loadString(classType.simpleName), classType)

    fun <GenericClass> load(key: String, type: Type): GenericClass? = Gson().fromJson<GenericClass>(loadString(key), type)

    fun save(`object`: Any) = saveString(`object`.javaClass.simpleName, Gson().toJson(`object`))

    fun save(key: String, `object`: Any) = saveString(key, Gson().toJson(`object`))

    fun delete(key: String) = prefs.edit().remove(key).apply()
}
