package com.rahil.newspoc.domain.interactor.news

import io.reactivex.Flowable
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.interactor.FlowableUseCase
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [News] instances from the [NewsRepository]
 */
open class GetCnnNews @Inject constructor(val newsRepository: NewsRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<News>, String?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Flowable<List<News>> {
        return newsRepository.getNewsList(params.toString())
    }

}