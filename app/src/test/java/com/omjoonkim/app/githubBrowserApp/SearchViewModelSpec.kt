package com.omjoonkim.app.githubBrowserApp

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.omjoonkim.app.githubBrowserApp.di.KoinSpek
import com.omjoonkim.app.githubBrowserApp.di.test_module
import com.omjoonkim.app.githubBrowserApp.viewmodel.SearchViewModel
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals

object SearchViewModelSpec : KoinSpek({

    beforeEachTest {
        startKoin(test_module)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    afterEachTest {
        ArchTaskExecutor.getInstance().setDelegate(null)
        stopKoin()
    }

    val viewModel: SearchViewModel by inject()

    Feature("SearchViewModel spec") {
        val name = "omjoonkim"
        Scenario("빈값이 아닌 이름을 입력 받는다") {
            When("${name}을 입력 받았을 때") {
                viewModel.input.name(name)
            }
            Then("검색 버튼이 활성화 되어 있어야 한다") {
                assertEquals(true, viewModel.output.state().value?.enableSearchButton)
            }
        }

        Scenario("빈값의 이름을 입력 받는다") {
            When("빈값의 이름을 입력 받았을 때") {
                viewModel.input.name("")
            }
            Then("검색 버튼은 비활성화 되어 있어야 한다") {
                assertEquals(false, viewModel.output.state().value?.enableSearchButton)
            }
        }

        Scenario("검색 버튼을 클릭 한다") {
            Given("${name}이 입력되어 있다.") {
                viewModel.input.name(name)
            }
            When("검색 버튼을 클릭 하였을 때") {
                viewModel.input.clickSearchButton()
            }
            Then("결과 화면으로 이동한다") {
                assertEquals(name, viewModel.output.goResultActivity().value)
            }
        }
    }
})
