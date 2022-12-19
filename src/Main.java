
import java.util.Random;

public class Main {

    private static int bossHealth = 700;
    private static int bossDamage = 50;
    private static String bossAttackType = "";
    private static int sumHeroesDamage = 0;
    private static int hpBoss;
    private static int[] heroesHealth = {200,230,200,300,280};
    private static int[] heroDamage = {50,30,30,10,0};
    private static String[] heroesAttackType = {"Волшебник","Войн", "Лучник","Голем","Целитель"};
    private static int roundCounter = 1;

    public static void main(String[] args) {
        printStatistics();
        while (isGameOver() != true){
            round();
            roundCounter++;
        }

    }

    public static void printStatistics (){

        System.out.println("_______________________________");
        System.out.println("Раунд "+roundCounter);
        System.out.println("Здоровье БОССА: "+bossHealth+" урон босса ["+bossDamage+"] ");
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i]+ " здоровье: "+heroesHealth[i]+" урон: ["+heroDamage[i]+"]");
        }
        System.out.println("________________________________");
    }


    public static void bossHits(){

        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0){
                if (heroesHealth[i] - bossDamage < 0){
                    heroesHealth[i] = 0;
                }else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHits(){

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0){
                if (bossAttackType == heroesAttackType[i]){
                    Random random = new Random();
                    int criticalFactor = random.nextInt(2)+2;
                    int criticalDamageHero = heroDamage[i]*criticalFactor;
                    if (bossHealth < criticalDamageHero){
                        bossHealth = 0;
                    }else {
                        bossHealth = bossHealth -criticalDamageHero;
                    }
                    System.out.println("Герои нанесли критический урон!!! "+criticalDamageHero);
                }else {
                    if ( bossHealth - heroDamage[i] < 0){
                        bossHealth = 0;
                    }else {
                        bossHealth = bossHealth - heroDamage[i];
                    }
                }
            }
        }

    }
    public static void golem (){


        int counter = 0;
        for (int i = 0; i < heroesAttackType.length; i++){
            if (i != 3){
                counter++;
            }
        }
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0){
                if (heroesHealth[i] - bossDamage/5*4 < 0 || heroesHealth[i] - ((bossDamage/5*counter)+bossDamage)<0){
                    heroesHealth[i] = 0;
                }else if (i!=3){
                    heroesHealth[i] = heroesHealth[i] - bossDamage/5*4;
                }else {
                    heroesHealth[i] = heroesHealth[i] - ((bossDamage/5*counter)+bossDamage);
                }
            }
        }
        System.out.println("Голем поглатил 1/5 часть урона ввиде "+bossDamage/5+" урона по героям, в сумме "+bossDamage/5*counter);
    }


    public static void medicHeal (){
        for (int i = 0; i < heroesAttackType.length; i++) {
            if ((i != 4 ) && (heroesHealth[i] <= 100) && (heroesHealth[i] > 0)){
                heroesHealth[i]+= 100;
                System.out.println("Целитель выбрал: "+heroesAttackType[i]+ "  + 100 исцеления");
                break;
            }
        }
    }
    public static void criticalChance(){
        int index = new Random().nextInt(heroesAttackType.length -1);
        bossAttackType = heroesAttackType[index];
    }

    public static boolean isGameOver(){
        if (bossHealth <= 0){
            System.out.println("Герои победили!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0){
                allHeroesDead =false;
                break;
            }
        }
        if (allHeroesDead == true){
            System.out.println("БОСС победил!!!");
        }
        return allHeroesDead;
    }

    public static void round (){

        if (heroesHealth[4] > 0){
            medicHeal();
        }
        if ( bossHealth> 0 && heroesHealth[3] > 0){
            golem();
        }else {
            if ( bossHealth > 0){
                bossHits();
            }
        }

        criticalChance();
        heroesHits();
        printStatistics();
    }


}
