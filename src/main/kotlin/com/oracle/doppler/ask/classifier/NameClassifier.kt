package com.oracle.doppler.ask.classifier

import com.oracle.doppler.ask.util.FileUtils
import weka.classifiers.bayes.NaiveBayes
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances
import java.util.*

object NameClassifier {
    private var nameClassifier: NaiveBayes? = null
    private var train: Instances? = null

    private fun initClassifier() {
        train = FileUtils.loadCSV("names")
        train?.setClassIndex(1)
        train?.let { FileUtils.saveCSVtoARFF(it, "names") }

        nameClassifier = NaiveBayes()
        nameClassifier!!.buildClassifier(train)
    }

    fun classify(input: String, locale: String = "en_us"): String? {
        if (nameClassifier == null) {
            initClassifier()
        }

        return getInput(input, locale)
    }

    private fun getInput(value: String, locale: String): String? {
        value.split(" ").forEach { word ->
            try {
                train?.let { train ->
                    val attributes = ArrayList<Attribute>()
                    repeat(train.numAttributes()) {
                        attributes.add(train.attribute(it))
                    }
                    val instance = DenseInstance(train.numAttributes())
                    instance.setValue(train.attribute(0), value.toLowerCase())
                    instance.setMissing(1)
                    instance.setValue(train.attribute(2), locale)

                    val instances = Instances("input", attributes, 0)
                    instances.add(instance)
                    instances.setClassIndex(1)

                    // this does the trick
                    nameClassifier?.classifyInstance(instances.instance(0))?.let {
                        instances.instance(0).setClassValue(it)
                        return instances.instance(0).stringValue(1)
                    }
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
        return null
    }


}