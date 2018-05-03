package snake;

import java.awt.Color;
import java.util.ArrayList;

public abstract class SnakeAgent {

    protected Cell cell;
    public ArrayList<Cell> visitedCells = new ArrayList<>();
    protected Color color;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }


    protected Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell));
    }

    protected void execute(Action action, Environment environment)
    {
        Cell nextCell = null;


        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(cell);
        }

        if (nextCell != null && !nextCell.hasAgent()) {
            setCell(nextCell);
        }
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){this.cell.setAgent(null);}
        this.cell = newCell;
        if(newCell != null){newCell.setAgent(this);}
        visitedCells.add(this.cell);
    }    
    
    public Color getColor() {
        return color;
    }
}
