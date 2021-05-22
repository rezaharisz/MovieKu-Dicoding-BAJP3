package com.rezaharis.movieku.utils

import androidx.paging.PagedList
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object PagedListUtil {
    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}