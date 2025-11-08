package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R

@Composable
fun Loader(
    linearIndicator: Boolean = true,
    msgLoader: String = stringResource(R.string.msg_process)
) {

    if (linearIndicator) {
        Column(modifier = Modifier.fillMaxSize()) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = msgLoader,
                modifier = Modifier.padding(24.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,

                )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun LoaderPreview() {
    Loader(false)
}