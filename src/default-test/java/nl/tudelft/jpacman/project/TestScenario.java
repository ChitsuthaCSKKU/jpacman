package nl.tudelft.jpacman.project;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestScenario {

    private Launcher launcher;



    //launch ui
    @BeforeEach
    public void setupGame(){

        launcher = new Launcher();
        launcher.launch();
    }

    //quit ui when done
    @AfterEach
    public void tearDouwn(){
        launcher.dispose();
    }

    //-------------------------------------------Scenario 1---------------------------------------------------------
    //description : game start success

    @Test
    public void startGame(){
        Game game = launcher.getGame();
        assertFalse(game.isInProgress());//check that game is not started yet
        game.start();//start game
        assertTrue(game.isInProgress());// check that game is starting
    }

    //---------------------------------------------Scenario 2-------------------------------------------------------------
    //description : Player consumes pallet each pallet is worth 10 pts.

    @Test //TS002 :consume pallet and earn points
    public void playerConsumePallet(){

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        assertTrue(game.isInProgress());//check if game is running
        assertEquals(0,player.getScore());//check if starting score is 0
        //moving player to consume pallet on the map
        game.move(player, Direction.EAST);//move east consume 1 pallet
        assertEquals(10,player.getScore());//check score. score should be 10
        game.move(player,Direction.EAST);//move east consume 1 pallet
        assertEquals(20,player.getScore());//check score. score should be 20

    }

    @Test//TS003 : move to empty square point should not be added
    public void emptySquare(){

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        assertTrue(game.isInProgress());
        //moving player to consume pallet on the map and move back
        game.move(player,Direction.EAST);//move 3 square
        game.move(player,Direction.EAST);
        game.move(player,Direction.EAST);
        assertEquals(30,player.getScore());
        game.move(player,Direction.WEST);//move back 3 square
        game.move(player,Direction.WEST);
        game.move(player,Direction.WEST);
        assertEquals(30,player.getScore());//point should remain the same
    }

    @Test// TS004 : move fail hit wall
    public void encounterWall(){

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        assertTrue(game.isInProgress());
        game.move(player,Direction.NORTH);//move player north to hit the wall
        Square square1 = player.getSquare();//get location
        game.move(player,Direction.NORTH);//move north again
        Square square2 = player.getSquare();// get location
        assertEquals(square1,square2);//position should be the same consider that the player didn't move due to wall blocking

    }

    private Direction randomDirection(){//generate random direction
        return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    @Test//TS005 : player die
    public void playerDie(){
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        assertTrue(game.isInProgress());

        Direction chosen;

        while (game.isInProgress()){//check game is still running
            chosen = randomDirection();//generate random direction
            game.move(player, chosen);//move the player by random
        }
        assertFalse(player.isAlive());//check if the player still alive
        assertFalse(game.isInProgress());//game stop when player is dead

    } 

    @Test//TS006 :player win all pallet has been consume
    public void playerWin(){
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        assertTrue(game.isInProgress());
        Direction chosen;

        while (game.isInProgress()){
            chosen = randomDirection();
            game.move(player,chosen);

            assertTrue(player.isAlive());
            assertFalse(game.isInProgress());
        }



    }



}
