package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.ClientItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.MoneyItem
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.clients.ClientDetailCmps
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.GoogleColor
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.dateFormat

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
) {

    Column {

        val titleHeader =
            stringResource(if (clientId != null) R.string.modify_client else R.string.create_new_cliet)
        Header(
            title = titleHeader,
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            titleColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )


        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

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
                textHint = stringResource(R.string.client_credit),
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
