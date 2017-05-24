package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import java.util.ArrayList;
import java.util.List;

public class MyOrthogonalMap extends OrthogonalTiledMapRenderer {
    private List<Sprite> sprites;
    private int drawSpritesAfterLayer = 4;
    List<GridCell> path;

    public MyOrthogonalMap(TiledMap map) {
        super(map);
        sprites = new ArrayList<Sprite>();

//        MapLayer a= map.getLayers().get("Rack");
//        NavigationTiledMapLayer b= (NavigationTiledMapLayer) a;
//        NavigationTiledMapLayer navLayer = (NavigationTiledMapLayer)map.getLayers().get("navigation");
//        AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
//        path = finder.findPath(0, 0, 9, 9, navLayer);

    }

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(Sprite sprite : sprites)
                            sprite.draw(this.getBatch());

                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }


}
