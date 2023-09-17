package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Instruction;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends MapObject {
    private float movementProgress;
    private final float movementSpeed;

    public Tank(String textureFileName, int x, int y, float rotation, float movementProgress, float movementSpeed) {
        super(textureFileName, x, y, rotation);
        this.movementProgress = movementProgress;
        this.movementSpeed = movementSpeed;
    }

    private GridPoint2 getNextCoordinates(Instruction instruction) {
        if (instruction.getDirection().equals("X")) {
            return changedX(getCurrentCoordinates(), instruction.getRange());
        } else {
            return changedY(getCurrentCoordinates(), instruction.getRange());
        }
    }

    private boolean collision(GridPoint2 nextCoordinates, List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getCurrentCoordinates().equals(nextCoordinates)) {
                return true;
            }
        }
        return false;
    }

    public void findAndSetNextCoordinates(Instruction instruction, List<Obstacle> obstacles) {
        if (instruction != null) {
            if (isEqual(movementProgress, 1f)) {
                GridPoint2 nextCoordinates = getNextCoordinates(instruction);

                // check potential player destination for collision with obstacles
                if (!collision(nextCoordinates, obstacles)) {
                    setNextCoordinates(nextCoordinates);
                    movementProgress = 0f;
                }
                setRotation(instruction.getRotation());
            }
        }
    }

    public void move(TileMovement tileMovement, List<Obstacle> obstacles, float deltaTime, Instruction instruction) {
        findAndSetNextCoordinates(instruction, obstacles);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(getRectangle(), getCurrentCoordinates(), getNextCoordinates(), movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            setCurrentCoordinates(getNextCoordinates());
        }
    }
}
