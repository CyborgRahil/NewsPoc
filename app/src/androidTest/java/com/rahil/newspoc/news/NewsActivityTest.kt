package com.rahil.newspoc.news

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rahil.newspoc.R
import io.reactivex.Flowable
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.test.TestApplication
import com.rahil.newspoc.test.util.DataFactory
import com.rahil.newspoc.ui.news.NewsActivity
import com.rahil.newspoc.test.util.RecyclerViewMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.rahil.newspoc.presentation.utility.NewsConstant

@RunWith(AndroidJUnit4::class)
class NewsActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<NewsActivity>(NewsActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubNewsRepositoryGetNewss(Flowable.just(makeNewsList(2)))
        activity.launchActivity(null)
    }

    @Test
    fun newsDisplay() {
        val news = makeNewsList(1)
        stubNewsRepositoryGetNewss(Flowable.just(news))
        activity.launchActivity(null)

        checkNewsDetailsDisplay(news[0], 0)
    }

    @Test
    fun spinnerVisibility(){
        val news = makeNewsList(1)
        stubNewsRepositoryGetNewss(Flowable.just(news))
        activity.launchActivity(null)
        Espresso.onView(withId(R.id.newsSpinnerList)).check(matches(isDisplayed()));
    }

    @Test
    fun newsAreScrollable() {
        val news = makeNewsList(20)
        stubNewsRepositoryGetNewss(Flowable.just(news))
        activity.launchActivity(null)

        news.forEachIndexed { index, news ->
            onView(withId(R.id.recyclerNews)).perform(RecyclerViewActions.
                    scrollToPosition<RecyclerView.ViewHolder>(index))
            checkNewsDetailsDisplay(news, index) }
    }

    private fun checkNewsDetailsDisplay(news: News, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerNews).atPosition(position))
                .check(matches(hasDescendant(withText(news.title))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerNews).atPosition(position))
                .check(matches(hasDescendant(withText(news.title))))
    }

    private fun stubNewsRepositoryGetNewss(single: Flowable<List<News>>) {
        whenever(TestApplication.appComponent().newsRepository().getNewsList(NewsConstant.BBC))
                .thenReturn(single)
    }

    private fun makeNewsList(count: Int): List<News> {
        val news = mutableListOf<News>()
        repeat(count) {
            news.add(makeNewsModel())
        }
        return news
    }

    private fun makeNewsModel(): News {
        return News(DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid())
    }

}