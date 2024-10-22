package com.kaesik.core.presentation.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaesik.core.presentation.designsystem.LogoIcon
import com.kaesik.core.presentation.designsystem.RuniqueTheme
import com.kaesik.core.presentation.designsystem.components.util.DropDownItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RuniqueToolbar(
    showBackButton: Boolean,
    title: String,
    modifier: Modifier,
    menuItems: List<DropDownItem> = emptyList(),
    onMenuItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    startContent: @Composable () -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun RuniqueToolbarPreview() {
    RuniqueTheme {
        RuniqueToolbar(
            showBackButton = false,
            title = "Runique",
            modifier = Modifier.fillMaxWidth(),
            startContent = {
                Icon(
                    imageVector = LogoIcon,
                    contentDescription = null
                )
            }
        )
    }
}
