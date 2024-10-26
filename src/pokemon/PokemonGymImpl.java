package pokemon;

import pokemon.types.ElectricPokemon;
import pokemon.types.FirePokemon;
import pokemon.types.GrassPokemon;
import pokemon.types.WaterPokemon;

import java.util.*;

// Los in deze klasse alle foutmeldingen op door (abstracte) klassen met variabelen en methodes te maken en een interface met methodes (en soms een import).
public class PokemonGymImpl implements PokemonGym {
    // Deze 4 constanten worden gebruikt om tekst met kleur te printen in de console.
    // Hier hoef je niks mee te doen
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    List<Pokemon> pokemons;

    public PokemonGymImpl(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public void enteredTheGym(PokemonTrainer player1) {
        PokemonGymOwner gymOwner = new PokemonGymOwner("Brock", "Pewter City", pokemons);

        System.out.println("You have entered the " + gymOwner.getTown() + " gym");
        System.out.println("In front of you stands a pokemontrainer");
        System.out.println(ANSI_RED + gymOwner.getName() + ANSI_RESET +": Hello stranger, I'm " + gymOwner.getName() + ", the owner of this gym. Who are you?");
        System.out.println(ANSI_GREEN + player1.getName() + ANSI_RESET + ": I'm " + player1.getName() + " and i'm here to challenge you for a battle");
        System.out.println(ANSI_RED + gymOwner.getName() + ANSI_RESET +": So you're after my badge too, lets fight!!!");

        Pokemon gymPokemon = chooseGymPokemon(gymOwner);
        System.out.println(ANSI_RED + gymOwner.getName() + ANSI_RESET +": I'll choose you, " + gymPokemon.getName());
        Pokemon pokemon = choosePokemon(player1);
        System.out.println(ANSI_GREEN + player1.getName() + ANSI_RESET + ": I'll choose you, " + pokemon.getName());

        fightRound(player1, gymOwner, pokemon, gymPokemon);

    }

    @Override
    public void printPokemon(List<Pokemon> pokemons) {
        for (Pokemon p : pokemons) {
            System.out.println(p.getName());
        }
    }

    @Override
    public Pokemon selectPokemon(String pokemon, PokemonTrainer trainer) {
        List<Pokemon> pokemons = trainer.getPokemons();
        int number = 0;
        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).getName().equalsIgnoreCase(pokemon)) {
                number = i;
            }
        }
        return pokemons.get(number);
    }

    @Override
    public void fightRound(PokemonTrainer trainer, PokemonGymOwner owner, Pokemon pokemon, Pokemon gymPokemon) {
        Scanner speler_A = new Scanner(System.in);
        while (pokemon.getHp() > 0 && gymPokemon.getHp() > 0) {

            System.out.println("Its " + owner.getName() + "'s turn to attack");
            gymOwnerAttacks(gymPokemon, pokemon);
            System.out.println("Its " + trainer.getName() + "'s turn to attack");
            attackOrChange(pokemon, gymPokemon, trainer, owner);

        }
        if(pokemon.getHp() <= 0){
            System.out.println(gymPokemon.getName() + " has defeated " + pokemon.getName());
        } else if (gymPokemon.getHp() <= 0){
            System.out.println(pokemon.getName() + " has defeated " + gymPokemon.getName());
        }

        System.out.println("Would you like to keep playing? yes or no");
        String keepPlaying = speler_A.nextLine();
        if (keepPlaying.equals("yes")){
            enteredTheGym(trainer);
        } else {
            System.out.println("Thank you for playing");
        }
    }

    @Override
    public Pokemon chooseGymPokemon(PokemonGymOwner gymOwner){
        Random rand = new Random();
        List<Pokemon> pokemons = new ArrayList<>();
        for (Pokemon p : gymOwner.getPokemons()) {
            if(p.getHp() > 0 ){
                pokemons.add(p);
            }
        }
        int amountOfPokemons = pokemons.size();
        int randomNumber = rand.nextInt(amountOfPokemons);
        return pokemons.get(randomNumber);
    }

    @Override
    public Pokemon choosePokemon(PokemonTrainer trainer){
        Scanner speler_A = new Scanner(System.in);
        List<Pokemon> pokemons = new ArrayList<>();
        for (Pokemon p : trainer.getPokemons()) {
            if(p.getHp() > 0 ){
                pokemons.add(p);
            }
        }
        System.out.println("Please make your choice of pokemon to attack");
        for (Pokemon p : pokemons) {
            System.out.println(p.getName());
        }
        String pokemon = speler_A.nextLine();
        return selectPokemon(pokemon, trainer);
    }

    @Override
    public int randomAttackByGymOwner(){
        Random rand = new Random();
        int maxAttacks = 4;
        return rand.nextInt(maxAttacks);
    }

    @Override
    public String chooseAttackPlayer(Pokemon p){
        Scanner speler_A = new Scanner(System.in);
        String type = p.getType();
        switch (type.toLowerCase()) {
            case "fire" -> {
                FirePokemon fp = (FirePokemon) p;
                System.out.println("Choose your attack");
                System.out.println(fp.getAttacks());
                return speler_A.nextLine();
            }
            case "water" -> {
                WaterPokemon wp = (WaterPokemon) p;
                System.out.println("Choose your attack");
                System.out.println(wp.getAttacks());
                return speler_A.nextLine();
            }
            case "electric" -> {
                ElectricPokemon ep = (ElectricPokemon) p;
                System.out.println("Choose your attack");
                System.out.println(ep.getAttacks());
                return speler_A.nextLine();
            }
            default -> {
                GrassPokemon gp = (GrassPokemon) p;
                System.out.println("Choose your attack");
                System.out.println(gp.getAttacks());
                return speler_A.nextLine();
            }
        }
    }

    @Override
    public void performAttackPlayer(Pokemon pokemon, Pokemon gymPokemon, String attack){
        FirePokemon fire;
        ElectricPokemon electric;
        GrassPokemon grass;
        WaterPokemon water;

        String choosenAttack = attack.toLowerCase(Locale.ROOT);

        switch (pokemon.getType()) {
            case "fire" -> {
                fire = new FirePokemon(pokemon.getName(), pokemon.getLevel(), pokemon.getHp(), pokemon.getFood(), pokemon.getSound());
                switch (choosenAttack) {
                    case "inferno" -> fire.inferno(gymPokemon);
                    case "pyroball" -> fire.pyroBall(gymPokemon);
                    case "firelash" -> fire.fireLash(gymPokemon);
                    default -> fire.flameThrower(gymPokemon);
                }
            }
            case "water" -> {
                water = new WaterPokemon(pokemon.getName(), pokemon.getLevel(), pokemon.getHp(), pokemon.getFood(), pokemon.getSound());
                switch (choosenAttack) {
                    case "surf" -> water.surf(gymPokemon);
                    case "hydropump" -> water.hydroPump(gymPokemon);
                    case "hydrocanon" -> water.hydroCanon(gymPokemon);
                    default -> water.rainDance(gymPokemon);
                }
            }
            case "grass" -> {
                grass = new GrassPokemon(pokemon.getName(), pokemon.getLevel(), pokemon.getHp(), pokemon.getFood(), pokemon.getSound());
                switch (choosenAttack) {
                    case "leafstorm" -> grass.leafStorm(gymPokemon);
                    case "solarbeam" -> grass.solarBeam(gymPokemon);
                    case "leechseed" -> grass.leechSeed(gymPokemon);
                    default -> grass.leaveBlade(gymPokemon);
                }
            }
            default -> {
                electric = new ElectricPokemon(pokemon.getName(), pokemon.getLevel(), pokemon.getHp(), pokemon.getFood(), pokemon.getSound());
                switch (choosenAttack) {
                    case "thunderpunch" -> electric.thunderPunch(gymPokemon);
                    case "electroball" -> electric.electroBall(gymPokemon);
                    case "thunder" -> electric.thunder(gymPokemon);
                    default -> electric.voltTackle(gymPokemon);
                }
            }
        }
    }

    @Override
    public void gymOwnerAttacks(Pokemon gymPokemon, Pokemon pokemon){
        FirePokemon fire;
        ElectricPokemon electric;
        GrassPokemon grass;
        WaterPokemon water;

        switch (gymPokemon.getType()) {
            case "fire" -> {
                fire = new FirePokemon(gymPokemon.getName(), gymPokemon.getLevel(), gymPokemon.getHp(), gymPokemon.getFood(), gymPokemon.getSound());
                String attack = fire.getAttacks().get(randomAttackByGymOwner());
                switch (attack) {
                    case "inferno" -> fire.inferno(pokemon);
                    case "pyroBall" -> fire.pyroBall(pokemon);
                    case "fireLash" -> fire.fireLash(pokemon);
                    default -> fire.flameThrower(pokemon);
                }
            }
            case "water" -> {
                water = new WaterPokemon(gymPokemon.getName(), gymPokemon.getLevel(), gymPokemon.getHp(), gymPokemon.getFood(), gymPokemon.getSound());
                String attack = water.getAttacks().get(randomAttackByGymOwner());
                switch (attack) {
                    case "surf" -> water.surf(pokemon);
                    case "hydroPump" -> water.hydroPump(pokemon);
                    case "hydroCanon" -> water.hydroCanon(pokemon);
                    default -> water.rainDance(pokemon);
                }
            }
            case "grass" -> {
                grass = new GrassPokemon(gymPokemon.getName(), gymPokemon.getLevel(), gymPokemon.getHp(), gymPokemon.getFood(), gymPokemon.getSound());
                String attack = grass.getAttacks().get(randomAttackByGymOwner());
                switch (attack) {
                    case "leafStorm" -> grass.leafStorm(pokemon);
                    case "solarBeam" -> grass.solarBeam(pokemon);
                    case "leechSeed" -> grass.leechSeed(pokemon);
                    default -> grass.leaveBlade(pokemon);
                }
            }
            default -> {
                electric = new ElectricPokemon(gymPokemon.getName(), gymPokemon.getLevel(), gymPokemon.getHp(), gymPokemon.getFood(), gymPokemon.getSound());
                String attack = electric.getAttacks().get(randomAttackByGymOwner());
                switch (attack) {
                    case "thunderPunch" -> electric.thunderPunch(pokemon);
                    case "electroBall" -> electric.electroBall(pokemon);
                    case "thunder" -> electric.thunder(pokemon);
                    default -> electric.voltTackle(pokemon);
                }
            }
        }
    }

    @Override
    public void attackOrChange(Pokemon pokemon, Pokemon gymPokemon, PokemonTrainer trainer, PokemonGymOwner gym){
        Scanner speler_A = new Scanner(System.in);

        System.out.println("Do you want to attack or change your pokemon?");
        System.out.println("Type a for attack or c for change");
        String choice = speler_A.nextLine();

        if (choice.equalsIgnoreCase("a")) {
            feedPokemon(pokemon);
            String attack = chooseAttackPlayer(pokemon);
            performAttackPlayer(pokemon, gymPokemon, attack);
        } else {
            pokemon = choosePokemon(trainer);
            attackOrChange(pokemon, gymPokemon, trainer, gym);
            fightRound(trainer, gym, pokemon, gymPokemon);
        }
    }

    @Override
    public void feedPokemon(Pokemon pokemon) {
        Scanner speler_A = new Scanner(System.in);

        System.out.println("What type of food do you want to feed your pokemon?: ");
        System.out.println("Type e for electric, type f for fire, type w for water or type g for grass");
        String choice = speler_A.nextLine();
        System.out.println("You passed food to the pokemon");
        String foodChoice = switch (choice.toLowerCase()) {
            case "e" -> "electric";
            case "f" -> "fire";
            case "w" -> "water";
            case "g" -> "grass";
            default -> "other";
        };

        pokemon.feed(foodChoice);
    }

}
