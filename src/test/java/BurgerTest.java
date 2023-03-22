import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bun;
    @Mock
    Ingredient ingredient;
    @Mock
    Ingredient movableIngredient;
    @Mock
    Ingredient ingredientAdditional;

    Database database = new Database();
    List<Bun> buns = database.availableBuns();
    Burger burger = new Burger();
    List<Ingredient> ingredients = database.availableIngredients();


    private Ingredient ingredient_3 = new Ingredient(IngredientType.SAUCE, "cosmo", 100500F);

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void checkingSetBunsName() {
        burger.setBuns(bun);
        Mockito.when(bun.getName()).thenReturn("Name");

        String expectedResult = "Name";
        assertEquals(expectedResult, burger.bun.getName());
    }

    @Test
    public void checkingAddIngredient() {
        int expectedIngredientsCount = 1;
        burger.addIngredient(ingredients.get(0));

        assertEquals(expectedIngredientsCount,burger.ingredients.size());
    }

    @Test
    public void checkRemoveIngredientsInBurger() {
        int expectedIngredientsCount = 0;
        burger.addIngredient(ingredients.get(3));
        burger.removeIngredient(0);

        assertEquals(expectedIngredientsCount, burger.ingredients.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkRemoveMissIngredientsInBurger() {
        burger.setBuns(buns.get(0));
        burger.removeIngredient(0);
    }

    @Test
    public void moveIngredientTest() {
        burger.ingredients.add(ingredient);
        burger.ingredients.add(movableIngredient);
        List<Ingredient> newList = new ArrayList<>(Arrays.asList(movableIngredient, ingredient));

        burger.moveIngredient(0, 1);
        Assert.assertEquals(newList, burger.ingredients);
    }


    @Test
    public void checkingBunsPrice() {
        burger.setBuns(bun);

        Mockito.when(bun.getPrice()).thenReturn(100500F);

        float expectedResult = 100500F;
        assertEquals(expectedResult, burger.bun.getPrice(), 0.0F);
    }

    @Test
    public void checkingGetPrice() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient_3);

        Mockito.when(bun.getPrice()).thenReturn(100F);

        float expectedResult = 100700F;
        float actualResult = burger.getPrice();
        assertEquals(expectedResult, actualResult, 0.0F);
    }

    @Test
    public void getReceiptTest() {
        burger.setBuns(bun);
        burger.ingredients.add(ingredient);
        burger.ingredients.add(ingredientAdditional);

        Mockito.when(bun.getName()).thenReturn("Harrys");
        Mockito.when(bun.getPrice()).thenReturn(50f);
        Mockito.when(ingredient.getName()).thenReturn("1000_островов");
        Mockito.when(ingredient.getPrice()).thenReturn(30f);
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);

        Mockito.when(ingredientAdditional.getName()).thenReturn("Лук");
        Mockito.when(ingredientAdditional.getPrice()).thenReturn(20f);
        Mockito.when(ingredientAdditional.getType()).thenReturn(IngredientType.FILLING);

        String expectedReceipt = String.format("(==== %s ====)%n", bun.getName()) +
                String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(), ingredient.getName()) +
                String.format("= %s %s =%n", ingredientAdditional.getType().toString().toLowerCase(), ingredientAdditional.getName()) +
                String.format("(==== %s ====)%n", bun.getName()) +
                String.format("%nPrice: %f%n", burger.getPrice());
        Assert.assertEquals(expectedReceipt, burger.getReceipt());
    }
}
