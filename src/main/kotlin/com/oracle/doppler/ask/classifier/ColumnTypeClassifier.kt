package com.oracle.doppler.ask.classifier

import com.oracle.doppler.ask.models.Column
import com.oracle.doppler.ask.util.FileUtils
import weka.classifiers.bayes.NaiveBayes
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances
import java.util.*

object ColumnTypeClassifier {
    const val FILE_NAME = "columns"
    const val colIndex = 2
    private var columnClassifier: NaiveBayes? = null
    private var train: Instances? = null

    private fun initClassifier() {
        train = FileUtils.loadCSV(FILE_NAME)
        train?.attribute(0)?.setWeight(2.0)
        train?.setClassIndex(2)
        train?.let { FileUtils.saveCSVtoARFF(it, FILE_NAME) }

        columnClassifier = NaiveBayes()
        columnClassifier!!.buildClassifier(train)
    }

    fun classify(column: Column, locale: String = "en_us"): String? {
        if (columnClassifier == null) {
            initClassifier()
        }

        return getInput(column, locale)
    }

    fun getInput(column: Column, locale: String): String? {
        train?.let { train ->
            val attributes = ArrayList<Attribute>()
            repeat(train.numAttributes()) {
                attributes.add(train.attribute(it))
            }
            val classifiedName = NameClassifier.classify(column.name)

            val instance = DenseInstance(train.numAttributes())
            instance.setDataset(train)
            if (classifiedName != null) {
                instance.setValue(0, classifiedName)
            } else {
                instance.setMissing(0)
            }
            instance.setValue(1, locale)
            instance.setMissing(colIndex)
            if (column.length != null) {
                instance.setValue(3, column.length.toDouble())
            } else {
                instance.setMissing(3)
            }
            if (column.decimals != null) {
                instance.setValue(4, column.decimals.toDouble())
            } else {
                instance.setMissing(4)
            }
            instance.setValue(5, if (column.isGeo) "TRUE" else "FALSE")
            instance.setValue(6, if (column.isTimeBased) "TRUE" else "FALSE")

            val instances = Instances("input", attributes, 0)
            instances.add(instance)
            instances.setClassIndex(2)

            // this does the trick
            columnClassifier?.classifyInstance(instances.instance(0))?.let {
                instances.instance(0).setClassValue(it)
                return instances.instance(0).stringValue(colIndex)
            }
        }
        return null
    }

}