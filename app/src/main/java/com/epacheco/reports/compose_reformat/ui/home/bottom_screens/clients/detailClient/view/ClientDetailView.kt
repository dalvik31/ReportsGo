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
fun ClientDetailView(
    client: Client?,
    inputName: String? = null,
    onInputNameChanged: ((String) -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null
) {

    Column {
        val clientName =
            client?.name ?: run { stringResource(R.string.client_not_found) }
        Header(
            title = stringResource(R.string.add_client_title, clientName),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            titleColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            ClientItem(client = client, showFullName = true)
            Spacer(modifier = Modifier.padding(8.dp))

            Row {
                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    textHint = stringResource(R.string.client_earns),
                    textValue = inputName ?: "",
                    onTextChange = { onInputNameChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    textHint = stringResource(R.string.client_earns_concept),
                    textValue = inputName ?: "",
                    onTextChange = { onInputNameChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
            PrimaryButton(
                textButton = "Abonar",
                iconBtn = R.drawable.ic_vector_sale,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextDivider(
                textDivider = stringResource(R.string.operations),
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoneyItem(
                    text = stringResource(R.string.option_sale),
                    icon = R.drawable.ic_vector_sale
                )
                MoneyItem(
                    text = stringResource(R.string.option_info),
                    icon = R.drawable.ic_vector_activity
                )
                MoneyItem(
                    text = stringResource(R.string.option_order),
                    icon = R.drawable.ic_vector_order
                )
                MoneyItem(
                    text = stringResource(R.string.option_call),
                    icon = R.drawable.ic_vector_phone
                )
            }




            Spacer(modifier = Modifier.padding(48.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientDetailViewPreview() {
    ClientDetailView(Client())
}
