package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    final int WIDTH = 25;
    final int HEIGHT = 25;
    HashMap<Tile,Tile> path;
    Tile pathHead, pathTail;
    Tile[][] grid = new Tile[WIDTH][HEIGHT];
    Player player = new Player();
    ArrayList<Enemy> enemyList = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();
    float gameLength = 1;
    int count = 600, pathLength = 0;
    int targetingType = 0;
    int killed = 0;

    Board(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                grid[i][j] = new Tile(i,j);
            }
        }
        grid[0][0].isGoal = true;
        grid[0][0].buildable = false;
        grid[WIDTH-1][HEIGHT-1].isSpawn=true;
        grid[WIDTH-1][HEIGHT-1].buildable = false;
        pathHead = grid[WIDTH-1][HEIGHT-1];
        pathTail=grid[0][0];
        path = getPath(pathTail,pathHead);
    }
    public void draw(SpriteBatch batch, ShapeDrawer sd){
        sd.filledRectangle(44,44,WIDTH*32,HEIGHT*32, Color.BLACK);
        for(int i = 0; i < WIDTH;i++){
            for(int j = 0; j < HEIGHT;j++){
                if(!grid[i][j].blocked && !grid[i][j].isGoal && !grid[i][j].isSpawn){
                    sd.filledRectangle(grid[i][j].px,grid[i][j].py,30,30,Color.WHITE);
                }else if(grid[i][j].isGoal){
                    sd.filledRectangle(grid[i][j].px,grid[i][j].py,30,30,Color.GREEN);
                }else if(grid[i][j].isSpawn){
                    sd.filledRectangle(grid[i][j].px,grid[i][j].py,30,30,Color.RED);
                }else{
                    sd.filledRectangle(grid[i][j].px,grid[i][j].py,30,30,Color.DARK_GRAY);
                }
            }
        }
        processProjectiles();
        drawPath(sd);
        updateEnemies();
        drawProjectiles(sd);
        drawEnemies(sd);
        drawTowers(sd);
    }

    public void updateEnemies(){
        if(count<=0){
            enemyList.add(new Enemy(grid[24][24].px+15,grid[24][24].py+15,10*(gameLength/2f),1f,grid[24][24]));
            count=60;
        }else{
            count--;
            gameLength+=0.002f;
        }
        for(int i = enemyList.size()-1;i>=0;i--){
            enemyList.get(i).path=path;
            enemyList.get(i).update();
            if(enemyList.get(i).hp<=0){
                player.gold+=5;
                enemyList.remove(i);
                killed++;
            }else if(enemyList.get(i).hitEnd){
                enemyList.remove(i);
                player.hp--;
            }
        }
    }

    public void drawEnemies(ShapeDrawer sd){
        for(Enemy e: enemyList){
            e.draw(sd);
        }
    }

    public void drawTowers(ShapeDrawer sd){
        for(int i = 0; i < WIDTH;i++){
            for(int j = 0; j < HEIGHT;j++){
                if(grid[i][j].tower!=null){
                    Projectile p = grid[i][j].tower.fire(enemyList,targetingType);
                    if(p!=null){
                        projectiles.add(p);
                    }
                    grid[i][j].tower.draw(sd);
                }
            }
        }
    }

    public void processProjectiles(){
        if(!projectiles.isEmpty()){
            for(int i = projectiles.size()-1;i>=0;i--){
                projectiles.get(i).update();
            }
        }
    }
    public void drawProjectiles(ShapeDrawer sd){
        if(!projectiles.isEmpty()){
            for(int i = projectiles.size()-1;i>=0;i--){
                if(projectiles.get(i).alive){
                    sd.filledCircle(projectiles.get(i).position.x,projectiles.get(i).position.y,projectiles.get(i).size,projectiles.get(i).color);
                }else{
                    projectiles.remove(i);
                }
            }
        }
    }

    public Tile getTile(Vector2 mouse){
        int x = (int) ((mouse.x-44.5f)/32);
        int y = (int) ((mouse.y-44.5f)/32);
        if(x >= 0 && y >= 0 && x < WIDTH && y < WIDTH){
            return grid[x][y];
        }
        return null;
    }

    public void buildTower(Tile selectedTile, Tower tower){
        if(selectedTile.buildable && player.gold >= tower.cost){
            selectedTile.tower = tower;
            selectedTile.tower.position.set(selectedTile.px+15,selectedTile.py+15);
            selectedTile.blocked=true;
            selectedTile.buildable=false;
            path = getPath(pathTail,pathHead);
            if(!path.containsKey(pathHead)){
                selectedTile.blocked=false;
                selectedTile.buildable=true;
                selectedTile.tower=null;
                path = getPath(pathTail,pathHead);
                return;
            }
            player.gold -= tower.cost;
            System.out.println(selectedTile.tower.debuff.speedmulti);
        }
    }
    public void sellTower(Tile selectedTile){
        if(selectedTile.tower != null){
            player.gold += selectedTile.tower.cost*0.7f;
            selectedTile.tower=null;
            selectedTile.buildable=true;
            selectedTile.blocked=false;
            path = getPath(pathTail,pathHead);
        }
    }

    public void drawPath(ShapeDrawer sd){
        pathLength=0;
        if(path.get(pathHead)!=null){
            Tile current = path.get(pathHead);
            while(current != pathTail){
                sd.filledRectangle(current.px,current.py,30,30,Color.CORAL);
                pathLength++;
                current = path.get(current);
            }
            current = null;
        }
    }

    public HashMap<Tile,Tile> getPath(Tile start, Tile end){
        PathfindingQueue<Tile> frontier = new PathfindingQueue<>();
        frontier.enqueue(start,0);
        HashMap<Tile, Tile> cameFrom = new HashMap<>();
        HashMap<Tile, Float> costSoFar = new HashMap<>();
        cameFrom.put(start,null);
        costSoFar.put(start,0f);
        while(frontier.count()>0){
            Tile current = frontier.dequeue();
            if(current.equalTo(end)){
                break;
            }
            for(int i = 0; i < 4;i++){
                Tile next = getNeighbor(current,i);
                if(next==null || next.weight >= 100 || next.blocked)continue;
                float newCost = costSoFar.get(current) + next.weight;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next,newCost);
                    float priority = newCost + next.distance(end)*2;
                    frontier.enqueue(next, priority);
                    cameFrom.put(next,current);
                }
            }

        }
        return cameFrom;
    }

    public Tile getNeighbor(Tile t, int direction){
        if(direction==0){
            if(t.x+1 > grid.length-1)return null;
            return grid[t.x+1][t.y];
        }else if(direction==1){
            if(t.x-1 < 0)return null;
            return grid[t.x-1][t.y];
        }else if(direction==2){
            if(t.y+1 > grid[t.x].length-1)return null;
            return grid[t.x][t.y+1];
        }else{
            if(t.y-1 < 0)return null;
            return grid[t.x][t.y-1];
        }
    }
}
