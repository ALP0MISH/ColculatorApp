package com.example.colculatorapp

import android.media.VolumeShaper.Operation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Calculator {

   val calculateFlow = MutableStateFlow(CalculatorModel())

   fun enterNumber(number: String) {
      val currentCalculate: CalculatorModel = calculateFlow.value
      if (currentCalculate.operation.isEmpty()) {
         val newModel: CalculatorModel = currentCalculate.copy(
            numberFirst = currentCalculate.numberFirst + number
         )
         calculateFlow.tryEmit(newModel)
         return
      }
      val newModel: CalculatorModel = currentCalculate.copy(
         numberSecond = currentCalculate.numberSecond + number
      )
      calculateFlow.tryEmit(newModel)

   }

   fun enterOperation(operation: String) {
      val currentCalculate: CalculatorModel = calculateFlow.value
      if (currentCalculate.operation.isEmpty() &&
         currentCalculate.numberFirst.lastOrNull() != '.'
      ){
         val newModel: CalculatorModel = currentCalculate.copy(
            operation = operation
         )
         calculateFlow.tryEmit(newModel)
      }
   }

   fun enterCalculate() {
      val currentCalculator: CalculatorModel = calculateFlow.value

     if (currentCalculator.numberSecond.lastOrNull() == '.') return

      val numberFirst: Double = currentCalculator.numberFirst.toDoubleOrNull() ?: return
      val numberSecond: Double = currentCalculator.numberSecond.toDoubleOrNull() ?: return

      val result: Double = when (currentCalculator.operation) {
         "+" -> numberFirst + numberSecond
         "-" -> numberFirst - numberSecond
         "x" -> numberFirst * numberSecond
         "/" -> numberFirst / numberSecond
         else -> 0.0
      }

      calculateFlow.tryEmit(
         currentCalculator.copy(
            numberFirst = result.toString(),
            numberSecond = "",
            operation = ""
         )
      )
   }

   fun enterDelete() {
      val currentCalculate: CalculatorModel = calculateFlow.value
      when {
         currentCalculate.numberSecond.isNotEmpty() -> {
            val numberSecond: String = currentCalculate.numberSecond.dropLast(1)
            calculateFlow.tryEmit(
               currentCalculate.copy(
                  numberSecond = numberSecond
               )
            )
         }

         currentCalculate.operation.isNotEmpty() -> {
            calculateFlow.tryEmit(
               currentCalculate.copy(
                  operation = ""
               )
            )
         }

         currentCalculate.numberFirst.isNotEmpty() -> {
            val numberFirst: String = currentCalculate.numberFirst.dropLast(1)
            calculateFlow.tryEmit(
               currentCalculate.copy(
                  numberFirst = numberFirst
               )
            )
         }
      }
   }

   fun enterDecimal() {
      val currentCalculate: CalculatorModel = calculateFlow.value

      if (currentCalculate.operation.isEmpty()
         && !currentCalculate.numberFirst.contains(".")
         && currentCalculate.numberFirst.isNotEmpty()
      ) {
         calculateFlow.tryEmit(
            currentCalculate.copy(
               numberFirst = currentCalculate.numberFirst + "."
            )
         )
         return
      }

      if (!currentCalculate.numberSecond.contains(".")
         && currentCalculate.numberSecond.isNotEmpty()
      ) {
         calculateFlow.tryEmit(
            currentCalculate.copy(
               numberSecond = currentCalculate.numberSecond + "."
            )
         )
      }


   }
   fun enterClear() {
      calculateFlow.tryEmit(CalculatorModel())
   }
}