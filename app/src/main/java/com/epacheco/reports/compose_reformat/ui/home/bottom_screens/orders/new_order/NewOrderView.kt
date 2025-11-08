package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.ClientItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryItem
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.color_picker.ColorPickerDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picker_dialog.PickerDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picker_dialog.PickerDialogOption
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.ui.theme.GrayLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.toColor
import com.epacheco.reports.compose_reformat.utils.extensions.toHexString


@Composable
fun NewOrderView(
    clientSelected: Client? = null,
    onInputStatus: Boolean = false,
    onInputStatusChanged: ((Boolean) -> Unit)? = null,
    onInputNameChanged: ((String) -> Unit)? = null,
    inputName: String? = null,
    onInputDescriptionChanged: ((String) -> Unit)? = null,
    inputDescription: String? = null,
    onInputSizeChanged: ((String) -> Unit)? = null,
    inputSize: String? = null,
    onInputIsNumericSize: Boolean = false,
    onInputIsNumericSizeChanged: ((Boolean) -> Unit)? = null,
    onInputColorChanged: ((String) -> Unit)? = null,
    inputColor: String? = null,
    inputColorCode: String? = null,
    onInputColorCodeChanged: ((String) -> Unit)? = null,
    onInputGenderChanged: ((String) -> Unit)? = null,
    inputGender: String? = null,
    orderToEdit: String? = null,
    orderNameToEdit: String? = null,
    onCreateOrder: (() -> Unit)? = null,
    onDeleteOrder: (() -> Unit?)? = null,
    onUpdateOrder: (() -> Unit?)? = null,
    onRemoveClient: (() -> Unit)? = null,
    onInputClientChanged: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {

    val listColorNames = stringArrayResource(R.array.colors_name_array)
    val listColorCode = stringArrayResource(R.array.colors_code_array)
    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    var sizePickerOpen by rememberSaveable { mutableStateOf(false) }
    var sizeNumericPickerOpen by rememberSaveable { mutableStateOf(false) }
    var genderPickerOpen by rememberSaveable { mutableStateOf(false) }
    var showDialogConfirmDeleteOrder by remember { mutableStateOf(false) }

    var currentlySelected by rememberSaveable(saver = colourSaver()) {
        mutableStateOf(listColorCode[0].toColor())
    }




    Column {

        Header(
            text = stringResource(orderToEdit?.let { R.string.modify_order_title }
                ?: run { R.string.create_new_order_title }),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },
            textColor = MaterialTheme.colorScheme.primary,
            tintImageRight = MaterialTheme.colorScheme.primary
        )

        clientSelected?.let { client ->
            ClientItem(
                modifier = Modifier.padding(horizontal = 20.dp),
                client = client,
                actionText = if (client.name.isEmpty()) "Buscar cliente" else "Cambiar cliente",
                iconAction = R.drawable.ic_error,
                onClickIcon = {
                    onRemoveClient?.invoke()
                }, onClick = {
                    onInputClientChanged?.invoke()
                },
            )
        } ?: run {
            SecondaryItem(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.search_client),
                icon = R.drawable.ic_vector_search_client,
                secondaryText = stringResource(R.string.client_not_selected),
                onClick = {
                    onInputClientChanged?.invoke()
                }
            )

        }

        Spacer(modifier = Modifier.padding(16.dp))
        TextDivider(
            textDivider = stringResource(R.string.order_detail_title),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(horizontal = 12.dp),
            fontSize = 14.sp

        )

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            orderToEdit?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = onInputStatus,
                        onCheckedChange = { onInputStatusChanged?.invoke(!onInputStatus) }
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                onInputStatusChanged?.invoke(!onInputStatus)
                            },
                        text = stringResource(R.string.new_order_status_title),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )

                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

           /* Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.new_order_name_title),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.padding(8.dp))*/
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.new_order_name_desc),
                textValue = inputName ?: "",
                onTextChange = { onInputNameChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences,

                )
            Spacer(modifier = Modifier.padding(8.dp))

            /*Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.new_order_description_title),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.padding(8.dp))*/
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.new_order_description_desc),
                textValue = inputDescription ?: "",
                singleLine = false,
                onTextChange = { onInputDescriptionChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                /*Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = stringResource(R.string.new_order_size_title),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )*/
                Spacer(Modifier.weight(1f))

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.new_order_number_sizes),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )

                Checkbox(
                    modifier = Modifier.padding(end = 8.dp),
                    checked = onInputIsNumericSize,
                    onCheckedChange = {
                        onInputSizeChanged?.invoke("")
                        onInputIsNumericSizeChanged?.invoke(!onInputIsNumericSize)
                    }
                )
            }


            InputTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        if (onInputIsNumericSize) {
                            sizeNumericPickerOpen = true
                        } else sizePickerOpen = true

                    },
                textHint = stringResource(R.string.new_order_size_desc),
                textValue = inputSize ?: "",
                enable = false
            )
           /* Spacer(modifier = Modifier.padding(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.new_order_color_title),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )*/
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                InputTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.new_order_color_desc),
                    textValue = inputColor ?: "",
                    onTextChange = { onInputColorChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences,
                )
                IconButton(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .background(GrayLight, shape = CircleShape),
                    onClick = {
                        colorPickerOpen = true
                    }) {

                    GetBtnSelectColor(inputColor ?: "")
                }
            }

           /* Spacer(modifier = Modifier.padding(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.new_order_gender_title),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )*/
            Spacer(modifier = Modifier.padding(8.dp))

            InputTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        genderPickerOpen = true
                    },
                textHint = stringResource(R.string.new_order_gender_desc),
                textValue = inputGender ?: "",
                enable = false
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                verticalAlignment = Alignment.CenterVertically,
            ) {
                orderToEdit?.let {
                    PrimaryButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textButton = stringResource(R.string.btn_delete),
                        colorBackground = GrayDark
                    ) {
                        showDialogConfirmDeleteOrder = true
                    }
                }

                PrimaryButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textButton = orderToEdit?.let {
                        stringResource(R.string.btn_update)

                    } ?: run { stringResource(R.string.btn_save) }) {

                    orderToEdit?.let {
                        onUpdateOrder?.invoke()
                    } ?: run {
                        onCreateOrder?.invoke()
                    }


                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }
    }




    if (sizeNumericPickerOpen) {
        PickerDialog(
            pickerDialogOption = PickerDialogOption.NUMBER_SIZES,
            onDismiss = { sizeNumericPickerOpen = false },
            onValueSelected = {
                onInputSizeChanged?.invoke(it)
            }
        )
    }

    if (sizePickerOpen) {
        PickerDialog(
            pickerDialogOption = PickerDialogOption.SIZES,
            onDismiss = { sizePickerOpen = false },
            onValueSelected = {
                onInputSizeChanged?.invoke(it)
            }
        )
    }

    if (genderPickerOpen) {
        PickerDialog(
            pickerDialogOption = PickerDialogOption.GENDERS,
            onDismiss = { genderPickerOpen = false },
            onValueSelected = {
                onInputGenderChanged?.invoke(it)
            }
        )
    }

    if (colorPickerOpen) {
        val colorPosition = colorPosition(inputColorCode ?: "")
        if (colorPosition > -1) {
            currentlySelected = listColorCode[colorPosition].toColor()
        }
        ColorPickerDialog(
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = { color, index ->
                currentlySelected = color
                onInputColorChanged?.invoke(listColorNames[index])
                onInputColorCodeChanged?.invoke(color.toHexString())
            }
        )
    }

    if (showDialogConfirmDeleteOrder) {
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_order_title),
            dialogSubTitle = stringResource(
                R.string.msg_delete_order_list_body,
                orderNameToEdit ?: ""
            ),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteOrder = false },
            onConfirmation = {
                showDialogConfirmDeleteOrder = false
                onDeleteOrder?.invoke()
            }
        )
    }
}

@Composable
fun GetBtnSelectColor(colorWrote: String) {
    val colorPosition = colorPosition(colorWrote)
    return if (colorPosition > -1) {
        Icon(
            painter = painterResource(R.drawable.ic_color_picker),
            contentDescription = "close",
            tint = getTintColor(colorPosition)
        )
    } else {
        Image(
            modifier = Modifier.padding(6.dp),
            painter = painterResource(R.drawable.ic_select_color),
            contentDescription = "close"
        )
    }

}

fun colourSaver() = Saver<MutableState<Color>, String>(
    save = { state -> state.value.toHexString() },
    restore = { value -> mutableStateOf(value.toColor()) }
)

@Composable
fun colorPosition(currentColor: String): Int {
    return stringArrayResource(R.array.colors_name_array).indexOfLast { it == currentColor }
}

@Composable
fun getTintColor(position: Int): Color {
    return stringArrayResource(R.array.colors_code_array)[position].toColor()
}


@Preview(showSystemUi = true)
@Composable
fun NewOrderViewPreview() {
    ReportsGoTheme {
        NewOrderView(orderToEdit = "")
    }
}