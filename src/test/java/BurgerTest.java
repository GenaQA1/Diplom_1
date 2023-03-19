import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bun;

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
    public void checkAddIngredientsInBurger() {
        burger.setBuns(buns.get(0));
        burger.addIngredient(ingredients.get(2));

        String expected = "(==== black bun ====)\n" +
                "= sauce chili sauce =\n" +
                "(==== black bun ====)\n" +
                "\n" +
                "Price: 500,000000\n";

        assertEquals(expected, burger.getReceipt());
    }

    @Test
    public void checkRemoveIngredientsInBurger() {
        burger.setBuns(buns.get(0));
        burger.addIngredient(ingredients.get(3));
        burger.removeIngredient(0);

        String expected = "(==== black bun ====)\n" +
                "(==== black bun ====)\n" +
                "\n" +
                "Price: 200,000000\n";

        assertEquals(expected, burger.getReceipt());
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void checkRemoveMissIngredientsInBurger() {
        burger.setBuns(buns.get(0));
        burger.removeIngredient(0);
    }

    @Test
    public void checkMoveIngredientsInBurger() {
        burger.setBuns(buns.get(1));
        burger.addIngredient(ingredients.get(4));
        burger.addIngredient(ingredients.get(1));
        burger.addIngredient(ingredients.get(3));

        burger.moveIngredient(0, 1);

        assertEquals(burger.getReceipt(), "(==== white bun ====)\n" +
                "= sauce sour cream =\n" +
                "= filling dinosaur =\n" +
                "= filling cutlet =\n" +
                "(==== white bun ====)\n" +
                "\n" +
                "Price: 900,000000\n");
    }

    @Test
    public void checkingSetBunsName() {
        burger.setBuns(bun);
        Mockito.when(bun.getName()).thenReturn("Name");

        String expectedResult = "Name";
        assertEquals(expectedResult, burger.bun.getName());
    }

    @Test
    public void checkingSetBunsPrice() {
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
    public void checkingGetReceipt() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient_3);

        Mockito.when(bun.getName()).thenReturn("bun");
        Mockito.when(bun.getPrice()).thenReturn(100500F);

        String expectedResult = "(==== bun ====)\n" +
                "= sauce cosmo =\n" +
                "(==== bun ====)\n" +
                "\n" +
                "Price: 301500,000000\n";
        String actualResult = burger.getReceipt();
        assertEquals(expectedResult, actualResult);
    }
}
