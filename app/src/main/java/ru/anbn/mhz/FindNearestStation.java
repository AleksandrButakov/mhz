package ru.anbn.mhz;

public class FindNearestStation extends MainActivity {
    public static int iDistance;
    public static int iMinDistance;
    public static int indexMinDistance;

    public static int findNearestStation(double lat, double lon) {

        // определим ближайшую к нам станцию
        indexMinDistance = 2;
        iDistance = (int) Math.sqrt(Math.pow((lat * 111 - dGeographicCoordinates[2][0] * 111), 2)
                + Math.pow((lon * 50 - dGeographicCoordinates[2][1] * 50), 2));
        iMinDistance = iDistance;

        for (int i = 3; i < countRows; i++) {
            iDistance = (int) Math.sqrt(Math.pow((lat * 111 - dGeographicCoordinates[i][0] * 111), 2)
                    + Math.pow((lon * 50 - dGeographicCoordinates[i][1] * 50), 2));
            if (iDistance < iMinDistance) {
                iMinDistance = iDistance;
                indexMinDistance = i;
            }
        }

        return indexMinDistance;
    }

}
