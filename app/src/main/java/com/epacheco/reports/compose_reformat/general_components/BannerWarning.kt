package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.YellowColor


@Composable
fun ModernProfileWarningBanner(
    title: String = "",
    subtitle: String = "",
    icon: Int = R.drawable.ic_info,
    onVerifyEmailClick: (() -> Unit)? = null,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(modifier = Modifier.clickable {
            onVerifyEmailClick?.invoke()
        }) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Contenedor de icono con indicador de alerta flotante (estilo burbuja moderna)
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFFEFEFF4), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = RedDark,
                        modifier = Modifier.size(22.dp)
                    )

                    // Punto indicador de advertencia minimalista en la esquina superior
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(RedDark, CircleShape)
                            .border(1.5.dp, Color.White, CircleShape)
                            .align(Alignment.TopEnd)
                    )
                }

                // Textos descriptivos optimizados
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold, // Ajustable a FontWeight.SemiBold
                        letterSpacing = (-0.2).sp
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = (-0.1).sp
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ModernProfileWarningBannerPreview() {
    ReportsGoTheme(darkTheme = true) {
        ModernProfileWarningBanner(title = "titulo", subtitle = "Subtitlo")
    }
}