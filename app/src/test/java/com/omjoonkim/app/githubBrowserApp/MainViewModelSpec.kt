package com.omjoonkim.app.githubBrowserApp

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.omjoonkim.app.githubBrowserApp.di.KoinSpek
import com.omjoonkim.app.githubBrowserApp.di.test_module
import com.omjoonkim.app.githubBrowserApp.viewmodel.MainViewModel
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetUserData
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals

object MainViewModelSpec : KoinSpek({

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

    val viewModel: MainViewModel by inject()
    val getUserData: GetUserData by inject()

    Feature("MainViewModel spec") {

        Scenario("normally refreshList") {
            When("User enter the screen") {
                viewModel.input.searchedUserName("omjoonkim")
            }
            Then("should normally data setting finish") {
                assertEquals(
                    getUserData.get("omjoonkim").blockingGet(), viewModel.output.refreshListData().value
                )
            }
        }

        Scenario("on clicked user in viewHolder") {
            Given("setting data") {
                viewModel.input.searchedUserName("omjoonkim")
            }
            Then("should normally data setting finish") {
                assertEquals(
                    getUserData.get("omjoonkim").blockingGet(), viewModel.output.refreshListData().value
                )
            }
            When("user clicked") {
                viewModel.input.onClickUser(
                    viewModel.output.refreshListData().value?.first ?: throw IllegalStateException()
                )
            }
            Then("should going profile activity") {
                assertEquals(
                    viewModel.output.refreshListData().value?.first?.name ?: throw IllegalStateException(),
                    viewModel.output.goProfileActivity().value
                )
            }
        }

        Scenario("on clicked home button") {
            When("clicked home button") {
                viewModel.input.onClickHomeButton()
            }
            Then("should finish activity") {
                assertEquals(
                    Unit,
                    viewModel.output.finish().value
                )
            }
        }
    }
})
