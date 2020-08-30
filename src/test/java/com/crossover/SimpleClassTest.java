package com.crossover;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimpleClassTest {

    private static final String TOP = "TOP+";
    private static final String HIGH = "HIGH=";
    private static final String LOW = "LOW-";

    private SimpleClass testClass = new SimpleClass();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenRatingGraterThanRatingCeilingWhenCreateRatingStringThenExceptionThrown() {
        // Act & Assert
        int rating = 2;
        int ratingCeiling = 1;
        assertThatThrownBy(() -> testClass.createRatingString(rating, ratingCeiling))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("Cannot be over the hard ceiling");
    }

    @Test
    public void givenApprovedRatingWhenCreateRatingStringThenTop() {
        // Arrange
        int rating = 2;
        int ratingCeiling = 2;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(TOP + rating , actual);
    }

    @Test
    public void givenRatingGraterThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 3;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(HIGH + rating , actual);
    }

    @Test
    public void givenRatingEqualsThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 2;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(HIGH + rating , actual);
    }

    @Test
    public void givenRatingLessThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 1;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(LOW + rating , actual);
    }
}
