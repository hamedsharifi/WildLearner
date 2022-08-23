package ir.haytech.core.testutils



import com.wildlearner.core.common.CoroutineDispatcherProviderCore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class MainCoroutineRuleCore : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
                testCoroutineDispatcher.cleanupTestCoroutines()
            }

        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testCoroutineDispatcher.runBlockingTest(block)
    }

    fun getDispatcher(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    fun getScope(): CoroutineScope {
        return testCoroutineScope
    }
}

fun createTestDispatcherProvider(coroutineDispatcher: CoroutineDispatcher): CoroutineDispatcherProviderCore =
    object : CoroutineDispatcherProviderCore {
        override fun bgDispatcher() = coroutineDispatcher
        override fun ioDispatcher() = coroutineDispatcher
        override fun uiDispatcher() = coroutineDispatcher
    }

fun CoroutineDispatcher.asDispatcherProvider(): CoroutineDispatcherProviderCore =
    createTestDispatcherProvider(this)