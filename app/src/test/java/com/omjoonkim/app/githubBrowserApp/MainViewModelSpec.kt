package com.omjoonkim.app.githubBrowserApp

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.omjoonkim.app.githubBrowserApp.di.KoinSpek
import com.omjoonkim.app.githubBrowserApp.di.test_module
import com.omjoonkim.app.githubBrowserApp.viewmodel.MainViewModel
import com.omjoonkim.project.githubBrowser.domain.entity.User
import com.omjoonkim.project.githubBrowser.domain.interactor.usecases.GetUserData
import org.koin.core.parameter.parametersOf
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

    lateinit var userName: String
    val viewModel: MainViewModel by inject { parametersOf(userName) }
    val getUserData: GetUserData by inject()

    Feature("MainViewModel spec") {
        Scenario("유저가 화면에 들어오면 검색한 유저의 프로필,저장소 데이터가 정상적으로 보여야 한다") {
            Given("검색하려는 유저의 이름은 omjoonkim이다"){
                userName = "omjoonkim"
            }
            Then("화면에 검색한 유저의 데이터가 정상적으로 나타난다") {
                assertEquals(
                    getUserData.get(userName).blockingGet(),
                    viewModel.output.refreshListData().value
                )
            }
        }
        Scenario("유저 프로필을 클릭하면 유저의 프로필 화면으로 이동되어야 한다") {
            When("프로필을 클릭 했을 때") {
                viewModel.input.clickUser(
                    viewModel.output.refreshListData().value?.first
                        ?: throw IllegalStateException()
                )
            }
            Then("해당 유저의 프로필 화면으로 이동 된다") {
                assertEquals(
                    viewModel.output.refreshListData().value?.first?.name
                        ?: throw IllegalStateException(),
                    viewModel.output.goProfileActivity().value
                )
            }
        }
        Scenario("홈버튼을 클릭하면 화면이 정상적으로 종료되어야 한다.") {
            When("홈버튼을 클릭 했을 때") {
                viewModel.input.clickHomeButton()
            }
            Then("화면이 정상적으로 종료 된다.") {
                assertEquals(
                    Unit,
                    viewModel.output.finish().value
                )
            }
        }
    }
})
