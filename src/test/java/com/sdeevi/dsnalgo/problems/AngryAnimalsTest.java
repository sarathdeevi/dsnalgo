package com.sdeevi.dsnalgo.problems;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AngryAnimalsTest {

    @Test
    public void angryAnimals() {
        assertThat(AngryAnimals.angryAnimals(8, Arrays.asList(1, 2, 2, 3), Arrays.asList(2, 4, 1, 7)), is(22L));
        assertThat(AngryAnimals.angryAnimals(8, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(2, 3, 4, 5, 6, 7, 8, 1)), is(8L));
        assertThat(AngryAnimals.angryAnimals(8, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1)), is(20L));
        assertThat(AngryAnimals.angryAnimals(13, Arrays.asList(1, 2, 2, 7, 4, 6), Arrays.asList(4, 4, 1, 8, 10, 13)), is(39L));
        assertThat(AngryAnimals.angryAnimals(13, Arrays.asList(1, 2, 2, 6, 4, 6), Arrays.asList(4, 4, 1, 7, 10, 13)), is(41L));
        assertThat(AngryAnimals.angryAnimals(13, Arrays.asList(1, 2, 2, 10, 4, 6), Arrays.asList(4, 4, 1, 3, 10, 13)), is(59L));
        assertThat(AngryAnimals.angryAnimals(5, Arrays.asList(1, 2), Arrays.asList(4, 4)), is(11L));
        assertThat(AngryAnimals.angryAnimals(3, Arrays.asList(1, 2, 3), Arrays.asList(3, 3, 1)), is(4L));
        assertThat(AngryAnimals.angryAnimals(3, Collections.emptyList(), Collections.emptyList()), is(6L));
        assertThat(AngryAnimals.angryAnimals(1, Collections.emptyList(), Collections.emptyList()), is(1L));
    }

    @Test
    public void angryAnimalsBruteForce() {
        assertThat(AngryAnimals.angryAnimalsBruteForce(8, Arrays.asList(1, 2, 2, 3), Arrays.asList(2, 4, 1, 7)), is(22L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(8, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1)), is(20L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(13, Arrays.asList(1, 2, 2, 7, 4, 6), Arrays.asList(4, 4, 1, 8, 10, 13)), is(39L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(13, Arrays.asList(1, 2, 2, 6, 4, 6), Arrays.asList(4, 4, 1, 7, 10, 13)), is(41L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(13, Arrays.asList(1, 2, 2, 10, 4, 6), Arrays.asList(4, 4, 1, 3, 10, 13)), is(59L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(5, Arrays.asList(1, 2), Arrays.asList(4, 4)), is(11L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(3, Arrays.asList(1, 2, 3), Arrays.asList(3, 3, 1)), is(4L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(3, Collections.emptyList(), Collections.emptyList()), is(6L));
        assertThat(AngryAnimals.angryAnimalsBruteForce(1, Collections.emptyList(), Collections.emptyList()), is(1L));
    }
}