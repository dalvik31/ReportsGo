package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.EmailTextField
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun RecoveryPasswordView(
    onBackPressed: (() -> Unit)? = null,
    onInputEmailChanged: ((String) -> Unit)? = null,
    inputEmail: String? = null,
    inputEmailIsValid: Boolean = false,
    onSendEmail: (() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            title = stringResource(R.string.recovery_password_screen_title),
            backgroundToolbar = Color.Transparent,
            titleColor = MaterialTheme.colorScheme.primary,
            onLeftIconClicked = { onBackPressed?.invoke() },
            leftImageVector = Icons.Default.ArrowBackIosNew,
            tintImageLeft = MaterialTheme.colorScheme.primary
        )
        Card(modifier = Modifier.padding(horizontal = 24.dp)) {
            Column {
                Spacer(modifier = Modifier.padding(8.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_notfication),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    text = stringResource(R.string.recovery_password_body),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }

        }
        EmailTextField(modifier = Modifier.padding(24.dp), email = inputEmail ?: "") {
            onInputEmailChanged?.invoke(it)
        }

        PrimaryButton(
            modifier = Modifier.padding(24.dp),
            enabledButton = inputEmailIsValid,
            textButton = stringResource(R.string.recovery_password_send_email)
        ) {
            onSendEmail?.invoke()
        }
    }
}


@Preview
@Composable
fun RecoveryPasswordViewPreview() {
    ReportsGoTheme {
        RecoveryPasswordView()
    }
}