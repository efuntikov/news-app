package com.efuntikov.newsapp.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.component.topbar.TopBar
import com.efuntikov.newsapp.component.topbar.TopBarState
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TopBar(topBarState = TopBarState(title = "Settings"))
        LanguageDropdownMenu()
    }
}

@Composable
fun SettingsItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {

    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    NewsAppTheme {
        SettingsScreen()
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    NewsAppTheme {
        SettingsItem()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownMenu() {
    val settingsViewModel: SettingsViewModel = getViewModel()
    LaunchedEffect(key1 = "initial") {
        settingsViewModel.load()
    }

    var expanded by remember { mutableStateOf(false) }
    val selectedOptionText by settingsViewModel.languageSelected
    val languageList by settingsViewModel.availableLanguagesList
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/ collapsing the menu on click. A read-only text field has         // the anchor type `PrimaryNotEditable`.
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            value = selectedOptionText.description,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("Language") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            languageList.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.description, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        settingsViewModel.setLanguage(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownMenuExample() {
    NewsAppTheme {
        LanguageDropdownMenu()
    }
}
