import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;

@RunWith(Parameterized.class)

public class ParameterizedIngredientTest {


    private final IngredientType type;
    private final String name;
    private final float price;

    public ParameterizedIngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }


    @Parameterized.Parameters
    public static Object[][] dataParametersIngredient() {
        return new Object[][]{
                {SAUCE, "Страшная", 500},
                {FILLING, "Новая", 0},
                {null, null, 500F},
        };
    }

    @Test
    public void checkReactionInParametersType() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals(ingredient.getType(), type);
    }

    @Test
    public void checkReactionInParametersName() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals(ingredient.getName(), name);
    }

    @Test
    public void checkReactionInParametersPrice() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals(ingredient.getPrice(), price, 0);
    }
}
