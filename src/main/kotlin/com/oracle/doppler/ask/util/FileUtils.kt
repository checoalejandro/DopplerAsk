package com.oracle.doppler.ask.util

import weka.core.Instances
import weka.core.converters.CSVLoader
import java.io.File
import weka.core.converters.ArffSaver


object FileUtils {

    fun getText(fileName: String): String? {
        try {
            return javaClass.getResource("/$fileName").readText()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun loadCSV(fileName: String): Instances {
        val csvLoader = CSVLoader()
        csvLoader.setSource(javaClass.getResourceAsStream("/$fileName.csv"))
        return csvLoader.dataSet
    }

    fun saveCSVtoARFF(data: Instances, outputName: String) {
        if (File("resources/$outputName.arff").exists()) {
            File("resources/$outputName.arff").delete()
        }
        val saver = ArffSaver()
        saver.instances = data
        saver.setFile(File("resources/$outputName.arff"))
        saver.writeBatch()
    }

}