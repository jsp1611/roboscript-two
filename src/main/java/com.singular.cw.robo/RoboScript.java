package com.singular.cw.robo;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implement RS1 for RoboScript.
 * <p>
 * See https://www.codewars.com/kata/5870fa11aa0428da750000da
 *
 * @author jon
 */
public class RoboScript {

    private enum Orientation {
        NORTH,
        EAST,
        SOUTH,
        WEST;
    }

    private static class Position {
        private final int x;
        private final int y;

        Position(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        static Position incY(final Position position) {
            return new Position(position.getX(), position.getY() + 1);
        }

        static Position decY(final Position position) {
            return new Position(position.getX(), position.getY() - 1);
        }

        static Position incX(final Position position) {
            return new Position(position.getX() + 1, position.getY());
        }

        static Position decX(final Position position) {
            return new Position(position.getX() - 1, position.getY());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Placement {
        private final Orientation orientation;
        private final Position position;

        private Placement(final Orientation orientation, final Position position) {
            this.orientation = orientation;
            this.position = position;
        }

        Orientation getOrientation() {
            return orientation;
        }

        Position getPosition() {
            return position;
        }
    }

    private enum Command {
        F('F', p -> {
            if (p.getOrientation() == Orientation.NORTH) {
                return new Placement(p.getOrientation(), Position.incY(p.getPosition()));
            } else if (p.getOrientation() == Orientation.SOUTH) {
                return new Placement(p.getOrientation(), Position.decY(p.getPosition()));
            } else if (p.getOrientation() == Orientation.EAST) {
                return new Placement(p.getOrientation(), Position.incX(p.getPosition()));
            } else {
                return new Placement(p.orientation, Position.decX(p.getPosition()));
            }
        }),
        L('L', p -> {
            if (p.getOrientation() == Orientation.NORTH) {
                return new Placement(Orientation.WEST, p.getPosition());
            } else {
                return new Placement(Orientation.values()[p.getOrientation().ordinal() - 1], p.getPosition());
            }
        }),
        R('R', p -> {
            if (p.getOrientation() == Orientation.WEST) {
                return new Placement(Orientation.NORTH, p.getPosition());
            } else {
                return new Placement(Orientation.values()[p.getOrientation().ordinal() + 1], p.getPosition());
            }
        });

        private static final Supplier<Map<Character, Command>> MAP_SUPPLIER = () -> {
            final Map<Character, Command> map = new HashMap<>();
            map.put('F', Command.F);
            map.put('L', Command.L);
            map.put('R', Command.R);
            return map;
        };

        private static final Map<Character, Command> LOOK_UP = MAP_SUPPLIER.get();

        private final char cmdChar;

        private final Function<Placement, Placement> apply;

        Command(final char cmdChar, final Function<Placement, Placement> apply) {
            this.cmdChar = cmdChar;
            this.apply = apply;
        }

        public static Command toCommand(final char c) {
            return LOOK_UP.get(c);
        }

        public Placement apply(Placement placement) {
            return apply.apply(placement);
        }
    }

    private static String expand(final String code) {
        final StringBuilder expanded = new StringBuilder();
        Character lastCharacter = null;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder digits = new StringBuilder();
                for (int j = i; j < code.length() && Character.isDigit(code.charAt(j)); j++) {
                    digits.append(code.charAt(j));
                    i=j;
                }
                for (int val = Integer.valueOf(digits.toString()); val > 1; val--) {
                    expanded.append(lastCharacter);
                }
            } else {
                expanded.append(c);
            }
            lastCharacter = c;
        }
        return expanded.toString();
    }

    private static String print(final Set<Position> positions, final int minX, final int maxX, final int minY, final int maxY) {
        int curX = minX;
        int curY = maxY;
        final StringBuilder builder = new StringBuilder();
        do {
            builder.append(positions.contains(new Position(curX, curY)) ? '*' : ' ');
            curX++;
            if (curX > maxX && curY > minY) {
                builder.append('\r');
                builder.append('\n');
                curX = minX;
                curY--;
            }
        } while (curX <= maxX && curY >= minY);
        return builder.toString();
    }

    public static String execute(final String code) {
        final String expandedCode = expand(code);
        final Set<Position> positions = new HashSet<>();
        Placement lastPlacement = new Placement(Orientation.EAST, new Position(0, 0));
        positions.add(lastPlacement.getPosition());
        int minX = 0, maxX = 0, minY = 0, maxY = 0;
        for (char c : expandedCode.toCharArray()) {
            final Placement placement = Command.toCommand(c).apply(lastPlacement);
            positions.add(placement.getPosition());
            lastPlacement = placement;
            minX = Integer.min(minX, placement.getPosition().getX());
            maxX = Integer.max(maxX, placement.getPosition().getX());
            minY = Integer.min(minY, placement.getPosition().getY());
            maxY = Integer.max(maxY, placement.getPosition().getY());
        }
        return print(positions, minX, maxX, minY, maxY);
    }

}
