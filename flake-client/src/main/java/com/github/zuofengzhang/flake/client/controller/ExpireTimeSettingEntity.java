package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.REPETITION_TYPE;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author averyzhang
 */
public class ExpireTimeSettingEntity implements Serializable {
    public static final int DEFAULT_DAY_VALUE = -1;
    public static final int DEFAULT_TIME_VALUE = -1;
    private final SimpleIntegerProperty expirationDay = new SimpleIntegerProperty(DEFAULT_DAY_VALUE);
    private final SimpleIntegerProperty expirationTime = new SimpleIntegerProperty(DEFAULT_TIME_VALUE);

    private final SimpleObjectProperty<REPETITION_TYPE> repetition = new SimpleObjectProperty<>(REPETITION_TYPE.NONE);

    public ExpireTimeSettingEntity(Builder builder) {
        setExpirationDay(builder.expirationDay);
        setExpirationTime(builder.expirationTime);
        setRepetition(builder.repetition);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getExpirationDay() {
        return expirationDay.get();
    }

    public Integer getExpirationTime() {
        return expirationTime.get();
    }

    public REPETITION_TYPE getRepetition() {
        return repetition.get();
    }

    public void setExpirationDay(Integer value) {
        expirationDay.set(value);
    }

    public void setExpirationTime(Integer value) {
        expirationTime.set(value);
    }

    public void setRepetition(REPETITION_TYPE value) {
        repetition.set(value);
    }

    public SimpleIntegerProperty expirationTime() {
        return expirationTime;
    }

    public SimpleIntegerProperty expirationDay() {
        return expirationDay;
    }

    public SimpleObjectProperty<REPETITION_TYPE> repetition() {
        return repetition;
    }

    public static class Builder {
        private Integer expirationDay;
        private Integer expirationTime;
        private REPETITION_TYPE repetition;

        public Builder expirationDay(Integer value) {
            this.expirationDay = value;
            return this;
        }

        public Builder expirationTime(Integer value) {
            this.expirationTime = value;
            return this;
        }

        public Builder repetition(REPETITION_TYPE value) {
            this.repetition = value;
            return this;
        }

        public ExpireTimeSettingEntity build() {
            return new ExpireTimeSettingEntity(this);
        }
    }

    @Override
    public String toString() {
        return "ExpireTimeSettingEntity{" +
                "expirationDay=" + expirationDay +
                ", expirationTime=" + expirationTime +
                ", repetition=" + repetition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpireTimeSettingEntity that = (ExpireTimeSettingEntity) o;
        return expirationDay.equals(that.expirationDay) && expirationTime.equals(that.expirationTime) && repetition.equals(that.repetition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expirationDay, expirationTime, repetition);
    }
}
