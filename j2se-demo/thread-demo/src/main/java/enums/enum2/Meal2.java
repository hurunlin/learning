/**
 * Project Name: learning
 * File Name: Meal2.java
 * Package Name: enums.enum2
 * Date: 2017-06-19 18:01
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package enums.enum2;

/**
 * ClassName: Meal2 <br>
 * Function: <br>
 * Date:  2017-06-19 18:01 <br>
 */
public enum Meal2 {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;

    private Meal2(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public interface Food {
        enum Appetizer implements Food {
            SALAD, SOUP, SPRING_ROLLS;
        }

        enum MainCourse implements Food {
            LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMOUS, VINDALOO;
        }

        enum Dessert implements Food {
            TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL;
        }

        enum Coffee implements Food {
            BLACK_COFFEE, DECAF_COFFEE, ESPRESSO, LATTE, CAPPUCCINO, TEA, HERB_TEA;
        }
    }

    public Food randomSelection() {
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Meal2 meal : Meal2.values()) {
                Food food = meal.randomSelection();
                System.out.println(food);
            }
            System.out.println("---");
        }
    }
}
