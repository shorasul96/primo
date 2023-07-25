package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Unit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnitDTO implements Serializable {

    private Long id;

    private String measurement;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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
        if (!(o instanceof UnitDTO)) {
            return false;
        }

        UnitDTO unitDTO = (UnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, unitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitDTO{" +
            "id=" + getId() +
            ", measurement='" + getMeasurement() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
