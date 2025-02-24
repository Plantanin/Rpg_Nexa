import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Character { // Classe abstraite (car non instantiable) imposant un « contrat » pour les classes dérivées
    private final String BATTLE_MODE = "1";
    private final String ESCAPE_MODE = "2";
    // Attributs communs à l'ensemble des classes filles
    private String name;
    private int health;
    private int maxHealth;
    private int experience;
    private int gold = 10;

    // Constructeur : définit l'état initial d'un personnage
    public Character(String name, int maxHealth, int experience) {
        this.name = name;
        this.health = this.maxHealth = maxHealth;
        this.experience = experience;
    }


    /**
     * <p>Méthode pour récupérer le nom du personnage</p>
     * @return nom du personnage
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Méthode pour définir le nom du personnage</p>
     * @param String name
     */


    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Méthode pour récupérer la vie du personnage</p>
     * @return la vie du personnage
     */

    public int getHealth() {
        return health;
    }

    /**
     * <p>Méthode pour récupérer la vie du personnage</p>
     * @param int health
     */

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * <p>Méthode pour baisser la vie du personnage</p>
     * @param int points
     */

    public void decreaseHealth(int points) {
        this.health -= points;
    }

    /**
     * <p>Méthode pour augmenter la vie du personnage</p>
     * @param int points
     */

    public void increaseHealth(int points) {
        this.health += points;
    }


    /**
     * <p>Méthode pour récupérer la vie max du personnage</p>
     * @return les points de vie maximum
     */

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * <p>Méthode pour récupérer la vie max du personnage</p>
     * @param int maxHealth
     */

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * <p>Méthode pour récupérer l'exp du personnage</p>
     * @return experience
     */

    public int getExperience() {
        return experience;
    }

    /**
     * <p>Méthode pour définir l'exp du personnage</p>
     * @param int experience
     */

    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * <p>Méthode pour décrémenter l'exp du personnage</p>
     * @param int points
     */

    public void decreaseExperience(int points) {
        this.experience -= points;
    }

    /**
     * <p>Méthode pour incrémenter la vie du personnage</p>
     * @param int points
     */

    public void increaseExperience(int points) {
        this.experience += points;
    }

    /**
     * <p>Méthode pour connaître l'état (vivant ou mort ) du personnage</p>
     * @return booléen
     */

    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * <p>Méthode pour choisir de se battre</p>
     * @return booléen
     */

    public boolean battle() {
        System.out.println("Battle");
        return true;
    }

    /**
     * <p>Méthode pour choisir se restaurer</p>
     * @return player Health
     */

    public int rest() {
        this.health = this.maxHealth;
        System.out.println(this.name + " s'est reposé et a récupéré tous ses points de vie !");
        return this.health;
    }

    /**
     * <p>Méthode pour choisir de fuir</p>
     * @return booléen
     */

    public boolean escape() {
        System.out.println("Escape");
        return true;
    }

    /**
     * <p>Méthode pour afficher le shopping</p>
     * @return booléen
     */

    public boolean shop() {
        System.out.println("Shopping");
        return true;
    }



// logique des stores

    /**
     * <p>Méthode pour récupérer les golds</p>
     * @return int gold
     */

    public int getGold() {
        return this.gold;
    }

    /**
     * <p>Méthode pour définir les golds</p>
     * @return booléen
     */

    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * <p>Méthode pour définir les golds</p>
     * @param int amount
     * @return player.health
     */

    public void heal(int amount) {
        this.health = Math.min(this.health + amount, this.maxHealth);
        System.out.println("Tes PV actuels : " + this.health);
    }

    /**
     * <p>Méthode de la logique du déroulement du combat</p>
     * @param Character character, String mode
     * @return booléen
     */
    public boolean fight(Character character, String mode) {
        Method fightMethod;
        boolean isOngoing = true;
        int dealtDamage;
        int takenDamage;

        try {
            fightMethod = this.getClass().getMethod(mode);
            try {
                isOngoing = (boolean) fightMethod.invoke(this);
            } catch (IllegalArgumentException e) {
                // TODO Implémenter le traitement adéquat
            } catch (IllegalAccessException e) {
                // TODO Implémenter le traitement adéquat
            } catch (InvocationTargetException e) {
                // TODO Implémenter le traitement adéquat
            }
        } catch (SecurityException e) {
            // TODO Implémenter le traitement adéquat
        } catch (NoSuchMethodException e) {
            // TODO Implémenter le traitement adéquat
        }

        switch(mode) {
            case BATTLE_MODE:
                dealtDamage = this.attack() - character.defend();
                takenDamage = character.attack() - this.defend();

                if (takenDamage < 0) {
                    dealtDamage -= takenDamage / 2;
                    takenDamage = 0;
                }

                if (dealtDamage < 0) {
                    dealtDamage = 0;
                }

                this.decreaseHealth(takenDamage);
                character.decreaseHealth(dealtDamage);

                if (this.isDead()) {
                    isOngoing = false;
                } else if (character.isDead()) {
                    // Calcul de montée en points d'expérience
                    this.increaseExperience(character.experience);
                    isOngoing = false;
                }
                break;
            case ESCAPE_MODE:
                if (Math.random() * 10 + 1 <= 3.5) {
                    isOngoing = false;
                    break;
                } else {
                    takenDamage = character.attack();
                    this.decreaseHealth(takenDamage);
                    if (this.isDead()) {
                        isOngoing = false;
                    }
                }
                break;
            default:
                // TODO À implémenter si nécessaire
                break;
        }
        return isOngoing;
    }

    // Méthodes abstraites liées à l'action de « combattre »
    /**
     * <p>Méthode pour actionner et calculer l'attaque</p>
     * @return int
     */
    public abstract int attack();

    /**
     * <p>Méthode pour actionner et calculer la défense</p>
     * @return int
     */

    public abstract int defend();

    /**
     * <p>Méthode pour convertir des resultat en chaine de caractere </p>
     * @return stats
     */

    public String toString() {
        String statsTemplate = """
                ****************************************
                ***** %S
                ****************************************
                * HP : %d / %d
                * XP : %d
                * Compétences : %s
                * Équipements : %s
                * Position: [X:%d, Y:%d]
                ****************************************
                """;

        String stats = String.format(statsTemplate, this.name, this.health, this.maxHealth, this.experience, "à implémenter", "à implémenter", 0, 0);

        return stats;
    }

}