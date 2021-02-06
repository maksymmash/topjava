package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> list = new ArrayList<>();
        int mealCalories;
        for (UserMeal meal : meals) {
            mealCalories = meal.getCalories();
            LocalDateTime dateTime = meal.getDateTime();
            for (UserMeal userMeal : meals) {
                if (dateTime.toLocalDate().equals(userMeal.getDateTime().toLocalDate()) && !dateTime.equals(userMeal.getDateTime())) {
                    mealCalories += userMeal.getCalories();
                }
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean excees = mealCalories > caloriesPerDay;
                list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excees));
            }
        }
//        List<UserMealWithExcess> list = new ArrayList<>();
//        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
//
//        for (UserMeal meal : meals) {
//            if (!map.containsKey(meal.getDateTime().toLocalDate())) {
//                List<UserMeal> listOfMeals = new ArrayList<>();
//                listOfMeals.add(meal);
//                map.put(meal.getDateTime().toLocalDate(), listOfMeals);
//            } else {
//                List<UserMeal> userMeals = map.get(meal.getDateTime().toLocalDate());
//                userMeals.add(meal);
//                map.put(meal.getDateTime().toLocalDate(), userMeals);
//            }
//        }
//
//        int calories = 0;

//        for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
//            List<UserMeal> value = entry.getValue();
//            for (UserMeal userMeal : value) {
//                calories += userMeal.getCalories();
//            }
//        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, List<UserMeal>> map = meals.stream()
                .collect(Collectors.groupingBy(e -> e.getDateTime().toLocalDate()));

        System.out.println(map);
        Stream.of(map)
                .flatMap(e -> e.values().stream().map(l -> l.stream().map(c->c.getCalories()).reduce(0, (s1, s2) -> s1 + s2))).filter(y->y>caloriesPerDay);


        return null;
    }
}
