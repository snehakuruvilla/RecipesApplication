package com.sk.recipe.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
public interface BaseMapper <D,E> {

	E fromDtoToEntity(D dto);

    D fromEntityToDto(E entity);

    List<E> fromDtoListToEntityList(List<D> dtoList);

    List<D> fromEntityListToDtoList(List<E> entityList);

}
