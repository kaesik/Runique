package com.kaesik.auth.presentation

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.kaesik.core.presentation.designsystem.Poppins
import com.kaesik.core.presentation.designsystem.RuniqueGray

@Composable
fun ClickableAuthSubtitle(
    @StringRes nonClickableTextResId: Int,
    @StringRes clickableTextResId: Int,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = Poppins,
                color = RuniqueGray
            )
        ) {
            append(stringResource(id = nonClickableTextResId) + " ")
            pushStringAnnotation(
                tag = "clickable_text",
                annotation = stringResource(id = clickableTextResId)
            )
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = "clickable_text",
                    linkInteractionListener = {
                        onClick()
                    }
                )
            ) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                ) {
                    append(stringResource(id = clickableTextResId))
                }
            }
        }
    }

    Text(text = annotatedString)
}
