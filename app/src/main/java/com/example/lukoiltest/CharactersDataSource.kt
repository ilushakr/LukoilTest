package com.example.lukoiltest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.usecases.list.DataSourceUseCase
import com.example.domain.apiobjects.CharacterListItem
import com.example.domain.appobjects.AppResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharactersDataSource(private val firstPage: String) :
    PagingSource<String, CharacterListItem>(),
    KoinComponent {

    private val useCase: DataSourceUseCase by inject()

    override var keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<String, CharacterListItem>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CharacterListItem> {

        val nextUrl = params.key ?: firstPage

        return try {
            when (val result = useCase.execute(nextUrl)) {
                is AppResult.Error -> LoadResult.Error(result.error)
                is AppResult.Success -> {
                    LoadResult.Page(
                        data = result.data.results,
                        nextKey = result.data.info.next,
                        prevKey = result.data.info.prev,
                    )
                }
            }

        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }

}