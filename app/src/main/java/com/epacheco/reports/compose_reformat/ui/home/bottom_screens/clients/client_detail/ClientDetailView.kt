package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.ClientItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.MoneyItem
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.clients.Client

@Composable
fun ClientDetailView(
    client: Client?,
    inputAmount: String? = null,
    onInputAmountChanged: ((String) -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    inputConcept: String? = null,
    onInputConceptChanged: ((String) -> Unit)? = null,
    openClientTransaction: ((String) -> Unit)? = null,
    onCreatePayment: ((String) -> Unit)? = null,
    openClientOrder: ((String) -> Unit)? = null,
    openClientSale: ((String) -> Unit)? = null,
    onPhoneClick: (() -> Unit)? = null
) {

    Column {
        val clientName =
            client?.name ?: run { stringResource(R.string.client_not_found) }
        Header(
            text = stringResource(R.string.add_client_title, clientName),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            textColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )


        Column(
            modifier = Modifier
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
                    textValue = inputAmount ?: "",
                    keyboardType = KeyboardType.Decimal,
                    onTextChange = { onInputAmountChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    textHint = stringResource(R.string.client_earns_concept),
                    textValue = inputConcept ?: "",
                    onTextChange = { onInputConceptChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
            PrimaryButton(
                textButton = stringResource(R.string.btn_abonar),
                iconBtn = R.drawable.ic_vector_sale,
                iconTint = MaterialTheme.colorScheme.onPrimary
            ) {
                client?.let {
                    onCreatePayment?.invoke(it.id)
                }

            }
            Spacer(modifier = Modifier.padding(8.dp))
            TextDivider(
                text = stringResource(R.string.operations),
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
                ) {
                    client?.let {
                        openClientSale?.invoke(it.id)
                    }
                }
                MoneyItem(
                    text = stringResource(R.string.option_info),
                    icon = R.drawable.ic_vector_activity
                ) {
                    client?.let {
                        openClientTransaction?.invoke(client.id)
                    }
                }
                MoneyItem(
                    text = stringResource(R.string.option_order),
                    icon = R.drawable.ic_vector_order
                ) {
                    client?.let {
                        openClientOrder?.invoke(client.id)
                    }
                }
                if (!client?.phone.isNullOrEmpty()) {
                    MoneyItem(
                        text = stringResource(R.string.option_call),
                        icon = R.drawable.ic_vector_phone
                    ) {
                        onPhoneClick?.invoke()

                    }
                }

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
