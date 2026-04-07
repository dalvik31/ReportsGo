package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.general_components.dialogs.color_picker.ColorPickerDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picker_dialog.PickerDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.GetBtnSelectColor
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.colorPosition
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.colourSaver
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.extensions.toColor
import com.epacheco.reports.compose_reformat.utils.extensions.toHexString

@Composable
fun NewProductView(
    productToEdit: String? = null,
    onUpdateProfilePictureClicked: (() -> Unit)? = null,
    inputName: String? = null,
    onInputNameChanged: ((String) -> Unit)? = null,
    inputDescription: String? = null,
    onInputDescriptionChanged: ((String) -> Unit)? = null,
    inputBuyPrice: String? = null,
    onInputBuyPriceChanged: ((String) -> Unit)? = null,
    inputSellPrice: String? = null,
    onInputSellPriceChanged: ((String) -> Unit)? = null,
    inputSize: String? = null,
    onInputSizeChanged: ((String) -> Unit)? = null,
    onInputIsNumericSize: Boolean = false,
    onInputIsNumericSizeChanged: ((Boolean) -> Unit)? = null,
    inputColor: String? = null,
    onInputColorChanged: ((String) -> Unit)? = null,
    inputGender: String? = null,
    onInputGenderChanged: ((String) -> Unit)? = null,
    inputStock: String? = null,
    onInputStockChanged: ((String) -> Unit)? = null,
    inputColorCode: String? = null,
    onInputColorCodeChanged: ((String) -> Unit)? = null,
    onOpenScanCodeDialog: (() -> Unit)? = null,
    inputCode: String? = null,
    onInputCodeChanged: ((String) -> Unit)? = null,
    inputUrlImg: String? = null,
    onCreateProduct: (() -> Unit)? = null,
    onDeleteProduct: (() -> Unit)? = null,
    onUpdateProduct: ((String) -> Unit)? = null,
) {
    val listColorNames = stringArrayResource(R.array.colors_name_array)
    val listColorCode = stringArrayResource(R.array.colors_code_array)
    var sizeNumericPickerOpen by rememberSaveable { mutableStateOf(false) }
    var sizePickerOpen by rememberSaveable { mutableStateOf(false) }
    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    var genderPickerOpen by rememberSaveable { mutableStateOf(false) }
    var currentlySelected by rememberSaveable(saver = colourSaver()) {
        mutableStateOf(listColorCode[0].toColor())
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val urlImg = if (inputUrlImg.isNullOrEmpty()) null else inputUrlImg.toUri()
        val productName =
            if (inputName.isNullOrEmpty()) stringResource(R.string.add_product_title) else inputName

        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            urlImg?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                        .clickable {
                            onUpdateProfilePictureClicked?.invoke()
                        },
                    contentScale = ContentScale.FillWidth
                )
            } ?: run {

                Image(
                    painterResource(R.drawable.ic_vector_add_photo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                        .alpha(0.2f)
                        .clickable {
                            onUpdateProfilePictureClicked?.invoke()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentScale = ContentScale.Fit,
                )
            }


        }

        Spacer(Modifier.padding(vertical = 8.dp))
        TextDivider(text = productName, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(8.dp))

        Column {
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.add_product_name_detail),
                textValue = inputName ?: "",
                onTextChange = { onInputNameChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))
            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.add_product_description),
                textValue = inputDescription ?: "",
                onTextChange = { onInputDescriptionChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                InputTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.add_product_buy_price),
                    keyboardType = KeyboardType.Decimal,
                    textValue = inputBuyPrice.toString(),
                    onTextChange = { onInputBuyPriceChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
                InputTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    keyboardType = KeyboardType.Decimal,
                    textHint = stringResource(R.string.add_product_sell_price),
                    textValue = inputSellPrice.toString(),
                    onTextChange = { onInputSellPriceChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.add_product_size_numeric_title),
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
                textHint = stringResource(R.string.add_product_size),
                textValue = inputSize ?: "",
                enable = false
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                InputTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.add_product_color),
                    textValue = inputColor ?: "",
                    onTextChange = { onInputColorChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences,
                )
                IconButton(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .background(GrayDark, shape = CircleShape),
                    onClick = {
                        colorPickerOpen = true
                    }) {

                    GetBtnSelectColor(inputColor ?: "")
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

            InputTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        genderPickerOpen = true
                    },
                textHint = stringResource(R.string.add_product_gender),
                textValue = inputGender ?: "",
                enable = false
            )
            Spacer(modifier = Modifier.padding(8.dp))

            InputTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                textHint = stringResource(R.string.add_product_inventory),
                textValue = inputStock.toString(),
                keyboardType = KeyboardType.NumberPassword,
                onTextChange = { onInputStockChanged?.invoke(it) },
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                InputTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    textHint = stringResource(R.string.add_product_code),
                    textValue = inputCode ?: "",
                    onTextChange = { onInputCodeChanged?.invoke(it) },
                    capitalization = KeyboardCapitalization.Sentences,
                )
                IconButton(
                    modifier = Modifier
                        .graphicsLayer()
                        .padding(end = 24.dp)
                        .background(GrayDark, shape = CircleShape),

                    onClick = {
                        onOpenScanCodeDialog?.invoke()
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_vector_bar_code),
                        contentDescription = "close",
                        tint = White
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                verticalAlignment = Alignment.CenterVertically,
            ) {
                productToEdit?.let {
                    SecondaryButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textButton = stringResource(R.string.btn_delete),
                    ) {
                        onDeleteProduct?.invoke()

                    }
                }

                PrimaryButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textButton = productToEdit?.let {
                        stringResource(R.string.btn_update)

                    } ?: run { stringResource(R.string.btn_save) }) {

                    productToEdit?.let {
                        onUpdateProduct?.invoke(it)
                    } ?: run {
                        onCreateProduct?.invoke()
                    }


                }
            }
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
    if (sizeNumericPickerOpen) {
        PickerDialog(
            items = stringArrayResource(R.array.number_sizes_array),
            onDismiss = { sizeNumericPickerOpen = false },
            onValueSelected = {
                onInputSizeChanged?.invoke(it)
            }
        )
    }

    if (sizePickerOpen) {
        PickerDialog(
            items = stringArrayResource(R.array.sizes_array),
            onDismiss = { sizePickerOpen = false },
            onValueSelected = {
                onInputSizeChanged?.invoke(it)
            }
        )
    }

    if (genderPickerOpen) {
        PickerDialog(
            items = stringArrayResource(R.array.gender_array),
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
}

@Preview(showSystemUi = true)
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        NewProductView()
    }
}
