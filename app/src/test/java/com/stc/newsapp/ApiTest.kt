import com.stc.newsapp.Utils
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.presentation.home.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ApiTest {

    private lateinit var viewModel: NewsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var newsAPIService: NewsAPIService

    @Mock
    private lateinit var db: NewsDB


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = NewsViewModel(newsAPIService,db)
    }

    @Test
    fun getNewsDataFromAPIandCheckSize() = testScope.runTest {
        val newsDataResponse = Utils().getMockNewsResponse()
        Mockito.lenient().`when`(newsAPIService.getLatestHeadLines(page = 1)).thenReturn(newsDataResponse)
        coroutineScope {
            assert(
                newsAPIService.getLatestHeadLines(page = 1).newsResponse.size ==6
            )
        }
    }

    @Test
    fun getNewsDataFromAPIandCheckEmptyList() = testScope.runTest {
        val newsDataResponse = Utils().getMockNewsResponseEmpty()
        Mockito.lenient().`when`(newsAPIService.getLatestHeadLines(page = 1)).thenReturn(newsDataResponse)
        coroutineScope {
            assert(newsAPIService.getLatestHeadLines(page = 1).newsResponse.isEmpty())
        }
    }
}