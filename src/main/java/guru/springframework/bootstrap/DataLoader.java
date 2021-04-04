package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final NotesRepository notesRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository, CategoryRepository categoryRepository, NotesRepository notesRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.notesRepository = notesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();

    }

    private void loadData() {
        Recipe recipe1 = new Recipe();
        Notes notes = new Notes();
        notes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");

        recipe1.setCookTime(20);
        recipe1.setDescription("Perfect Guacamole");
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setDirections("Hva er dette?");
        recipe1.setPrepTime(15);
        recipe1.setServings(10);
        recipe1.setSource("Simply recipes");
        recipe1.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        recipe1.setNotes(notes);
        recipeRepository.save(recipe1);

        notes.setRecipe(recipe1);
        notesRepository.save(notes);

        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(2);
        ingredient1.setDescription("Avocado");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ripe");
        ingredient1.setUom(unitOfMeasureOptional.get());
        ingredient1.setRecipe(recipe1);
        ingredientRepository.save(ingredient1);
        ingredients.add(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setAmount(1);
        ingredient2.setDescription("Salt");
        unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        ingredient2.setUom(unitOfMeasureOptional.get());
        ingredient2.setRecipe(recipe1);
        ingredientRepository.save(ingredient2);
        ingredients.add(ingredient2);
        recipe1.setIngredients(ingredients);


        Set<Category> categorySet = new HashSet<Category>();
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        categorySet.add(categoryOptional.get());
        recipe1.setCategories(categorySet);
        for (Category category : recipe1.getCategories()) {
            System.out.println(category.getDescription());
        }
        for (Ingredient ingredient : recipe1.getIngredients()) {
            System.out.println(ingredient.getDescription());
        }


        Recipe recipe2 = new Recipe();
        recipe2.setCookTime(90);
        recipe2.setDescription("Spicy grilled Chicken");
        recipe2.setDifficulty(Difficulty.EASY);
        recipe2.setDirections("Hva er dette?");
        recipe2.setPrepTime(17);
        recipe2.setServings(12);
        recipe2.setSource("Simply recipes");
        recipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        recipeRepository.save(recipe2);
    }
}
