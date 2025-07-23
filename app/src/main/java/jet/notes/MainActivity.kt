package jet.notes

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val lists = listOf(
        "The 'economic boom' that only benefits the top 0.1%.",
        "Politician's promise tracker: Still at 0% fulfillment since last election.",
        "Latest government 'initiative' is just a re-branding of an old failure.",
        "Experts confirm: Public trust in *anything* said by officials is at an all-time low.",
        "They're 'listening to the people,' as long as the people agree with them.",
        "New bill passed without reading; nobody's surprised, nobody cares.",
        "Remember to stock up on cynicism; the news cycle requires it.",
        "The 'transparent' report conveniently omits all the inconvenient truths.",
        "Another 'urgent' crisis that will be 'addressed' after the next holiday.",
        "Their 'bold new vision' involves doubling down on proven mistakes.",
        "Official statement: 'Mistakes were made,' by someone else, obviously.",
        "The 'unity' rhetoric is loudest just before they stab each other in the back.",
        "Public debate: Two sides yelling past each other, no actual discussion.",
        "My social media feed is more productive than parliamentary sessions.",
        "Just another day of pretending we have real choices at the ballot box."
    )

    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
       // contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            
            LazyColumn {
                items(lists) {
                    list ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = list,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "23 July 2025",
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}
