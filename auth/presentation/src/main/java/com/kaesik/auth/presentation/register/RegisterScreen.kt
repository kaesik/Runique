package com.kaesik.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaesik.auth.domain.PasswordValidationState
import com.kaesik.auth.domain.UserDataValidator
import com.kaesik.auth.presentation.R
import com.kaesik.core.presentation.designsystem.CheckIcon
import com.kaesik.core.presentation.designsystem.CrossIcon
import com.kaesik.core.presentation.designsystem.EmailIcon
import com.kaesik.core.presentation.designsystem.Poppins
import com.kaesik.core.presentation.designsystem.RuniqueDarkRed
import com.kaesik.core.presentation.designsystem.RuniqueGray
import com.kaesik.core.presentation.designsystem.RuniqueGreen
import com.kaesik.core.presentation.designsystem.RuniqueTheme
import com.kaesik.core.presentation.designsystem.components.GradientBackground
import com.kaesik.core.presentation.designsystem.components.RuniqueActionButton
import com.kaesik.core.presentation.designsystem.components.RuniquePasswordTextField
import com.kaesik.core.presentation.designsystem.components.RuniqueTextField
import com.kaesik.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) {event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_success,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is RegisterAction.OnLoginClick -> onSignInClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
                .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium,
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RuniqueGray,
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                style = MaterialTheme.typography.bodyMedium,
                onClick = {
                    offset -> annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = offset,
                    end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.email_info),
                keyboardType = KeyboardType.Email,
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.password_requirement_1,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinimalLength,
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.password_requirement_2
                ),
                isValid = state.passwordValidationState.hasNumber,
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.password_requirement_3
                ),
                isValid = state.passwordValidationState.hasLowerCaseChar,
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.password_requirement_4
                ),
                isValid = state.passwordValidationState.hasUpperCaseChar,
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if (isValid) {
                RuniqueGreen
            } else {
                RuniqueDarkRed
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasMinimalLength = true,
                    hasNumber = true,
                    hasLowerCaseChar = true,
                    hasUpperCaseChar = true,
                )
            ),
            onAction = {}
        )
    }
}
