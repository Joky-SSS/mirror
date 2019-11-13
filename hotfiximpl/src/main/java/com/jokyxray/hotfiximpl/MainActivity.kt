package com.jokyxray.hotfiximpl

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jokyxray.hotfiximpl.bean.Student
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Array

class MainActivity : AppCompatActivity() {
    private val TAG = "SS=>"
    private var patchJob = Job()
    private var patchScope = CoroutineScope(Dispatchers.Default + patchJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        patchScope.launch {
            //            loadDexFile()
            replacePathList()
        }
    }

    private suspend fun loadDexFile() {
        val filePath = releaseDexFile()
        val dexClassLoader = DexClassLoader(filePath,
                cacheDir.absolutePath + "/optimizedDirectory/",
                "", classLoader)
        val clazz = dexClassLoader.loadClass("com.jokyxray.hotfiximpl.bean.Student")
        val instance = clazz.newInstance()
        val setMethod = clazz.getMethod("setName", String::class.java)
        val getMethod = clazz.getMethod("getName")
        setMethod.invoke(instance, "right")
        val returnValue = getMethod.invoke(instance)
        Log.e(TAG, "the value is $returnValue")
    }

    private suspend fun releaseDexFile(): String {
        return withContext(Dispatchers.IO) {
            val dir = cacheDir
            val file = File(dir, resources.getResourceEntryName(R.raw.student) + ".dex")
            if (!file.exists()) {
                val inputStream = resources.openRawResource(R.raw.student)
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var size: Int
                while (true) {
                    size = inputStream.read(buffer)
                    if (size == -1) break
                    outputStream.write(buffer, 0, size)
                    outputStream.flush()
                }
                Log.e(TAG, "file release done! path: ${file.absolutePath}")
            } else {
                Log.e(TAG, "dex file already exist")
            }
            file.absolutePath
        }
    }

    private suspend fun replacePathList() {
        val filePath = releaseDexFile()
        val dexClassLoader = DexClassLoader(filePath,
                cacheDir.absolutePath + "/optimizedDirectory/",
                "", classLoader)
        val pathClassLoader: PathClassLoader = classLoader as PathClassLoader
        val patchElements = getElements(getPathList(dexClassLoader))
        val dexElements = getElements(getPathList(pathClassLoader))
        val newDexElements = combineElements(patchElements, dexElements)
        val obj = getPathList(pathClassLoader)
        setField(obj!!, obj.javaClass, "dexElements", newDexElements)
        val student = Student()
        student.name = "Right"
        Log.e(TAG, "the name is ${student.name}")
    }

    private fun getPathList(classLoader: BaseDexClassLoader): Any? {
        val clazz = classLoader.javaClass
        val superClazz = if (clazz == BaseDexClassLoader::class.java) clazz else clazz.superclass
        try {
            val field = superClazz!!.getDeclaredField("pathList")
            field.isAccessible = true
            return field.get(classLoader)
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return null
    }

    private fun getElements(pathList: Any?): Any? {
        if (pathList == null) return null
        val clazz = pathList.javaClass
        try {
            val field = clazz.getDeclaredField("dexElements")
            field.isAccessible = true
            return field.get(pathList)
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return null
    }

    private fun combineElements(patchObj: Any?, dexElementsObj: Any?): Any? {
        val patchElements = patchObj as kotlin.Array<*>
        val dexElements = dexElementsObj as kotlin.Array<*>
        val obj = patchElements[0]
        val clazz = obj!!.javaClass
        val newArray = Array.newInstance(clazz, patchElements.size + dexElements.size)
        var index = 0
        for (element in patchElements) {
            Array.set(newArray, index++, element)
        }
        for (element in dexElements) {
            Array.set(newArray, index++, element)
        }
        return newArray
    }

    private fun setField(pathList: Any, clazz: Class<*>, fieldName: String, fieldValue: Any?) {
        try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(pathList, fieldValue)
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
    }
}
