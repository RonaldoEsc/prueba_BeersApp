package net.hrec.pruebatecnica.usecases.common.interfaces

import androidx.navigation.NavDirections

interface NavEventListener {
    fun onNavigateChangeEvent(action: NavDirections)
}