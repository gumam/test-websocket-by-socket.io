package ru.tradernet.data

import ru.tradernet.data.api.model.RepositoryApiModel

fun RepositoryApiModel.toRepository(commitsCount: Int? = null): Repository {
    return Repository(
        id,
        name,
        description,
        language,
        starsCount,
        forksCount,
        commitsCount,
        owner.login,
        owner.avatarUrl
    )
}