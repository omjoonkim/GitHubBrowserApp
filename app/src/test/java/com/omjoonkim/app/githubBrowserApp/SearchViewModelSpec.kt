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

        Scenario("input not empty name") {
            When("name is beomjoon") {
                viewModel.input.name("beomjoon")
            }
            Then("should output searchButtonEnable is true") {
                assertEquals(true, viewModel.output.state().value?.enableSearchButton)
            }
        }

        Scenario("input empty name") {
            When("name is Empty") {
                viewModel.input.name("")
            }
            Then("should output searchButtonEnable is false") {
                assertEquals(false, viewModel.output.state().value?.enableSearchButton)
            }
        }

        Scenario("input not empty name") {
            When("name is Empty") {
                viewModel.input.name("park")
            }
            Then("should output searchButtonEnable is true") {
                assertEquals(true, viewModel.output.state().value?.enableSearchButton)
            }
        }

        Scenario("click searchButton when name not empty") {
            Given("name is beomjoon") {
                viewModel.input.name("beomjoon")
            }
            When("clicked searchButton") {
                viewModel.input.clickSearchButton()
            }

            Then("goResultsssActivity.value == beomjoon ") {
                assertEquals("beomjoon", viewModel.output.goResultActivity().value)
            }
        }

        Scenario("click searchButton when name empty") {
            Given("name is empty") {
                viewModel.input.name("")
            }
            When("clicked searchButton") {
                viewModel.input.clickSearchButton()
            }

            Then("nothing happend") {
                assertEquals("", viewModel.output.goResultActivity().value)
            }
        }
    }
})
