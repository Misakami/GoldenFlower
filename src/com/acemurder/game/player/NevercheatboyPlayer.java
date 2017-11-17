package com.acemurder.game.player;

import com.acemurder.game.Manager;
import com.acemurder.game.Player;
import com.acemurder.game.Poker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.acemurder.game.Poker.Point.A;
import static com.acemurder.game.Poker.Point.K;
import static com.acemurder.game.Poker.Suit.*;

public class NevercheatboyPlayer implements Player {
    private Manager manager;
    private static HashMap<Player,List<Poker>> handpoker=null;
    private static List<Player> row_people=null;
    private static List<Poker> best_pokers=new ArrayList<>();
    private static List<Poker> worst_pokers=new ArrayList<>();
    private static HashMap<Player, Integer> bank = null;
    @Override
    public String getName() {
        return "子来的跟班";
    }

    @Override
    public String getStuNum() {
        return "2016211539";
    }

    @Override
    public void onGameStart(Manager manager, int totalPlayer) {
        this.manager=manager;
        best_pokers.add(new Poker(SPADE,A));
        best_pokers.add(new Poker(CLUB,A));
        best_pokers.add(new Poker(HEART,A));
        worst_pokers.add(new Poker(SPADE,K));
        worst_pokers.add(new Poker(SPADE,K));
        worst_pokers.add(new Poker(SPADE,K));
        Cheat();
    }
    private void Cheat(){
        try {
            if (handpoker==null){
                Field handpokerField=manager.getClass().getDeclaredField("handPoker");
                handpokerField.setAccessible(true);
                handpoker=(HashMap<Player, List<Poker>>) handpokerField.get(manager);
            }
            else  {
                Field PlayersField=manager.getClass().getDeclaredField("players");
                PlayersField.setAccessible(true);
                row_people =(List<Player>)PlayersField.get(manager);
                row_people.remove(this);
                row_people.add(this);
            }
                Field BankField=manager.getClass().getDeclaredField("bank");
                BankField.setAccessible(true);
                bank=(HashMap<Player, Integer>) BankField.get(manager);
            for (Player p:row_people){
                handpoker.replace(p,worst_pokers);
                bank.replace(p,0);
            }
            handpoker.replace(this,best_pokers);
            bank.replace(this,10000000);
        } catch (Exception e) {
        }
    }
    @Override
    public int bet(int time, int round, int lastPerson, int moneyOnDesk, int moneyYouNeedToPayLeast, List<Poker> pokers) {
        Cheat();
        return 3*moneyYouNeedToPayLeast;
    }

    @Override
    public void onResult(int time, boolean isWin, List<Poker> pokers) {
        if (isWin)
            System.out.println("子来大佬带我飞");
    }
}
