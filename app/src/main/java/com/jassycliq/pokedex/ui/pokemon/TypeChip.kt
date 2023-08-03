package com.jassycliq.pokedex.ui.pokemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalMaterial3Api
@Composable
fun TypeChip(
    text: String,
    bodyColor: Color,
    backgroundColor: Color,
) {
    AssistChip(
        onClick = { },
        enabled = false,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = bodyColor,
                    background = backgroundColor,
                )
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            disabledLabelColor = bodyColor,
            disabledContainerColor = backgroundColor,
        ),
        border = AssistChipDefaults.assistChipBorder(
            disabledBorderColor = bodyColor,
        ),
        modifier = Modifier
            .clickable(enabled = false) { }
            .wrapContentWidth()
            .wrapContentHeight(),
    )
}