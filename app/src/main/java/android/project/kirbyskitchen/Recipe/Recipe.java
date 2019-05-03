package android.project.kirbyskitchen.Recipe;
//creating recipe object
public class Recipe {
    int id;
    String name, yield, ingredients,  directions, equipment;

    public Recipe() {

    }
    public Recipe(int id, String name, String yield, String ingredients, String directions, String equipment) {
        this.id = id;
        this.name = name;
        this.yield = yield;
        this.ingredients = ingredients;
        this.directions = directions;
        this.equipment = equipment;
    }

    public Recipe(String name, String yield, String ingredients, String directions, String equipment) {
        this.name = name;
        this.yield = yield;
        this.ingredients = ingredients;
        this.directions = directions;
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
