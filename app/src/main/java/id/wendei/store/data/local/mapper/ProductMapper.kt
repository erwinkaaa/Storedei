package id.wendei.store.data.local.mapper

import id.wendei.store.data.local.entity.ProductEntity
import id.wendei.store.domain.model.Product

fun ProductEntity.toModel(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating
)

fun List<ProductEntity>.toModels(): List<Product> = this.map { it.toModel() }

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating
)