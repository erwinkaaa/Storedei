package id.wendei.store.data.remote.mapper

import id.wendei.store.domain.model.Product
import id.wendei.store.domain.model.Rating
import id.wendei.store.data.remote.response.ProductResponse
import id.wendei.store.data.remote.response.RatingResponse

fun RatingResponse.toModel(): Rating = Rating(
    rate = rate,
    count = count
)

fun ProductResponse.toModel(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toModel()
)

fun List<ProductResponse>.toModels(): List<Product> = this.map { it.toModel() }