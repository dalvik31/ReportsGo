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
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.clients.ClientDetailCmps
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.GoogleColor
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.dateFormat

@Composable
fun DetailClientView(client : Client?) {
    var clientDetail = ClientDetailCmps()
    var clientsDetail = emptyList<ClientDetailCmps>()
    if (client != null) {
        if (!client.clientsDetails.isNullOrEmpty()) {
            clientsDetail = client.clientsDetails.values.toList().sortedByDescending { it.datePayment }
            clientDetail = clientsDetail.first()
        }
    }
    Column(modifier = Modifier) {
        Card(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)){
                GetInfoClient(clientDetail = clientDetail, client = client!!)
                GetCreditLimit(client = client, debt = clientDetail.debt)
                GetPayment()
                GetClientTransactions(clientsDetail)
            }
        }
    }
}

@Preview
@Composable
fun DetailClientViewPreview() {
    DetailClientView(Client())
}

@Composable
fun GetInfoClient(clientDetail : ClientDetailCmps, client: Client) {
    var showDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        Popup(
            onDismissRequest = { showDialog = false },
            alignment = Alignment.TopEnd, // Clave: Alinea en la parte superior
            properties = PopupProperties(
                excludeFromSystemGesture = true,
                clippingEnabled = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp) // Ajusta este valor según necesites
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = Color.Blue.copy(alpha = 0.2f)
                    )
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = White,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Ideas",
                            tint = GoogleColor,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Detalles del cliente...",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoogleColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            color = Black,
                            text = client.detail,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { showDialog = false },
                            border = BorderStroke(1.dp, GoogleColor),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = GoogleColor
                            )
                        ) {
                            Text("¡OK!")
                        }
                    }
                }
            }
        }
    }

    Row(Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)) {
        Image(modifier = Modifier
            .weight(0.25f)
            .padding(8.dp)
            .fillMaxWidth()
            .clip(CircleShape),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.img),
            contentDescription = null)

        Column(modifier = Modifier
            .weight(0.6f)){
            Text(
                modifier = Modifier.padding(7.dp),
                text = client.name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Text(
                modifier = Modifier.padding(start = 7.dp, end = 7.dp),
                text = stringResource(R.string.txt_debt_client, clientDetail.debt),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(start = 7.dp, end = 7.dp),
                text = stringResource(R.string.txt_client_date_format, client.dateClient) ,
                color = colorResource(R.color.colorGray),
                fontSize = 16.sp
            )
        }
        Image(
            modifier = Modifier
                .weight(0.15f)
                .align(Alignment.CenterVertically)
                .size(50.dp)
                .padding(bottom = 20.dp)
                .clickable() { showDialog = true },
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = "info"
        )
    }
}

@Composable
fun GetCreditLimit(client: Client, debt: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            text = "Limite de credito"
        )
        Text(
            modifier = Modifier.padding(top = 7.dp),
            text = stringResource(R.string.txt_credit_limit_client, client.limit.toString()),
            fontSize = 22.sp
        )
        val credit = client.limit + debt
        val porcent = (debt * 100) / credit
        Log.e("porcent", "porcent: $porcent")
        LinearProgressIndicator(
            progress = { (porcent / 100).toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp)
                .padding(top = 9.dp),
            color = Color(0xffff4444),
            trackColor = Color.LightGray
        )
    }
}

@Composable
fun GetClientTransactions(listTransaction: List<ClientDetailCmps>) {
    Column(modifier = Modifier.padding(top = 15.dp)) {
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            text = "Transacciones"
        )
        GetListTransactions(listTransaction = listTransaction)
    }
}

@Composable
fun GetListTransactions(listTransaction: List<ClientDetailCmps>) {
    if (listTransaction.isNotEmpty()) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            listTransaction.forEach { client ->
                GetItemTransaction(client)
            }
        }
    }
}

@Preview
@Composable
fun GetItemTransaction(clientDetail: ClientDetailCmps = ClientDetailCmps()) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Image(
            painter = painterResource(id = if (clientDetail.isPay) R.drawable.arrow_down else R.drawable.arrow_up),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .weight(0.2f)
                .padding(5.dp)
                .align(Alignment.CenterVertically)
        )
        Column(modifier = Modifier
            .weight(0.8f)
            .align(Alignment.CenterVertically)) {
            Text(
                modifier = Modifier.padding(7.dp),
                text = clientDetail.concept,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier.padding(start = 7.dp, end = 7.dp),
                text = stringResource(R.string.txt_client_amount_format, clientDetail.amount),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(start = 7.dp, end = 7.dp),
                text = dateFormat(clientDetail.datePayment, DateUtils.FORMAT_DATE1),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GetPayment() {

    val context = LocalContext.current

    val makePayment = remember { mutableStateOf(false) }
    val value = remember { mutableStateOf("") }
    if (makePayment.value) {
        Column(modifier = Modifier.padding(top = 15.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .weight(0.8f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = "Realiza un abono"
                )
                Image(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(20.dp)
                        .weight(0.2f)
                        .clickable { makePayment.value = false },
                    painter = painterResource(id = R.drawable.subtract_ic),
                    contentDescription = "close payment"
                )
            }
            InputTextField(textHint = "Ingresa el monto a abonar", textValue = value.value, keyboardType = KeyboardType.Number){
                value.value = it
            }

            PrimaryButton(
                modifier = Modifier.padding(top = 15.dp),
                "Abonar".uppercase(),
                colorBackground = GoogleColor,
                onButtonClicked = { Toast.makeText(context, "Abonar : ${value.value}", Toast.LENGTH_SHORT).show() }
            )
            Row(modifier = Modifier.padding(top = 15.dp)) {
                PrimaryButton(modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 5.dp), "Generar Pedido".uppercase(), colorBackground = Black)
                PrimaryButton(modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 5.dp), "Abrir Venta".uppercase(), colorBackground = GoogleColor)

            }
        }
    } else {
        PrimaryButton(
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
            "Realizar abono".uppercase(),
            colorBackground = GoogleColor,
            onButtonClicked = { makePayment.value = true }
        )
    }
}