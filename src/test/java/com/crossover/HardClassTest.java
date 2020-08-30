package com.crossover;

import com.crossover.services.ExternalRatingApprovalService;
import com.crossover.services.NotificationService;
import com.crossover.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@ExtendWith(MockitoExtension.class)
@PrepareForTest(fullyQualifiedNames = "com.crossover.HardClass")
public class HardClassTest {

    private static final String MOCK = "MOCK";
    private static final String TOP = "TOP+";
    private static final String CACHED = "-CACHED";
    private static final String HIGH = "HIGH=";
    private static final String HARD_CACHE = "HARD_CACHE";
    private static final String NOT_APP = "NOT-APP";
    private static final String LOW = "LOW-";
    private static final int MIN_NUMBER_OF_INVOCATIONS = 1;

    @InjectMocks
    private HardClass testClass;

    @Mock
    private ExternalRatingApprovalService externalRatingApprovalService;

    @Mock
    private Utils utils;

    @Mock
    private NotificationService notificationService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(HardClass.class, "UTILS", utils);
        when(utils.getRatingDecoration()).thenReturn(MOCK);
        when(externalRatingApprovalService.isApproved(anyInt())).thenReturn(true);
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
    public void givenRejectedRatingWhenCreateRatingStringThenNoApp() {
        // Arrange
        int rating = 1;
        int ratingCeiling = 1;
        when(externalRatingApprovalService.isApproved(anyInt())).thenReturn(false);

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(NOT_APP, actual);
        verify(externalRatingApprovalService, atLeast(rating)).isApproved(rating);
    }

    @Test
    public void givenApprovedRatingWhenCreateRatingStringThenTop() {
        // Arrange
        int rating = 2;
        int ratingCeiling = 2;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(TOP + rating + MOCK, actual);
        verify(externalRatingApprovalService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).isApproved(rating);
        verify(notificationService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).notify(rating);
    }

    @Test
    public void givenApprovedRatingEqualHardCashWhenCreateRatingStringThenTopCash() {
        // Arrange
        int rating = 2;
        int ratingCeiling = 2;
        Whitebox.setInternalState(HardClass.class, HARD_CACHE, rating);

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(TOP + rating + CACHED + MOCK, actual);
        verify(externalRatingApprovalService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).isApproved(rating);
        verify(notificationService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).notify(rating);
    }

    @Test
    public void givenRatingGraterThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 3;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(HIGH + rating + MOCK, actual);
        verify(externalRatingApprovalService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).isApproved(rating);
        verify(notificationService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).notify(rating);
    }

    @Test
    public void givenRatingEqualsThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 2;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(HIGH + rating + MOCK, actual);
        verify(externalRatingApprovalService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).isApproved(rating);
        verify(notificationService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).notify(rating);
    }

    @Test
    public void givenRatingLessThanMidCeilingWhenCreateRatingStringThenHigh() {
        // Arrange
        int rating = 1;
        int ratingCeiling = 4;

        // Act
        String actual = testClass.createRatingString(rating, ratingCeiling);

        // Assert
        Assert.assertEquals(LOW + rating + MOCK, actual);
        verify(externalRatingApprovalService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).isApproved(rating);
        verify(notificationService, atLeast(MIN_NUMBER_OF_INVOCATIONS)).notify(rating);
    }
}
