import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kaesik.core.presentation.designsystem.RuniqueTheme
import com.kaesik.run.presentation.run_overview.RunOverviewAction
import com.kaesik.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.compose.koinViewModel

@Composable

fun RunOverviewScreenRoot(
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = viewModel::onAction
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {

}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
     RuniqueTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}
