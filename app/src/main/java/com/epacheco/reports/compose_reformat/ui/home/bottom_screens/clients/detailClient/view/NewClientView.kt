package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryItem
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.utils.Constants


@Composable
fun NewClientView(
    clientId: String? = null,
    inputName: String? = null,
    onInputNameChanged: ((String) -> Unit)? = null,
    inputLastName: String? = null,
    onInputLastNameChanged: ((String) -> Unit)? = null,
    inputInfo: String? = null,
    onInputInfoChanged: ((String) -> Unit)? = null,
    inputPhone: String? = null,
    onInputPhoneChanged: ((String) -> Unit)? = null,
    inputCredit: String? = null,
    onInputCreditChanged: ((String) -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    onCreateClient: (() -> Unit)? = null,
    onDeleteClient: (() -> Unit)? = null,
    onUpdateClient: ((String) -> Unit)? = null,
    onSelectContact: (() -> Unit)? = null,
) {

    Column {

        val titleHeader =
            stringResource(if (clientId != null) R.string.modify_client else R.string.create_new_cliet)
        Header(
            text = titleHeader,
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            textColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )


        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            SecondaryItem(
                text = "Elegir desde contactos",
                secondaryText = "Contactos",
                modifier = Modifier.padding(horizontal = 24.dp),
                onClick = {
                    onSelectContact?.invoke()
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Row {

                InputTextField(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 8.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.client_name),
                    textValue = inputName ?: "",
                    onTextChange = { onInputNameChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
                InputTextField(
                    modifier = Modifier
                        .padding(end = 24.dp, start = 8.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.client_lastname),
                    textValue = inputLastName ?: "",
                    onTextChange = { onInputLastNameChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )


            }

            Spacer(modifier = Modifier.padding(8.dp))
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.client_info),
                textValue = inputInfo ?: "",
                onTextChange = { onInputInfoChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.client_cel),
                textValue = inputPhone ?: "",
                onTextChange = { onInputPhoneChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.client_credit, Constants.LIMIT_AMOUNT),
                textValue = inputCredit ?: "",
                onTextChange = { onInputCreditChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                verticalAlignment = Alignment.CenterVertically,
            ) {
                clientId?.let {
                    PrimaryButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textButton = stringResource(R.string.btn_delete),
                        colorBackground = GrayDark
                    ) {
                        onDeleteClient?.invoke()
                    }
                }

                PrimaryButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textButton = clientId?.let {
                        stringResource(R.string.btn_update)

                    } ?: run { stringResource(R.string.btn_save) }) {

                    clientId?.let {
                        onUpdateClient?.invoke(it)

                    } ?: run {
                        onCreateClient?.invoke()
                    }
                }
            }


            Spacer(modifier = Modifier.padding(48.dp))

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewClientViewPreview() {
    NewClientView()
}
