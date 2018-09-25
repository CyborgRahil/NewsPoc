package com.rahil.newspoc.domain.interactor.news

import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.interactor.FlowableUseCase
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetNyTimeNews @Inject constructor(val newsRepository: NewsRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<News>, String?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Flowable<List<News>> {
        return newsRepository.getNewsList(params!!)
    }

}