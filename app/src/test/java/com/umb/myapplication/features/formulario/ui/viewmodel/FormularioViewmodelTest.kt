package com.umb.myapplication.features.formulario.ui.viewmodel

import org.junit.Assert.*
import org.junit.Test
import java.lang.NumberFormatException

internal class FormularioViewmodelTest {

    @Test
    fun `validacion de nombre`() {
        assertEquals("1", validateName(null))
        assertEquals("1", validateName(""))
        assertEquals("2", validateName("?__+_+"))
        assertEquals("2", validateName("12372987128937120893"))
        assertEquals("2", validateName("<./,/.,?>>"))
        assertEquals("", validateName("Cristhian Rodriguez"))
        assertEquals("", validateName("Cristhian Rodríguez"))
        assertEquals("", validateName("Harold Rodriguez"))

        var test = "Cristhian    David     Rodriguez"
        do {
            test = test.replace("  ", " ")
        }while (test.contains("  "))
        assertEquals("Cristhian David Rodriguez",test)
    }

    private fun validateName(name: String?): String {
        if (name.isNullOrEmpty()) {
            return "1"
        }
        val allowedCharacters = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚ "
        name.forEach {
            if (!allowedCharacters.contains(it.toUpperCase())) {
                return "2"
            }
        }
        return ""
    }

    @Test
    fun `validacion de edad`() {
        assertEquals("1", validateAge(null))
        assertEquals("1", validateAge(""))
        assertEquals("3", validateAge("     "))
        assertEquals("3", validateAge(" "))
        assertEquals("3", validateAge("abc"))
        assertEquals("3", validateAge("9.0"))
        assertEquals("3", validateAge("9.2"))
        assertEquals("3", validateAge("4.0"))
        assertEquals("2", validateAge("3"))
        assertEquals("2", validateAge("4"))
        assertEquals("2", validateAge("10"))
        assertEquals("2", validateAge("11"))
        assertEquals("", validateAge("5"))
        assertEquals("", validateAge("8"))
        assertEquals("", validateAge("9"))
    }

    private fun validateAge(ageString: String?): String {
        if (ageString.isNullOrEmpty()) {
            return "1"
        }
        try {
            val age: Int = ageString.toInt()
            if (age < 5 || age > 9) {
                return "2"
            }
        } catch (e: NumberFormatException) {
            return "3"
        }
        return ""
    }
}