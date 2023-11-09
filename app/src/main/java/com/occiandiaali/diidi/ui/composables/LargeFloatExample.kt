package com.occiandiaali.diidi.ui.composables

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable

@Composable
fun LargeFloatExample(onClick: () -> Unit) {
    LargeFloatingActionButton(
        onClick = {onClick},
        shape = CircleShape
        ) {
        Icon(Icons.Filled.List, "Large FAB")
    }
}