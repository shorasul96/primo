package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.ManufactureStage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ManufactureStageDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManufactureStageDTO)) {
            return false;
        }

        ManufactureStageDTO manufactureStageDTO = (ManufactureStageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, manufactureStageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManufactureStageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
