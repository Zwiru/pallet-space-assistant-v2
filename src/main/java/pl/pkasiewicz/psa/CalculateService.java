package pl.pkasiewicz.psa;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class CalculateService {
    private final double spaceOnTruckInMmExceptEuro = 13600;
    private final double spaceOnTruckInMmForEuro = 13200;
    private final double euroLengthInMm = 1200;
    private final double euroWidthInMm = 800;
    private final double j7LengthInMm = 1200;
    private final double hg5LengthInMm = 1260;
    private final double gm6LengthInMm = 1260;
    private final int rowSpaceForPallets = 2;
    private final int euroPalletsInRowByLength = 3;

    List<String> calculateRemainingSpaceForSpecificPallets(double euro, double j7, double hg5, double gm6) {
        ArrayList<String> remainingSpaceForSpecificPallets = new ArrayList<>();
        double leftSpace = calculateLeftFreeSpace(euro, j7, hg5, gm6);
        double spaceForEuroPallets = Math.floor((leftSpace / euroWidthInMm) * rowSpaceForPallets - 1);
        double spaceForJ7Pallets = Math.floor((leftSpace / j7LengthInMm) * rowSpaceForPallets);
        double spaceForHg5Pallets = Math.floor((leftSpace / hg5LengthInMm) * rowSpaceForPallets - 1);
        double spaceForGm6Pallets = Math.floor((leftSpace / gm6LengthInMm) * rowSpaceForPallets - 1);
        remainingSpaceForSpecificPallets.add(String.valueOf((int) spaceForEuroPallets));
        remainingSpaceForSpecificPallets.add(String.valueOf((int) setTo0IfPalletsNumberIsNegative(spaceForJ7Pallets)));
        remainingSpaceForSpecificPallets.add(String.valueOf((int) setTo0IfPalletsNumberIsNegative(spaceForHg5Pallets)));
        remainingSpaceForSpecificPallets.add(String.valueOf((int) setTo0IfPalletsNumberIsNegative(spaceForGm6Pallets)));
        return remainingSpaceForSpecificPallets;
    }

//    private double calculateHowManyMoreEuroPallets(double leftSpace) {
//
//        double spaceForEuroPallets = leftSpace / euroWidthInMm;
//        if (spaceForEuroPallets % 2 != 0) {
//            spaceForEuroPallets = Math.floor(spaceForEuroPallets) + euroPalletsInRowByLength;
//            leftSpaceForEuro -= euroLengthInMm;
//            leftSpaceForEuro /= euroWidthInMm;
//            spaceForEuroPallets = leftSpaceForEuro + euroPalletsInRowByLength;
//        }
//        return spaceForEuroPallets;
//    }

    private double setTo0IfPalletsNumberIsNegative(double space) {
        if (space < 0) {
            space = 0;
        }
        return space;
    }
    private double calculateLeftFreeSpace(double euro, double j7, double hg5, double gm6) {
        double leftFreeSpace = spaceOnTruckInMmExceptEuro;
        if (euro > 0) {
            leftFreeSpace -= calculateSpaceOccupiedByEuroPallets(euro);
        }
        if (j7 > 0) {
            leftFreeSpace -= calculateSpaceOccupiedByJ7Pallets(j7);

        }
        if (hg5 > 0) {
            leftFreeSpace -= calculateSpaceOccupiedByHg5Pallets(hg5);

        }
        if (gm6 > 0) {
            leftFreeSpace -= calculateSpaceOccupiedByGm6Pallets(gm6);

        }
        return leftFreeSpace;
    }

    private double calculateSpaceOccupiedByGm6Pallets(double gm6Qty) {
        double spaceOccupiedByGm6Pallets;
        gm6Qty /= rowSpaceForPallets;
        spaceOccupiedByGm6Pallets = gm6Qty * gm6LengthInMm;
        return spaceOccupiedByGm6Pallets;
    }

    private double calculateSpaceOccupiedByHg5Pallets(double hg5Qty) {
        double spaceOccupiedByHg5Pallets;
        hg5Qty /= rowSpaceForPallets;
        spaceOccupiedByHg5Pallets = hg5Qty * hg5LengthInMm;
        return spaceOccupiedByHg5Pallets;
    }

    private double calculateSpaceOccupiedByJ7Pallets(double j7Qty) {
        double spaceOccupiedByJ7Pallets;
        j7Qty /= rowSpaceForPallets;
        spaceOccupiedByJ7Pallets = j7Qty * j7LengthInMm;
        return spaceOccupiedByJ7Pallets;
    }

    private double calculateSpaceOccupiedByEuroPallets(double euroQty) {
        double spaceOccupiedByEuroPallets;
        if (euroQty % 2 == 0) {
            euroQty /= rowSpaceForPallets;
            spaceOccupiedByEuroPallets = euroQty * euroWidthInMm;
        } else {
            euroQty -= euroPalletsInRowByLength;
            euroQty /= rowSpaceForPallets;
            spaceOccupiedByEuroPallets = euroQty * euroWidthInMm + euroLengthInMm;
        }
        return spaceOccupiedByEuroPallets;
    }
}
