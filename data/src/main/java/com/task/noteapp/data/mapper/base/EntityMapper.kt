package com.task.noteapp.data.mapper.base

interface EntityMapper<Entity, Domain> {

    fun mapToDomain(entity: Entity): Domain
    fun mapToEntity(domain: Domain): Entity

    fun mapToEntityList(models: List<Domain>): List<Entity> {
        return models.mapTo(mutableListOf(), ::mapToEntity)
    }

    fun mapToDomainList(entities: List<Entity>): List<Domain> {
        return entities.mapTo(mutableListOf(), ::mapToDomain)
    }
}
