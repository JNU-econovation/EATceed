package com.gaebaljip.exceed.model.dto.response

data class FoodListPagingResponseDTO(
    val content: List<FoodNameAndId>,
    val pageable: PagingInfo,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: SortInfo,
    val numberOfElement: Int,
    val empty: Boolean,
)

data class FoodNameAndId(
    val id: Int,
    val name: String,
)

data class PagingInfo(
    val sort: SortInfo,
    val offset: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class SortInfo(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)