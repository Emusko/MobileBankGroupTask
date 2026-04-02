package az.mobile.bankgroup.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import az.mobile.bankgroup.task.ui.theme.MobilBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobilBankTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MobilBankNavHost()
                }
            }
        }
    }
}
