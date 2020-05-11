package com.oracle.doppler.ask

import com.oracle.doppler.ask.classifier.ColumnTypeClassifier
import com.oracle.doppler.ask.classifier.NameClassifier
import com.oracle.doppler.ask.models.Column
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HelloTest {


    @Test
    fun `classify season`() {
        val column = Column(
                name = "Season",
                isGeo = true
        )

        val result = ColumnTypeClassifier.classify(column)
        println("Received: ${column.name} and considered as $result")
    }

    @Test
    fun `classify income`() {
        val column = Column(
                name = "Income",
                decimals = 2
        )

        val result = ColumnTypeClassifier.classify(column)
        println("Received: ${column.name} and considered as $result")
    }

    @Test
    fun `classify units sold`() {
        val column = Column(
                name = "Units Sold",
                decimals = 0
        )

        val result = ColumnTypeClassifier.classify(column)
        println("Received: ${column.name} and considered as $result")
    }
}
