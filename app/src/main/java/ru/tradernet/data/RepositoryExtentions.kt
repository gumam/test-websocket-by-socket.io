package ru.tradernet.data

import ru.tradernet.domain.model.TikerInfoModel

fun TikerApiModel.toTiker(): TikerInfoModel {
    return TikerInfoModel(
        name
    )
}